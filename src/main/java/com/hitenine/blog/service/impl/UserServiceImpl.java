package com.hitenine.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hitenine.blog.dao.RefreshTokenMapper;
import com.hitenine.blog.dao.SettingMapper;
import com.hitenine.blog.dao.UserMapper;
import com.hitenine.blog.pojo.RefreshToken;
import com.hitenine.blog.pojo.Setting;
import com.hitenine.blog.pojo.User;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.response.ResponseState;
import com.hitenine.blog.service.UserService;
import com.hitenine.blog.utils.*;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Map;
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
    private RefreshTokenMapper refreshTokenMapper;

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
        redisUtils.set(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey, content, 10 * Constants.TimeValue.MIN);
        // 输出图片流
        targetCaptcha.out(response.getOutputStream());
    }

    @Autowired
    private TaskService taskService;

    /**
     * 发送邮件
     * <p>
     * 使用场景：注册、找回密码、修改邮箱（新的邮箱）
     * 注册(register)：如果已经注册过了：该邮箱已经被注册
     * 找回密码(forget)：如果已经注册过了：该邮箱没有被注册
     * 修改邮箱（新的邮箱）(update)：如果已经注册过了：该邮箱已经注册
     * </p>
     */
    @Override
    public ResponseResult sendEmail(HttpServletRequest request, String type, String emailAddress) {
        if (StringUtils.isEmpty(emailAddress)) {
            return ResponseResult.FAILED("邮箱地址不可以为空");
        }
        // 根据类型，查询邮箱是否存在
        User userByEmail = userMapper.selectOne(new QueryWrapper<User>().eq("email", emailAddress));
        if ("register".equals(type) || "update".equals(type)) {
            if (userByEmail != null) {
                return ResponseResult.FAILED("该邮箱已经被注册");
            }
        } else if ("forget".equals(type)) {
            if (userByEmail == null) {
                return ResponseResult.FAILED("该邮箱没有被注册");
            }
        }
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
        redisUtils.set(Constants.User.KEY_EMAIL_SEND_IP + remoteAddr, ipSendTime, Constants.TimeValue.HOUR);
        // 间隔三十秒
        redisUtils.set(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress, "true", 30);
        // 保存code，10分钟内有效
        redisUtils.set(Constants.User.KEY_EMAIL_CONTENT + emailAddress, String.valueOf(code), 10 * Constants.TimeValue.MIN);
        return ResponseResult.SUCCESS("验证码发送成功");
    }

    @Override
    public ResponseResult register(HttpServletRequest request, User user, String emailCode, String captchaCode, String captchaKey) {
        // 1.检查当前用户是否已经注册（可以前端异步操作，双重检测！）
        String userName = user.getUserName();
        if (StringUtils.isEmpty(user)) {
            return ResponseResult.FAILED("用户名不能为空");
        }
        // 从数据库中检查是否存在
        User userByUserName = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", userName));
        if (userByUserName != null) {
            return ResponseResult.FAILED("用户名已存在");
        }
        // 2.检查邮箱格式是否正确
        String email = user.getEmail();
        if (StringUtils.isEmpty(user)) {
            return ResponseResult.FAILED("邮箱地址不能为空");
        }
        if (!TextUtils.isEmailAddressOk(email)) {
            return ResponseResult.FAILED("邮箱格式不正确");
        }
        // 3.检查邮箱是否注册
        User userByEmail = userMapper.selectOne(new QueryWrapper<User>().eq("email", email));
        if (userByEmail != null) {
            return ResponseResult.FAILED("邮箱地址已存在");
        }
        // 4.检查邮箱验证码是否正确
        String verifyCode = (String) redisUtils.get(Constants.User.KEY_EMAIL_CONTENT + email);
        if (StringUtils.isEmpty(verifyCode)) {
            return ResponseResult.FAILED("邮箱验证已过期");
        }
        if (!verifyCode.equals(emailCode)) {
            return ResponseResult.FAILED("邮箱验证码不正确");
        } else {
            // 正确的话删除redis中的邮箱验证码
            redisUtils.del(Constants.User.KEY_EMAIL_CONTENT + email);
        }
        // 5.检查图灵验证码是否正确
        String captchaVerifyCode = (String) redisUtils.get(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        if (StringUtils.isEmpty(captchaVerifyCode)) {
            return ResponseResult.FAILED("图灵验证码已过期");
        }
        if (!captchaVerifyCode.equals(captchaCode)) {
            // 正确的话删除redis中的图灵验证码
            return ResponseResult.FAILED("图灵验证码不正确");
        } else {
            // 正确的话删除redis中的邮箱验证码
            redisUtils.del(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        }
        // 达到注册条件
        // 6.密码进行加密
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return ResponseResult.FAILED("密码不可以为空");
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
        // 7.补全数据
        // 包括：注册IP，登录IP，角色，头像，创建时间，更新时间（mp处理器自动添加），
        String remoteAddr = request.getRemoteAddr();
        user.setRegIp(remoteAddr);
        user.setLoginIp(remoteAddr);
        // LocalDateTime localDateTime = LocalDateTime.now();
        // user.setCreateTime(localDateTime);
        // user.setUpdateTime(localDateTime);
        user.setAvatar(Constants.User.DEFAULT_AVATAR);
        user.setRoles(Constants.User.ROLE_NORMAL);
        user.setState(Constants.User.DEFAULT_STATE);
        // 8.保存到数据库
        userMapper.insert(user);
        // 9.返回结果
        return ResponseResult.SUCCESS(ResponseState.JOIN_IN_SUCCESS);
    }

    @Override
    public ResponseResult doLogin(HttpServletRequest request,
                                  HttpServletResponse response,
                                  String captchaKey,
                                  String captcha,
                                  User user) {
        // captcha因为它是路径不能为空，所以captchaValue可能为空
        String captchaValue = (String) redisUtils.get(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        if (!captcha.equals(captchaValue)) {
            ResponseResult.FAILED("图灵验证码不正确");
        }
        // 有可能是用户名，也有可能是邮箱 让下面来做判断
        String userName = user.getUserName();
        if (StringUtils.isEmpty(userName)) {
            ResponseResult.FAILED("用户名不能为空");
        }
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            ResponseResult.FAILED("密码不能为空");
        }
        User userFromDb = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", userName));
        if (userFromDb == null) {
            userFromDb = userMapper.selectOne(new QueryWrapper<User>().eq("email", userName));
        }
        if (userFromDb == null) {
            return ResponseResult.FAILED("用户名或密码错误");
        }
        // 用户存在，对比密码
        boolean matches = bCryptPasswordEncoder.matches(password, userFromDb.getPassword());
        if (!matches) {
            ResponseResult.FAILED("用户名或密码错误");
        }
        // 密码正确
        // 判断用户状态，如果是非正常，则返回结果
        if (!Constants.User.DEFAULT_STATE.equals(userFromDb.getState())) {
            return ResponseResult.FAILED("当前账号已被禁止");
        }
        createToken(response, userFromDb);
        return ResponseResult.SUCCESS("登录成功");
    }

    /**
     * 检查用户是否有登录，如果登录就返回用户信息
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    public User checkUser(HttpServletRequest request, HttpServletResponse response) {
        // 拿到token_key
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        log.info("checkUse tokenKey == > " + tokenKey);
        User user = parseByTokenKey(tokenKey);
        if (user == null) {
            // 解析出错，过期了
            // 1.去数据库查询refreshToken
            RefreshToken refreshToken = refreshTokenMapper.selectOne(new QueryWrapper<RefreshToken>().eq("token_key", tokenKey));
            // 2.不存在就没有登录
            if (refreshToken == null) {
                log.info("refresh token is null...");
                return null;
            }
            // 3.如果存在，解析refreshToken
            try {
                JwtUtils.parseJWT(refreshToken.getRefreshToken());
                // 5.refreshToken有效，创建新的token和新的refreshToken
                String userId = refreshToken.getUserId();
                User userFromByDb = userMapper.selectById(userId);
                // 删掉refreshToken的记录
                // 创建新的
                String newTokenKey = createToken(response, userFromByDb);
                // 返回token
                log.info("created new token and refresh token");
                return parseByTokenKey(newTokenKey);
            } catch (Exception e1) {
                log.info("refresh token is expired...");
                // 4.如果解析refreshToken过期，当前访问信息没有登录，提示登录
                return null;
            }
        }
        return user;
    }

    /**
     * 删除旧的refresh token创建新的 返回token被md5加密后的tokenKey
     *
     * @param response
     * @param userFromDb
     * @return
     */
    private String createToken(HttpServletResponse response, User userFromDb) {
        refreshTokenMapper.delete(new QueryWrapper<RefreshToken>().eq("user_id", userFromDb.getId()));
        log.info("createToken deleted refresh token user_id == > " + userFromDb.getId());
        // 生成token
        Map<String, Object> claims = ClaimsUtils.userToClaims(userFromDb);
        // 默认有效两个小时
        String token = JwtUtils.createToken(claims);
        // 返回token的md5值，token保存在redis中
        // 前端访问的时候携带md5Key，从redis中获取token
        String tokenKey = DigestUtils.md5DigestAsHex(token.getBytes());
        // 保存token到redis里，有效时期为2个小时，key是token
        redisUtils.set(Constants.User.KEY_TOKEN + tokenKey, token, Constants.TimeValue.HOUR_2);
        // 把tokenKey写到cookies里
        // 这个要动态获取，可以从request里获取
        CookieUtils.setUpCookie(response, Constants.User.COOKIE_TOKEN_KEY, tokenKey);
        // TODO:生成refreshToken
        String refreshTokenValue = JwtUtils.createRefreshToken(userFromDb.getId(), Constants.TimeValue.MONTH);// 一个月有效
        // TODO: 保存数据库里
        // refreshToken tokenKey 用户id 创建更新时间
        RefreshToken refreshToken = new RefreshToken();
        // refreshToken.setId(idWorker.nextId() + "");
        refreshToken.setRefreshToken(refreshTokenValue);
        refreshToken.setUserId(userFromDb.getId());
        refreshToken.setTokenKey(tokenKey);
        // refreshToken.setCreateTime(LocalDateTime.now());
        // refreshToken.setUpdateTime(LocalDateTime.now());
        refreshTokenMapper.insert(refreshToken);
        return tokenKey;
    }

    /**
     * 解析tokenKey返回User
     *
     * @param tokenKey
     * @return
     */
    private User parseByTokenKey(String tokenKey) {
        String token = (String) redisUtils.get(Constants.User.KEY_TOKEN + tokenKey);
        if (token != null) {
            try {
                Claims claims = JwtUtils.parseJWT(token);
                return ClaimsUtils.claimsToUser(claims);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
