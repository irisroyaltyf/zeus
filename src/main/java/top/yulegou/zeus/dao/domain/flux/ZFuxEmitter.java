package top.yulegou.zeus.dao.domain.flux;/*
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
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.FluxSink;
import top.yulegou.zeus.constant.Constants;
import top.yulegou.zeus.dao.domain.ZPublishRule;
import top.yulegou.zeus.dao.domain.ZTask;
import top.yulegou.zeus.dao.domain.publish.ZBasePublishRuleConfig;
import top.yulegou.zeus.domain.ContentCollectedDTO;
import top.yulegou.zeus.domain.PublishResult;
import top.yulegou.zeus.manager.ZeusPublishRuleManager;
import top.yulegou.zeus.publish.BasePublishExecutor;
import top.yulegou.zeus.publish.PublishCreator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author irisroyalty
 * @date 2020/6/27
 **/
@Slf4j
public class ZFuxEmitter {
    private List<ContentCollectedDTO> collectedDTOs = Collections.synchronizedList(new ArrayList<>());
    private final FluxSink<String> sink;
    @Setter
    private ZTask task;
    @Setter
    private ZeusPublishRuleManager publishRuleManager;


    public ZFuxEmitter(FluxSink<String> sink, ZTask task, ZeusPublishRuleManager zeusPublishRuleManager){
        this.sink = sink;
        this.task = task;
        this.publishRuleManager = zeusPublishRuleManager;
    }

    public void emit(ContentCollectedDTO contentCollectedDTO) {
        collectedDTOs.add(contentCollectedDTO);
//                    sink.next(s);
    }
    public void complete() {
        int xSuccess = 0, xFailed = 0;
        for (ContentCollectedDTO contentCollectedDTO : collectedDTOs) {
            if (contentCollectedDTO.getFieldsRst() == null) {
                log.error("contentCollectedDTO field is null");
                continue;
            }
            //DO publish
            ZPublishRule publishRule = publishRuleManager.getPublishRuleByTaskId(contentCollectedDTO.getTaskId());
            if (publishRule == null || publishRule.getRuleConfig() == null) {
                //TODO  替换成object
                sink.next("error: 内容发布配置没有配");
                break;
            }
            ZBasePublishRuleConfig publishRuleConfig = publishRule.getRuleConfig();
            BasePublishExecutor executor = PublishCreator.create(publishRuleConfig);
            PublishResult publishResult = executor.publish(contentCollectedDTO, task, publishRule);
            if(publishResult.isSuccess()) {
                xSuccess ++;
                JSONObject object = new JSONObject();
                object.put("type", "publish");
                object.put("publishTpe", executor.getPublishType());
                object.put("rst", 1);
                if (Constants.PUBLISH_RULE_FILE == executor.getPublishType()) {
                    object.put("location", publishResult.getRstMsg().get("msg"));
                } else if(Constants.PUBLISH_RULE_DB == executor.getPublishType()) {

                }
                object.put("url", contentCollectedDTO.getUrl());
                sink.next(object.toJSONString());
            } else {
                xFailed ++;
                JSONObject object = new JSONObject();
                object.put("type", "publish");
                object.put("rst", 0);
                object.put("url", contentCollectedDTO.getUrl());
                sink.next(object.toJSONString());
            }
        }
        JSONObject obj = new JSONObject();
        obj.put("total", collectedDTOs.size());
        obj.put("success", xSuccess);
        obj.put("failed", xFailed);
        obj.put("type", "publishRst");
        sink.next(obj.toJSONString());
        sink.complete();
    }
}
