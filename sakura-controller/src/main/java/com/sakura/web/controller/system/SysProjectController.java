package com.sakura.web.controller.system;

import com.sakura.common.annotation.Log;
import com.sakura.common.core.domain.JsonResult;
import com.sakura.common.core.domain.R;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.enums.BusinessType;
import com.sakura.system.domain.SysEnvironment;
import com.sakura.system.domain.SysProject;
import com.sakura.system.domain.SysThemeConfig;
import com.sakura.system.domain.SysYamlConfig;
import com.sakura.system.service.SysProjectService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 项目设置 项目管理
 *
 * @author wutun
 * @data 2022/8/18 15:49
 */

@RestController
@RequestMapping("/system/project")
public class SysProjectController {

    @Autowired
    private SysProjectService sysProjectService;

    /**
     * 查询项目列表信息
     *
     * @param sysProject
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:project:list')")
    @GetMapping("/list")
    public JsonResult<PageInfo> list(SysProject sysProject, HttpServletRequest request, HttpServletResponse response) {
        sysProject.setPage(new PageDomain(request, response));
        return JsonResult.success(sysProjectService.findPage(sysProject));
    }

    /**
     * 获取所有项目非分页
     *
     * @return
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:project:list')")
    @GetMapping("/getAll")
    public JsonResult getAll() {
        SysProject sysProject = new SysProject();
        return JsonResult.success(sysProjectService.findList(sysProject));
    }

    /**
     * 获取项目信息记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:project:list')")
    @GetMapping(value = "/{id}")
    public JsonResult<SysProject> detail(@PathVariable("id") String id) {
        return JsonResult.success(sysProjectService.get(id));
    }

    /**
     * 新增项目信息记录
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:project:add')")
    @Log(title = "用户主题信息记录", businessType = BusinessType.INSERT)
    @PostMapping
    public JsonResult add(@RequestBody @Validated SysProject sysProject) {
        return JsonResult.status(sysProjectService.insert(sysProject));
    }

    /**
     * 修改项目信息记录(先删除所有的成员，在添加成员)
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:project:edit')")
    @Log(title = "用户主题信息记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public JsonResult edit(@RequestBody @Validated SysProject sysProject) {
        return JsonResult.status(sysProjectService.save(sysProject));

    }

    /**
     * 删除项目信息记录
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:project:delete')")
    @Log(title = "用户主题信息记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public JsonResult remove(@PathVariable String[] ids) {
        return JsonResult.status(sysProjectService.deleteSysProjectByIds(ids));
    }

    /**
     * 获取详细环境配置信息
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:project:config')")
    @GetMapping(value = "/configDetail/{id}")
    public JsonResult<SysYamlConfig.Project> configDetail(@PathVariable("id") String id) throws IOException, NoSuchFieldException {
        return JsonResult.success(sysProjectService.getConfigDetail(id));
    }

    /**
     * 编辑环境配置
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:project:config')")
    @Log(title = "环境配置", businessType = BusinessType.UPDATE)
    @PutMapping("/editDetail")
    public JsonResult editConfig(@RequestBody @Validated List<SysEnvironment> sysEnvironmentList) {
        return JsonResult.status(sysProjectService.editEnvironmentConfig(sysEnvironmentList));
    }

    /**
     * 删除环境配置
     */
    @PreAuthorize("@ss.hasPermi('projectSetting:project:delete')")
    @Log(title = "删除环境配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeConfig/{projectId}/{environmentId}")
    public JsonResult removeConfig(@PathVariable("projectId") String projectId, @PathVariable("environmentId") String environmentId) {
        return JsonResult.status(sysProjectService.removeConfig(projectId, environmentId));
    }
}