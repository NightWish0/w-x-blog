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
import java.util.List;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Data
@Alias("Comment")
@TableName("blog_comment")
public class Comment implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String email;
    private Long receiverId;
    private Long parentId;
    private Long topicId;
    private String content;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdAt;
    private Long authorId;

    @TableField(exist = false)
    private Topic topic;
    @TableField(exist = false)
    private Integer commentCount;
    @TableField(exist = false)
    private String receiverName;
    @TableField(exist = false)
    private List<Comment> childComments;
}
