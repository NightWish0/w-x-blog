package com.wxblog.web.service;

import com.wxblog.core.bean.User;
import com.wxblog.core.response.ResultJson;
import org.springframework.ui.Model;

public interface UserService {

    User initUserInfo(Model model);

    ResultJson updatePwd(String oldPwd, String newPwd, String confirmPwd,Model model);

    int checkUserIsExist();
}
