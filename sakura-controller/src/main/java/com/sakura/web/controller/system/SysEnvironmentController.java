package com.sakura.web.controller.system;

import com.sakura.common.annotation.Log;
import com.sakura.common.core.domain.JsonResult;
import com.sakura.common.core.domain.R;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.enums.BusinessType;
import com.sakura.system.domain.SysDataBaseConfig;
import com.sakura.system.domain.SysEnvironment;
import com.sakura.system.domain.SysProject;
import com.sakura.system.service.SysEnvironmentService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 项目设置 环境管理
 *
 * @author wutun
 */

@RestController
@RequestMapping("/system/environment")
public class SysEnvironmentController {


    @Autowired
    private SysEnvironmentService sysEnvironmentService;

    /**
     * 查询环境列表信息
     *
     * @param sysEnvironment
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:environment:list')")
    @GetMapping("/list")
    public JsonResult<PageInfo> list(SysEnvironment sysEnvironment, HttpServletRequest request, HttpServletResponse response) {
        sysEnvironment.setPage(new PageDomain(request, response));
        return JsonResult.success(sysEnvironmentService.findPage(sysEnvironment));
    }

    /**
     * 获取环境信息记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:environment:list')")
    @GetMapping(value = "/{id}")
    public JsonResult<SysEnvironment> detail(@PathVariable("id") String id) {
        return JsonResult.success(sysEnvironmentService.get(id));
    }

    /**
     * 新增环境信息记录
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:environment:add')")
    @Log(title = "用户主题信息记录", businessType = BusinessType.INSERT)
    @PostMapping
    public JsonResult add(@RequestBody @Validated SysEnvironment sysEnvironment) {
        return JsonResult.status(sysEnvironmentService.insert(sysEnvironment));
    }

    /**
     * 修改环境信息记录
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:environment:edit')")
    @Log(title = "环境信息记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public JsonResult edit(@RequestBody @Validated SysEnvironment sysEnvironment) {
        return JsonResult.status(sysEnvironmentService.save(sysEnvironment));
    }

    /**
     * 删除环境信息记录
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:environment:delete')")
    @Log(title = "环境信息记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public JsonResult remove(@PathVariable String[] ids) {
        return JsonResult.status(sysEnvironmentService.deleteSysEnvironmentByIds(ids));
    }

    /**
     * 校验数据库配置连接
     *
     * @param sysDataBaseConfig
     * @return
     */
    @PostMapping("/validate")
    public JsonResult validate(@RequestBody @Validated SysDataBaseConfig sysDataBaseConfig) {

        return JsonResult.status(sysEnvironmentService.verifyDataSourceConn(sysDataBaseConfig));
    }
}