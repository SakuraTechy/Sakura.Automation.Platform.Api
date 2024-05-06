package com.sakura.web.controller.system;

import com.sakura.common.config.SakuraConfig;
import com.sakura.common.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 *
 * @author liuzhi
 */
@RestController
public class SysIndexController
{
    /** 系统基础配置 */
    @Autowired
    private SakuraConfig sakuraConfig;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index()
    {
        return StringUtils.format("欢迎使用{}后台管理框架，当前版本：{}，请通过前端地址访问。", sakuraConfig.getName(), sakuraConfig.getVersion());
    }
}
