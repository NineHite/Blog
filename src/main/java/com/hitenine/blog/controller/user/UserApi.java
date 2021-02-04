package com.hitenine.blog.controller.user;

import com.hitenine.blog.pojo.User;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
     *<p>
     *     需要的数据：
     *              1.用户账号（邮箱/用户名）
     *              2.密码
     *              3.图灵验证码
     *              4.图灵验证码key  captchaKey
     *</p>
     *
     * @param captchaKey
     * @param captcha
     * @param user
     * @return
     */
    @PostMapping("/{captcha}/{captcha_key}")
    public ResponseResult login(HttpServletRequest request,
                                HttpServletResponse response,
                                @PathVariable("captcha_key") String captchaKey,
                                @PathVariable("captcha") String captcha,
                                @RequestBody User user) {
        return userService.doLogin(request, response, captchaKey, captcha, user);
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
        return userService.getUserInfo(userId);
    }

    /**
     * 修改用户信息
     * <p>
     *     允许用户修改的内容：
     *                      1.头像
     *                      2.用户名（唯一的）
     *                      3.签名
     *                      4.密码（单独修改）
     *                      5.Email（唯一的，单独修改）
     * </p>
     *
     * @param user
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseResult updateUserInfo(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @PathVariable("userId") String userId,
                                         @RequestBody User user) {
        return userService.updateUserInfo(request, response, userId, user);
    }

    @GetMapping("/list")
    public ResponseResult listUsers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }

    /**
     * 需要管理员权限
     * 不是真的删除，而是需改状态c
     *
     * @param request
     * @param response
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseResult deleteUser(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @PathVariable("userId") String userId) {
        // 判断当前操作的用户是谁
        // 根据用户角色判断是否可以删除
        // TODO: 2021/2/4 通过注解的方式来控制权限
        return userService.deleteUserById(request, response, userId);
    }

    /**
     * 检查Email的唯一性，是否已经注册
     *
     * @param email 邮箱地址
     * @return SUCCESS -- > 已经注册，FAILED -- > 没有注册
     */
    @ApiResponses({
            @ApiResponse(code = 20000, message = "表示当前邮箱已经注册"),
            @ApiResponse(code = 40000, message = "表示当前邮箱未注册")
    })
    @GetMapping("/email")
    public ResponseResult checkEmail(@RequestParam("email") String email) {
        return userService.checkEmail(email);
    }

    /**
     * 检查用户名的唯一性，是否已经注册
     *
     * @param userName 用户名
     * @return SUCCESS -- > 已经注册，FAILED -- > 没有注册
     */
    @ApiResponses({
            @ApiResponse(code = 20000, message = "表示当前用户名已经注册"),
            @ApiResponse(code = 40000, message = "表示当前用户名未注册")
    })
    @GetMapping("/user_name")
    public ResponseResult checkUserName(@RequestParam("user_name") String userName) {
        return userService.checkUserName(userName);
    }

}

