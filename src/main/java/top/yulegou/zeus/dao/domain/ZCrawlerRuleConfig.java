package top.yulegou.zeus.dao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 抓取规则config配置实例类,最终转化为json存储在 Crawler_rule的config字段
 */
@Data
public class ZCrawlerRuleConfig implements Serializable {

    private String userAgent;
    /**
     * 采集规则refer
     */
    private String refer;

    /**
     * TODO 待开发
     */
    private String cookie;
    private Map<String, String> customize;

    /**
     * 起始页面为列表页 false 或者为空则代表页面是列表页面
     */
    private Boolean sourceIsContent;
    /**
     * 起始页面是post请求,为空或者false 则代码为GET请求
     */
    private Boolean post;
    /**
     *
     */
    private List<String> fromUrls;
    /**
     * 抓取内容页url规则
     */
    private ZCrawlerUrlConfig zCrawlerUrlConfig;
    /**
     * 内容页面字段等规则
     */
    private ZCrawlerContentConfig zCrawlerContentConfig;
}
