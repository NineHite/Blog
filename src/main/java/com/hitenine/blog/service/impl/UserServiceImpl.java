package com.hitenine.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hitenine.blog.dao.SettingMapper;
import com.hitenine.blog.dao.UserMapper;
import com.hitenine.blog.pojo.Setting;
import com.hitenine.blog.pojo.User;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.UserService;
import com.hitenine.blog.utils.Constants;
import com.hitenine.blog.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * <p>
 *  用户服务实现类
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
@Slf4j
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private IdWorker idWorker;

    @Override
    public ResponseResult initManagerAccount(User user, HttpServletRequest request) {
        // 当前时间
        LocalDateTime now = LocalDateTime.now();
        // 检查是否初始化
        Setting managerAccountState = settingMapper.selectOne(new QueryWrapper<Setting>().eq("`key`", Constants.Setting.MANAGER_ACCOUNT_INIT_STATE));
        if (managerAccountState != null) {
            return ResponseResult.FAILED("管理员账号已经初始化了");
        }
        //TODO:
        // 检查数据
        if (StringUtils.isEmpty(user.getUserName())) {
            return ResponseResult.FAILED("用户名不能为空");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            return ResponseResult.FAILED("密码不能为空");
        }
        if (StringUtils.isEmpty(user.getEmail())) {
            return ResponseResult.FAILED("邮箱不能为空");
        }
        // 补充数据
        // user.setId(String.valueOf(idWorker.nextId()));
        user.setRoles(Constants.User.ROLE_ADMIN);
        user.setAvatar(Constants.User.DEFAULT_AVATAR);
        user.setState(Constants.User.DEFAULT_STATE);
        String remoteAddr = request.getRemoteAddr();
        String localAddr = request.getLocalAddr();
        log.info("remoteAddr == > " + remoteAddr);
        log.info("localAddr == > " + localAddr);
        user.setLoginIp(remoteAddr);
        user.setRegIp(remoteAddr);
        // user.setCreateTime(now);
        // user.setUpdateTime(now);
        // 对密码进行加密
        // 原密码
        String originPassword = user.getPassword();
        // 加密
        String encode = bCryptPasswordEncoder.encode(originPassword);
        user.setPassword(encode);

        // 保存到数据库
        userMapper.insert(user);
        // 更新已添加的标记
        // 更定没有
        Setting setting = new Setting();
        // setting.setId(String.valueOf(idWorker.nextId()));
        // setting.setCreateTime(now);
        // setting.setUpdateTime(now);
        setting.setKey(Constants.Setting.MANAGER_ACCOUNT_INIT_STATE);
        setting.setValue("1");
        settingMapper.insert(setting);
        return ResponseResult.SUCCESS("初始化成功");
    }
}
