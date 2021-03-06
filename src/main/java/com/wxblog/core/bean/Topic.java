package com.wxblog.core.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wxblog.core.util.UserUtils;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Data
@Alias("Topic")
@TableName("blog_topic")
public class Topic implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private String markdownContent;
    private Long userId;
    private Long categoryId;
    private Integer readCount;
    private Integer likeCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdAt;
    @TableLogic
    private Integer status;
    private Date deletedAt;
    private Long deletedUserId;
    private Integer deletedBeforeStatus;
    private String fileMarkHash;

    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private Integer commentCount;
    @TableField(exist = false)
    private Category category;
    @TableField(exist = false)
    private List<Label> labels;
}
