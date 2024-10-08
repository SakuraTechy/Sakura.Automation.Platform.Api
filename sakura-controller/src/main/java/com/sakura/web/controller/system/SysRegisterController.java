package com.sakura.web.controller.system;

import com.sakura.common.core.controller.BaseController;
import com.sakura.common.core.domain.R;
import com.sakura.common.core.domain.entity.RegisterBody;
import com.sakura.framework.cache.ConfigUtils;
import com.sakura.framework.web.service.SysRegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证
 *
 * @author liuzhi
 */
@RestController
public class SysRegisterController extends BaseController {

    @Autowired
    private SysRegisterService registerService;

    @PostMapping("/register1")
    public R register(@RequestBody RegisterBody user) {
        if (!ConfigUtils.getConfigBooleanValueByKey("sys.account.registerUser", Boolean.FALSE)) {
            return R.fail("当前系统没有开启注册功能！");
        }
        String msg = registerService.register1(user);
        return StringUtils.isEmpty(msg) ? R.success() : R.fail(msg);
    }
}
