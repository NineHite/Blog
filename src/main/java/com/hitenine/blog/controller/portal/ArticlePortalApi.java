package com.hitenine.blog.controller.portal;

import com.hitenine.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hitenine
 * @version 1.0
 * @date 2021/1/29 15:38
 */
@RestController
@RequestMapping("/portal/article")
public class ArticlePortalApi {

    @GetMapping("/list/{page}/{size}")
    public ResponseResult listArticle(@PathVariable String page, @PathVariable String size) {
        return null;
    }

    @GetMapping("/list/{categoryId}/{page}/{size}")
    public ResponseResult listArticleByCategoryId(@PathVariable("categoryId") String categoryId,
                                                  @PathVariable("page") String page,
                                                  @PathVariable("size") String size) {
        return null;
    }

    @GetMapping("/{articleId}")
    public ResponseResult getArticleDetail(@PathVariable("articleId") String articleId) {
        return null;
    }

    @GetMapping("/recommend/{articleId}")
    public ResponseResult getRecommendArticles(@PathVariable("articleId") String articleId) {
        return null;
    }
}
