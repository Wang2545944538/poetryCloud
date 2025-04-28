package com.yunmo.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMsg {
    private Integer code;  //自定义响应状态码
    private Object data;  //响应数据

    public static ResponseMsg loginSuccess(String token){
        return new ResponseMsg(200, token);
    }
    public static ResponseMsg loginFail(){
        return new ResponseMsg(201, "帐号或密码错误，认证失败!");
    }
    //操作成功
    public static ResponseMsg success(String msg){
        return new ResponseMsg(200, msg);
    }

    //操作失败
    public static ResponseMsg fail(String msg){
        return new ResponseMsg(201, msg);
    }
}
