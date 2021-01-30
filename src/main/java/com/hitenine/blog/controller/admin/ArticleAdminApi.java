package com.hitenine.blog.controller.admin;


import com.hitenine.blog.pojo.Article;
import com.hitenine.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/admin/article")
public class ArticleAdminApi {

    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    @PostMapping
    public ResponseResult postArticle(@RequestBody Article article) {
        return null;
    }

    /**
     * 删除文章
     *
     * @param articleId
     * @return
     */
    @DeleteMapping("/{articleId}")
    public ResponseResult deleteArticle(@PathVariable("articleId") String articleId) {
        return null;
    }

    /**
     * 更新文章
     *
     * @param articleId
     * @param article
     * @return
     */
    @PutMapping("/{articleId}")
    public ResponseResult updateArticle(@PathVariable("articleId") String articleId, @RequestBody Article article) {
        return null;
    }

    /**
     * 获取文章
     *
     * @param articleId
     * @return
     */
    @GetMapping("/{articleId}")
    public ResponseResult getArticle(@PathVariable("articleId") String articleId) {
        return null;
    }

    /**
     * 获取文章列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listArticles(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }

    /**
     * 更新文章状态
     *
     * @param articleId
     * @param state
     * @return
     */
    @PutMapping("/state/{articleId}/{state}")
    public ResponseResult updateArticleState(@PathVariable("articleId") String articleId, @PathVariable("state") String state) {
        return null;
    }

    /**
     * 置顶文章
     *
     * @param articleId
     * @return
     */
    @PutMapping("/top/{articleId}")
    public ResponseResult topArticle(@PathVariable("articleId") String articleId) {
        return null;
    }
}

