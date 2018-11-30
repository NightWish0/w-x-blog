package com.wxblog.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxblog.core.bean.Topic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface TopicMapper extends BaseMapper<Topic> {

    List<Map<String,String>> topics(@Param("status")Integer status,
                                    @Param("userId")Long userId);

    List<Map<String,String>> topicsOfDeleted(@Param("userId")Long userId);

    IPage<Topic> topicShowByPage(Page page);

    List<Topic> topicShowOfCategoryByPage(@Param("userId")Long userId,@Param("categoryId")Long categoryId,Page page);

    List<Topic> topicShowOfLabelByPage(@Param("ids")List<Long> ids,Page page);


    Topic topic(@Param("id") Long id);

    int updateById(Topic topic);

    int updateReadCount(@Param("id")Long id);

    int updateLikeCount(@Param("id")Long id);

    int deleteTopicById(@Param("id")Long id,@Param("deletedAt") Date deletedAt, @Param("deletedUserId")Long deletedUserId);

    void deleteAll(@Param("deletedAt") Date deletedAt, @Param("deletedUserId")Long deletedUserId);

    int deleteAllByIds(List<Long> ids,@Param("deletedAt") Date deletedAt, @Param("deletedUserId")Long deletedUserId);

    int resumeById(Long id);

    int resumeAllByIds(List<Long> ids);

    int resumeAll();

    int destroyById(Long id);

    int destroyAll();

    int destroyAllByIds(List<Long> ids);

    int deleteTopicCategory(@Param("categoryId") Long categoryId);

    Map<String,Integer> totalData();

    Map<String,Integer> todayData(@Param("userId")Long userId);
}
