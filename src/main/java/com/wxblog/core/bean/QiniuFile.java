package com.wxblog.core.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Data
@Alias("QiniuFile")
@TableName("qiniu_file")
public class QiniuFile {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String etag;
    private String key;
    private String bucket;
    private Long fsize;
    private String mimeType;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createdAt;
}
