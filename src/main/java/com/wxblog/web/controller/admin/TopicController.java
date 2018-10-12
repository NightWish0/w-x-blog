package com.wxblog.web.controller.admin;

import com.wxblog.core.bean.Topic;
import com.wxblog.web.service.TopicService;
import com.wxblog.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @Autowired
    private TopicService topicService;

    @GetMapping
    public String topics(Model model){
        userService.initUserInfo(model);
        return "admin/topics";
    }

    @GetMapping("/my")
    public String myTopics(Model model){
        userService.initUserInfo(model);
        topicService.topics(model);
        return "admin/topics_my";
    }

    @GetMapping("/new")
    public String topicNew(@ModelAttribute("topic") Topic topic,
                           @ModelAttribute("labelId") String labelId,
                           @ModelAttribute("errorMsg") String errorMsg,
                           Model model){
        userService.initUserInfo(model);
        model.addAttribute("labelId",labelId);
        model.addAttribute("topic",topic);
        model.addAttribute("errorMsg",errorMsg);
        return "admin/topic_edit";
    }

    @PostMapping("/new")
    public String topicNew(Topic topic,String label_id, RedirectAttributes model){
        boolean isSuccess=topicService.createTopic(topic,label_id,model);
        if (isSuccess){
            return "redirect:/admin/topics/my";
        }
        return "redirect:/admin/topics/new";
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
