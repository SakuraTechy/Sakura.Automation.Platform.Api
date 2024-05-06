package com.sakura.web.controller.project;

import com.sakura.common.annotation.Log;
import com.sakura.common.core.controller.BaseController;
import com.sakura.common.core.domain.R;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.enums.BusinessType;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.poi.ExcelUtil;
import com.sakura.framework.cache.DictUtils;
import com.sakura.project.domain.AutomationConfig;
import com.sakura.project.service.AutomationConfigService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自动化配置Controller
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-26
 */
@RestController
@RequestMapping("/project/automationConfig")
public class AutomationConfigController extends BaseController {

    @Autowired
    private AutomationConfigService automationConfigService;

    /**
     * 查询自动化配置列表
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:list')")
    @GetMapping("/list")
    public R<PageInfo> list(AutomationConfig automationConfig, HttpServletRequest request, HttpServletResponse response) {
        automationConfig.setPage(new PageDomain(request, response));
        return R.data(automationConfigService.findPage(automationConfig));
    }

    /**
     * 获取自动化配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:query')")
    @GetMapping(value = "/{id}")
    public R<AutomationConfig> detail(@PathVariable("id") String id) {
        return R.data(automationConfigService.get(id));
    }

    /**
     * 新增自动化配置
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:add')")
    @Log(title = "新增自动化配置", businessType = BusinessType.INSERT)
    @PostMapping
    public R add(@RequestBody @Validated AutomationConfig automationConfig) {
        boolean status = automationConfigService.save(automationConfig);
        return status?R.success(automationConfigService.get(automationConfig)):R.error();
    }

    /**
     * 修改自动化配置
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:edit')")
    @Log(title = "修改自动化配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public R edit(@RequestBody @Validated AutomationConfig automationConfig) {
        return R.status(automationConfigService.edit(automationConfig));
    }

    /**
     * 删除自动化配置
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:remove')")
    @Log(title = "删除自动化配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R remove(@PathVariable String[] ids) {
        return R.status(automationConfigService.deleteAutomationConfigByIds(ids));
    }

    /**
     * 删除自动化配置
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:edit')")
    @Log(title = "删除自动化配置", businessType = BusinessType.UPDATE)
    @PutMapping("/remove")
    public R remove(@RequestBody AutomationConfig automationConfig) {
        return R.status(automationConfigService.remove(automationConfig));
    }

    /**
     * 更新自动化配置状态
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:edit')")
    @Log(title = "更新自动化配置状态", businessType = BusinessType.UPDATE)
    @PutMapping("/updateStatus")
    public R updateStatus(@RequestBody AutomationConfig automationConfig) {
        return R.status(automationConfigService.updateStatus(automationConfig));
    }

    /**
     * 导出自动化配置列表
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:export')")
    @Log(title = "导出自动化配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@RequestBody @Validated AutomationConfig automationConfig, HttpServletResponse resp, HttpServletRequest req) throws IOException, NoSuchFieldException {
        automationConfigService.export(automationConfig,resp,req);
    }

    @GetMapping("/export1")
    public R export1(@RequestBody AutomationConfig automationConfig) {
        List<AutomationConfig> list = automationConfigService.findList(automationConfig);
        ExcelUtil<AutomationConfig> util = new ExcelUtil<AutomationConfig>(AutomationConfig.class);
        return util.exportExcel(list, "自动化配置数据");
    }

    /**
     * 新增项目配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:projectConfig:add')")
    @Log(title = "自动化配置-新增项目配置信息", businessType = BusinessType.UPDATE)
    @PostMapping("/addProject")
    public R addProject(@RequestBody @Validated AutomationConfig automationConfig) {
        return R.status(automationConfigService.addProject(automationConfig));
    }

    /**
     * 修改项目配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:projectConfig:edit')")
    @Log(title = "自动化配置-修改项目配置信息", businessType = BusinessType.UPDATE)
    @PutMapping("/editProject")
    public R editProject(@RequestBody @Validated AutomationConfig automationConfig) {
        return R.status(automationConfigService.editProject(automationConfig));
    }

    /**
     * 删除项目配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:projectConfig:remove')")
    @Log(title = "自动化配置-删除项目配置信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeProject")
    public R removeProject(@RequestBody @Validated AutomationConfig automationConfig) {
        return R.status(automationConfigService.removeProject(automationConfig));
    }

    /**
     * 新增Jenkins配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:jenkinsConfig:add')")
    @Log(title = "自动化配置-新增Jenkins配置信息", businessType = BusinessType.UPDATE)
    @PostMapping("/addJenkins")
    public R addJenkins(@RequestBody @Validated AutomationConfig automationConfig) {
        return R.status(automationConfigService.addJenkins(automationConfig));
    }

    /**
     * 修改Jenkins配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:jenkinsConfig:edit')")
    @Log(title = "自动化配置-修改Jenkins配置信息", businessType = BusinessType.UPDATE)
    @PutMapping("/editJenkins")
    public R editJenkins(@RequestBody @Validated AutomationConfig automationConfig) {
        return R.status(automationConfigService.editJenkins(automationConfig));
    }

    /**
     * 删除Jenkins配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:jenkinsConfig:remove')")
    @Log(title = "自动化配置-删除Jenkins配置信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeJenkins")
    public R removeJenkins(@RequestBody @Validated AutomationConfig automationConfig) {
        return R.status(automationConfigService.removeJenkins(automationConfig));
    }

    /**
     * 新增环境配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:environmentConfig:add')")
    @Log(title = "自动化配置-新增环境配置信息", businessType = BusinessType.UPDATE)
    @PostMapping("/addEnvironment")
    public R addEnvironment(@RequestBody @Validated AutomationConfig automationConfig) {
        return R.status(automationConfigService.addEnvironment(automationConfig));
    }

    /**
     * 修改环境配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:environmentConfig:edit')")
    @Log(title = "自动化配置-修改环境配置信息", businessType = BusinessType.UPDATE)
    @PutMapping("/editEnvironment")
    public R editEnvironment(@RequestBody @Validated AutomationConfig automationConfig) {
        return R.status(automationConfigService.editEnvironment(automationConfig));
    }

    /**
     * 删除环境配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:environmentConfig:remove')")
    @Log(title = "自动化配置-删除环境配置信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeEnvironment")
    public R removeEnvironment(@RequestBody @Validated AutomationConfig automationConfig) {
        return R.status(automationConfigService.removeEnvironment(automationConfig));
    }

    /**
     * 同步环境配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:environmentConfig:add')")
    @Log(title = "自动化配置-同步环境配置信息", businessType = BusinessType.CLEAN)
    @PostMapping("/syncEnvironment")
    public R syncEnvironment(@RequestBody @Validated AutomationConfig automationConfig) throws JsonProcessingException {
        if(StringUtils.isNotEmpty(automationConfig.getNames())){
            return R.status(automationConfigService.syncEnvironment(automationConfig));
        }
        return R.status(automationConfigService.syncEnvironmentAll(automationConfig));
    }

    /**
     * 新增浏览器配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:browserConfig:add')")
    @Log(title = "自动化配置-新增浏览器配置信息", businessType = BusinessType.UPDATE)
    @PostMapping("/addBrowser")
    public R addBrowser(@RequestBody @Validated AutomationConfig automationConfig) {
        return R.status(automationConfigService.addBrowser(automationConfig));
    }

    /**
     * 修改浏览器配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:browserConfig:edit')")
    @Log(title = "自动化配置-修改浏览器配置信息", businessType = BusinessType.UPDATE)
    @PutMapping("/editBrowser")
    public R editBrowser(@RequestBody @Validated AutomationConfig automationConfig) {
        return R.status(automationConfigService.editBrowser(automationConfig));
    }

    /**
     * 删除浏览器配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:automationConfig:browserConfig:remove')")
    @Log(title = "自动化配置-删除浏览器配置信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeBrowser")
    public R removeBrowser(@RequestBody @Validated AutomationConfig automationConfig) {
        return R.status(automationConfigService.removeBrowser(automationConfig));
    }

    /**
     * 根据字典类型查询字典数据信息等其他自定义信息
     */
    @GetMapping(value = "/getInitData/{dictTypes}")
    public R getInitData(@PathVariable String dictTypes) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.putAll(DictUtils.getMultiDictList(dictTypes));
        return R.data(dataMap);
    }

}