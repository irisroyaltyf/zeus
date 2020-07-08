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

import top.yulegou.zeus.dao.domain.publish.ZBasePublishRuleConfig;

/**
 * @author irisroyalty
 * @date 2020/6/26
 **/
public class PublishCreator {
    public static BasePublishExecutor create(ZBasePublishRuleConfig ruleConfig) {
        if (ruleConfig == null || ruleConfig.getType() == null) {
            return  FilePublishExecutor.getPublish();
        } else {
            switch (ruleConfig.getType()) {
                case 1:
                    return FilePublishExecutor.getPublish();
                case 2:
                    return DbPublishExecutor.getPublish();
                default:
                    return EmptyPublishExecutor.getPublish();
            }
        }
    }
}
