package top.yulegou.zeus.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抓取内容返回结果
 */
@Data
public class ContentCollectedDTO {
    /**
     * 抓取到的内容结果
     */
    private Map<String, String> fieldsRst;
    /**
     * 抓取内容页面
     */
    private String url;
    private Integer taskId;
    private boolean success = false;

    public ContentCollectedDTO() {
        fieldsRst = new HashMap<>();
    }
    public void addFieldResult(String fieldName, String collected) {
        fieldsRst.put(fieldName, collected);
    }
}
