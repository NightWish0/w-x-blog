package com.wxblog.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxblog.core.bean.Category;
import com.wxblog.core.bean.User;
import com.wxblog.core.dao.CategoryMapper;
import com.wxblog.core.dao.CommentMapper;
import com.wxblog.core.dao.UserMapper;
import com.wxblog.core.response.ResultJson;
import com.wxblog.core.util.MD5Util;
import com.wxblog.core.util.UploadUtil;
import com.wxblog.core.util.UserUtils;
import com.wxblog.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: NightWish
 * @create: 2018-09-21 15:45
 * @description:
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void users(Model model) {
        List<User> users=userMapper.selectList(null);
        model.addAttribute("users",users);
    }

    @Override
    public User initUserInfo(Model model) {
        User user= UserUtils.currentUser();
        model.addAttribute("user",user);
        return user;
    }

    @Override
    public boolean updateUserInfo(MultipartFile file, String userName, String profile,Model model) {
        User user=initUserInfo(model);
        String userNameOld=user.getUserName();
        String avatarName=null;
        if (file!=null&&!file.isEmpty()){
            String oldName=user.getAvatar();
            avatarName=UploadUtil.uploadAvatar(file);
            UploadUtil.deleteAvatar(oldName);
        }
        if(userMapper.updateInfo(user.getId(),userName,avatarName,profile)==1){
            if (!userNameOld.equals(userName)){
                commentMapper.updateCommentAuthorName(userName,user.getId());
            }
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

    @Override
    public void updateLastLogin() {
        User user=UserUtils.currentUser();
        userMapper.updateLastLogin(user.getId(),new Date());
    }

    @Override
    public void userWithCategories(Long userId,Model model) {
        User user=userMapper.userWithCategories(userId);
        int count=categoryMapper.notCategotyOfUser(userId);
        Category category=new Category();
        category.setId(0l);
        category.setName("未分类");
        category.setTopicTotal(count);
        user.getCategories().add(category);
        model.addAttribute("user",user);
    }

    @Override
    public void usersWithCategories(Model model) {
        List<User> users=userMapper.usersWithCategories();
        List<Map<String,Object>> list=categoryMapper.notCategoty();
        for (Map<String,Object> map:list){
            for (User user:users){
                if ((long)map.get("userId")==user.getId()){
                    Category category=new Category();
                    category.setId(0l);
                    category.setName("未分类");
                    category.setTopicTotal(Integer.valueOf(map.get("total").toString()));
                    user.getCategories().add(category);
                }
            }
        }
        model.addAttribute("users",users);
    }

}
