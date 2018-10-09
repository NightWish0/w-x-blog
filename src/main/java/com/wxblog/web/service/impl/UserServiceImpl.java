package com.wxblog.web.service.impl;

import com.wxblog.core.bean.User;
import com.wxblog.core.dao.UserMapper;
import com.wxblog.core.response.ResultJson;
import com.wxblog.core.util.MD5Util;
import com.wxblog.core.util.UploadUtil;
import com.wxblog.web.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

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
    public boolean updateUserInfo(MultipartFile file, String userName, String profile,Model model) {
        User user=initUserInfo(model);
        String avatarName=null;
        if (file!=null&&!file.isEmpty()){
            String oldName=user.getAvatar();
            avatarName=UploadUtil.upload(file);
            if (oldName!=null){
                String path=oldName.substring(oldName.lastIndexOf("/")+1,oldName.length());
                UploadUtil.delete(path);
            }
        }
        if(userMapper.updateInfo(user.getId(),userName,avatarName,profile)==1){
            user.setAvatar(avatarName);
            user.setUserName(userName);
            user.setProfile(profile);
            return true;
        }
        return false;
    }

    @Override
    public ResultJson updatePwd(String oldPwd, String newPwd,
                                String confirmPwd,Model model) {
        User user=initUserInfo(model);
        if (!newPwd.equals(confirmPwd)){
            return ResultJson.failure("两次密码输入不一致");
        }
        String md5OldPwd=MD5Util.encodeMd5Salt(user.getLoginName(),oldPwd,user.getSalt());
        if (user.getPassword().equals(md5OldPwd)){
            String md5NewPwd=MD5Util.encodeMd5Salt(user.getLoginName(),confirmPwd,user.getSalt());
            if (userMapper.updatePwd(user.getId(),md5NewPwd)==1){
                user.setPassword(md5NewPwd);
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
