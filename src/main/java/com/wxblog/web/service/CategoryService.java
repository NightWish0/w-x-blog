package com.wxblog.web.service;

import com.wxblog.core.response.ResultJson;
import org.springframework.ui.Model;

public interface CategoryService {

    void categories(Model model);

    void blogCategoryModel(Long categoryId,Model model);

    ResultJson addCategory(String name);

    ResultJson updateCategory(Long id,String name);

    ResultJson deleteCategory(Long categoryId);
}
