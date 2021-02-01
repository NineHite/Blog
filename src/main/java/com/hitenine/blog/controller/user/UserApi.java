package com.hitenine.blog.controller.user;

import com.hitenine.blog.pojo.User;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private UserService userService;

    /**
     * 初始化管理员账户-init-admin
     *
     * @param user
     * @param request
     * @return
     */
    @PostMapping("/admin_account")
    public ResponseResult initManagerAccount(@RequestBody User user, HttpServletRequest request) {
        log.info("user name == > " + user.getUserName());
        log.info("password == > " + user.getPassword());
        log.info("email == > " + user.getEmail());
        return userService.initManagerAccount(user, request);
    }

    /**
     * 注册
     *
     * @return
     */
    @PostMapping
    public ResponseResult register(HttpServletRequest request,
                                   @RequestBody User user,
                                   @RequestParam("verify_code") String emailCode,
                                   @RequestParam("captcha_code") String captchaCode,
                                   @RequestParam("captcha_key") String captchaKey) {
        return userService.register(request, user, emailCode, captchaCode, captchaKey);
    }

    /**
     * 登录sign-up
     *
     * @param captcha
     * @param user
     * @return
     */
    @PostMapping("/{captcha}")
    public ResponseResult login(@PathVariable("captcha") String captcha, @RequestBody User user) {
        return null;
    }

    /**
     * 获取图灵验证码
     *
     * @param response
     * @param captchaKey 以13位以上的时间秒数来确定每个用户的验证码（感觉有些bug）
     * @throws IOException
     * @throws FontFormatException
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response, @RequestParam("captcha_key") String captchaKey) {
        User user = new User();
        try {
            userService.getCaptcha(response, captchaKey);
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 发送邮件
     * <p>
     * 使用场景：注册、找回密码、修改邮箱（新的邮箱）
     * 注册：如果已经注册过了：该邮箱已经注册
     * 找回密码：如果已经注册过了：该邮箱没有被注册
     * 修改邮箱（新的邮箱）：如果已经注册过了：该邮箱已经注册
     * </p>
     *
     * @param request
     * @param emailAddress
     * @return
     */
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(HttpServletRequest request,
                                         @RequestParam("type") String type,
                                         @RequestParam("email") String emailAddress) {
        log.info("email == > " + emailAddress);
        return userService.sendEmail(request, type, emailAddress);
    }

    /**
     * 修改密码
     *
     * @param user
     * @return
     */
    @PutMapping("/password/{userId}")
    public ResponseResult updatePassword(@PathVariable("userId") String userId, @RequestBody User user) {
        return null;
    }

    /**
     * 获取作者信息user-info
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseResult getUserInfo(@PathVariable("userId") String userId) {
        return null;
    }

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseResult updateUserInfo(@PathVariable("userId") String userId, @RequestBody User user) {
        return null;
    }

    @GetMapping("/list")
    public ResponseResult listUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }

    @DeleteMapping("/{userId}")
    public ResponseResult deleteUser(@PathVariable("userId") String userId) {
        return null;
    }

}

