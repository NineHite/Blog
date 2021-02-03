package com.hitenine.blog.controller;

import com.hitenine.blog.dao.CommentMapper;
import com.hitenine.blog.pojo.Comment;
import com.hitenine.blog.pojo.User;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.UserService;
import com.hitenine.blog.utils.Constants;
import com.hitenine.blog.utils.CookieUtils;
import com.hitenine.blog.utils.IdWorker;
import com.hitenine.blog.utils.RedisUtils;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @author Hitenine
 * @version 1.0
 * @date 2021/1/29 21:25
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IdWorker idWorker;

    @GetMapping("/hello_world")
    public ResponseResult helloworld() {
        log.info("hello world...");
        String value = (String) redisUtils.get(Constants.User.KEY_CAPTCHA_CONTENT + "123456");
        return ResponseResult.SUCCESS().setData(value);
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
        // specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
        specCaptcha.setFont(Captcha.FONT_1);
        // 设置类型，纯数字、纯字母、字母数字混合
        // specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);

        String content = specCaptcha.text().toLowerCase();
        log.info("captcha content == > " + content);
        // 验证码存入session
        // request.getSession().setAttribute("captcha", content); // 前后端分离保存到redis中
        //保存redis中 10分钟有效
        redisUtils.set(Constants.User.KEY_CAPTCHA_CONTENT + "123456", content, 60 * 10);
        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }

    @PostMapping("/comment")
    public ResponseResult testComment(@RequestBody Comment comment, HttpServletRequest request, HttpServletResponse response) {
        String content = comment.getContent();
        log.info("comment content == > " + content);
        // 还得知道是谁的评论，对这个评论，身份确定
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        if (tokenKey == null) {
            return ResponseResult.FAILED("账号未登录");
        }
        String token = (String) redisUtils.get(Constants.User.KEY_TOKEN + tokenKey);
        User user = userService.checkUser(request, response);
        if (user == null) {
            return ResponseResult.FAILED("账号未登录");
        }
        comment.setUserId(user.getId());
        comment.setUserAvatar(user.getAvatar());
        comment.setUserName(user.getUserName());
        comment.setCreateTime(LocalDateTime.now());
        comment.setUpdateTime(LocalDateTime.now());
        // comment.setId(idWorker.nextId() + ""); // mp默认雪花算法自动生成ID
        commentMapper.insert(comment);
        return ResponseResult.SUCCESS("评论成功");
    }
}
