package com.wxblog.web.service.impl;

import com.wxblog.core.dao.CommentMapper;
import com.wxblog.core.dao.TopicMapper;
import com.wxblog.core.util.UserUtils;
import com.wxblog.web.service.BaseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Service
public class BaseServiceImpl implements BaseService {
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public boolean loginAuthentication(String loginName, String password, Model model) {
        if (!loginAuthentication()){
            Subject subject=SecurityUtils.getSubject();
            UsernamePasswordToken token=new UsernamePasswordToken(loginName,password);
            try {
                subject.login(token);
                Session session=subject.getSession();
                session.setAttribute("user",token.getPrincipal());
                return true;
            }catch (AuthenticationException ae){
                model.addAttribute("loginName",loginName);
                model.addAttribute("msg","用户名或者密码错误");
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean loginAuthentication(){
        Subject subject=SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public void loginOut() {
        Subject subject=SecurityUtils.getSubject();
        subject.logout();
    }

    @Override
    public void initAdminIndex(Model model) {
        Map<String,Integer> totalData=topicMapper.totalData();
        Map<String,Integer> todayData=topicMapper.todayData(UserUtils.currentUser().getId());
        Integer todayComment=commentMapper.todayCount();
        Map<String,Integer> map=new HashMap<>();
        map.putAll(totalData);
        map.putAll(todayData);
        map.put("todayCommentCount",todayComment);
        model.addAttribute("data",map);
    }
}
