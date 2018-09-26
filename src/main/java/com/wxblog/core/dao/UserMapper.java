package com.wxblog.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxblog.core.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author: NightWish
 * @create: 2018-09-21 14:48
 * @description:
 **/
@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {

    User checkUserIsExists(@Param("loginName") String loginName);
}
