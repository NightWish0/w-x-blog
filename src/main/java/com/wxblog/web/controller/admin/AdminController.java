package com.wxblog.web.controller.admin;

import com.wxblog.core.bean.User;
import com.wxblog.core.response.ResultJson;
import com.wxblog.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/info_update")
    public String settingUpdate(Model model){
        userService.initUserInfo(model);
        return "admin/user_info_update";
    }

    @PostMapping("/info_save")
    public String infoSave(@RequestParam("file") MultipartFile file, String userName, String profile, Model model){
        boolean isSuccess=userService.updateUserInfo(file,userName,profile,model);
        if (isSuccess){
            return "redirect:/admin/info";
        }
        return "redirect:/admin/info_update";
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
