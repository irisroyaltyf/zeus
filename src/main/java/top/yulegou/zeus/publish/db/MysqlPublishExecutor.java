package top.yulegou.zeus.publish.db;/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import top.yulegou.zeus.constant.Constants;
import top.yulegou.zeus.dao.domain.ZPublishRule;
import top.yulegou.zeus.dao.domain.ZTask;
import top.yulegou.zeus.dao.domain.publish.DbColumnBindConfig;
import top.yulegou.zeus.dao.domain.publish.DbConnectionConfig;
import top.yulegou.zeus.dao.domain.publish.DbPublishRuleConfig;
import top.yulegou.zeus.dao.domain.publish.DbTableConfig;
import top.yulegou.zeus.domain.ContentCollectedDTO;
import top.yulegou.zeus.domain.PublishResult;
import top.yulegou.zeus.manager.ZeusConfigManager;
import top.yulegou.zeus.manager.db.MysqlManager;
import top.yulegou.zeus.manager.http.HttpExecutorManager;
import top.yulegou.zeus.util.ZeusBeanUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author irisroyalty
 * @date 2020/7/4
 **/
@Slf4j
public class MysqlPublishExecutor {
    @Autowired
    private MysqlManager mysqlManager;
    @Autowired
    private HttpExecutorManager httpExecutorManager;

    private static MysqlPublishExecutor mysqlPublishExecutor = new MysqlPublishExecutor();
    public static MysqlPublishExecutor getPublish() {
        if (mysqlPublishExecutor.mysqlManager == null) {
            mysqlPublishExecutor.mysqlManager = ZeusBeanUtil.getBean(MysqlManager.class);
        }
        if (mysqlPublishExecutor.httpExecutorManager == null) {
            mysqlPublishExecutor.httpExecutorManager = ZeusBeanUtil.getBean(HttpExecutorManager.class);
        }
        return mysqlPublishExecutor;
    }
    private MysqlPublishExecutor() {}
    public PublishResult publish(ContentCollectedDTO content, ZTask zTask,
                                 ZPublishRule publishRule, DbConnectionConfig connectionConfig) {
        DbPublishRuleConfig ruleConfig = (DbPublishRuleConfig) publishRule.getRuleConfig();
        DbTableConfig tableConfig = ruleConfig.getTableConfig();
        if (tableConfig == null
                || tableConfig.getTableFields() == null
                || tableConfig.getTableFields().isEmpty()) {
            return PublishResult.success();
        }
        StringBuilder sb = new StringBuilder();
        boolean err = false;

        Integer transfer = ZeusConfigManager.getConfigDetail(Constants.ZCONFIG_IMAGE_CONFIG, Constants.ZCONFIG_IMAGE_TRANSFER);
        String ImageDir = ZeusConfigManager.getConfigDetail(Constants.ZCONFIG_IMAGE_CONFIG, Constants.ZCONFIG_IMAGE_DIR);
        for (DbColumnBindConfig tableField : tableConfig.getTableFields()) {
            tableField.getTableName();
            if (tableField.getFieldBind() == null
                    || tableField.getFieldBind().isEmpty()) {
                continue;
            }

            Map<String, String> insertKeyValue = new HashMap<>();
            tableField.getFieldBind().forEach((x, y)-> {
                String realKey = null;
                String fieldContent = null;
                if (StringUtils.startsWith(y, "field:")) {
                    realKey = StringUtils.substring(y, 6);
                    fieldContent = content.getFieldsRst().get(realKey);
                    if (transfer != null && transfer == 1) {
                        for (Iterator<String> it = content.getFieldImages(realKey).iterator();
                             it.hasNext(); ) {
                            String imageUrl = it.next();
                            String newImage = content.getDownload(imageUrl);
                            if (StringUtils.isNotEmpty(newImage)) {
                                fieldContent = RegExUtils.replaceAll(fieldContent, imageUrl, newImage);
                            } else {
                                newImage = httpExecutorManager.download(imageUrl, ImageDir + "/" + zTask.gettName());
                                if (StringUtils.isNotEmpty(newImage)) {
                                    fieldContent = RegExUtils.replaceAll(fieldContent, imageUrl, newImage);
                                    content.addDownLoad(imageUrl, newImage);
                                } else {
                                    log.error("图片下载失败，没有替换 " + imageUrl);
                                }
                            }
                        }
                    }
                } else if (StringUtils.startsWith(y, "custom:")) {
                    fieldContent = StringUtils.substring(y, 7);
                }
               if (StringUtils.isNotEmpty(fieldContent)) {
                   insertKeyValue.put(x, fieldContent);
               }
            });
            try {
                JSONObject obj = mysqlManager.insertRow(connectionConfig, tableField.getTableName(), insertKeyValue);
                if (sb.length() > 0) {
                    sb.append("\n");
                }
                if (obj != null && obj.getInteger("result") > 0) {
                    sb.append("mysql:").append(connectionConfig.getSchema())
                            .append("@table:").append(tableField.getTableName());
                    if (obj.containsKey("generateValue")) {
                        sb.append("@generatedKey:").append(obj.getString("generateValue"));
                    }
                } else {
                    err = true;
                    sb.append("mysql:@table").append(tableField.getTableName()).append("数据库插入失败");
                }
            } catch (SQLException throwables) {
                log.error("mysql publish error " + JSONObject.toJSONString(tableField), throwables);
                err = true;
                sb.append(throwables.getMessage());
            }
        };
        if (err) {
            return PublishResult.failed(sb.toString());
        }
        return PublishResult.successWithMsg(sb.toString());
    }
}
