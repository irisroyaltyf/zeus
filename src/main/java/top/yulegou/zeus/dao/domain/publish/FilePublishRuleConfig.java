package top.yulegou.zeus.dao.domain.publish;/*
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
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author irisroyalty
 * @date 2020/6/24
 **/
@Data
@EqualsAndHashCode(callSuper=true)
public class FilePublishRuleConfig extends ZBasePublishRuleConfig {
    /**
     * 文件目录
     */
    String fileLocation;
    /**
     * 文件类型
     * txt
     * xls excel2003
     * xlsx excel2007
     */
    String fileType;
    List<String> excludeList;
}
