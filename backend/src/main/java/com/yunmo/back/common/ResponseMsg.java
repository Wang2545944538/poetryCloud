package com.yunmo.back.common;

import java.io.Serializable;

public class ResponseMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    // 状态码，例如：200 成功，401 未授权，500 服务器错误等
    private int code;

    // 提示信息
    private String msg;

    // 响应数据
    private Object data;

    // 构造方法
    public ResponseMsg() {
    }

    public ResponseMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseMsg(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseMsg(int code, Object data) {
        this.code = code;
        this.data = data;
        this.msg = "success";
    }

    // 静态工厂方法（推荐使用）
    public static ResponseMsg success(Object data) {
        return new ResponseMsg(200, "success", data);
    }

    public static ResponseMsg success(String msg, Object data) {
        return new ResponseMsg(200, msg, data);
    }

    public static ResponseMsg error(int code, String msg) {
        return new ResponseMsg(code, msg, null);
    }

    public static ResponseMsg error(String msg) {
        return new ResponseMsg(500, msg, null);
    }

    public static ResponseMsg fail(String msg) {
        return new ResponseMsg(201, msg, null);
    }

    public static ResponseMsg fail(int code, String msg) {
        return new ResponseMsg(code, msg, null);
    }

    public static ResponseMsg loginSuccess(String token){
        return new ResponseMsg(200,null, token);
    }

    public static ResponseMsg loginFail(){
        return new ResponseMsg(201,null, "帐号或密码错误，认证失败!");
    }

    // Getter & Setter
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    // toString 方法（可选，用于日志）
    @Override
    public String toString() {
        return "ResponseMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
