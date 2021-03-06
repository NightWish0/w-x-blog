package com.wxblog.core.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Data
@Alias("Category")
@TableName("blog_user_category")
public class Category implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer sort;
    private Long userId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdAt;

    @TableField(exist = false)
    private Integer topicTotal;
}
