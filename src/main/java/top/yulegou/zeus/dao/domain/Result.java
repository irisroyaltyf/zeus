package top.yulegou.zeus.dao.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class Result<T> {
    /**
     * 0 success
     * other error code
     */
    private int code = 0;

    private String msg;

    private T Data;

    public static String success() {
        Result s = new Result();
        return JSONObject.toJSONString(s);
    }

    public static String success(Object data) {
        Result s = new Result();
        s.setData(data);
        return JSONObject.toJSONString(s);
    }

    public static String failed(int errorCode, String msg) {
        Result rst = new Result();
        rst.setCode(errorCode);
        rst.setMsg(msg);
        return JSONObject.toJSONString(rst);
    }
}
