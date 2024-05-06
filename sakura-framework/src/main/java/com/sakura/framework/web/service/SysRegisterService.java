package com.sakura.framework.web.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.regex.RegexUtli;
import com.sakura.common.constant.Constants;
import com.sakura.common.constant.UserConstants;
import com.sakura.common.core.domain.JsonResult;
import com.sakura.common.core.domain.entity.RegisterBody;
import com.sakura.common.core.domain.entity.SysUser;
import com.sakura.common.core.redis.RedisCache;
import com.sakura.common.utils.MessageUtils;
import com.sakura.common.utils.PinYin4JCn;
import com.sakura.common.utils.SecurityUtils;
import com.sakura.framework.cache.ConfigUtils;
import com.sakura.framework.manager.AsyncManager;
import com.sakura.framework.manager.factory.AsyncFactory;
import com.sakura.system.service.ISysUserService;

/**
 * 注册校验方法
 *
 * @author liuzhi
 */
@Component
public class SysRegisterService {

    private static Logger log = LoggerFactory.getLogger(SysRegisterService.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 注册用户
     *
     * @param reg
     * @return
     */
    public JsonResult<RegisterBody> register(RegisterBody reg) {
        String msg = "",
               code = reg.getCode(),
               uuid = reg.getUuid(),
               name = reg.getName(),
               nickName = reg.getNickName(),
               no = reg.getNo(),
               userName = reg.getUserName(),
               passWord = reg.getPassWord(),
               deptId = reg.getDeptId(),
               userType = reg.getUserType(),
               status = reg.getStatus(),
               phoneNumber = reg.getPhoneNumber(),
               sex = reg.getSex(),
               email = reg.getEmail();

        String[] postIds = reg.getPostIds(), roleIds = reg.getRoleIds();

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
        } else {
            if (ConfigUtils.getConfigBooleanValueByKey("consys.account.captchaOnOfffigKey", Boolean.TRUE)) {
                msg = validateCaptcha(code, uuid);
            }
            SysUser user = new SysUser();
            user.setUserName(userName);
            user.setPhonenumber(phoneNumber);
            user.setEmail(email);
            if (StringUtils.isEmpty(msg)) {
                msg = userService.checkUserNameUniquen(user);
            }
            if (StringUtils.isEmpty(msg)) {
                msg = userService.checkPhoneUniquen(user);
            }
            if (StringUtils.isEmpty(msg)) {
                msg = userService.checkEmailUniquen(user);
            }
            if (StringUtils.isEmpty(msg)) {
                user.setNo(no);
                user.setSex(sex);
                user.setName(name);
                user.setDeptId(deptId);
                user.setPostIds(postIds);
                user.setRoleIds(roleIds);
                user.setNickName(nickName);
                user.setUserType(userType);
                user.setStatus(status);
                user.setPassword(SecurityUtils.encryptPassword(passWord));
                user.setUserPinyin(PinYin4JCn.getFullAndSimplePinYin(userName, 500));
                if (userService.insertUsern(user)) {
                    msg = MessageUtils.message("user.register.success");
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.REGISTER, msg));
                    return JsonResult.success(msg);
                } else {
                    msg = MessageUtils.message("user.register.error");
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.REGISTER, msg));
                }
            }
        }
        return JsonResult.error(msg);
    }

    public String register1(RegisterBody registerBody) {
        String msg = "", username = registerBody.getUserName(), password = registerBody.getPassWord();
        if (ConfigUtils.getConfigBooleanValueByKey("sys.account.captchaOnOff", Boolean.TRUE)) {
            msg = validateCaptcha(registerBody.getCode(), registerBody.getUuid());
        }
        if (StringUtils.isEmpty(username)) {
            msg = "用户名不能为空";
        } else if (StringUtils.isEmpty(password)) {
            msg = "用户密码不能为空";
        } else if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            msg = "账户长度必须在2到20个字符之间";
        } else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在5到20个字符之间";
        } else if (!RegexUtli.isChinese(username)) {
            msg = "用户名为非汉字";
        } else {
            SysUser userQuery = new SysUser();
            userQuery.setUserName(username);
            try {
                userService.checkUserNameUnique(userQuery);
            } catch (Exception e) {
                msg = "注册失败，[" + username + "]已存在";
                log.error(msg);
                e.printStackTrace();
            }
            if (StringUtils.isEmpty(msg)) {
                SysUser sysUser = new SysUser();
                sysUser.setId(registerBody.getUuid());
                sysUser.setUserName(username);
                sysUser.setNickName(username);
                sysUser.setPassword(SecurityUtils.encryptPassword(password));
                boolean regFlag = userService.registerUser(sysUser);
                if (!regFlag) {
                    msg = "注册失败,请联系后台管理人员";
                    log.error(msg);
                } else {
                    AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER,
                            MessageUtils.message("user.register.success")));
                }
            }
        }
        return msg;
    }

    /**
     * 校验验证码
     *
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String validateCaptcha(String code, String uuid) {
        String msg = "";
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        if (captcha == null) {
            msg = MessageUtils.message("user.jcaptcha.expire");
        }
        if (captcha != null && !code.equalsIgnoreCase(captcha)) {
            msg = MessageUtils.message("user.jcaptcha.error");
        }
        return msg;
    }
}
