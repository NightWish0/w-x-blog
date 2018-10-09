package com.wxblog.web.service;

import com.wxblog.core.bean.User;
import com.wxblog.core.response.ResultJson;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    User initUserInfo(Model model);

    boolean updateUserInfo(MultipartFile file, String userName, String profile,Model model);

    ResultJson updatePwd(String oldPwd, String newPwd, String confirmPwd,Model model);

    int checkUserIsExist();
}
