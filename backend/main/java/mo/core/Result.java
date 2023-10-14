package mo.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 统一API响应结果封装
 *
 */
public class Result {
    private int code;
    private String message;
    private JSONObject data;


    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public JSONObject getData() {
        return data;
    }

    public Result setData(JSONObject data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }
    public Result setCode(ResultCode code) {
        this.code = code.code();
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}