package com.wxblog.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxblog.core.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author: NightWish
 * @create: 2018-09-21 14:48
 * @description:
 **/
@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {

    User checkUserIsExists(@Param("loginName") String loginName);

    int updatePwd(@Param("id") Long id,@Param("password") String password);

    int updateInfo(@Param("id")Long id,@Param("userName")String userName,
                   @Param("avatar")String avatar,@Param("profile")String profile);

    int updateLastLogin(@Param("id")Long id,@Param("lastLoginAt")Date lastLoginAt);

    List<User> userWithCategories();
}
