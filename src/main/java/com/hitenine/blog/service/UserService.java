package com.hitenine.blog.service;

import com.hitenine.blog.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hitenine.blog.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;

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
}
