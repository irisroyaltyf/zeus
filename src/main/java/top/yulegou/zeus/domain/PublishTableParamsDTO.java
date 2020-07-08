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

import lombok.Data;
import top.yulegou.zeus.dao.domain.publish.DbColumnBindConfig;

import java.util.List;
import java.util.Map;

/**
 * 数据绑定表配置
 * @author irisroyalty
 * @date 2020/6/29
 **/
@Data
public class PublishTableParamsDTO {
    Integer taskId;
    Integer publishRuleId;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        tableParams.stream().forEach(x->{sb.append(x);});
        return "PublishTableParamsDTO{" +
                "taskId=" + taskId +
                ", publishRuleId=" + publishRuleId +
                ", tableParams=" + sb.toString() +
                '}';
    }

    List<DbColumnBindConfig> tableParams;
}
