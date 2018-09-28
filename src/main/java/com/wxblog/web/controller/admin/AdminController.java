package com.wxblog.web.controller.admin;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Controller
@RequestMapping("/admin")
@RequiresRoles("admin")
public class AdminController {

    @GetMapping
    public String index(){
        return "admin/index";
    }

    @GetMapping("/main")
    public String main(){
        return "admin/main";
    }

    @RequestMapping(value = "/druid",method = RequestMethod.GET)
    public String druid(){
        return "redirect:/druid/webapp.html";
    }
}
