package com.wxblog.core.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Data
public class EditorResultJson<T> implements Serializable {

    private static final Integer SUCCESS_CODE=1; //成功
    private static final Integer FALIURE_CODE=0; //失败

    private int success;
    private String message;
    private String url;

    public static <T> EditorResultJson<T> success(String url){
        EditorResultJson<T> resultJson=new EditorResultJson<T>();
        resultJson.success=SUCCESS_CODE;
        resultJson.message="上传成功";
        resultJson.url=url;
        return resultJson;
    }

    public static <T> EditorResultJson<T> failure(){
        EditorResultJson<T> resultJson=new EditorResultJson<T>();
        resultJson.success=FALIURE_CODE;
        resultJson.message="上传失败";
        resultJson.url="";
        return resultJson;
    }

}
