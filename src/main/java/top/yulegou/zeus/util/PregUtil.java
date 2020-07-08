package top.yulegou.zeus.util;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PregUtil {
    public static final String HREF_PATTEN = "\\bhref=[\'\"](?<match>[^\'\"<>]+?)[\'\"]";
    //让配置变成正则pattren
    public static String pregConvertMatchContentOnly(String src) {
        Pattern p = Pattern.compile("(={0,1})(\\s*)([\\\'\\\"]{0,1})\\[内容(?<num>\\d*)\\]\\3");
        Matcher matcher = p.matcher(src);
        StringBuilder sb = new StringBuilder();
        int preEnd = 0;
        while (matcher.find()) {
            MatchResult matchResult = matcher.toMatchResult();
            if (matcher.start() > preEnd) {
                sb.append(src.substring(preEnd, matcher.start()));
            }
            sb.append(matcher.group(1))
                    .append(matcher.group(2))
                    .append(matcher.group(3))
                    .append("(?<match")
                    .append(matcher.group("num"))
                    .append(">");
            if (StringUtils.isNotBlank(matcher.group(1))
                    && StringUtils.isNotBlank(matcher.group(3))) {
                sb.append("[^<>]*?)").append(matcher.group(3));
            } else {
                sb.append("[\\s\\S]*?)");
            }
            preEnd = matchResult.end();
        }
        sb.append(src.substring(preEnd));
        System.out.println(sb.toString());
        return sb.toString();
    }
    public static String pregConvertMatch(String src) {
        String pattern = pregConvertMatchContentOnly(src);
        pattern = RegExUtils.replaceAll(pattern, "\\(\\*\\)", "\\[\\\\s\\\\S\\]*?");
        return pattern;
    }

    /**
     * 在src总获取所有匹配pattern的字符串
     * @param src
     * @param pattern
     * @return
     */
    public static List<String> pregWithPattern(String src, String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m =  p.matcher(src);
        List<String> rst = new ArrayList<>();
        while(m.find()) {
            rst.add(m.group(0));
        }
        return rst;
    }

    /**
     * 在src中获取所有匹配pattern的字符串中 groupName的捕获组
     * @param src
     * @param pattern 正则
     * @param groupName 捕获组名称
     * @return
     */
    public static List<String> pregWithPattern(String src, String pattern, String groupName) {
        Pattern p = Pattern.compile(pattern);
        Matcher m =  p.matcher(src);
        List<String> rst = new ArrayList<>();
        while(m.find()) {
            String x = m.group(groupName);
            if( null != x) {
                rst.add(x);
            }
        }
        return rst;
    }

    /**
     * 获取 src中匹配pattern的字符串将获得到的捕获组通过groupName组装成map对象
     * @param src
     * @param pattern
     * @param groupNames
     * @return
     */
    public static List<Map<String, String>> pregWithPattern(String src, String pattern, List<String> groupNames) {
        Pattern p = Pattern.compile(pattern);
        Matcher m =  p.matcher(src);
        List<Map<String, String>> rst = new ArrayList<>();
        while(m.find()) {
            Map<String, String> rstMap = new HashMap<>();
            for (String gName: groupNames) {
                rstMap.put(gName, m.group(gName));
            }
            rst.add(rstMap);
        }
        return rst;
    }

    /**
     * 将匹配到的内容拼接到一起
     * @param src
     * @param pattern
     * @return
     */
    public static String joinContentMatch(String pattern) {
        String matchPattern = "\\[内容(?<num>\\d*)\\]";
        List<String> matchContent = pregWithPattern(pattern, matchPattern);
        StringBuilder sb = new StringBuilder();
        for (String m: matchContent) {
            sb.append(m);
        }
        return sb.toString();
    }

}
