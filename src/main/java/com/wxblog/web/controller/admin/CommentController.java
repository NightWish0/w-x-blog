package com.wxblog.web.controller.admin;

import com.wxblog.core.response.ResultJson;
import com.wxblog.core.util.StatusCode;
import com.wxblog.web.service.TopicService;
import com.wxblog.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Controller
@RequestMapping("/admin/comments")
public class CommentController {

    @Autowired
    private UserService userService;
    @Autowired
    private TopicService topicService;

    /**
     * 所有评论
     * @param model
     * @return
     */
    @GetMapping
    public String comments(Model model){
        userService.initUserInfo(model);
        topicService.comments(model);
        return "admin/comment/comments";
    }

    @GetMapping("/{id}/topic")
    public String commentPreview(@PathVariable("id")Long id, Model model){
        topicService.topic(id,model,false);
        return "admin/comment/topic";
    }

    @PostMapping("/{id}/reply")
    @ResponseBody
    public ResultJson replyComment(@PathVariable("id")Long receiverId,String content){
        return topicService.replyComment(receiverId,content);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResultJson updateComment(@PathVariable("id")Long id,String content){
        return topicService.updateComment(id,content);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResultJson deleteComment(@PathVariable("id")Long id){
        return topicService.deleteComment(id);
    }

    /**
     * 我的评论
     * @param model
     * @return
     */
    @GetMapping("/my")
    public String myComments(Model model){
        userService.initUserInfo(model);
        topicService.myComments(model);
        return "admin/comment/comments_my";
    }

    /**
     * 删除选中评论
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteSelected")
    @ResponseBody
    public ResultJson commentsDelete(@RequestBody List<Long> ids){
        return topicService.deleteComments(ids);
    }

    /**
     * 删除全部
     * @return
     */
    @DeleteMapping("/deleteAll")
    @ResponseBody
    public ResultJson commentsDelete(){
        return topicService.deleteComments();
    }

}
