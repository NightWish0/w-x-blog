package com.wxblog.core.bean;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author: NightWish
 * @create: 2018-09-21 13:44
 * @description:
 **/
@Data
@Alias("User")
public class User {

    private Long id;
    private String loginName;
    private String userName;
    private String password;
    private String salt;
    private String avatar;
    private String profile;
    private Date lastLoginAt;
    private Date createdAt;

}
