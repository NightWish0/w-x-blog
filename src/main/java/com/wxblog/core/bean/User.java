package com.wxblog.core.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(type = IdType.AUTO)
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
