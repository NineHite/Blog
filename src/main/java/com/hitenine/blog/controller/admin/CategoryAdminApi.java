package com.hitenine.blog.controller.admin;

import com.hitenine.blog.pojo.Category;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  管理中心，分类的API
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryAdminApi {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加分类
     *需要管理员权限
     *
     * @param category
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    /**
     * 删除分类
     *
     * @param categoryId
     * @return
     */
    @DeleteMapping("categoryId")
    public ResponseResult deleteCategory(@PathVariable("categoryId") String categoryId) {
        return null;
    }

    /**
     * 更新分类
     *
     * @param categoryId
     * @param category
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("/{categoryId}")
    public ResponseResult updateCategory(@PathVariable("categoryId") String categoryId, @RequestBody Category category) {
        return categoryService.updateCategory(categoryId, category);
    }

    /**
     * 获取分类
     * 修改的时候获取一下，填充弹窗
     * 不获取也是可以的，从列表获取
     * 管理员权限
     *
     * @param categoryId
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/{categoryId}")
    public ResponseResult getCategory(@PathVariable("categoryId") String categoryId) {
        return categoryService.getCategory(categoryId);
    }

    /**
     * 获取分类列表
     * 管理员权限
     *
     * @param page
     * @param size
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/list/{page}/{size}")
    public ResponseResult listCategories(@PathVariable("page") int page, @PathVariable("size") int size) {
        return categoryService.listCategories(page, size);
    }
}

