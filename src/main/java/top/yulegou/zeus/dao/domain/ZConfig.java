package top.yulegou.zeus.dao.domain;

import com.alibaba.fastjson.JSONObject;

/**
 * 系统配置
 */
public class ZConfig {
    private Integer id;

    private String cname;

    private Integer ctype;

    private Long gmtCreate;

    private Long gmtModified;

    private String cdata;

    private JSONObject jsonCData;

    public JSONObject getConfigData(){
        if (jsonCData == null) {
            jsonCData = JSONObject.parseObject(cdata);
        }
        return jsonCData;
    }

    public ZConfig(Integer id, String cname, Integer ctype, Long gmtCreate, Long gmtModified, String cdata) {
        this.id = id;
        this.cname = cname;
        this.ctype = ctype;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.cdata = cdata;
    }

    public ZConfig() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    public Integer getCtype() {
        return ctype;
    }

    public void setCtype(Integer ctype) {
        this.ctype = ctype;
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

    public String getCdata() {
        return cdata;
    }

    public void setCdata(String cdata) {
        this.cdata = cdata == null ? null : cdata.trim();
    }
}