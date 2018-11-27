package com.wxblog.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxblog.core.bean.QiniuFile;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper
public interface QiniuFileMapper extends BaseMapper<QiniuFile> {

}
