package com.hitenine.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hitenine.blog.dao.CategoryMapper;
import com.hitenine.blog.pojo.Category;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.CategoryService;
import com.hitenine.blog.utils.Constants;
import com.hitenine.blog.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author Hitenine
 * @version 1.0
 * @date 2021/1/29 14:43
 */
@Service
@Transactional
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private IdWorker idWorker;

    @Override
    public ResponseResult addCategory(Category category) {
        String name = category.getName();
        String description = category.getDescription();
        String pinyin = category.getPinyin();
        // 检查数据：分类名称、分类的拼音、顺序、描述
        if (StringUtils.isEmpty(name)) {
            return ResponseResult.FAILED("分类名称不可以为空");
        }
        if (StringUtils.isEmpty(pinyin)) {
            return ResponseResult.FAILED("分类拼音不可以为空");
        }
        if (StringUtils.isEmpty(description)) {
            return ResponseResult.FAILED("分类描述不可以为空");
        }
        // 补全数据
        category.setStatus("1");
        // mybatis-plus自动补全
        // category.setId(idWorker.nextId() + "");
        // category.setCreateTime(LocalDateTime.now());
        // category.setUpdateTime(LocalDateTime.now());
        // 保存数据
        categoryMapper.insert(category);
        // 返回结果
        return ResponseResult.SUCCESS("添加分类成功");
    }

    @Override
    public ResponseResult getCategory(String categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            return ResponseResult.FAILED("分类不存在");
        }
        return ResponseResult.SUCCESS("获取分类成功").setData(category);
    }

    @Override
    public ResponseResult listCategories(int page, int size) {
        page = Math.max(page, Constants.PageSize.DEFAULT_PAGE);
        size = Math.max(size, Constants.PageSize.MIN_SIZE);
        Page<Category> categoryPage = new Page<>(page, size);
        categoryPage.addOrder(OrderItem.desc("create_time"));
        Page<Category> all = categoryMapper.selectPage(categoryPage, null);
        return ResponseResult.SUCCESS("获取分类列表成功").setData(all);
    }

    @Override
    public ResponseResult updateCategory(String categoryId, Category category) {
        // 1.找出来
        Category categoryFromDb = categoryMapper.selectById(categoryId);
        if (categoryFromDb == null) {
            return ResponseResult.FAILED("分类不存在");
        }
        category.setId(categoryFromDb.getId());
        // 2.对内容进行判断，有些字段不可以为空
        String name = category.getName();
        if (!StringUtils.isEmpty(name)) {
            category.setName(name);
        }
        String pinyin = category.getPinyin();
        if (!StringUtils.isEmpty(pinyin)) {
            categoryFromDb.setPinyin(pinyin);
        }
        String description = category.getDescription();
        if (!StringUtils.isEmpty(description)) {
            categoryFromDb.setDescription(description);
        }
        categoryFromDb.setOrder(category.getOrder());
        // 3.保存数据
        categoryMapper.updateById(categoryFromDb);
        // 4.返回结果
        return ResponseResult.SUCCESS("分类修改成功");
    }

    @Override
    public ResponseResult deleteCategory(String categoryId) {
        int result = categoryMapper.deleteCategoryByUpdateState(categoryId);
        return result > 0 ? ResponseResult.SUCCESS("分类删除成功") : ResponseResult.FAILED("分类不存在");
    }
}
