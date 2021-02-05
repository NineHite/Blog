package com.hitenine.blog.controller;

import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.response.ResponseState;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 错误码转统一返回的结果
 *
 * @author Hitenine
 * @version 1.0
 * @date 2021/2/4 20:53
 */
@RestController
public class ErrorPageController {

    @RequestMapping("/403")
    public ResponseResult page403() {
        return ResponseResult.RESULT(ResponseState.FORBIDDEN);
    }

    @RequestMapping("/404")
    public ResponseResult page404() {
        return ResponseResult.RESULT(ResponseState.NOT_FOUND);
    }

    @RequestMapping("/504")
    public ResponseResult page504() {
        return ResponseResult.RESULT(ResponseState.GATEWAY_TIMEOUT);
    }

    @RequestMapping("/505")
    public ResponseResult page505() {
        return ResponseResult.RESULT(ResponseState.HTTP_VERSION_NOT_SUPPORTED);
    }

}
