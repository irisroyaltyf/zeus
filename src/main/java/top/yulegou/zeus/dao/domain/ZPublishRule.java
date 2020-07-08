package top.yulegou.zeus.dao.domain;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import top.yulegou.zeus.dao.domain.publish.ZBasePublishRuleConfig;
import top.yulegou.zeus.util.PublishRuleConfigFactory;

public class ZPublishRule {
    private Integer id;

    private String pName;

    /**
     * 发布渠道 在config里面有冗余
     */
    private Integer type; // 1 文件 2,

    private Long gmtCreate;

    private Long gmtModified;

    private Integer taskId;

    private String config;

    private ZBasePublishRuleConfig ruleConfig;

    public ZBasePublishRuleConfig getRuleConfig() {
        if (ruleConfig == null && StringUtils.isNotEmpty(config)) {
            ruleConfig = PublishRuleConfigFactory.getPublishRuleConfig(config, type);
        }
        return  ruleConfig;
    }

    public void setRuleConfig(ZBasePublishRuleConfig config) {
        ruleConfig = config;
        setType(ruleConfig.getType());
        if (ruleConfig != null) {
            this.setConfig(JSONObject.toJSONString(ruleConfig));
        } else {
            this.setConfig("");
        }
    }

    public ZPublishRule(Integer id, String pName, Integer type, Long gmtCreate, Long gmtModified, Integer taskId, String config) {
        this.id = id;
        this.pName = pName;
        this.type = type;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.taskId = taskId;
        this.config = config;
    }

    public ZPublishRule() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName == null ? null : pName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config == null ? null : config.trim();
    }
}