package com.wxblog.web.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxblog.core.bean.Category;
import com.wxblog.core.bean.Comment;
import com.wxblog.core.bean.Topic;
import com.wxblog.core.response.ResultJson;
import javafx.scene.control.Pagination;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface TopicService {

    boolean createTopic(Topic topic,String labelId, RedirectAttributes model);

    void topics(Model model);

    void myTopics(Model model);

    void draft(Model model);

    void recycle(Model model);

    void topic(Long id,Model model,boolean updateReadCount);

    boolean edit(Topic topic,String label,RedirectAttributes model);

    void comments(Model model);

    void myComments(Model model);

    ResultJson deleteComment(Long id);

    ResultJson updateComment(Long id,String content);

    ResultJson deleteComments();

    ResultJson deleteComments(List<Long> ids);

    ResultJson replyComment(Long receiverId,String content);

    IPage<Topic> topicShowByPage(Integer currentPage, Integer pageSize);

    ResultJson deleteTopic(Long id);

    ResultJson deleteTopics(Integer status);

    ResultJson deleteTopics(List<Long> ids);

    ResultJson destroy(List<Long> ids);

    ResultJson commentPublish(Comment comment);

    ResultJson updateLikeCount(Long id);
}
