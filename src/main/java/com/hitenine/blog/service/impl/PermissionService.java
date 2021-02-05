package com.hitenine.blog.service.impl;

import com.hitenine.blog.pojo.User;
import com.hitenine.blog.service.UserService;
import com.hitenine.blog.utils.Constants;
import com.hitenine.blog.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hitenine
 * @date 2021/2/4 19:51
 * @version 1.0
 */
@Service("permission")
public class PermissionService {

    @Autowired
    private UserService userService;

    /**
     * 判断是不是管理员
     *
     * @return
     */
    public boolean admin() {
        // 拿到request、response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        // 没有令牌的key，没有登录，不用往下执行了
        if (StringUtils.isEmpty(tokenKey)) {
            return false;
        }
        // 检验当前操作的用户是谁
        User currentUser = userService.checkUser();
        if (currentUser == null) {
            return false;
        }
        // 判断角色
        if (Constants.User.ROLE_ADMIN.equals(currentUser.getRoles())) {
            return true;
        }
        return false;
    }
}
