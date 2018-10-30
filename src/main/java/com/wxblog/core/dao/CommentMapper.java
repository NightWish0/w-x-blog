package com.wxblog.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxblog.core.bean.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {


}
