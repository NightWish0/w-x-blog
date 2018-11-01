package com.wxblog.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxblog.core.bean.Category;
import com.wxblog.core.bean.User;
import com.wxblog.core.dao.CategoryMapper;
import com.wxblog.core.dao.TopicMapper;
import com.wxblog.core.response.ResultJson;
import com.wxblog.core.util.UserUtils;
import com.wxblog.web.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.List;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TopicMapper topicMapper;

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
            topicMapper.deleteTopicCategory(categoryId);
            return ResultJson.success();
        }
        return ResultJson.failure();
    }

}
