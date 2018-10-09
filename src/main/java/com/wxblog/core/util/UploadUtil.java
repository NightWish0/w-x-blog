package com.wxblog.core.util;

import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
public class UploadUtil {

    private static final String AVATAR_PATH=System.getProperty("user.dir") +
                               "\\src\\main\\resources\\static\\statics\\upload\\avatar\\";
    private static final String AVATAR_WEB_PATH="/statics/upload/avatar/";

    public static String upload(MultipartFile file) {
        String fileName=file.getOriginalFilename();
        String suffixName=fileName.substring(fileName.lastIndexOf("."));
        String newFileName=String.valueOf(System.currentTimeMillis());
        fileName=newFileName+suffixName;
        File newFile=new File(AVATAR_PATH+fileName);
        if (!newFile.getParentFile().exists()){
            newFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(newFile);
            return AVATAR_WEB_PATH+fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void delete(String name){
        File file=new File(AVATAR_PATH+name);
        file.delete();
    }

    public static void main(String[] args){
        System.out.println(AVATAR_PATH);
    }
}
