package com.hitenine.blog.controller.admin;


import com.hitenine.blog.pojo.Category;
import com.hitenine.blog.response.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  管理中心，分类的API
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
@Controller
@RequestMapping("/admin/category")
public class CategoryAdminApi {

    /**
     * 添加分类
     *
     * @param category
     * @return
     */
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category) {
        return null;
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
    @PutMapping("/{categoryId}")
    public ResponseResult updateCategory(@PathVariable("categoryId") String categoryId, @RequestBody Category category) {
        return null;
    }

    /**
     * 获取分类
     *
     * @param categoryId
     * @return
     */
    @GetMapping("/{categoryId}")
    public ResponseResult getCategory(@PathVariable("categoryId") String categoryId) {
        return null;
    }

    /**
     * 获取分类列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listCategories(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }
}

