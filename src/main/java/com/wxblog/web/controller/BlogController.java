package com.wxblog.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wxblog.core.bean.Comment;
import com.wxblog.core.bean.Topic;
import com.wxblog.core.response.ResultJson;
import com.wxblog.web.service.LabelService;
import com.wxblog.web.service.TopicService;
import com.wxblog.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

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
    @Autowired
    private UserService userService;

    /**
     * 首页
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model){
        userService.users(model);
        return "index";
    }

    /**
     * 博文
     * @param model
     * @return
     */
    @GetMapping("/blog")
    public String blog(Model model){
        labelService.labels(false,model);
        userService.userWithCategories(model);
        return "blog";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") Long id, Model model){
        topicService.topic(id,model,true);
        return "blog_topic";
    }

    @GetMapping("/blogByPage")
    @ResponseBody
    public IPage<Topic> blog(Integer currentSize){
        return topicService.topicShowByPage(currentSize,10);
    }

    @PostMapping("/blog/{topicId}/comment")
    @ResponseBody
    public ResultJson commentPublish(Comment comment){
        comment.setCreatedAt(new Date());
        return topicService.commentPublish(comment);
    }

    @PostMapping("/blog/{topicId}/like")
    @ResponseBody
    public ResultJson commentLike(@PathVariable("topicId") Long topicId){
        return topicService.updateLikeCount(topicId);
    }

    /**
     * 小黑屋
     * @param model
     * @return
     */
    @GetMapping("/darkHouse")
    public String darkHouse(Model model){
        return "dark_house";
    }

    /**
     * 浮生
     * @param model
     * @return
     */
    @GetMapping("/life")
    public String life(Model model){
        return "life";
    }

    /**
     * 留言
     * @param model
     * @return
     */
    @GetMapping("/message")
    public String message(Model model){
        return "message";
    }
}
