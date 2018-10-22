package com.wxblog.core.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@Alias("Label")
@TableName("blog_label")
public class Label implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Date createdAt;
}
