package com.wxblog.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxblog.core.bean.Category;
import com.wxblog.core.bean.Comment;
import com.wxblog.core.bean.Label;
import com.wxblog.core.bean.Topic;
import com.wxblog.core.dao.CategoryMapper;
import com.wxblog.core.dao.CommentMapper;
import com.wxblog.core.dao.LabelMapper;
import com.wxblog.core.dao.TopicMapper;
import com.wxblog.core.response.ResultJson;
import com.wxblog.core.util.StatusCode;
import com.wxblog.core.util.UserUtils;
import com.wxblog.web.service.TopicService;
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
    @Autowired
    private CommentMapper commentMapper;

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
        Topic topic=topicMapper.topic(id);
        StringBuffer stringBuffer=new StringBuffer();
        for(Label label:topic.getLabels()){
            stringBuffer.append(label.getName()+",");
        }
        if (stringBuffer.length()>0){
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }
        if (updateReadCount){
            Integer readCount=topic.getReadCount()+1;
            topicMapper.updateReadCount(readCount,id);
        }
        List<Comment> comments=commentMapper.commentsOfTopic(id);
        model.addAttribute("topic",topic);
        model.addAttribute("comments",comments);
        model.addAttribute("labels",stringBuffer.toString());
    }

    @Transactional
    @Override
    public boolean createTopic(Topic topic, String labelNames, RedirectAttributes model) {
        try {
            labelHandle(topic,labelNames);
            //插入文章
            topic.setCreatedAt(new Date());
            topic.setUserId(UserUtils.currentUser().getId());
            topicMapper.insert(topic);
            return true;
        }catch (Exception e){
            model.addFlashAttribute("labelId",labelNames);
            model.addFlashAttribute("topic",topic);
            if (topic.getStatus()==0){
                model.addFlashAttribute("errorMsg","保存为草稿失败");
            }else{
                model.addFlashAttribute("errorMsg","发布文章失败");
            }
            return false;
        }
    }

    @Transactional
    @Override
    public boolean edit(Topic topic,String labelNames, RedirectAttributes model) {
        /**
         * 1、删除该文章下标签关联
         * 2、过滤新标签
         * 3、新标签插入标签库，并返回id
         * 4、关联文章和标签id
         */
        labelMapper.deleteAssociationOfTopic(topic.getId());
        try {
            labelHandle(topic,labelNames);
            topicMapper.updateById(topic);
            return true;
        }catch (Exception e){
            if (topic.getStatus()==0){
                model.addFlashAttribute("errorMsg","保存为草稿失败");
            }else{
                model.addFlashAttribute("errorMsg","发布文章失败");
            }
            return false;
        }
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

    private void labelHandle(Topic topic,String labelNames){
        String[] nameArr=labelNames.split(",");
        List<String> nameList=new ArrayList<>();
        for (String name:nameArr){
            if (name.trim().length()>0){
                nameList.add(name);
            }
        }
        List<Label> labels=labelMapper.selectList(null);
        Map<String,Long> map=new HashMap<>();
        for (Label label:labels){
            map.put(label.getName(),label.getId());
        }
        Set<String> nameListTemp=new HashSet<>();
        nameListTemp.addAll(nameList);
        List<Long> ids=new ArrayList<>();
        for (String name:nameList){
            if (map.containsKey(name)){
                ids.add(map.get(name));
                nameListTemp.remove(name);
            }
        }
        List<Label> labelList=new ArrayList<>();
        for (String name:nameListTemp){
            Label label=new Label();
            label.setName(name);
            label.setCreatedAt(new Date());
            labelList.add(label);
        }
        if (labelList.size()>0){
            labelMapper.insertBatch(labelList);
            for (Label label:labelList){
                ids.add(label.getId());
            }
        }
        if (ids.size()>0){
            //文章标签关联
            labelMapper.insertTidAndLidBatch(topic.getId(),ids);
        }
    }

    @Override
    public ResultJson commentPublish(Comment comment) {
        if (commentMapper.insert(comment)==1){
            return ResultJson.success(comment);
        }
        return ResultJson.failure();
    }
}
