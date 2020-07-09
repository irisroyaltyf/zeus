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
import top.yulegou.zeus.dao.domain.ZTask;
import top.yulegou.zeus.dao.domain.ZTaskCollected;
import top.yulegou.zeus.dao.domain.ZTaskCollectedExample;
import top.yulegou.zeus.dao.domain.ZTaskExample;
import top.yulegou.zeus.dao.mapper.ZTaskCollectedMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author irisroyalty
 * @date 2020/7/9
 **/
@Component
@Slf4j
public class ZeusCollectedManager {
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

    public List<ZTaskCollected> selectCollected(Integer start, Integer limit) {
        ZTaskCollectedExample example = new ZTaskCollectedExample();
        example.setLimit(limit);
        example.setOffset(start);
        return zTaskCollectedMapper.selectByExample(example);
    }
    public List<ZTaskCollected> selectCollected(ZTaskCollectedExample example) {
        return zTaskCollectedMapper.selectByExample(example);
    }

    public List<ZTaskCollected> selectCollected() {
        ZTaskCollectedExample example = new ZTaskCollectedExample();
        return zTaskCollectedMapper.selectByExample(example);
    }

    public long countCollected() {
        ZTaskCollectedExample example = new ZTaskCollectedExample();
        return zTaskCollectedMapper.countByExample(example);
    }

    public int deleteByIds(ArrayList<Integer> selectedIds) {
        ZTaskCollectedExample example = new ZTaskCollectedExample();
        ZTaskCollectedExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(selectedIds);
        return zTaskCollectedMapper.deleteByExample(example);
    }
}
