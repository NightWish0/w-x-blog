package com.wxblog.core.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

/**
 * @author: NightWish
 * @create: 2018-09-21 13:44
 * @description:
 **/
@Data
@Alias("User")
@TableName("blog_user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String loginName;
    private String userName;
    private String password;
    private String salt;
    private String avatar;
    private String profile;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastLoginAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdAt;
    private String fileMarkHash;

    @TableField(exist = false)
    private List<Category> categories;

}
