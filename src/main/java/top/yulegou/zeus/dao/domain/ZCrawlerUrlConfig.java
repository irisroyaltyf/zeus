package top.yulegou.zeus.dao.domain;

import lombok.Data;

@Data
public class ZCrawlerUrlConfig {
    private String contentArea;
    private String contentUrlRule;
    private String finalMergeRule;
}
