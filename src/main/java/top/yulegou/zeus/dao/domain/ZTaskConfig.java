package top.yulegou.zeus.dao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class ZTaskConfig implements Serializable {
    private String userAgent;
    private String refer;
    private Integer taskId;
    /**
     * TODO 待开发
     */
    private String cookie;
    private Map<String, String> customize;

}
