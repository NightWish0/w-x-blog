package com.wxblog.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxblog.core.bean.Label;
import com.wxblog.core.bean.Topic;
import com.wxblog.core.dao.LabelMapper;
import com.wxblog.core.dao.TopicMapper;
import com.wxblog.core.dao.UserMapper;
import com.wxblog.core.response.ResultJson;
import com.wxblog.core.util.StatusCode;
import com.wxblog.core.util.UserUtils;
import com.wxblog.web.service.BaseService;
import com.wxblog.web.service.TopicService;
import javafx.scene.control.Pagination;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private LabelMapper labelMapper;

    @Transactional
    @Override
    public boolean createTopic(Topic topic, String labelNames, RedirectAttributes model) {
        List<Label> labels=labelMapper.selectList(null);
        Map<Long,String> map=new HashMap<>();
        for (Label label:labels){
            map.put(label.getId(),label.getName());
        }
        String[] nameArr=labelNames.split(",");
        List<String> nameList=Arrays.asList(nameArr);
        /**
         * 1、新的标签插入标签库，并返回id
         * 2、关联文章和标签id
         */
        //插入标签
        List<String> nameListTemp=new ArrayList<>();
        nameListTemp.addAll(nameList);
        List<Long> ids=new ArrayList<>();
        for (Map.Entry<Long,String > entry:map.entrySet()){
            if (nameList.contains(entry.getValue())){
                ids.add(entry.getKey());
                nameListTemp.remove(entry.getValue());
            }
        }
        List<Label> labelList=new ArrayList<>();
        for (String name:nameListTemp){
            Label label=new Label();
            label.setName(name);
            label.setCreatedAt(new Date());
            labelList.add(label);
        }
        ids.addAll(labelMapper.insertBatch(labelList));
        //插入文章
        topic.setCreatedAt(new Date());
        topic.setUserId(UserUtils.currentUser().getId());
        Long topicId=(long)topicMapper.insert(topic);
        //文章标签关联
        int cloumn=labelMapper.insertTidAndLidBatch(topicId,ids);
        if (cloumn==ids.size()){
            return true;
        }
        model.addFlashAttribute("labelId",labelNames);
        model.addFlashAttribute("topic",topic);
        if (topic.getStatus()==0){
            model.addFlashAttribute("errorMsg","保存为草稿失败");
        }else{
            model.addFlashAttribute("errorMsg","发布文章失败");
        }
        return false;
    }

    @Override
    public IPage<Topic> topicShowByPage(Integer currentPage, Integer pageSize) {
        Page<Topic> page=new Page<>(currentPage,pageSize);
        return topicMapper.topicShowByPage(page);
    }

    @Override
    public void topics(Model model) {
        List<Map<String,String>> topics=topicMapper.topics(StatusCode.TOPIC_PUBLISH_CODE,null);
        model.addAttribute("topics",topics);
    }

    @Override
    public void myTopics(Model model) {
        List<Map<String,String>> topics=topicMapper.topics(StatusCode.TOPIC_PUBLISH_CODE,
                                                            UserUtils.currentUser().getId());
        model.addAttribute("topics",topics);
    }

    @Override
    public void draft(Model model) {
        List<Map<String,String>> topics=topicMapper.topics(StatusCode.TOPIC_DRAFT_CODE,
                UserUtils.currentUser().getId());
        model.addAttribute("topics",topics);
    }

    @Override
    public void recycle(Model model) {
        List<Map<String,String>> topics=topicMapper.topics(StatusCode.TOPIC_DELETE_CODE,
                UserUtils.currentUser().getId());
        model.addAttribute("topics",topics);
    }

    @Override
    public void topic(Long id,Model model,boolean updateReadCount) {
        Map<String,String> map=topicMapper.topic(id);
        if (updateReadCount){
            Topic topic=new Topic();
            topic.setReadCount(Integer.valueOf(map.get("readCount"))+1);
            topic.setId(id);
            topicMapper.updateById(topic);
        }
        model.addAttribute("topic",map);
    }

    @Override
    public boolean edit(Topic topic, String labelId, RedirectAttributes model) {
        if (topicMapper.updateById(topic)==1){
            return true;
        }
        if (topic.getStatus()==0){
            model.addFlashAttribute("errorMsg","保存为草稿失败");
        }else{
            model.addFlashAttribute("errorMsg","发布文章失败");
        }
        return false;
    }

    @Override
    public ResultJson deleteTopic(Long id) {
        if (topicMapper.deleteById(id)==1){
            return ResultJson.success();
        }
        return ResultJson.failure();
    }

    @Override
    public ResultJson deleteTopics(Integer status) {
        topicMapper.deleteAll(status);
        return ResultJson.success();
    }

    @Override
    public ResultJson deleteTopics(List<Long> ids) {
        if(topicMapper.delete(new QueryWrapper<Topic>().in("id",ids))==ids.size()){
            return ResultJson.success();
        }
        return ResultJson.failure();
    }

    @Override
    public ResultJson destroy(List<Long> ids) {
        if (topicMapper.destroy(ids)==ids.size()){
            return ResultJson.success();
        }
        return  ResultJson.failure();
    }

}
