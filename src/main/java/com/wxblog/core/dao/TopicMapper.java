package com.wxblog.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxblog.core.bean.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface TopicMapper extends BaseMapper<Topic> {

    List<Map<String,String>> topics(@Param("status")Integer status,
                                    @Param("userId")Long userId);

    Map<String,String> topic(@Param("id") Long id);

    int updateById(Topic topic);

    void deleteAll(Integer status);

    int destroy(List<Long> ids);
}
