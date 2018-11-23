package com.wxblog.web.controller.admin;

import com.wxblog.core.bean.Topic;
import com.wxblog.core.response.EditorResultJson;
import com.wxblog.core.response.ResultJson;
import com.wxblog.core.util.StatusCode;
import com.wxblog.web.service.CategoryService;
import com.wxblog.web.service.LabelService;
import com.wxblog.web.service.TopicService;
import com.wxblog.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Controller
@RequestMapping("/admin/topics")
public class  TopicController {

    @Autowired
    private UserService userService;
    @Autowired
    private TopicService topicService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LabelService labelService;

    /**
     * 所有文章
     * @param model
     * @return
     */
    @GetMapping
    public String topics(Model model){
        userService.initUserInfo(model);
        topicService.topics(model);
        return "admin/topic/topics";
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
     * 我的分类
     * @param model
     * @return
     */
    @GetMapping("/category")
    public String category(Model model){
        userService.initUserInfo(model);
        categoryService.categories(model);
        return "admin/topic/topics_category";
    }

    @PostMapping("/category")
    @ResponseBody
    public ResultJson category(String name){
        return categoryService.addCategory(name);
    }

    @PostMapping("/category/{id}")
    @ResponseBody
    public ResultJson category(@PathVariable("id") Long id,String name){
        return categoryService.updateCategory(id,name);
    }

    @DeleteMapping("/category/{id}")
    @ResponseBody
    public ResultJson deleteCategory(@PathVariable("id") Long id){
        return categoryService.deleteCategory(id);
    }

    /**
     * 标签管理
     * @param model
     * @return
     */
    @GetMapping("/label")
    public String label(Model model){
        userService.initUserInfo(model);
        labelService.labels(true,model);
        return "admin/topic/topics_label";
    }

    @PostMapping("/label")
    @ResponseBody
    public ResultJson label(String name){
        return labelService.addLabel(name);
    }

    @PostMapping("/label/{id}")
    @ResponseBody
    public ResultJson label(@PathVariable("id") Long id,String name){
        return labelService.updateLabel(id,name);
    }

    @DeleteMapping("/label/{id}")
    @ResponseBody
    public ResultJson deleteLabel(@PathVariable("id") Long id){
        return labelService.deleteLabel(id);
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
        categoryService.categories(model);
        model.addAttribute("labelId",labelId);
        model.addAttribute("topic",topic);
        model.addAttribute("errorMsg",errorMsg);
        return "admin/topic/topic_new";
    }

    /**
     * 创建文章
     * @param topic
     * @param label 标签名称，用,分割
     * @param model
     * @return
     */
    @PostMapping("/new")
    public String topicNew(Topic topic,String label, RedirectAttributes model){
        boolean isSuccess=topicService.createTopic(topic,label,model);
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
        topicService.topic(id,model,false);
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
        topicService.topic(id,model,false);
        categoryService.categories(model);
        model.addAttribute("errorMsg",errorMsg);
        return "admin/topic/topic_edit";
    }

    /**
     * 更新文章
     * @param topic
     * @param model
     * @return
     */
    @PostMapping("/{id}/edit")
    public String topicEdit(Topic topic,String label, RedirectAttributes model){
        boolean isSuccess=topicService.edit(topic,label,model);
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
    @DeleteMapping("/{id}")
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
    public ResultJson topicsDelete(@RequestBody List<Long> ids){
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

    @PostMapping("/upload")
    @ResponseBody
    public EditorResultJson upload(@RequestParam("editormd-image-file")MultipartFile multipartFile){
        return topicService.upload(multipartFile);
    }

}
