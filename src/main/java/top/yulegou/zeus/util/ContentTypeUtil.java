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

import org.apache.commons.lang3.StringUtils;

/**
 * @author irisroyalty
 * @date 2020/7/11
 **/
public class ContentTypeUtil {
    public static String suffixByContentType(String contentType) {
        String suffix = ".jpg";
        if (StringUtils.endsWithIgnoreCase(contentType, "image/gif")) {
            suffix = ".gif";
        } else if(StringUtils.endsWithIgnoreCase(contentType, "image/jpg")
                || StringUtils.endsWithIgnoreCase(contentType, "image/jpeg")) {
            suffix = ".jpg";
        } else if(StringUtils.endsWithIgnoreCase(contentType, "image/png")) {
            suffix = ".png";
        } else if(StringUtils.endsWithIgnoreCase(contentType, "image/bmp")) {
            suffix = ".bmp";
        }
        return suffix;
    }
}
