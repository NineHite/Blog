package com.hitenine.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hitenine.blog.pojo.Category;
import com.hitenine.blog.response.ResponseResult;

public interface CategoryService extends IService<Category> {
    ResponseResult addCategory(Category category);

    ResponseResult getCategory(String categoryId);

    ResponseResult listCategories(int page, int size);

    ResponseResult updateCategory(String categoryId, Category category);
}
