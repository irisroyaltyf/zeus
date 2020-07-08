package top.yulegou.zeus.publish;/*
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

import lombok.extern.slf4j.Slf4j;
import top.yulegou.zeus.constant.Constants;
import top.yulegou.zeus.dao.domain.ZPublishRule;
import top.yulegou.zeus.dao.domain.ZTask;
import top.yulegou.zeus.dao.domain.publish.DbConnectionConfig;
import top.yulegou.zeus.dao.domain.publish.DbPublishRuleConfig;
import top.yulegou.zeus.dao.domain.publish.ZBasePublishRuleConfig;
import top.yulegou.zeus.domain.ContentCollectedDTO;
import top.yulegou.zeus.domain.PublishResult;
import top.yulegou.zeus.publish.db.MysqlPublishExecutor;

import java.util.List;

/**
 * @author irisroyalty
 * @date 2020/6/27
 **/
@Slf4j
public class DbPublishExecutor implements BasePublishExecutor {

    private static DbPublishExecutor dbPublishExecutor = new DbPublishExecutor();
    public static DbPublishExecutor getPublish() {
        return dbPublishExecutor;
    }
    private DbPublishExecutor() {}


    @Override
    public int getPublishType() {
        return Constants.PUBLISH_RULE_EMPTY;
    }

    @Override
    public int publish(List<ContentCollectedDTO> fieldList, ZTask zTask, ZPublishRule publishRule) {
        return 0;
    }

    @Override
    public PublishResult publish(ContentCollectedDTO content, ZTask zTask, ZPublishRule publishRule) {
        if (publishRule == null) {
            log.error("publishRule is null");
            return PublishResult.failed();
        }
        ZBasePublishRuleConfig ruleConfig = publishRule.getRuleConfig();
        if (ruleConfig == null || !(ruleConfig instanceof DbPublishRuleConfig)) {
            log.error("publish rule is null or error");
            return PublishResult.failed();
        }
        DbPublishRuleConfig dbRuleConfig = (DbPublishRuleConfig) ruleConfig;
        DbConnectionConfig connectionConfig = dbRuleConfig.getConnectionConfig();
        if (connectionConfig == null) {
            return PublishResult.failed("数据库配置没有正确配置");
        }
        if (connectionConfig.getDbType() == 1) {
            MysqlPublishExecutor publishExecutor = MysqlPublishExecutor.getPublish();
            return publishExecutor.publish(content, zTask, publishRule, connectionConfig);
        }
        return PublishResult.failed("发布数据库配置未能解析");
    }
}
