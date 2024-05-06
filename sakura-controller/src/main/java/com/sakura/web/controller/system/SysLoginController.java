package com.sakura.web.controller.system;

import com.sakura.common.constant.Constants;
import com.sakura.common.core.domain.AjaxResult;
import com.sakura.common.core.domain.JsonResult;
import com.sakura.common.core.domain.R;
import com.sakura.common.core.domain.entity.RegisterBody;
import com.sakura.common.core.domain.entity.SysMenu;
import com.sakura.common.core.domain.entity.SysUser;
import com.sakura.common.core.domain.model.LoginBody;
import com.sakura.common.core.domain.model.LoginUser;
import com.sakura.common.core.redis.RedisCache;
import com.sakura.common.utils.MessageUtils;
import com.sakura.common.utils.SecurityUtils;
import com.sakura.common.utils.ServletUtils;
import com.sakura.common.utils.StringUtils;
import com.sakura.framework.cache.ConfigUtils;
import com.sakura.framework.web.service.SysLoginService;
import com.sakura.framework.web.service.SysPermissionService;
import com.sakura.framework.web.service.SysRegisterService;
import com.sakura.framework.web.service.TokenService;
import com.sakura.system.domain.SysNotice;
import com.sakura.system.domain.SysPortalConfig;
import com.sakura.system.service.SysMenuService;
import com.sakura.system.service.SysNoticeService;
import com.sakura.system.service.SysPortalConfigService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 登录验证
 *
 * @author liuzhi
 */
@Api(tags = { "登录模块" }, description = "SysLoginController")
@ApiSupport(author = "刘智", order = 30)
@RestController
public class SysLoginController {

    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private SysNoticeService sysNoticeService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysPortalConfigService sysPortalConfigService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysRegisterService registerService;

    /**
     * 用户注册
     *
     * @param registerBody 注册信息
     * @return 结果
     */
    @ApiOperation(value = "用户注册", notes = "根据用户名称和用户密码注册用户", position = 10,produces = "application/json")
    @PostMapping("/register")
    public JsonResult<RegisterBody> register(@RequestBody RegisterBody registerBody){
        if(!ConfigUtils.getConfigBooleanValueByKey("sys.account.registerUser", Boolean.FALSE)){
            return JsonResult.error("当前系统没有开启注册功能！");
        }
        return registerService.register(registerBody);
    }

    /**
     * 用户登录
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @ApiOperation(value = "用户登录", notes = "根据用户名称和用户密码登录", position = 20,produces = "application/json")
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUserName(), loginBody.getPassWord(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.replace("msg", "登录成功");
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 忘记密码
     *
     * @param loginBody 登录信息
     * @return {@link JsonResult}<{@link LoginBody}>
     */
    @ApiOperation(value = "忘记密码", notes = "根据用户名称修改密码", position = 30,produces = "application/json")
    @PostMapping("/forgotPassword")
    public JsonResult<LoginBody> forgotPassword(@RequestBody LoginBody loginBody) {
        return loginService.forgotPassword(loginBody);
    }
}
