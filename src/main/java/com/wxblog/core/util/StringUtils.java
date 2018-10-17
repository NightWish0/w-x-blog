package com.wxblog.core.util;

/**
 * @author: NightWish
 * @create: 2018-09-27 15:54
 * @description:
 **/
public class StringUtils {

    public static String charToString(char[] chars){
        StringBuffer stringBuffer=new StringBuffer();
        for (char c:chars){
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }
}
