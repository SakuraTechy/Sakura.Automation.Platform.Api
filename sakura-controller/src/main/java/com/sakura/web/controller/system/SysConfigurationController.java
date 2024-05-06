package com.sakura.web.controller.system;

import com.sakura.common.annotation.Log;
import com.sakura.common.core.domain.JsonResult;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.enums.BusinessType;
import com.sakura.system.domain.SysConfiguration;
import com.sakura.system.service.SysConfigurationService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wutun
 */
@RestController
@RequestMapping("/system/configuration")
public class SysConfigurationController {

    @Autowired
    private SysConfigurationService sysConfigurationService;


    /**
     * 获取配置管理列表
     * @param sysConfiguration
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:configManage:list')")
    @GetMapping("/list")
    public JsonResult<PageInfo> list(SysConfiguration sysConfiguration, HttpServletRequest request, HttpServletResponse response){
        sysConfiguration.setPage(new PageDomain(request,response));
        return JsonResult.success(sysConfigurationService.findPage(sysConfiguration));
    }



    /**
     * 根据id查询配置管理列表
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:configManage:list')")
    @GetMapping(value = "/{id}")
    public JsonResult<SysConfiguration> detail(@PathVariable("id") String id)
    {
        return JsonResult.success(sysConfigurationService.get(id));
    }


    /**
     * 新增配置信息记录
     * @param sysConfiguration
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:configManage:list')")
    @Log(title = "配置信息记录", businessType = BusinessType.INSERT)
    @PostMapping
    public JsonResult add(@RequestBody @Validated SysConfiguration sysConfiguration)
    {
        return JsonResult.status(sysConfigurationService.save(sysConfiguration));
    }

    /**
     * 修改配置信息记录
     * @param sysConfiguration
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:configManage:list')")
    @Log(title = "配置信息记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public JsonResult edit(@RequestBody @Validated SysConfiguration sysConfiguration)
    {
        return JsonResult.status(sysConfigurationService.save(sysConfiguration));

    }

    /**
     * 删除配置信息记录
     * @param ids
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:configManage:list')")
    @Log(title = "配置信息记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public JsonResult remove(@PathVariable String[] ids)
    {
        return JsonResult.status(sysConfigurationService.deleteSysConfigurationByIds(ids));
    }

}
