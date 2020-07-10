package top.yulegou.zeus.crawler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yulegou.zeus.dao.domain.ZCrawlerUrlConfig;
import top.yulegou.zeus.dao.domain.ZTaskConfig;
import top.yulegou.zeus.manager.ZeusConfigManager;
import top.yulegou.zeus.manager.http.HttpExecutorManager;
import top.yulegou.zeus.util.PregUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 列表页面抓取
 */
@Slf4j
@Component
public class SourceUrlCrawler extends BaseCrawler {
    @Autowired
    private HttpExecutorManager httpExecutorManager;

    /**
     * 获取url页面 匹配urlConfig的urls
     * @param url
     * @param taskConfig
     * @param urlConfig
     * @return
     */
    public List<String> getUrls(String url, ZTaskConfig taskConfig, ZCrawlerUrlConfig urlConfig) {
        if (urlConfig == null) {
            return new ArrayList<>();
        }
        Map<String, String> headers = new HashMap<>();
        if (StringUtils.isNotBlank(taskConfig.getUserAgent())) {
            headers.put(ZeusConfigManager.USER_AGENT, taskConfig.getUserAgent());
        }
        if (StringUtils.isNotBlank(taskConfig.getRefer())) {
            headers.put(ZeusConfigManager.REFER, taskConfig.getRefer());
        }
        String html = httpExecutorManager.doGet(url, headers);
        if (StringUtils.isEmpty(html)) {
            log.error("html is null");
            return new ArrayList<>();
        }
        List<String> areas ;
        // 从选定区域获取内容 默认为整个页面
        if (StringUtils.isNotBlank(urlConfig.getContentArea())) {
            //TODO 如果config 没有更新的话是可以重复使用放在cache中
            String pattern = PregUtil.pregConvertMatch(urlConfig.getContentArea());
            System.out.println(pattern);
            // TODO需要得到的捕获组 而不是所有内容
            areas = PregUtil.pregWithPattern(html, pattern);
            if (areas.isEmpty()) {
                log.error("未提取到区域");
            }
        } else {
            areas = new ArrayList<>();
            areas.add(html);
        }
        List<String> rstUrls = new ArrayList<>();
        areas.stream().forEach((area) -> {
            rstUrls.addAll(getContentUrls(area, urlConfig));
        });
        return rstUrls;
    }

    /**
     * 获取area中 urlconfig匹配内容的url
     * @param areas
     * @param urlConfig
     * @return
     */
    public List<String> getContentUrls(String areas, ZCrawlerUrlConfig urlConfig) {
        // 如果没有配置则匹配所有的url
        String urlPattern;
        if (urlConfig == null
                || StringUtils.isEmpty(urlConfig.getContentUrlRule())) {
            urlPattern = PregUtil.HREF_PATTEN;
        } else {
            urlPattern = PregUtil.pregConvertMatch(urlConfig.getContentUrlRule());
        }
        String rstTemplate = urlConfig.getFinalMergeRule();
        // 1. 通过正则匹配找到最终拼接url的所有的[内容i] 拿到i就可以了
        if ( StringUtils.isEmpty(rstTemplate)) {
            rstTemplate = "[内容]";
        }
        // 2. 然后获取find所有的 matchi 将最终url里面的[内容i] 替换成 得到的matchi
        return pregAndMatch(areas, urlPattern, rstTemplate);
    }

    public Set<String> checkAndExpendFromUrls(List<String> urls) {
        final String regex = "\\{param:(?<type>\\w+)\\,(?<param>[^\\}]*)\\}";
        Set<String> rst = new LinkedHashSet<>();
        urls.stream().forEach(url -> {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(url);
            if (m.find()) {
                if (StringUtils.equals(m.group("type"), "num")) {
                    String param = m.group("param");
                    String[] params = StringUtils.split(param, "\t", 4);
                    int start = Integer.parseInt(params[0]);
                    int end = Integer.parseInt(params[1]);
                    int step = Integer.parseInt(params[2]);
                    int desc = Integer.parseInt(params[3]);
                    end = Math.max(start, end);
                    step = Math.max(1, step);
                    if (desc == 1) {
                        int tmp = start;
                        start = end;
                        end = tmp;
                    }
                    int i = start;
                    do {
                        rst.add(RegExUtils.replacePattern(url, regex, String.valueOf(i)));
                        if (desc == 1) {
                            i -= step;
                            if (i < end) {
                                break;
                            }
                        } else {
                            i += step;
                            if (i > end) {
                                break;
                            }
                        }
                    } while(true);
                } else if (StringUtils.equals(m.group("type"), "letter")) {
                    String param = m.group("param");
                    String[] params = param.split("\t", 3);
                    String start = (params[0]);
                    String end = (params[1]);
                    int desc = Integer.parseInt(params[2]);
                    char i = 0, s = 0, e = 0;
                    if (StringUtils.isNotEmpty(start)) {
                        s = start.charAt(0);
                    }
                    if (StringUtils.isNotEmpty(end)) {
                        e = end.charAt(0);
                    }
                    if (desc == 1) {
                        char tmp = s;
                        s = e;
                        e = tmp;
                    }
                    i = s;
                    do{
                        if (i == 0) {
                            rst.add(RegExUtils.replacePattern(url, regex, ""));
                            break;
                        } else {
                            rst.add(RegExUtils.replacePattern(url, regex, String.valueOf(i)));
                        }
                        if (desc == 1) {
                            i --;
                            if (i < e) {
                                break;
                            }
                        } else {
                            i ++;
                            if (i > e) {
                                break;
                            }
                        }
                    } while (true);
                }
            }
        });
        return rst;
    }

}
