package com.hitenine.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hitenine.blog.pojo.User;
import com.hitenine.blog.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
public interface UserService extends IService<User> {

    /**
     * 初始化管理员
     *
     * @param user
     * @param request
     * @return
     */
    ResponseResult initManagerAccount(User user, HttpServletRequest request);

    /**
     * 获取验证码：
     * 1.根据每个用户带来的 captcha_key 来确定用户
     * 2.随机验证码类型：gif、算数、字母数字
     * 3.随机验证码字体
     *
     * @param response
     * @param captchaKey
     * @throws Exception
     */
    void getCaptcha(HttpServletResponse response, String captchaKey) throws Exception;

    /**
     * 发送邮箱验证码，避免邮箱轰炸
     *
     * @param request
     * @param type 类型：注册、找回密码、修改邮箱
     * @param emailAddress
     * @return
     */
    ResponseResult sendEmail(HttpServletRequest request, String type, String emailAddress);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    ResponseResult register(HttpServletRequest request, User user, String emailCode, String captchaCode, String captchaKey);

    /**
     *
     * @param captchaKey
     * @param captcha
     * @param user
     * @return
     */
    ResponseResult doLogin(String captchaKey, String captcha, User user);

    /**
     * 查看用户登录状态：未登录为null
     *
     * @return
     */
    User checkUser();

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    ResponseResult getUserInfo(String userId);

    /**
     * 检查邮箱是否注册
     *
     * @param email
     * @return
     */
    ResponseResult checkEmail(String email);

    /**
     * 检查用户名是否注册
     *
     * @param userName
     * @return
     */
    ResponseResult checkUserName(String userName);

    /**
     * 修改用户信息
     *
     * @param userId
     * @param user
     * @return
     */
    ResponseResult updateUserInfo(String userId, User user);

    /**
     * 根据用户id删除（修改状态）用户
     *
     * @param userId
     * @return
     */
    ResponseResult deleteUserById(String userId);

    /**
     * 获取用户列表
     * 权限：管理员权限
     *
     * @param page
     * @param size
     * @return
     */
    ResponseResult listUsers(int page, int size);

    /**
     * 修改密码
     *
     * @param verifyCode
     * @param user
     * @return
     */
    ResponseResult updatePassword(String verifyCode, User user);

    /**
     * 修改邮箱
     *
     * @param email
     * @param verifyCode
     * @return
     */
    ResponseResult updateEmail(String email, String verifyCode);

    ResponseResult doLogout();
}
