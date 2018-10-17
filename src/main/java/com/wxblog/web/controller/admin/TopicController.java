package com.wxblog.web.controller.admin;

import com.wxblog.core.bean.Topic;
import com.wxblog.core.response.ResultJson;
import com.wxblog.core.util.StatusCode;
import com.wxblog.web.service.TopicService;
import com.wxblog.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

    /**
     * 所有文章
     * @param model
     * @return
     */
    @GetMapping
    public String topics(Model model){
        userService.initUserInfo(model);
        topicService.topics(model);
        return "admin/topics";
    }

    /**
     * 我的文章
     * @param model
     * @return
     */
    @GetMapping("/my")
    public String myTopics(Model model){
        userService.initUserInfo(model);
        topicService.myTopics(model);
        return "admin/topic/topics_my";
    }

    /**
     * 写文章
     * @param topic
     * @param labelId
     * @param errorMsg
     * @param model
     * @return
     */
    @GetMapping("/new")
    public String topicNew(@ModelAttribute("topic") Topic topic,
                           @ModelAttribute("labelId") String labelId,
                           @ModelAttribute("errorMsg") String errorMsg,
                           Model model){
        userService.initUserInfo(model);
        model.addAttribute("labelId",labelId);
        model.addAttribute("topic",topic);
        model.addAttribute("errorMsg",errorMsg);
        return "admin/topic/topic_new";
    }

    /**
     * 创建文章
     * @param topic
     * @param label_id
     * @param model
     * @return
     */
    @PostMapping("/new")
    public String topicNew(Topic topic,String label_id, RedirectAttributes model){
        boolean isSuccess=topicService.createTopic(topic,label_id,model);
        if (isSuccess){
            return "redirect:/admin/topics/my";
        }
        return "redirect:/admin/topics/new";
    }

    /**
     * 查看文章
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String topicLook(@PathVariable("id") Long id, Model model){
        userService.initUserInfo(model);
        topicService.topic(id,model);
        return "admin/topic/topic";
    }

    /**
     * 编辑文章
     * @param model
     * @return
     */
    @GetMapping("/{id}/edit")
    public String topicEdit(@PathVariable("id") Long id,
                            @ModelAttribute("errorMsg") String errorMsg,
                            Model model){
        userService.initUserInfo(model);
        topicService.topic(id,model);
        model.addAttribute("errorMsg",errorMsg);
        return "admin/topic/topic_edit";
    }

    /**
     * 更新文章
     * @param topic
     * @param label_id
     * @param model
     * @return
     */
    @PostMapping("/{id}/edit")
    public String topicEdit(Topic topic,String label_id, RedirectAttributes model){
        boolean isSuccess=topicService.edit(topic,label_id,model);
        if (isSuccess){
            return "redirect:/admin/topics/"+topic.getId();
        }
        return "redirect:/admin/topics/"+topic.getId()+"/edit";
    }

    /**
     * 删除单篇文章<逻辑删除>
     * @param id
     * @return
     */
    @PostMapping("/{id}")
    @ResponseBody
    public ResultJson topicDelete(@PathVariable("id") Long id){
        return topicService.deleteTopic(id);
    }

    /**
     * 删除选中文章<逻辑删除>
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteSelected")
    @ResponseBody
    public ResultJson topicsDelete(List<Long> ids){
        return topicService.deleteTopics(ids);
    }

    /**
     * 删除全部<逻辑删除>
     * @return
     */
    @DeleteMapping("/deleteAll")
    @ResponseBody
    public ResultJson topicsDelete(){
        return topicService.deleteTopics(StatusCode.TOPIC_PUBLISH_CODE);
    }

    /**
     * 草稿箱
     * @param model
     * @return
     */
    @GetMapping("/draft")
    public String draft(Model model){
        userService.initUserInfo(model);
        topicService.draft(model);
        return "admin/topic/topics_draft";
    }

    /**
     * 回收站
     * @param model
     * @return
     */
    @GetMapping("/recycle")
    public String recycle(Model model){
        userService.initUserInfo(model);
        topicService.recycle(model);
        return "admin/topic/topics_recycle";
    }

    /**
     * 清空回收站/彻底删除单个文章
     * @return
     */
    @DeleteMapping("/destroy")
    @ResponseBody
    public ResultJson destroy(List<Long> ids){
        return topicService.destroy(ids);
    }

}
