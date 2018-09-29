package com.wxblog.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Controller
@RequestMapping("/admin")
public class TopicController {

    @GetMapping("/topics")
    public String topics(){
        return "admin/topic_all";
    }

    @GetMapping("/my_topics")
    public String myTopics(){
        return "admin/topic_my";
    }

    @GetMapping("/draft")
    public String draft(){
        return "admin/topic_draft";
    }
}
