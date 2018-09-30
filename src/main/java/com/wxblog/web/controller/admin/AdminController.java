package com.wxblog.web.controller.admin;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Controller
@RequiresRoles("admin")
public class AdminController {

    @GetMapping("/admin")
    public String index(){
        return "admin/index";
    }
}
