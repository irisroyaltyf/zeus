package top.yulegou.zeus.crawler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yulegou.zeus.dao.domain.*;
import top.yulegou.zeus.manager.ZeusConfigManager;
import top.yulegou.zeus.util.PregUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * crawler 基类 做一些管理用
 */
@Slf4j
@Component
public class BaseCrawler {
    @Autowired
    private ZeusConfigManager zeusConfigManager;

    /**
     * 做2个事情
     * 1. 根据请求头和配置抓取网页
     * 2. 根据配置相关信息解析相关内容
     * @param task
     */
    public void doCollect(ZTask task)  {
        if (task == null) {
            log.error("docollect task is null");
        }
    }


    public String getHtmlContent(ZTask task, ZCrawlerRule crawlerRule) {
//        ZConfig config =  zeusConfigManager.getCachedConfig("robots");
//        ZConfig auto = zeusConfigManager.getCachedConfig("auto");
        // TODO 看是否是robot允许的地址 通过或者/robots.txt 去看是否robots定义不能爬取
        // TODO 增加缓存 可能有需要
        ZTaskConfig taskConfig = task.getzTaskConfig();
        if (taskConfig != null && StringUtils.isNotBlank(taskConfig.getUserAgent())) {

        }

        //TODO 查看代理ip
        zeusConfigManager.getCachedConfig(ZeusConfigManager.PROXY);
        ZCrawlerRuleConfig crawlerRuleConfig = crawlerRule.getCrawlerRuleConfig();
        // GET 请求
        if (crawlerRuleConfig.getPost() == null || crawlerRuleConfig.getPost() == false) {
//            crawlerRuleConfig.getFromUrls().stream()
//            httpExecutorManager.doGet()
            if (crawlerRuleConfig != null) {
                crawlerRuleConfig.getFromUrls().stream().forEach(url -> {
                    log.info(url);
                    //TODO 先手动填写地址,后续增加规则
//                    List<String> contentUrls =

                });
            }
        }
        return null;
    }

    public static List<String> pregAndMatch(String area, String pattern, String rstTemplate) {
        return pregAndMatch(area, pattern, rstTemplate, false);
    }

    /**
     * 提取area文本中匹配 pattern 的[内容i]并替换到rsttemplate
     * @param area
     * @param pattern  匹配内容
     * @param rstTemplate 结果模板 为空则会把所有的pattern里面的[内容] 拼接起来
     * @param duplicate 是否可以重复,
     * @return
     */
    public static List<String> pregAndMatch(String area, String pattern, String rstTemplate, boolean duplicate) {
        String matchFromFinalPattern = "\\[内容(?<num>\\d*)\\]";
        String matchFromSourcePattern = "\\?\\<match(?<num>\\d*)\\>";
        List<String> matchs = null;
        if (StringUtils.isEmpty(rstTemplate)) {
            matchs = PregUtil.pregWithPattern(pattern, matchFromSourcePattern, "num");
            StringBuilder sb = new StringBuilder();
            for (String m: matchs) {
                sb.append("[内容").append(m).append("]");
            }
            rstTemplate = sb.toString();
        } else {
            matchs = PregUtil.pregWithPattern(rstTemplate, matchFromFinalPattern, "num");
        }
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(area);
        List<String> rstUrls = new ArrayList<>();
        while(m.find()) {
            String resultUrl = rstTemplate;
            for (String group: matchs) {
                resultUrl = StringUtils.replace(resultUrl, "[内容" + group +"]", m.group("match" + group));
            }
            rstUrls.add(resultUrl);
        }
        if (!duplicate) {
            return rstUrls.stream().distinct().collect(Collectors.toList());
        }
        return rstUrls;
    }


    //TODO 暂时只提供 正则匹配的方式, 后续提供css 解析的方式
}
