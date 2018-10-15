package com.wxblog.core.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
@Alias("Topic")
public class Topic implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Long categoryId;
    private Integer readCount;
    private Integer likeCount;
    private Date createdAt;
    @TableLogic
    private Integer status;
}
