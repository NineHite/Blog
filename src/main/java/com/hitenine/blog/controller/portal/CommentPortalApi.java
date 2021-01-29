package com.hitenine.blog.controller.portal;

import com.hitenine.blog.pojo.Comment;
import com.hitenine.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/portal/comment")
public class CommentPortalApi {

    /**
     * 添加评论
     *
     * @param comment
     * @return
     */
    @PostMapping
    public ResponseResult postComment(@RequestBody Comment comment) {
        return null;
    }

    /**
     * 删除评论
     *
     * @param commentId
     * @return
     */
    @DeleteMapping("/{commentId}")
    public ResponseResult deleteComment(@PathVariable("commentId") String commentId) {
        return null;
    }

    @GetMapping("/list/{articleId}")
    public ResponseResult listCommentsByArticleId(@PathVariable("articleId") String articleId) {
        return null;
    }
}

