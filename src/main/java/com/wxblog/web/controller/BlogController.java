package com.wxblog.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wxblog.core.bean.Topic;
import com.wxblog.web.service.LabelService;
import com.wxblog.web.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Controller
public class BlogController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private LabelService labelService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/blog")
    public String blog(Model model){
        labelService.labels(model);
        return "blog";
    }

    @GetMapping("/blogByPage")
    @ResponseBody
    public IPage<Topic> blog(Integer currentSize){
        return topicService.topicShowByPage(currentSize,10);
    }
}
