package com.sakura.web.controller.project;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sakura.common.annotation.Log;
import com.sakura.common.core.domain.R;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.enums.BusinessType;
import com.sakura.common.utils.poi.ExcelUtil;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.core.controller.BaseController;
import com.sakura.framework.cache.DictUtils;

import com.sakura.project.domain.ProjectConfig;
import com.sakura.project.service.ProjectConfigService;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 项目配置Controller
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-22
 */
@RestController
@RequestMapping("/project/projectConfig")
public class ProjectConfigController extends BaseController {

    @Autowired
    private ProjectConfigService projectConfigService;

    /**
     * 查询项目配置列表
     */
    @PreAuthorize("@ss.hasPermi('project:projectConfig:list')")
    @GetMapping("/list")
    public R<PageInfo> list(ProjectConfig projectConfig, HttpServletRequest request, HttpServletResponse response) {
        projectConfig.setPage(new PageDomain(request, response));
        if (projectConfig.getStatus() != null && projectConfig.getStatus() == 2) {
            projectConfig.setStatus(null);
        }
        if (StringUtils.inStringIgnoreCase(projectConfig.getCreateByName(), "全部")) {
            projectConfig.setCreateByName("");
        }
        return R.success(projectConfigService.findPage(projectConfig));
    }

    /**
     * 获取项目配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:projectConfig:query')")
    @GetMapping(value = "/{id}")
    public R<ProjectConfig> detail(@PathVariable("id") String id) {
        return R.success(projectConfigService.get(id));
    }

//    /**
//     * 获取项目环境配置
//     */
//    @PreAuthorize("@ss.hasPermi('project:projectConfig:query')")
//    @GetMapping(value = "/{id}")
//    public R<ProjectConfig> getEnvironmentConfig(@PathVariable("id") String id) {
//        return R.success(projectConfigService.getEnvironmentConfig(id));
//    }

    /**
     * 新增项目配置
     */
    @PreAuthorize("@ss.hasPermi('project:projectConfig:add')")
    @Log(title = "新增项目配置", businessType = BusinessType.INSERT)
    @PostMapping
    public R<?> add(@RequestBody @Validated ProjectConfig projectConfig) {
        return projectConfigService.save(projectConfig) ? R.success(projectConfigService.get(projectConfig)) : R.error();
    }

    /**
     * 修改项目配置
     */
    @PreAuthorize("@ss.hasPermi('project:projectConfig:edit')")
    @Log(title = "修改项目配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public R edit(@RequestBody @Validated ProjectConfig projectConfig) {
        return R.status(projectConfigService.edit(projectConfig));
    }

    /**
     * 删除项目配置
     */
    @PreAuthorize("@ss.hasPermi('project:projectConfig:remove')")
    @Log(title = "删除项目配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R remove(@PathVariable String[] ids) {
        return R.status(projectConfigService.deleteProjectConfigByIds(ids));
    }

    /**
     * 删除项目配置
     */
    @PreAuthorize("@ss.hasPermi('project:projectConfig:edit')")
    @Log(title = "删除项目配置", businessType = BusinessType.UPDATE)
    @PutMapping("/remove")
    public R remove(@RequestBody ProjectConfig projectConfig) {
        return R.status(projectConfigService.remove(projectConfig));
    }

    /**
     * 更新项目配置状态
     */
    @PreAuthorize("@ss.hasPermi('project:projectConfig:edit')")
    @Log(title = "更新项目配置状态", businessType = BusinessType.UPDATE)
    @PutMapping("/updateStatus")
    public R updateStatus(@RequestBody ProjectConfig projectConfig) {
        return R.status(projectConfigService.updateStatus(projectConfig));
    }

    /**
     * 批量导出项目配置列表
     */
    @PreAuthorize("@ss.hasPermi('project:projectConfig:export')")
    @Log(title = "批量导出项目配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R export(ProjectConfig projectConfig) {
        List<ProjectConfig> list = projectConfigService.findList(projectConfig);
        ExcelUtil<ProjectConfig> util = new ExcelUtil<ProjectConfig>(ProjectConfig.class);
        return util.exportExcel(list, "项目配置数据");
    }

    /**
     * 根据字典类型查询字典数据信息等其他自定义信息
     */
    @GetMapping(value = "/getInitData/{dictTypes}")
    public R getInitData(@PathVariable String dictTypes) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.putAll(DictUtils.getMultiDictList(dictTypes));
        return R.success(dataMap);
    }

}