package com.wxblog.web.controller.admin;

import com.wxblog.core.response.ResultJson;
import com.wxblog.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String index(Model model){
        userService.initUserInfo(model);
        return "admin/index";
    }

    @GetMapping("/info")
    public String info(Model model){
        userService.initUserInfo(model);
        return "admin/user_info";
    }

    @GetMapping("/setting")
    public String setting(Model model){
        userService.initUserInfo(model);
        return "admin/user_setting";
    }

    @PostMapping("/update_pwd")
    @ResponseBody
    public ResultJson updatePwd(String oldPwd, String newPwd,
                                String confirmPwd,Model model){
        return userService.updatePwd(oldPwd,newPwd,confirmPwd,model);
    }

}
