package top.yulegou.zeus.util;/*
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
import top.yulegou.zeus.dao.domain.publish.FilePublishRuleConfig;
import top.yulegou.zeus.dao.domain.publish.ZBasePublishRuleConfig;
import top.yulegou.zeus.dao.domain.publish.DbPublishRuleConfig;

/**
 * @author irisroyalty
 * @date 2020/6/24
 **/
public class PublishRuleConfigFactory {
    public static ZBasePublishRuleConfig getPublishRuleConfig(String config, Integer publishType) {
        if (publishType == 1) {
            return JSONObject.parseObject(config, FilePublishRuleConfig.class);
        } else if(publishType == 2) {
            return JSONObject.parseObject(config, DbPublishRuleConfig.class);
        }else {
            return null;
        }
    }
}
