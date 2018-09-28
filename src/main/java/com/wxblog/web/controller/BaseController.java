package com.wxblog.web.controller;

import com.wxblog.web.service.BaseService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author: NightWish
 * @create: 2018-09-21 17:14
 * @description:
 **/

@Controller
public class BaseController {

    @Autowired
    private BaseService baseService;

    @GetMapping("/login")
    public String loginPage(String loginName,String password,Model model){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(String loginName,String password,Model model){
        boolean isSuccess=baseService.loginAuthentication(loginName,password,model);
        if (isSuccess){
            return "redirect:/admin";
        }
        return "admin/login";
    }
    
    /**
     * 测试用
     */
    @PostMapping("/loginOut")
    public String loginOut(HttpServletRequest request){
    	String test = "测试成功";
    	return test;
    }

}
