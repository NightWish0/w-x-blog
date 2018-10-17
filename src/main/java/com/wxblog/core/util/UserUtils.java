package com.wxblog.core.util;

import com.wxblog.core.bean.User;
import org.apache.shiro.SecurityUtils;

/**
 * @author: NightWish
 * @create: 2018-10-10 08:53
 * @description:
 **/
public class UserUtils {

    public static User currentUser(){
        User user= (User) SecurityUtils.getSubject().getPrincipal();
        return user;
    }
}
