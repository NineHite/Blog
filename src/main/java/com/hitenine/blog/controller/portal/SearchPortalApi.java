package com.hitenine.blog.controller.portal;

import com.hitenine.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hitenine
 * @version 1.0
 * @date 2021/1/29 15:53
 */
@RestController
@RequestMapping("/portal/search")
public class SearchPortalApi {

    @GetMapping
    public ResponseResult doSearch(@RequestParam("keyword") String keyword, @RequestParam("page") int page) {
        return null;
    }
}
