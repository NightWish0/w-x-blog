package com.wxblog.core.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxblog.core.bean.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    List<Category> selectByUserId(@Param("userId") Long userId);

    int maxSort();
}
