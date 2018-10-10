package com.wxblog.core.bean;

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

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private Integer readCount;
    private Integer likeCount;
    private Date createdAt;
    private Integer status;
}
