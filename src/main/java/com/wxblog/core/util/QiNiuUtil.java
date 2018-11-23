package com.wxblog.core.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.wxblog.core.response.QiniuResultJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author: NightWish
 * @create:
 * @description: 七牛云存储工具类
 **/
//@PropertySource("classpath:qiniu.yml")
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiNiuUtil {

    @Value("${AccessKey}")
    private static String accessKey;
    @Value("${SecretKey}")
    private static String secretKey;
    @Value("${Bucket}")
    private static String bucket;
    @Value("${Domain}")
    public static String domain;

    private static final String TOPIC_PREFIX="topic/";
    private static final String USER_PREFIX="user/";


    public static String generateToken(){
        Auth auth = Auth.create(accessKey, secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\"," +
                "\"hash\":\"$(etag)\"," +
                "\"bucket\":\"$(bucket)\"," +
                "\"mimeType\":\"$(mimeType)\"," +
                "\"fsize\":$(fsize)}");
        return auth.uploadToken(bucket, null, 3600L, putPolicy);
    }

    public static String  generateEncodedEntryURI(String key){
        String entry = bucket+":"+key;
        return UrlSafeBase64.encodeToString(entry);
    }

    public static QiniuResultJson uploadOfTopic(MultipartFile file){
        return upload(file,TOPIC_PREFIX);
    }

    public static QiniuResultJson upload(MultipartFile file,String prefix){
        String fileName=renameOfFile(file.getOriginalFilename(),prefix);
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            Response response = uploadManager.put(file.getInputStream(),fileName,generateToken(),null, null);
            //解析上传成功的结果
            QiniuResultJson result = new Gson().fromJson(response.bodyString(), QiniuResultJson.class);
            return result;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
            return null;
        }catch (IOException e){
            return null;
        }
    }

    public static String renameOfFile(String fileName,String prefix){
        String suffixName=fileName.substring(fileName.lastIndexOf("."));
        String newFileName=prefix+String.valueOf(System.currentTimeMillis());
        return newFileName+suffixName;
    }

    public static void main(String[] args){
        System.out.println(generateToken());
    }

}
