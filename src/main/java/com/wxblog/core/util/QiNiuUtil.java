package com.wxblog.core.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.wxblog.core.bean.QiniuFile;
import com.wxblog.core.config.QiNiuConfig;
import com.wxblog.core.response.QiniuResultJson;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: NightWish
 * @create:
 * @description: 七牛云存储工具类
 **/
@Component
public class QiNiuUtil {

    private static final String TOPIC_PREFIX="topic/";
    private static final String USER_PREFIX="user/";


    public static String generateToken(QiNiuConfig config){
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\"," +
                "\"hash\":\"$(etag)\"," +
                "\"bucket\":\"$(bucket)\"," +
                "\"mimeType\":\"$(mimeType)\"," +
                "\"fsize\":$(fsize)}");
        return auth.uploadToken(config.getBucket(), null, 3600L, putPolicy);
    }

    public static QiniuResultJson uploadOfTopic(MultipartFile file,QiNiuConfig config){
        return upload(file,TOPIC_PREFIX,config);
    }

    public static QiniuResultJson uploadOfUser(MultipartFile file,QiNiuConfig config){
        return upload(file,USER_PREFIX,config);
    }

    public static QiniuResultJson upload(MultipartFile file, String prefix,QiNiuConfig config){
        String fileName=renameOfFile(file.getOriginalFilename(),prefix);
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            Response response = uploadManager.put(file.getInputStream(),fileName,generateToken(config),null, null);
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

    public static boolean delete(String key,QiNiuConfig config){
        BucketManager bucketManager = getBucketManager(config);
        try {
            bucketManager.delete(config.getBucket(), key);
            return true;
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            return false;
        }
    }

    /**
     * 批量删除文件
     * @param fileList
     * @param config
     * @return ids，已删除的文件id
     */
    public static List<Long> deleteBatch(List<QiniuFile> fileList, QiNiuConfig config){
        List<Long> ids=new ArrayList<>();
        List<String> keyList=new ArrayList<>();
        Map<String,Long> map=new HashMap<>();
        for (QiniuFile file:fileList){
            map.put(file.getHashKey(),file.getId());
            keyList.add(file.getHashKey());
        }
        String[] keys= (String[]) keyList.toArray();
        BucketManager bucketManager = getBucketManager(config);
        try {
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(config.getBucket(), keys);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < keys.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = keys[i];
                if (status.code == 200) {
                    ids.add(map.get(key));
                    System.out.println(status.data.error);
                }
            }
        } catch (QiniuException ex) {
            System.err.println(ex.response.toString());
        }
        return ids;
    }

    public static String renameOfFile(String fileName,String prefix){
        String suffixName=fileName.substring(fileName.lastIndexOf("."));
        String newFileName=prefix+String.valueOf(System.currentTimeMillis());
        return newFileName+suffixName;
    }

    private static BucketManager getBucketManager(QiNiuConfig config){
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        return bucketManager;
    }

}
