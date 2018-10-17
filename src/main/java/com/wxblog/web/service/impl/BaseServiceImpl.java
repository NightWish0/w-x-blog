package com.wxblog.web.service.impl;

import com.wxblog.web.service.BaseService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Service
public class BaseServiceImpl implements BaseService {

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
}
