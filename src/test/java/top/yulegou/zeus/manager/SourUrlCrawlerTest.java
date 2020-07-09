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

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yulegou.zeus.crawler.SourceUrlCrawler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author irisroyalty
 * @date 2020/7/8
 **/
@SpringBootTest
public class SourUrlCrawlerTest {
    @Autowired
    private SourceUrlCrawler sourceUrlCrawler;

    @Test
    public void checkAndExpendFromUrlsTest() {
        List<String> urls = new ArrayList<>();
        urls.add("https://www.techsir.com/reviews/index_{param:num,1\t10\t1\t0}.html");
        Set<String> rst = sourceUrlCrawler.checkAndExpendFromUrls(urls);
        System.out.println(rst);
    }
}
