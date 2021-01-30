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
    public ResponseResult register() {
        // 1.检查当前用户是否已经注册（可以前端异步操作，双重检测！）
        // 2.检查邮箱格式是否正确
        // 3.检查邮箱是否注册
        // 4.检查邮箱验证码是否正确
        // 5.检查图灵验证码是否正确
        // 达到注册条件
        // 6.密码进行加密
        // 7.补全数据
        // 包括：注册IP，登录IP，角色，头像，创建时间，更新时间（mp处理器自动添加），
        // 8.保存到数据库
        // 9.返回结果
        return null;
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

    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(HttpServletRequest request, @RequestParam("email") String emailAddress) {
        log.info("email == > " + emailAddress);
        return userService.sendEmail(request, emailAddress);
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

