package com.wxblog.web.controller.admin;

import com.wxblog.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Controller
@RequestMapping("/admin")
public class SystemController {

    @Autowired
    private UserService userService;

    @GetMapping("/druid")
    public String druid(Model model){
        userService.initUserInfo(model);
        return "admin/system_webMonitor";
    }
}
