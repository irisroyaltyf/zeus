package top.yulegou.zeus.domain;
/*
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
/**
 * table 采集配置字段对象
 * @author irisroyalty
 * @date 2020/6/24
 **/
@Data
public class FieldDTO {
    private String fieldName;
    private String belongPage;
    private String fieldRule;
    private String finalMerge;
    private Boolean multi;
}
