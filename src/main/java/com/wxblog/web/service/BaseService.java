package com.wxblog.web.service;

import org.springframework.ui.Model;

/**
 * @author: NightWish
 * @create:
 * @description: 基础服务
 **/
public interface BaseService {

    boolean loginAuthentication(String loginName, String password, Model model);
}
