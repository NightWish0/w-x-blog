package com.wxblog.web.controller.admin;

import com.wxblog.core.bean.Label;
import com.wxblog.web.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: NightWish
 * @create:
 * @description:
 **/
@Controller
@RequestMapping("/admin")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @GetMapping("/labels")
    @ResponseBody
    public List<Label> labels(){
        return labelService.labels(null);
    }
}
