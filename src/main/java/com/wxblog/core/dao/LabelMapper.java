package com.wxblog.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxblog.core.bean.Label;
import com.wxblog.core.bean.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface LabelMapper extends BaseMapper<Label> {

    List<Label> labels(@Param("id") Long id);

    List<String> names();

    int insertBatch(List<Label> labels);

    int insertTidAndLidBatch(@Param("topicId") Long topicId,
                             @Param("ids")List<Long> ids);
}
