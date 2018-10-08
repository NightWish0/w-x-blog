package com.wxblog.web.service.impl;

import com.wxblog.core.bean.User;
import com.wxblog.core.dao.UserMapper;
import com.wxblog.core.response.ResultJson;
import com.wxblog.core.util.MD5Util;
import com.wxblog.web.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
    public User initUserInfo(Model model) {
        Subject subject=SecurityUtils.getSubject();
        User user= (User) subject.getPrincipal();
        model.addAttribute("user",user);
        return user;
    }

    @Override
    public ResultJson updatePwd(String oldPwd, String newPwd,
                                String confirmPwd,Model model) {
        User user=initUserInfo(model);
        if (!newPwd.equals(confirmPwd)){
            return ResultJson.failure("两次密码输入不一致");
        }
        if (user.getPassword().equals(MD5Util.encodeMd5Salt(
                user.getLoginName(),oldPwd,user.getSalt()))){
            if (userMapper.updatePwd(user.getId(),MD5Util.encodeMd5Salt(
                    user.getLoginName(),confirmPwd,user.getSalt()))==1){
                return ResultJson.success();
            }else{
               return ResultJson.failure("异常错误");
            }
        }
        return ResultJson.failure("旧密码输入错误");
    }

    @Override
    public int checkUserIsExist() {
        return 0;
    }
}
