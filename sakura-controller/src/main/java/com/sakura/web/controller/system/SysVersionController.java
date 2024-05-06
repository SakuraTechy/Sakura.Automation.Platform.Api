package com.sakura.web.controller.system;

import com.sakura.common.annotation.Log;
import com.sakura.common.core.domain.JsonResult;
import com.sakura.common.core.domain.R;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.enums.BusinessType;
import com.sakura.system.domain.SysThemeConfig;
import com.sakura.system.domain.SysVersion;
import com.sakura.system.service.SysVersionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 项目设置 版本管理
 *
 * @author wutun
 */

@RestController
@RequestMapping("/system/version")
public class SysVersionController {

    @Autowired
    public SysVersionService sysVersionService;

    /**
     * 查询版本管理列表
     *
     * @param sysVersion
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:version:list')")
    @GetMapping("/list")
    public JsonResult<PageInfo> list(SysVersion sysVersion, HttpServletRequest request, HttpServletResponse response) {
        sysVersion.setPage(new PageDomain(request, response));
        return JsonResult.success(sysVersionService.findPage(sysVersion));
    }

    /**
     * 根据版本信息id查询详细记录
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:version:list')")
    @GetMapping(value = "/{id}")
    public JsonResult<SysVersion> detail(@PathVariable("id") String id) {
        return JsonResult.success(sysVersionService.get(id));
    }


    /**
     * 新增版本信息记录
     *
     * @param sysVersion
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:version:add')")
    @Log(title = "版本信息记录", businessType = BusinessType.INSERT)
    @PostMapping
    public JsonResult add(@RequestBody @Validated SysVersion sysVersion) {
        return JsonResult.status(sysVersionService.save(sysVersion));
    }

    /**
     * 修改版本信息记录
     *
     * @param sysVersion
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:version:edit')")
    @Log(title = "版本信息记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public JsonResult edit(@RequestBody @Validated SysVersion sysVersion) {
        return JsonResult.status(sysVersionService.save(sysVersion));

    }

    /**
     * 删除版本信息记录
     *
     * @param ids
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:version:delete')")
    @Log(title = "版本信息记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public JsonResult remove(@PathVariable String[] ids) {
        return JsonResult.status(sysVersionService.deleteSysVersionServiceByIds(ids));
    }

    /**
     * 改变版本状态
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:version:edit')")
    @GetMapping("/change/{id}")
    public JsonResult closed(@PathVariable("id") String id) {
        return JsonResult.status(sysVersionService.editSysVersionStatusById(id));
    }

    /**
     * 编辑版本最新状态
     *
     * @param sysVersion
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:version:edit')")
    @PutMapping("/latest")
    public JsonResult latest(@RequestBody @Validated SysVersion sysVersion) {
        return JsonResult.status(sysVersionService.editSysVersionIsLatestById(sysVersion));
    }

    /**
     * 通过项目信息获取所有版本信息
     *
     * @param projectId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:version:edit')")
    @GetMapping("/getVersion/{projectId}")
    public JsonResult getVersionByProjectId(@PathVariable("projectId") String projectId) {
        SysVersion sysVersion = new SysVersion();
        sysVersion.setProjectId(projectId);
        return JsonResult.success(sysVersionService.findList(sysVersion));
    }
}
