package com.wxblog.web.service.impl;

import com.wxblog.core.bean.Label;
import com.wxblog.core.dao.LabelMapper;
import com.wxblog.web.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
    public List<Label> labels(Model model) {
        List<Label> labels=labelMapper.selectList(null);
        if (model!=null){
            model.addAttribute("labels",labels);
        }
        return labels;
    }

}
