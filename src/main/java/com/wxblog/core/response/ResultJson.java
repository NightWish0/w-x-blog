package com.wxblog.core.response;

import lombok.Setter;

import java.io.Serializable;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
public class ResultJson<T> implements Serializable {

    private static final String SUCCESS_CODE="1"; //成功
    private static final String FALIURE_CODE="0"; //失败

    private boolean state;
    private String code;
    private T data;

    public static <T> ResultJson<T> success(){
        ResultJson<T> resultJson=new ResultJson<T>();
        resultJson.state=true;
        resultJson.code=SUCCESS_CODE;
        return resultJson;
    }

    public static <T> ResultJson<T> success(T data){
        ResultJson<T> resultJson=success();
        resultJson.data=data;
        return resultJson;
    }

    public static <T> ResultJson<T> failure(T data){
        ResultJson<T> resultJson=new ResultJson<T>();
        resultJson.state=false;
        resultJson.code=FALIURE_CODE;
        resultJson.data=data;
        return resultJson;
    }
}
