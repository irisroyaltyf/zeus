package top.yulegou.zeus.dao.domain;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.io.Serializable;

public class ZCrawlerRule implements Serializable {
    private Integer id;

    private String cName;

    private Integer type;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer taskId;

    private String config;
    private ZCrawlerRuleConfig zCrawlerRuleConfig;
    public ZCrawlerRuleConfig getCrawlerRuleConfig() {
        if (zCrawlerRuleConfig == null && StringUtils.isNotBlank(config)) {
            zCrawlerRuleConfig = JSONObject.parseObject(config, ZCrawlerRuleConfig.class);
            if(zCrawlerRuleConfig != null) {
                if (zCrawlerRuleConfig.getZCrawlerUrlConfig() != null) {
//                    zCrawlerRuleConfig.getZCrawlerUrlConfig()
//                            .setContentArea(
//                                    StringEscapeUtils.escapeHtml4(
//                                            zCrawlerRuleConfig.getZCrawlerUrlConfig().getContentArea()));
//                    zCrawlerRuleConfig.getZCrawlerUrlConfig()
//                            .setContentUrlRule(
//                                    StringEscapeUtils.escapeHtml4(
//                                            zCrawlerRuleConfig.getZCrawlerUrlConfig().getContentUrlRule()));
                }
//                if (zCrawlerRuleConfig.getZCrawlerContentConfig() != null) {
//                    zCrawlerRuleConfig.getZCrawlerContentConfig()
//                }
            }
        }
        return zCrawlerRuleConfig;
    }

    public ZCrawlerRule(Integer id, String cName, Integer type, Long gmtCreate, Long gmtModified, Integer taskId, String config) {
        this.id = id;
        this.cName = cName;
        this.type = type;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.taskId = taskId;
        this.config = config;
    }

    public ZCrawlerRule() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName == null ? null : cName.trim();
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

    public void setzCrawlerRuleConfig(ZCrawlerRuleConfig ruleConfig) {
        this.zCrawlerRuleConfig = ruleConfig;
        if (ruleConfig != null) {
            this.setConfig(JSONObject.toJSONString(ruleConfig));
        } else {
            this.setConfig("");
        }
    }
}