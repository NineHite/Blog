package com.hitenine.blog.controller.portal;

import com.hitenine.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hitenine
 * @version 1.0
 * @date 2021/1/29 15:04
 */
@RestController
@RequestMapping("/portal/web_size_info")
public class WebSizeInfoPortalApi {

    @GetMapping("/categories")
    public ResponseResult getCategoryes() {
        return null;
    }

    @GetMapping("/title")
    public ResponseResult getWebSizeTitle(@RequestParam("title") String title) {
        return null;
    }

    @GetMapping("/web_view_count")
    public ResponseResult getWebViewCount() {
        return null;
    }

    @GetMapping("/seo")
    public ResponseResult getWebSizeSeoInfo() {
        return null;
    }

    @GetMapping("/loop")
    public ResponseResult getLoopers() {
        return null;
    }

    @GetMapping("/friend_link")
    public ResponseResult getFriendsLinks() {
        return null;
    }

}