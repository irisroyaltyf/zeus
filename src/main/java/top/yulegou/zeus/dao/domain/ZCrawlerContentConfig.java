package top.yulegou.zeus.dao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ZCrawlerContentConfig implements Serializable {
    /**
     * 页面字段配置
     */
    List<ZCrawlerFieldConfig> fieldConfigList;

    /**
     * 内容页面获取是否post
     */
    private boolean isPost = false;
}
