package com.wxblog.web.service;

import com.wxblog.core.bean.Topic;
import com.wxblog.core.response.ResultJson;
import net.sf.jsqlparser.statement.select.Top;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface TopicService {

    boolean createTopic(Topic topic,String labelId, RedirectAttributes model);

    void topics(Model model);

    void topic(Long id,Model model);

    boolean edit(Topic topic,String labelId,RedirectAttributes model);

    ResultJson deleteTopic(Long id);

    ResultJson deleteTopics(Integer status);

    ResultJson deleteTopics(List<Long> ids);

    ResultJson destroy(List<Long> ids);
}
