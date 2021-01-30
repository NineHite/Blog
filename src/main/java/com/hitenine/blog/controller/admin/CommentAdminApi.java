package com.hitenine.blog.controller.admin;


import com.hitenine.blog.pojo.Comment;
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
@RequestMapping("/admin/comment")
public class CommentAdminApi {

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

    /**
     * 获取评论列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listComments(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }

    @PutMapping("/top/{commentId}")
    public ResponseResult topComment(@PathVariable("commentId") String commentId) {
        return null;
    }
}

