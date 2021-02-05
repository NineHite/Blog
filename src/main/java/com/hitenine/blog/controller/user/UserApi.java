package com.hitenine.blog.controller.user;

import com.hitenine.blog.pojo.User;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PostMapping("/join_in")
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
    @PostMapping("/login/{captcha}/{captcha_key}")
    public ResponseResult login(@PathVariable("captcha_key") String captchaKey,
                                @PathVariable("captcha") String captcha,
                                @RequestBody User user) {
        return userService.doLogin(captchaKey, captcha, user);
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
     * 普通做法：通过旧密码对比来更新密码
     *
     * 即可以找回密码，也可以修改密码
     * 找回密码：发送邮箱验证码/手机 -- > 判断验证码是否正确来
     * 对应邮箱/手机号码注册的账号是否属于你
     *
     * 步骤：
     *      1.用户填写邮箱
     *      2.用户获取验证码type = forget
     *      3.填写验证码
     *      4.用户填写新的密码
     *      5.提交数据：邮箱、密码、新密码、验证码（正确）
     *
     *
     * 注册(register)：如果已经注册过了：该邮箱已经被注册
     * 找回密码(forget)：如果已经注册过了：该邮箱没有被注册
     * 修改邮箱（新的邮箱）(update)：如果已经注册过了：该邮箱已经注册
     *
     * @param user
     * @return
     */
    @PutMapping("/password/{verify_code}")
    public ResponseResult updatePassword(@PathVariable("verify_code") String verifyCode,
                                         @RequestBody User user) {
        return userService.updatePassword(verifyCode, user);
    }

    /**
     * 获取作者信息user-info
     *
     * @param userId
     * @return
     */
    @GetMapping("/user_info/{userId}")
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
    @PutMapping("/user_info/{userId}")
    public ResponseResult updateUserInfo(@PathVariable("userId") String userId,
                                         @RequestBody User user) {
        return userService.updateUserInfo(userId, user);
    }

    /**
     * 获取用户列表
     * 权限：管理员权限
     *
     * @param page
     * @param size
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/list")
    public ResponseResult listUsers(@RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        return userService.listUsers(page, size);
    }

    /**
     * 需要管理员权限
     * 不是真的删除，而是需改状态c
     *
     * @param userId
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @DeleteMapping("/{userId}")
    public ResponseResult deleteUser(@PathVariable("userId") String userId) {
        // 判断当前操作的用户是谁
        // 根据用户角色判断是否可以删除
        // TODO: 2021/2/4 通过注解的方式来控制权限
        return userService.deleteUserById(userId);
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

    /**
     * 1.必须已经登录
     * 2.新的邮箱没有注册过
     *
     * 用户步骤：
     *          1.登录
     *          2.输入新的邮箱地址
     *          3.获取验证码 type=update
     *          4.输入验证码
     *          5.提交数据
     *
     * 需要提交的数据：
     *              1.新的邮箱
     *              2.验证码
     *              3.其他信息从token中获取
     *
     * @return
     */
    @PutMapping("/email")
    public ResponseResult updateEmail(@RequestParam("email") String email,
                                      @RequestParam("verify_code") String verifyCode) {
        return userService.updateEmail(email, verifyCode);
    }

    /**
     * 退出登录
     * 拿到koken_key
     * 删除redis里对应的token
     * 删除mysql里对应的refreshToken
     * 删除cookie里的tokenKey
     *
     * @return
     */
    @GetMapping("/logout")
    public ResponseResult logout() {
        return userService.doLogout();
    }

}

