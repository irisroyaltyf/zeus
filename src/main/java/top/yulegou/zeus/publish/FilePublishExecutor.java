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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import top.yulegou.zeus.constant.Constants;
import top.yulegou.zeus.dao.domain.ZPublishRule;
import top.yulegou.zeus.dao.domain.ZTask;
import top.yulegou.zeus.dao.domain.ZTaskConfig;
import top.yulegou.zeus.dao.domain.publish.FilePublishRuleConfig;
import top.yulegou.zeus.dao.domain.publish.ZBasePublishRuleConfig;
import top.yulegou.zeus.domain.ContentCollectedDTO;
import top.yulegou.zeus.domain.PublishResult;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author irisroyalty
 * @date 2020/6/24
 **/
@Slf4j
public class FilePublishExecutor implements BasePublishExecutor {
    private static FilePublishExecutor filePublishExecutor = new FilePublishExecutor();
    public static FilePublishExecutor getPublish() {
        return filePublishExecutor;
    }
    private FilePublishExecutor() {}

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public int getPublishType() {
        return Constants.PUBLISH_RULE_FILE;
    }

    @Override
    public String getStringPublishType() {
        return "FILE";
    }

    @Override
    public int publish(List<ContentCollectedDTO> fieldList, ZTask task, ZPublishRule publishRule) {
        ZBasePublishRuleConfig ruleConfig = publishRule.getRuleConfig();
        if (ruleConfig == null || !(ruleConfig instanceof FilePublishRuleConfig)) {
            log.error("publish rule is null or error");
            return 0;
        }
        FilePublishRuleConfig fileConfig = (FilePublishRuleConfig) ruleConfig;
        String fileLocation = fileConfig.getFileLocation();
        if (StringUtils.isEmpty(fileLocation)) {
            log.error("文件目录为空");
            return 0;
        }
        fileLocation = fileLocation + task.gettName();
        //TODO 配置的时候确保最后是一个/  先是GROUPname 然后再是taskName
//        if (StringUtils.endsWithIgnoreCase(fileLocation, "/"))
        File dir = new File(fileLocation) ;
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                log.error("创建文件夹错误,请确认目录权限和磁盘空间");
                return 0;
            }
        }
        File f = new File(fileLocation + sdf.format(new Date()) + ".txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(f, true);
            int rstCount = 0;
            for (ContentCollectedDTO collectedDTO : fieldList) {
                if (collectedDTO.getFieldsRst() == null || collectedDTO.getFieldsRst().isEmpty()) {
                    continue;
                }
                for (Iterator<Map.Entry<String, String>> fit = collectedDTO.getFieldsRst().entrySet().iterator();
                     fit.hasNext();) {
                    Map.Entry<String, String> entryField = fit.next();
                    if (StringUtils.isNotEmpty(entryField.getValue())) {
                        fileWriter.write(entryField.getValue());
                        fileWriter.write("\t");
                    }
                    fileWriter.write("\r\n");
                }
                fileWriter.flush();
                rstCount ++;
            }
            return rstCount;
        } catch (Exception e) {
            log.error("write file error ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    @Override
    public PublishResult publish(ContentCollectedDTO collectedDTO, ZTask task, ZPublishRule publishRule) {
        ZBasePublishRuleConfig ruleConfig = publishRule.getRuleConfig();
        if (ruleConfig == null || !(ruleConfig instanceof FilePublishRuleConfig)) {
            log.error("publish rule is null or error");
            return PublishResult.failed();
        }
        FilePublishRuleConfig fileConfig = (FilePublishRuleConfig) ruleConfig;
        String fileLocation = fileConfig.getFileLocation();
        if (StringUtils.isEmpty(fileLocation)) {
            log.error("文件目录为空");
            return PublishResult.failed();
        }
        fileLocation = fileLocation + task.gettName() ;
        //TODO 配置的时候确保最后是一个/  先是GROUPname 然后再是taskName
//        if (StringUtils.endsWithIgnoreCase(fileLocation, "/"))
        File dir = new File(fileLocation) ;
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                log.error("创建文件夹错误,请确认目录权限和磁盘空间");
                return PublishResult.failed("创建文件夹错误,请确认目录权限和磁盘空间");
            }
        }
        File f = new File(fileLocation + "/" + sdf.format(new Date()) + ".txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(f, true);
            if (collectedDTO.getFieldsRst() == null || collectedDTO.getFieldsRst().isEmpty()) {
                return PublishResult.failed();
            }
            for (Iterator<Map.Entry<String, String>> fit = collectedDTO.getFieldsRst().entrySet().iterator();
                 fit.hasNext();) {
                Map.Entry<String, String> entryField = fit.next();
                if (StringUtils.isNotEmpty(entryField.getValue())) {
                    fileWriter.write(entryField.getValue());
                    fileWriter.write("\t");
                }
                fileWriter.write("\r\n");
            }
            fileWriter.flush();
            return PublishResult.successWithMsg(f.getAbsolutePath());
        } catch (Exception e) {
            log.error("write file error ", e);
            return PublishResult.failed(e.getMessage());
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
