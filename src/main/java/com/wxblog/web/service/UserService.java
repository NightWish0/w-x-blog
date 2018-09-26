package com.wxblog.web.service;

import com.wxblog.core.bean.User;

public interface UserService {

    User checkUserIsExist(String loginName);
}
