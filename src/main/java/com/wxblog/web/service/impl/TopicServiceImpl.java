package com.wxblog.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxblog.core.bean.Topic;
import com.wxblog.core.dao.TopicMapper;
import com.wxblog.core.dao.UserMapper;
import com.wxblog.core.util.UserUtils;
import com.wxblog.web.service.BaseService;
import com.wxblog.web.service.TopicService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public boolean createTopic(Topic topic, String labelId, RedirectAttributes model) {
        topic.setCreatedAt(new Date());
        topic.setUserId(UserUtils.currentUser().getId());
        if (topicMapper.insert(topic)==1){
            return true;
        }
        model.addFlashAttribute("labelId",labelId);
        model.addFlashAttribute("topic",topic);
        if (topic.getStatus()==0){
            model.addFlashAttribute("errorMsg","保存为草稿失败");
        }else{
            model.addFlashAttribute("errorMsg","发布文章失败");
        }
        return false;
    }

    @Override
    public void topics(Model model) {
        List<Map<String,String>> topics=topicMapper.topics();
        model.addAttribute("topics",topics);
    }
}
