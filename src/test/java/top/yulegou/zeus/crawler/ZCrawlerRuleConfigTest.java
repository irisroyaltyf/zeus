package top.yulegou.zeus.crawler;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.yulegou.zeus.dao.domain.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class ZCrawlerRuleConfigTest {
    @Test
    public void configSerializeTest() {
        ZCrawlerRuleConfig zCrawlerRuleConfig = new ZCrawlerRuleConfig();
        ZCrawlerUrlConfig urlConfig = new ZCrawlerUrlConfig();
        ZCrawlerContentConfig contentConfig = new ZCrawlerContentConfig();
        zCrawlerRuleConfig.setZCrawlerUrlConfig(urlConfig);
        zCrawlerRuleConfig.setZCrawlerContentConfig(contentConfig);
        urlConfig.setContentArea("<div class=\"container post-listing\"(*)>[内容]<!-- end content area -->");
        urlConfig.setContentUrlRule("<a href=\"[内容1]\" class=\"permalink\">(*)</a>");
        urlConfig.setFinalMergeRule("https://www.techsir.com[内容1]");
        List<ZCrawlerFieldConfig> fieldConfigList = new ArrayList<>();
        for (int i = 0; i< 3; ++i){
            ZCrawlerFieldConfig fieldConfig = new ZCrawlerFieldConfig();
            fieldConfig.setName("title");
            fieldConfig.setBelongPage("next");
            fieldConfig.setRule("<a href=\"[内容1]\" class=\"permalink\">(*)</a>");
            fieldConfigList.add(fieldConfig);
        }
        contentConfig.setFieldConfigList(fieldConfigList);
        String x = JSONObject.toJSONString(zCrawlerRuleConfig);
        System.out.println(x);
        ZCrawlerRuleConfig ruleConfig = JSONObject.parseObject(x, ZCrawlerRuleConfig.class);
        System.out.println(ruleConfig.getZCrawlerUrlConfig().getContentUrlRule());
    }
}
