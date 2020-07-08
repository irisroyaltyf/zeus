package top.yulegou.zeus.domain;

import lombok.Data;
@Data
public class CrawlerUrlDTO {
    Integer taskId;
    Integer crawlerId;
    String urlArea;
    String contentUrlRule;
    String finalMergeUrl;
}
