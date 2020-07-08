package top.yulegou.zeus.domain;/*
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
import lombok.Data;

/**
 * publish 结果对象
 * @author irisroyalty
 * @date 2020/6/27
 **/
@Data
public class PublishResult {
    boolean success = false;
    JSONObject rstMsg;
    public static PublishResult failed() {
        PublishResult rst = new PublishResult();
        rst.success = false;
        return rst;
    }

    public static PublishResult success() {
        PublishResult rst = new PublishResult();
        rst.success = true;
        return rst;
    }

    public static PublishResult failed(String msg) {
        PublishResult rst = new PublishResult();
        rst.success = false;
        rst.rstMsg= new JSONObject();
        rst.rstMsg.put("msg", msg);
        return rst;
    }

    public static PublishResult successWithMsg(String msg) {
        PublishResult rst = new PublishResult();
        rst.success = true;
        rst.rstMsg = new JSONObject();
        rst.rstMsg.put("msg", msg);
        return rst;
    }
}
