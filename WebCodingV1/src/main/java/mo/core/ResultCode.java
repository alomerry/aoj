package mo.core;

/**
 * 响应码枚举
 */
public enum ResultCode {
    OK(200),//成功
    BAD_REQUEST(400),//错误请求
    FORBIDDEN(400),//没有权利访问
    UNAUTHORIZED(401),//需要身份验证以访问
    NOT_FOUND(404),//接口不存在
    INTERNAL_SERVER_ERROR(500);//服务器内部错误

    private final int code;

    ResultCode(int code) {
        this.code = code;
    }

    public int code() {
        return code;
    }
}
