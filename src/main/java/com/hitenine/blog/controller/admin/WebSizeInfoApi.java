package com.hitenine.blog.controller.admin;

import com.hitenine.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author Hitenine
 * @version 1.0
 * @date 2021/1/29 15:04
 */
@RestController
@RequestMapping("/admin/web_size_info")
public class WebSizeInfoApi {

    @GetMapping("/title")
    public ResponseResult getWebSizeTitle() {
        return null;
    }

    @PutMapping("/title")
    public ResponseResult upWebSizeTitle(@RequestParam("title") String title) {
        return null;
    }

    @GetMapping("/seo")
    public ResponseResult getSeoInfo() {
        return null;
    }

    @PutMapping("/seo")
    public ResponseResult putSeoInfo(@RequestParam("keywords") String keywords, @RequestParam("description") String description ) {
        return null;
    }

    @GetMapping("/web_size_count")
    public ResponseResult getWebSizeViewCount() {
        return null;
    }
}