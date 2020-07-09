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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;
import top.yulegou.zeus.dao.domain.ZTaskCollected;

import java.util.Date;

/**
 * @author irisroyalty
 * @date 2020/7/9
 **/
@SpringBootTest
public class ZeusCollectedManagerTest {
    @Autowired
    ZeusCollectedManager zeusCollectedManager;
    @Test
    public void insertTest() {
        ZTaskCollected cl = new ZTaskCollected();
        cl.setGmtCreate(new Date().getTime());
        cl.setUrl("www.baidu.com");
        cl.setUrlMd5(DigestUtils.md5DigestAsHex(cl.getUrl().getBytes()));
        cl.setPublishType("db");
        cl.setTaskId(1);
        cl.setDes("1111");
        cl.setStatus(1);
        ZTaskCollected o = JSONObject.parseObject("{\"des\":\"mysql:meimeng@table:shop_bifeng_news@generatedKey:21305\",\"gmtCreate\":1594278922262,\"publishType\":\"DB\",\"status\":1,\"taskId\":2,\"url\":\"https://www.techsir.com/a/202007/65112.html\",\"urlMd5\":\"ffb34fd75d33a635188d8b1f07549f15\"}", ZTaskCollected.class);
//        {"desc":"mysql:meimeng@table:shop_bifeng_news@generatedKey:21305","gmtCreate":1594278922262,"publishType":"DB","status":1,"taskId":2,"url":"https://www.techsir.com/a/202007/65112.html","urlMd5":"ffb34fd75d33a635188d8b1f07549f15"}
        System.out.println( zeusCollectedManager.insert(o));
    }
}
