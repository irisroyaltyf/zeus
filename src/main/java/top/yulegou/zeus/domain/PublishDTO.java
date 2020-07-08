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

import java.util.List;
import java.util.Map;

/**
 * 发布配置
 * @author irisroyalty
 * @date 2020/6/24
 **/
@Data
public class PublishDTO {

    private Integer taskId;
    private Integer publishId;

    private Integer publishType;
    // 以下内容是发布到文件的配置
    private String fileLocation;
    private String fileType;
    private List<String> excludeField;
    //!-- 文件配置结束
    //以下内容是发布到数据库配置
    private Integer dbStep;
    private Integer dbType;
    private String host;
    private String port;
    private String user;
    private String pwd;
    private String schema;
    private String charset;
    //!-- 数据库配置结束

}
