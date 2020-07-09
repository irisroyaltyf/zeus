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
import top.yulegou.zeus.dao.domain.publish.DbConnectionConfig;
import top.yulegou.zeus.domain.DbColumnsDTO;
import top.yulegou.zeus.manager.db.MysqlManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author irisroyalty
 * @date 2020/6/28
 **/
@SpringBootTest
public class MysqlManagerTest {
    @Autowired
    private MysqlManager mysqlManager;

    @Test
    public void getColumnTest() {
        DbConnectionConfig connectionConfig = new DbConnectionConfig();
        connectionConfig.setSchema("utf8");
        connectionConfig.setHost("localhost");
        connectionConfig.setSchema("meimeng");
        connectionConfig.setPort("3306");
        connectionConfig.setUser("debian-sys-maint");
        connectionConfig.setPwd("OWHUxKOQNitQhnRb");
        try {
            List<DbColumnsDTO> rst =  mysqlManager.getColumnNames(connectionConfig, "shop_bifeng_news");
            rst.stream().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void insertTest() {
        DbConnectionConfig connectionConfig = new DbConnectionConfig();
        connectionConfig.setSchema("utf8");
        connectionConfig.setHost("localhost");
        connectionConfig.setSchema("test");
        connectionConfig.setPort("3306");
        connectionConfig.setUser("debian-sys-maint");
        connectionConfig.setPwd("OWHUxKOQNitQhnRb");
        Map<String, String> params = new HashMap<>();
        params.put("name", "test1");
        params.put("empid", "100");
        try {
            JSONObject rst = mysqlManager.insertRow(connectionConfig, "honer", params);
            System.out.println("rst: " + rst);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
