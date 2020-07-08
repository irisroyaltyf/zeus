package top.yulegou.zeus.manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yulegou.zeus.dao.domain.ZConfig;

@SpringBootTest
public class ZConfigManagerTest {
    @Autowired
    private ZeusConfigManager zeusConfigManager;
    @Test
    public void configInsertTest() {
        ZConfig config = new ZConfig();
        config.setCname("test");
        config.setCtype(1);
        config.setCdata("testData");
        zeusConfigManager.insertConfig(config);
    }
}
