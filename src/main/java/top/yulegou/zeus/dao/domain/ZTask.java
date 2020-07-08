package top.yulegou.zeus.dao.domain;

import com.alibaba.fastjson.JSONObject;

public class ZTask {
    private Integer id;

    /**
     * 任务名称
     */
    private String tName;

    /**
     * 分组
     */
    private Integer groupId = 0;

    private String tModule;

    /**
     * 自动采集
     */
    private Integer tAuto;

    /**
     * 最后次采集时间
     */
    private Long lastCaiji;

    private Long gmtCreate;

    private Long gmtModified;

    private String cron;
    /**
     * 0. 未开启
     * 1. 抓取中
     * 2. 抓取结束
     */
    private Integer status;

    private String tConfig;

    public long getNextTime() {
        return nextTime;
    }

    public void setNextTime(long nextTime) {
        this.nextTime = nextTime;
    }

    /**
     * 显示用 下次执行时间
     */
    private long nextTime;

    public ZTaskConfig getzTaskConfig() {
        if (zTaskConfig == null && tConfig != null) {
            zTaskConfig = JSONObject.parseObject(tConfig, ZTaskConfig.class);
        }
        if (zTaskConfig == null) {
            zTaskConfig = new ZTaskConfig();
        }
        return zTaskConfig;
    }
    public void setzTaskConfig(ZTaskConfig config) {
        zTaskConfig = config;
        if (zTaskConfig == null) {
            settConfig("");
        } else {
            settConfig(JSONObject.toJSONString(zTaskConfig));
        }
    }

    private ZTaskConfig zTaskConfig;

    public ZTask(Integer id, String tName, Integer groupId, String tModule, Integer tAuto, Long lastCaiji, Long gmtCreate, Long gmtModified, String cron, Integer status, String tConfig) {
        this.id = id;
        this.tName = tName;
        this.groupId = groupId;
        this.tModule = tModule;
        this.tAuto = tAuto;
        this.lastCaiji = lastCaiji;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.cron = cron;
        this.status = status;
        this.tConfig = tConfig;
    }

    public ZTask() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String gettName() {
        return tName;
    }

    public String getJobName() {
        return "task_" + this.id;
    }

    public void settName(String tName) {
        this.tName = tName == null ? null : tName.trim();
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String gettModule() {
        return tModule;
    }

    public void settModule(String tModule) {
        this.tModule = tModule == null ? null : tModule.trim();
    }

    public Integer gettAuto() {
        return tAuto;
    }

    public void settAuto(Integer tAuto) {
        this.tAuto = tAuto;
    }

    public Long getLastCaiji() {
        return lastCaiji;
    }

    public void setLastCaiji(Long lastCaiji) {
        this.lastCaiji = lastCaiji;
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

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron == null ? null : cron.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String gettConfig() {
        return tConfig;
    }

    public void settConfig(String tConfig) {
        this.tConfig = tConfig == null ? null : tConfig.trim();
    }
}