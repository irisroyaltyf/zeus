package top.yulegou.zeus.dao.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 页面内容字段配置
 */
@Data
public class ZCrawlerFieldConfig implements Serializable {

    /**
     * 只在和前端交互的时候使用base64编码的字符串
     */
    private String base64Config;
    /**
     * 字段名
     */
    private String name;
    /**
     * 来源页面
     */
    private String belongPage;

    /**
     * 字段获取方式
     * 1 规则匹配 5 固定文字, 6 随机数字, 7 时间, 8 随机抽取
     */
    private Integer type = 1;

    /**
     * 字段获取的规则
     */
    private String rule;

    /**
     * 最终拼接的url
     */
    private String finalMerge;

    /**
     * 是否可以匹配多个
     */
    private boolean multi = false;

}
