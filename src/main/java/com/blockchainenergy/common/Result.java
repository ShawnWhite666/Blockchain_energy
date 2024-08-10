package com.blockchainenergy.common;

public class Result {

    /**
     * 业务错误
     */
    public static final int ERR_CODE_BUSINESS = 500;

    public static final int ERR_CODE_UNLOGIN = 520;
    public static final int ERR_CODE_MANAGER_UNLOGIN = 521;
    public static final int TIME_ERROR = 540;
    public static final int TOKEN_LOSE = 501;
    /**
     * 系统错误
     */
    public static final int ERR_CODE_SYS = 530;


    public static Result success() {
        return new Result(200, true, null, null);
    }

    public static Result success(String message) {
        return new Result(200, true, message, null);
    }

    public static Result success(String message, Object data) {
        return new Result(200, true, message, data);
    }

    public static Result success(Object data) {
        return new Result(200, true, null, data);
    }

    public static Result fail(int code, String message) {
        return new Result(code, false, message, null);
    }

    private int code;//200成功 500、530错误
    private boolean success;//是否成功
    private String message;
    private Object data;


    private Result(int code, boolean success, String message, Object data) {
        this.code = code;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
