package com.wxblog.web.controller;

import com.wxblog.web.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Controller
public class BlogController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/blog")
    public String blog(Model model){
        topicService.topicShow(model);
        return "blog";
    }
}
