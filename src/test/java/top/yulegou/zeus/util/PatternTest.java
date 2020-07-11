package top.yulegou.zeus.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.yulegou.zeus.manager.http.HttpExecutorManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class PatternTest {
    @Autowired
    HttpExecutorManager httpExecutorManager;
    @Test
    public void matchTest() {
        Pattern p = Pattern.compile("(\\d+)([￥$])$");
        String str = "8899￥";
        Matcher m = p.matcher(str);
        if(m.find()){
            System.out.println("0 :" + m.group(0));
            System.out.println("货币金额: " + m.group(1));
            System.out.println("货币种类: " + m.group(2));
        }
    }
    @Test
    public void matchTest2 () {
        Pattern p = Pattern.compile("/\\(\\?<(content|match)");
        String src = "<div class=\"container post-listing\"(*)>[内容]<!-- end content area -->";
        Matcher m = p.matcher(src);
        System.out.println(m.find());
        //        , '(?P<match', $str)
    }

    @Test
    public void matchTest3() {
        String src = "<h1 class=\"[内容2]\">[内容1]</h1>";
        System.out.println(        PregUtil.pregConvertMatch(src));
    }

    @Test
    public void matchTest4() {
        String src = httpExecutorManager.doGet("https://www.techsir.com/", null);
        Pattern  p = Pattern.compile(PregUtil.HREF_PATTERN);
        Matcher m = p.matcher(src);
        System.out.println(m.matches());
        while(m.find()) {
            System.out.println(m.group("match"));
        }
    }

    @Test
    public void matchTest5() {
        String src = "[内容]";
        Pattern p = Pattern.compile("\\[内容(?<num>\\d*)\\]");
        Matcher m = p.matcher(src);
        while(m.find()) {
            System.out.println( "match" + m.group("num"));
//            System.out.println(m.group("num"));
        }
//        StringUtils.replace()
    }

    @Test
    public void machTest6() {
        Pattern p = Pattern.compile("\\{param:(?<type>\\w+)\\,(?<param>[^\\}]*)\\}");
        String src = "https://www.techsir.com/reviews/index_{param:num,1\t10\t1\t0}.html";
        Matcher m = p.matcher(src);
        while (m.find()) {
            System.out.println(m.group("type"));
            System.out.println(m.group("param"));
        }
        System.out.println(StringUtils.substring("11", 0, 8));
//        String rst = PregUtil.joinContentMatch("示例：<div id=\"a\">[内容]</div>(*)<div id=\"b\">[内容2]</div>");
//        System.out.println(rst);
    }

    @Test
    public void matchTest7() {
        String src = httpExecutorManager.doGet("https://www.techsir.com/", null);
        System.out.println(PregUtil.matchImgSrcs(src));
    }

}
