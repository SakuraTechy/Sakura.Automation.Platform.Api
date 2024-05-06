package com.sakura.web.controller.system;

import com.sakura.common.core.domain.AjaxResult;
import com.sakura.common.core.domain.entity.SysMenu;
import com.sakura.common.core.domain.entity.SysUser;
import com.sakura.common.core.redis.RedisCache;
import com.sakura.common.utils.SecurityUtils;
import com.sakura.framework.web.service.SysPermissionService;
import com.sakura.system.domain.SysNotice;
import com.sakura.system.domain.SysPortalConfig;
import com.sakura.system.service.SysMenuService;
import com.sakura.system.service.SysNoticeService;
import com.sakura.system.service.SysPortalConfigService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 登录验证
 *
 * @author liuzhi
 */
@Api(tags = { "主页模块【需Authorization】" }, description = "SysHomeController")
@ApiSupport(author = "刘智", order = 40)
@RestController
 @RequestMapping("/home")
public class SysHomeController {

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private SysNoticeService sysNoticeService;

    @Autowired
    private SysPortalConfigService sysPortalConfigService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", position = 10)
    @GetMapping("/getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        //获取用户自定义首页
        Map<String, Object> resultMap = sysPortalConfigService.findUserConfigList(user);
        if (resultMap != null) {
            List<SysPortalConfig> userPortalConfig = (List<SysPortalConfig>) resultMap.get("portalList");
            SysPortalConfig defaultSysPortalConfig = (SysPortalConfig) resultMap.get("default");
            ajax.put("userPortalConfig", userPortalConfig);
            ajax.put("defaultPortalConfig", defaultSysPortalConfig);
        }
        //获取用户待读通知公告
        List<SysNotice> sysNoticeList = sysNoticeService.getNoticeListByUserId(user.getId());
        if (CollectionUtils.isEmpty(sysNoticeList)) {
            sysNoticeList = new ArrayList<SysNotice>();
        }
        String lincenseInfo = redisCache.getStringValue("sakura.license.info");
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        ajax.put("lincenseInfo", lincenseInfo);
        ajax.put("sysNoticeList", sysNoticeList);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @ApiOperation(value = "获取路由信息", notes = "获取路由信息", position = 20)
    @GetMapping("/getRouters")
    public AjaxResult getRouters() {
        // 用户信息
        SysUser user = SecurityUtils.getLoginUser().getUser();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(user.getId());
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
