package com.wxblog.core.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: NightWish
 * @create: 2018-09-27 11:31
 * @description:
 **/
public class MD5Util {

    /**
     * Md5+盐 加密
     * 算列算法 md5(md5(password+loginName+salt))
     * @param loginName
     * @param password
     * @return
     */
    public static Map<String,String> encodeMd5Salt(String loginName, String password){
        Map<String,String> map=new HashMap();
        String salt=new SecureRandomNumberGenerator().nextBytes().toHex();
        map.put("salt",salt);
        SimpleHash hash=new SimpleHash("md5",password,ByteSource.Util.bytes(loginName+salt),2);
        String encodePwd=hash.toBase64();
        map.put("password",encodePwd);
        return map;
    }

    public static String encodeMd5Salt(String loginName, String password,String salt){
        SimpleHash hash=new SimpleHash("md5",password,ByteSource.Util.bytes(loginName+salt),2);
        String encodePwd=hash.toBase64();
        return encodePwd;
    }

    public static void main(String[] args){
        Map<String,String> map=encodeMd5Salt("18156816237","admin");
        System.out.println(map.get("salt"));
        System.out.println(map.get("password"));
        System.out.println(ByteSource.Util.bytes("18156816237"+"27fea06199fdb62cf486792cba05fa59"));
    }
}
