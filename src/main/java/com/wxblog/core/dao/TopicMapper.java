package com.wxblog.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxblog.core.bean.Topic;
import javafx.scene.control.Pagination;
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

    List<Topic> topicShow(Page page);

    IPage<Topic> topicShowByPage(Page page);

    Map<String,String> topic(@Param("id") Long id);

    int updateById(Topic topic);

    void deleteAll(Integer status);

    int destroy(List<Long> ids);
}
