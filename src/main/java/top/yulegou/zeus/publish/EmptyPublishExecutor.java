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

import top.yulegou.zeus.constant.Constants;
import top.yulegou.zeus.dao.domain.ZPublishRule;
import top.yulegou.zeus.dao.domain.ZTask;
import top.yulegou.zeus.domain.ContentCollectedDTO;
import top.yulegou.zeus.domain.PublishResult;

import java.util.List;

/**
 * @author irisroyalty
 * @date 2020/6/26
 **/
public class EmptyPublishExecutor implements BasePublishExecutor {

    private static EmptyPublishExecutor emptyPublishExecutor = new EmptyPublishExecutor();
    public static EmptyPublishExecutor getPublish() {
        return emptyPublishExecutor;
    }
    private EmptyPublishExecutor() {}

    @Override
    public int getPublishType() {
        return Constants.PUBLISH_RULE_EMPTY;
    }

    @Override
    public String getStringPublishType() {
        return "NULL";
    }

    @Override
    public int publish(List<ContentCollectedDTO> fieldList, ZTask zTask, ZPublishRule publishRule) {
        return 0;
    }

    @Override
    public PublishResult publish(ContentCollectedDTO content, ZTask zTask, ZPublishRule publishRule) {
        return PublishResult.failed("未发布任何内容");
    }
}
