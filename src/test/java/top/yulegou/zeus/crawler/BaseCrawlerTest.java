package top.yulegou.zeus.crawler;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yulegou.zeus.dao.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BaseCrawlerTest {

    @Autowired
    private SourceUrlCrawler sourceUrlCrawler;
    @Autowired
    private ContentCrawler contentCrawler;

    @Test
    public void getUrlTest() {
        ZTaskConfig config = new ZTaskConfig();
        ZCrawlerUrlConfig crawlerUrlConfig = new ZCrawlerUrlConfig();
        crawlerUrlConfig.setContentArea("<div class=\"container post-listing\"(*)>[内容]<!-- end content area -->");
        crawlerUrlConfig.setContentUrlRule("<a href=\"[内容1]\" class=\"permalink\">(*)</a>");
        crawlerUrlConfig.setFinalMergeRule("https://www.techsir.com[内容1]");
        List<String> finalUrls = sourceUrlCrawler.getUrls("https://www.techsir.com/reviews/index_2.html", config, crawlerUrlConfig);
        for (String url : finalUrls) {
            System.out.println(url);
        }
    }
    @Test
    public void getContentTest() {
        ZTask task = new ZTask();
        ZTaskConfig taskConfig = new ZTaskConfig();
        task.setzTaskConfig(taskConfig);
        ZCrawlerContentConfig contentConfig = new ZCrawlerContentConfig();
        List<ZCrawlerFieldConfig> fieldConfigList = new ArrayList<>();
        ZCrawlerFieldConfig fieldConfig = new ZCrawlerFieldConfig();
        fieldConfig.setName("title");
        fieldConfig.setBelongPage("next");
        fieldConfig.setRule("<h1 class=\"title\">[内容1]</h1>");
        fieldConfig.setFinalMerge("[内容1]");
        fieldConfigList.add(fieldConfig);
        contentConfig.setFieldConfigList(fieldConfigList);
        contentCrawler.collectField("https://www.techsir.com/a/202006/64449.html", task, contentConfig);
    }
}