package top.yulegou.zeus.manager;/*
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yulegou.zeus.dao.domain.ZTaskCollected;
import top.yulegou.zeus.dao.mapper.ZTaskCollectedMapper;

/**
 * @author irisroyalty
 * @date 2020/7/9
 **/
@Component
@Slf4j
public class ZeusTaskCollectedManager {
    @Autowired
    ZTaskCollectedMapper zTaskCollectedMapper;
    public int insert(ZTaskCollected collected) {
        try {
            return zTaskCollectedMapper.insertSelective(collected);
        } catch (Exception e ) {
            log.error("insert collected error " + JSONObject.toJSONString(collected), e);
        }
        return 0;
    }
}
