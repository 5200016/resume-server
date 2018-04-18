package com.resume.web.rest.util;

/**
 * User : 黄志成
 * Date : 2018/4/10
 * Desc : 枚举错误码
 */

public enum ErrorCode {

    SUCCESS(200,"请求成功"),

    UNKNOWN_ERROR(-1,"未知错误"),

    INVALID_SIGNATURE(0,"没有携带JWT令牌"),
    INVALID_TOKEN(1,"JWT令牌无效"),
    EXPIRED_TOKEN(2,"JWT令牌过期"),
    UNSUPPORTED_TOKEN(3,"不支持的JWT令牌"),

    REQ_NOT_FOUND_ERROR(10000,"请求失败(not found) 400"),
    REQ_METHOD_ERROR(10001,"请求类型错误"),
    REQ_PARAM_NULL_ERROR(10002,"请求缺少参数"),
    REQ_PARAMS_TYPE_ERROR(10003,"请求参数格式错误"),

    SER_NULL_POINTER_ERROR(20000, "空指针"),
    SER_CLASS_NOT_FOUND_ERROR(20001,"类不存在"),
    SER_ARRAY_INDEX_OUT_OF_BOUNDS_ERROR(20002,"数组下标越界"),
    SER_ILLEGAL_ARGUMENT_ERROR(20003,"方法参数错误"),
    SER_INCOMPATIBLE_CLASS_CHANGE_ERROR(20004,"不兼容类变化错误"),
    SER_INSTANTIATION_ERROR(20005,"实例化错误"),
    SER_LINKAGE_ERROR(20006,"链接错误"),
    SER_STACK_OVERFLOW_ERROR(20007,"堆栈溢出错误"),
    SER_ARITHMETIC_ERROR(20008,"数学运算错误"),
    SER_ILLEGAL_ACCESS_ERROR(20009,"没有访问权限"),

    DB_EXCEPTION(30000,"数据库异常");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getErrorCode(int code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode.getMsg();
            }
        }
        return null;
    }
}
