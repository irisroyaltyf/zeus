package top.yulegou.zeus.constant;

public enum  ErrorCode {
    REQUEST_PARAM_ERROR(1001, "参数错误"),
    DB_ERROR_FAILED(2001, "数据库插入错误"),
    DB_ERROR_DUPLICATE(2002, "数据库字段重复"),
    DB_ERROR_CONNECTION(2003, "数据库链接错误"),
    DB_ERROR_UPDATE_ERROR(2004, "数据库更新错误"),
    PUBLISH_RULE_ERROR_NULL(3001, "发布配置未正确配置"),
    PUBLISH_RULE_ERROR_DB_CONFIG_NULL(3002, "数据库配置未正确配置"),
    PUBLISH_RULE_ERROR_DB_CONFIG_STATE(3003, "发布配置状态不正确"),
    OTHER_ERROR_CRON_INVALID(7001, "cron表达式错误"),
    OTHER_ERROR_CRON_EMPTY(7002, "cron表达式为空"),
    SYSTEM_ERROR(4004, "系统错误")
    ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private int code;
    private String desc;

    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ErrorCode{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
