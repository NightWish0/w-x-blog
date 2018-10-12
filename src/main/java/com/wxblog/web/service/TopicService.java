package com.wxblog.web.service;

import com.wxblog.core.bean.Topic;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface TopicService {

    boolean createTopic(Topic topic,String labelId, RedirectAttributes model);

    void topics(Model model);
}
