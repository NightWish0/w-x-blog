package com.wxblog.core.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
public class UploadUtil {

    private static final String AVATAR_PATH=System.getProperty("user.dir") +
                               "\\src\\main\\resources\\static\\statics\\upload\\avatar\\";
    private static final String AVATAR_WEB_PATH="/statics/upload/avatar/";

    public static String uploadAvatar(MultipartFile file) {
        String fileName=file.getOriginalFilename();
        String suffixName=fileName.substring(fileName.lastIndexOf("."));
        String newFileName=UserUtils.currentUser().getId()+"_"+
                            String.valueOf(System.currentTimeMillis());
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

    public static void deleteAvatar(String name){
        if (name!=null){
            name=name.substring(name.lastIndexOf("/")+1,name.length());
        }
        File file=new File(AVATAR_PATH+name);
        file.delete();
    }

    public static void main(String[] args){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(new Date("yyyy-MM-dd HH:mm:ss"));
    }
}
