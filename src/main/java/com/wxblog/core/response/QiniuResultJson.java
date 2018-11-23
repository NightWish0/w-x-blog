package com.wxblog.core.response;

/**
 * @author: NightWish
 * @create:
 * @description: 七牛上传response
 **/
public final class QiniuResultJson {
    public String hash;
    public String key;
    public String bucket;
    public String mimeType;
    public String fsize;

    public QiniuResultJson() {
    }
}
