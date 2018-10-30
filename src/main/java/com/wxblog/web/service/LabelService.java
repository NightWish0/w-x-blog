package com.wxblog.web.service;

import com.wxblog.core.bean.Label;
import com.wxblog.core.response.ResultJson;
import org.springframework.ui.Model;

import java.util.List;


public interface LabelService {

    List<Label> labels(boolean showTopicTotal,Model model);

    List<String> namesOfLabel();

    ResultJson addLabel(String name);

    ResultJson updateLabel(Long id,String name);

    ResultJson deleteLabel(Long id);
}
