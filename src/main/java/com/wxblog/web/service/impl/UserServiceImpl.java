package com.wxblog.web.service.impl;

import com.wxblog.core.bean.User;
import com.wxblog.core.dao.UserMapper;
import com.wxblog.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: NightWish
 * @create: 2018-09-21 15:45
 * @description:
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User checkUserIsExist(String loginName) {
        User user=userMapper.checkUserIsExists(loginName);
        return user;
    }

}
