package com.wxblog.web.service;

import com.wxblog.core.bean.Label;
import org.springframework.ui.Model;

import java.util.List;


public interface LabelService {

    List<Label> labels(Model model);

    List<String> namesOfLabel();

}
