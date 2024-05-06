package com.sakura.framework.web.service;

import javax.annotation.Resource;

import com.sakura.common.core.domain.JsonResult;
import com.sakura.common.core.domain.entity.SysUser;
import com.sakura.common.exception.ExpireException;
import com.sakura.common.utils.*;
import com.sakura.common.utils.date.DateUtils;
import com.sakura.common.utils.ip.IpUtils;
import com.sakura.common.utils.regex.RegexUtli;
import com.sakura.framework.cache.ConfigUtils;
import com.sakura.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Value;
import com.sakura.framework.manager.AsyncManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sakura.common.constant.Constants;
import com.sakura.common.constant.UserConstants;
import com.sakura.common.core.domain.model.LoginBody;
import com.sakura.common.core.domain.model.LoginUser;
import com.sakura.common.core.redis.RedisCache;
import com.sakura.common.exception.user.CaptchaException;
import com.sakura.common.exception.user.CaptchaExpireException;
import com.sakura.common.exception.user.UserPasswordNotMatchException;
import com.sakura.framework.manager.factory.AsyncFactory;

/**
 * 登录校验方法
 * 
 * @author liuzhi
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    // 是否允许账户多终端同时登录（true允许 false不允许）
    @Value("${token.soloLogin}")
    private boolean soloLogin;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        // 验证码开关
        if (ConfigUtils.getConfigBooleanValueByKey("sys.captcha.onOff", true)) {
            validateCaptcha(username, code, uuid);
        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 6K+l5pa55rOV5Lya5Y676LCD55SoVXNlckRldGFpbHNTZXJ2aWNlSW1wbC5sb2FkVXNlckJ5VXNlcm5hbWU=
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
                        MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me()
                        .execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ExpireException(e.getMessage());
            }
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if (!soloLogin) {
            // 如果用户不允许多终端同时登录，清除缓存信息
            String userIdKey = Constants.LOGIN_USERID_KEY + loginUser.getUser().getId();
            String userKey = redisCache.getCacheObject(userIdKey);
            if (StringUtils.isNotEmpty(userKey)) {
                redisCache.deleteObject(userIdKey);
                redisCache.deleteObject(userKey);
            }
        }
        // 生成token
        String token = tokenService.createToken(loginUser);
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS,
                MessageUtils.message("user.login.success")));
        recordLoginInfo(loginUser.getUser().getId());
        return token;
    }

    /**
     * 忘记密码
     *
     * @param loginBody 登录信息
     * @return {@link JsonResult}<{@link LoginBody}>
     */
    public JsonResult<LoginBody> forgotPassword(LoginBody loginBody) {
        String msg = "",
                userName = loginBody.getUserName(),
                passWord = loginBody.getPassWord(),
                uuid = loginBody.getUuid(),
                code = loginBody.getCode();

        if (StringUtils.isEmpty(userName)) {
            msg = "用户名不能为空";
        } else if (!RegexUtli.isUserName(userName)) {
            msg = "用户名非中文,字母或数字组合";
        } else if (StringUtils.isEmpty(passWord)) {
            msg = "用户密码不能为空";
        } else if (userName.length() < UserConstants.USERNAME_MIN_LENGTH
                || userName.length() > UserConstants.USERNAME_MAX_LENGTH) {
            msg = "账户长度必须在2到20个字符之间";
        } else if (passWord.length() < UserConstants.PASSWORD_MIN_LENGTH
                || passWord.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在5到20个字符之间";
        } else if (ConfigUtils.getConfigBooleanValueByKey("consys.account.captchaOnOfffigKey", Boolean.TRUE)) {
            msg = validateCaptcha(uuid, code);
        }
        if(StringUtils.isEmpty(msg)) {
            msg = userService.checkWhetherUserNameExists(loginBody);
            if (StringUtils.isEmpty(msg)) {
                loginBody.setPassWord(SecurityUtils.encryptPassword(passWord));
                if (userService.updatePassWord(loginBody) > 0) {
                    msg = MessageUtils.message("update.success");
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.REGISTER, msg));
                    return JsonResult.success(msg);
                } else {
                    msg = MessageUtils.message("update.error");
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.REGISTER, msg));
                }
            }
        }
        return JsonResult.error(msg);
    }

    /**
     * 校验验证码
     *
     * @param userName 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String userName, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGIN_FAIL,
                    MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    public String validateCaptcha(String uuid, String code) {
        String msg = "";
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteKey(verifyKey);
        if (captcha == null) {
            msg = MessageUtils.message("user.jcaptcha.expire");
        }
        if (captcha != null && !code.equalsIgnoreCase(captcha)) {
            msg = MessageUtils.message("user.jcaptcha.error");
        }
        return msg;
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(String userId) {
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserLoginInfo(sysUser);
    }

}
