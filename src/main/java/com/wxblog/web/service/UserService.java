package com.wxblog.web.service;

import com.wxblog.core.bean.Category;
import com.wxblog.core.bean.User;
import com.wxblog.core.response.ResultJson;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    User initUserInfo(Model model);

    void users(Model model);

    boolean updateUserInfo(MultipartFile file, String userName, String profile,Model model);

    ResultJson updatePwd(String oldPwd, String newPwd, String confirmPwd,Model model);

    int checkUserIsExist();

    void updateLastLogin();

}
