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
     *             1.根据每个用户带来的 captcha_key 来确定用户
     *             2.随机验证码类型：gif、算数、字母数字
     *             3.随机验证码字体
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
     * @param emailAddress
     * @return
     */
    ResponseResult sendEmail(HttpServletRequest request, String emailAddress);
}
