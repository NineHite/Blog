package com.hitenine.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hitenine.blog.dao.SettingMapper;
import com.hitenine.blog.dao.UserMapper;
import com.hitenine.blog.pojo.Setting;
import com.hitenine.blog.pojo.User;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.UserService;
import com.hitenine.blog.utils.*;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * <p>
 * 用户服务实现类
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
    private RedisUtils redisUtils;

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

    public static final int[] captcha_font_types = {
            Captcha.FONT_1, Captcha.FONT_2,
            Captcha.FONT_3, Captcha.FONT_4,
            Captcha.FONT_5, Captcha.FONT_6,
            Captcha.FONT_7, Captcha.FONT_8,
            Captcha.FONT_9, Captcha.FONT_10,
    };

    @Autowired
    private Random random;

    @Override
    public void getCaptcha(HttpServletResponse response, String captchaKey) throws Exception {
        if (StringUtils.isEmpty(captchaKey) || captchaKey.length() < 13) {
            return;
        }
        long key;
        try {
            key = Long.parseLong(captchaKey);
        } catch (Exception e) {
            return;
        }
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        int captchaType = random.nextInt(3);
        Captcha targetCaptcha;
        if (captchaType == 0) {
            // 三个参数分别为宽、高、位数
            targetCaptcha = new SpecCaptcha(200, 60, 5);
        } else if (captchaType == 1) {
            // gif类型
            targetCaptcha = new GifCaptcha(200, 60);
        } else {
            // 算术类型
            targetCaptcha = new ArithmeticCaptcha(200, 60);
            // targetCaptcha.setLen(2);  // 几位数运算，默认是两位
            // ((ArithmeticCaptcha) targetCaptcha).getArithmeticString();  // 获取运算的公式：3+2=?
            // targetCaptcha.text();  // 获取运算的结果：5
        }
        int index = random.nextInt(captcha_font_types.length);
        log.info("captcha font type index == > " + index);
        targetCaptcha.setFont(captcha_font_types[index]);
        targetCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        String content = targetCaptcha.text().toLowerCase();
        log.info("captcha content == > " + content);
        //保存redis中 10分钟有效
        redisUtils.set(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey, content, 10 * 60);
        // 输出图片流
        targetCaptcha.out(response.getOutputStream());
    }

    @Autowired
    private TaskService taskService;

    @Override
    public ResponseResult sendEmail(HttpServletRequest request, String emailAddress) {
        // 1.防止暴力发送，就是不断发送：同一个邮箱，间隔要超过30秒发一次，1小时内同一个IP最多只能发10次（如果是短信，最多只能发5次）
        String remoteAddr = request.getRemoteAddr();
        log.info("sendEmail == > ip == > " + remoteAddr);
        if (remoteAddr != null) {
            remoteAddr = remoteAddr.replaceAll(":", "_");
        }
        // 从redis拿出登录的ip或者email地址发送的次数，如果没有，那就过
        Integer ipSendTime = (Integer) redisUtils.get(Constants.User.KEY_EMAIL_SEND_IP + remoteAddr);
        if (ipSendTime != null && ipSendTime > 10) {
            return ResponseResult.FAILED("您发送验证码也太频繁了吧！");
        }
        Object hasEmailSent = redisUtils.get(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress);
        if (hasEmailSent != null) {
            return ResponseResult.FAILED("您发送验证码也太频繁了吧！");
        }
        // 2.检查邮箱地址是否正确
        boolean isEmailFormatOk = TextUtils.isEmailAddressOk(emailAddress);
        if (!isEmailFormatOk) {
            ResponseResult.FAILED("邮箱地址格式不正确");
        }
        int code = random.nextInt(9000000) + 100000;
        log.info("sendEmail == > code " + code);
        // 3.发送验证码，6位数：100000~999999
        try {
            taskService.sendEmailVerifyCode(String.valueOf(code), emailAddress);
        } catch (Exception e) {
            return ResponseResult.FAILED("验证码发送失败，请稍后重试。");
        }
        // 4.做记录
        // 发送记录，code
        //
        if (ipSendTime == null) {
            ipSendTime = 0;
        }
        ipSendTime++;
        // 1个小时有效期
        redisUtils.set(Constants.User.KEY_EMAIL_SEND_IP + remoteAddr, ipSendTime, 60 * 60);
        // 间隔三十秒
        redisUtils.set(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress, "true", 30);
        // 保存code，10分钟内有效
        redisUtils.set(Constants.User.KEY_EMAIL_CONTENT, String.valueOf(code), 10 * 60);
        return ResponseResult.SUCCESS("验证码发送成功");
    }
}
