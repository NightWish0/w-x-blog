package com.wxblog.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wxblog.core.bean.Category;
import com.wxblog.core.bean.Label;
import com.wxblog.core.bean.Topic;
import com.wxblog.core.bean.User;
import com.wxblog.core.dao.CategoryMapper;
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
    private CategoryMapper categoryMapper;

    @Transactional
    @Override
    public boolean createTopic(Topic topic, String labelNames, RedirectAttributes model) {
        String[] nameArr=labelNames.split(",");
        List<String> nameList=new ArrayList<>();
        for (String name:nameArr){
            if (name.trim().length()>0){
                nameList.add(name);
            }
        }
        /**
         * 1、过滤新标签
         * 2、新标签插入标签库，并返回id
         * 3、关联文章和标签id
         */
        List<Label> labels=labelMapper.selectList(null);
        Map<Long,String> map=new HashMap<>();
        for (Label label:labels){
            map.put(label.getId(),label.getName());
        }
        Set<String> nameListTemp=new HashSet<>();
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
        try {
            if (labelList.size()>0){
                //批量插入标签
                labelMapper.insertBatch(labelList);
                for (Label label:labelList){
                    ids.add(label.getId());
                }
            }
            //插入文章
            topic.setCreatedAt(new Date());
            topic.setUserId(UserUtils.currentUser().getId());
            topicMapper.insert(topic);
            if (ids.size()>0){
                //文章标签关联
                labelMapper.insertTidAndLidBatch(topic.getId(),ids);
            }
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

    @Override
    public void categories(Model model) {
        List<Category> categories=categoryMapper.selectByUserId(UserUtils.currentUser().getId());
        model.addAttribute("categories",categories);
    }

    @Override
    public ResultJson addCategory(String name) {
        Integer sort=categoryMapper.maxSort()+1;
        Category category=new Category();
        category.setName(name);
        category.setUserId(UserUtils.currentUser().getId());
        category.setSort(sort);
        category.setCreatedAt(new Date());
        if (categoryMapper.insert(category)==1){
            return ResultJson.success();
        }
        return ResultJson.failure();
    }

    @Override
    public ResultJson updateCategory(Long id,String name) {
        Category category=new Category();
        category.setId(id);
        category.setName(name);
        if (categoryMapper.updateById(category)==1){
            return ResultJson.success();
        }
        return ResultJson.failure();
    }

    @Override
    public ResultJson deleteCategory(Long categoryId) {
        if (categoryMapper.delete(new QueryWrapper<Category>().eq("id",categoryId))==1){
            return ResultJson.success();
        }
        return ResultJson.failure();
    }
}
