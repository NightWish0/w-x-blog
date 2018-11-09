package com.wxblog.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxblog.core.bean.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> comments();

    List<Comment> myComments(@Param("authorId") Long authorId);

    List<Comment> commentsOfTopic(@Param("topicId")Long topicId);

    Comment comment(@Param("id")Long id);

    int updateContent(@Param("content")String content,@Param("id")Long id);

    void deleteAll();

    int updateCommentAuthorName(@Param("name")String name,
                                @Param("authorId")Long authorId);

    int commentCountOfTopic(@Param("topicId")Long topicId);
}
