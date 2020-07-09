package top.yulegou.zeus.dao.domain;

public class ZTaskCollected {
    private Integer id;

    private String url;

    private String urlMd5;

    private String publishType;

    private Integer taskId;

    private String target;

    private String des;

    /**
     * 1成功2 失败
     */
    private Integer status;

    private Long gmtCreate;

    private String titleMd5;

    public ZTaskCollected(Integer id, String url, String urlMd5, String publishType, Integer taskId, String target, String des, Integer status, Long gmtCreate, String titleMd5) {
        this.id = id;
        this.url = url;
        this.urlMd5 = urlMd5;
        this.publishType = publishType;
        this.taskId = taskId;
        this.target = target;
        this.des = des;
        this.status = status;
        this.gmtCreate = gmtCreate;
        this.titleMd5 = titleMd5;
    }

    public ZTaskCollected() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getUrlMd5() {
        return urlMd5;
    }

    public void setUrlMd5(String urlMd5) {
        this.urlMd5 = urlMd5 == null ? null : urlMd5.trim();
    }

    public String getPublishType() {
        return publishType;
    }

    public void setPublishType(String publishType) {
        this.publishType = publishType == null ? null : publishType.trim();
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getTitleMd5() {
        return titleMd5;
    }

    public void setTitleMd5(String titleMd5) {
        this.titleMd5 = titleMd5 == null ? null : titleMd5.trim();
    }
}