package com.wxblog.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxblog.core.bean.Label;
import com.wxblog.core.dao.LabelMapper;
import com.wxblog.core.response.ResultJson;
import com.wxblog.web.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Service
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelMapper labelMapper;

    @Override
    public List<Label> labels(boolean showTopicTotal,Model model) {
        List<Label> labels=new ArrayList<>();
        if (showTopicTotal){
            labels=labelMapper.labelsWithTopicTotal();
        }else {
            labels=labelMapper.selectList(null);
        }
        model.addAttribute("labels",labels);
        return labels;
    }

    @Override
    public List<String> namesOfLabel() {
        return labelMapper.names();
    }

    @Override
    public ResultJson addLabel(String name) {
        Label label=new Label();
        label.setName(name);
        label.setCreatedAt(new Date());
        if (labelMapper.insert(label)==1){
            return ResultJson.success();
        }
        return ResultJson.failure();
    }

    @Override
    public ResultJson updateLabel(Long id, String name) {
        Label label=new Label();
        label.setId(id);
        label.setName(name);
        if (labelMapper.updateById(label)==1){
            return ResultJson.success();
        }
        return ResultJson.failure();
    }

    @Override
    public ResultJson deleteLabel(Long id) {
        if (labelMapper.delete(new QueryWrapper<Label>().eq("id",id))==1){
            return ResultJson.success();
        }
        return ResultJson.failure();
    }
}
