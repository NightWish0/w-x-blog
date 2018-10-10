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
@RequestMapping("/admin/topics")
public class TopicController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String topics(Model model){
        userService.initUserInfo(model);
        return "admin/topics";
    }

    @GetMapping("/my")
    public String myTopics(Model model){
        userService.initUserInfo(model);
        return "admin/topics_my";
    }

    @GetMapping("/new")
    public String topicNew(Model model){
        userService.initUserInfo(model);
        return "admin/topic_edit";
    }

    @GetMapping("/{id}/edit")
    public String topicEdit(Model model){
        userService.initUserInfo(model);
        return "admin/topic_edit";
    }

    @GetMapping("/draft")
    public String draft(Model model){
        userService.initUserInfo(model);
        return "admin/topics_draft";
    }
}
