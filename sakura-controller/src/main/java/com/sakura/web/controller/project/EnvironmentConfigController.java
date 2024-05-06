package com.sakura.web.controller.project;

import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.sakura.common.annotation.Log;
import com.sakura.common.core.domain.R;
import com.github.pagehelper.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sakura.common.enums.BusinessType;
import com.sakura.common.utils.poi.ExcelUtil;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.core.controller.BaseController;
import com.sakura.framework.cache.DictUtils;

import com.sakura.project.domain.EnvironmentConfig;
import com.sakura.project.service.EnvironmentConfigService;

import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 环境配置Controller
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-26
 */
@RestController
@RequestMapping("/project/environmentConfig")
public class EnvironmentConfigController extends BaseController {

    @Autowired
    private EnvironmentConfigService environmentConfigService;

    /**
     * 查询环境配置列表
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:list')")
    @GetMapping("/list")
    public R<PageInfo> list(EnvironmentConfig environmentConfig, HttpServletRequest request, HttpServletResponse response) {
        environmentConfig.setPage(new PageDomain(request, response));
        if (environmentConfig.getStatus() != null && environmentConfig.getStatus() == 2) {
            environmentConfig.setStatus(null);
        }
        return R.data(environmentConfigService.findPage(environmentConfig));
    }

    /**
     * 获取环境配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:query')")
    @GetMapping(value = "/{id}")
    public R<EnvironmentConfig> detail(@PathVariable("id") String id) {
        return R.data(environmentConfigService.get(id));
    }

    /**
     * 新增环境配置
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:add')")
    @Log(title = "新增环境配置", businessType = BusinessType.INSERT)
    @PostMapping
    public R add(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        boolean status = environmentConfigService.save(environmentConfig);
        return status?R.success(environmentConfigService.get(environmentConfig)):R.error();
    }

    /**
     * 修改环境配置
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:edit')")
    @Log(title = "修改环境配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public R edit(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.edit(environmentConfig));
    }

    /**
     * 删除环境配置
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:remove')")
    @Log(title = "删除环境配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R remove(@PathVariable String[] ids) {
        return R.status(environmentConfigService.deleteEnvironmentConfigByIds(ids));
    }

    /**
     * 删除环境配置
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:edit')")
    @Log(title = "删除环境配置", businessType = BusinessType.UPDATE)
    @PutMapping("/remove")
    public R remove(@RequestBody EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.remove(environmentConfig));
    }

    /**
     * 更新环境配置状态
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:edit')")
    @Log(title = "更新环境配置状态", businessType = BusinessType.UPDATE)
    @PutMapping("/updateStatus")
    public R updateStatus(@RequestBody EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.updateStatus(environmentConfig));
    }

    /**
     * 导出环境配置列表
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:export')")
    @Log(title = "导出环境配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@RequestBody @Validated EnvironmentConfig environmentConfig, HttpServletResponse resp, HttpServletRequest req) throws IOException, NoSuchFieldException {
        environmentConfigService.export(environmentConfig,resp,req);
    }

    @GetMapping("/export1")
    public R export1(@RequestBody EnvironmentConfig environmentConfig) {
        List<EnvironmentConfig> list = environmentConfigService.findList(environmentConfig);
        ExcelUtil<EnvironmentConfig> util = new ExcelUtil<EnvironmentConfig>(EnvironmentConfig.class);
        return util.exportExcel(list, "环境配置数据");
    }

    /**
     * 新增版本配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:versionConfig:add')")
    @Log(title = "环境配置-新增版本配置信息", businessType = BusinessType.UPDATE)
    @PostMapping("/addVersion")
    public R addVersion(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.addVersion(environmentConfig));
    }

    /**
     * 修改版本配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:versionConfig:edit')")
    @Log(title = "环境配置-修改版本配置信息", businessType = BusinessType.UPDATE)
    @PutMapping("/editVersion")
    public R editVersion(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.editVersion(environmentConfig));
    }

    /**
     * 删除版本配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:versionConfig:remove')")
    @Log(title = "环境配置-删除版本配置信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeVersion")
    public R removeVersion(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.removeVersion(environmentConfig));
    }

    /**
     * 新增域名配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:domainConfig:add')")
    @Log(title = "环境配置-新增域名配置信息", businessType = BusinessType.UPDATE)
    @PostMapping("/addDomain")
    public R addDomain(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.addDomain(environmentConfig));
    }

    /**
     * 修改域名配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:domainConfig:edit')")
    @Log(title = "环境配置-修改域名配置信息", businessType = BusinessType.UPDATE)
    @PutMapping("/editDomain")
    public R editDomain(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.editDomain(environmentConfig));
    }

    /**
     * 删除域名配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:domainConfig:remove')")
    @Log(title = "环境配置-删除域名配置信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeDomain")
    public R removeDomain(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.removeDomain(environmentConfig));
    }

    /**
     * 新增帐号配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:accountConfig:add')")
    @Log(title = "环境配置-新增帐号配置信息", businessType = BusinessType.UPDATE)
    @PostMapping("/addAccount")
    public R addAccount(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.addAccount(environmentConfig));
    }

    /**
     * 修改帐号配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:accountConfig:edit')")
    @Log(title = "环境配置-修改帐号配置信息", businessType = BusinessType.UPDATE)
    @PutMapping("/editAccount")
    public R editAccount(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.editAccount(environmentConfig));
    }

    /**
     * 删除账号配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:accountConfig:remove')")
    @Log(title = "环境配置-删除账号配置信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeAccount")
    public R removeAccount(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.removeAccount(environmentConfig));
    }

    /**
     * 新增服务器配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:serverConfig:add')")
    @Log(title = "环境配置-新增服务器配置信息", businessType = BusinessType.UPDATE)
    @PostMapping("/addServer")
    public R addServer(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.addServer(environmentConfig));
    }

    /**
     * 修改服务器配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:serverConfig:edit')")
    @Log(title = "环境配置-修改服务器配置信息", businessType = BusinessType.UPDATE)
    @PutMapping("/editServer")
    public R editServer(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.editServer(environmentConfig));
    }

    /**
     * 删除服务器配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:serverConfig:remove')")
    @Log(title = "环境配置-删除服务器配置信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeServer")
    public R removeServer(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.removeServer(environmentConfig));
    }

    /**
     * 新增数据库配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:dataBaseConfig:add')")
    @Log(title = "环境配置-新增数据库配置信息", businessType = BusinessType.UPDATE)
    @PostMapping("/addDataBase")
    public R addDataBase(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.addDataBase(environmentConfig));
    }

    /**
     * 修改数据库配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:dataBaseConfig:edit')")
    @Log(title = "环境配置-修改数据库配置信息", businessType = BusinessType.UPDATE)
    @PutMapping("/editDataBase")
    public R editDataBase(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.editDataBase(environmentConfig));
    }

    /**
     * 删除数据库配置信息
     */
    @PreAuthorize("@ss.hasPermi('project:environmentConfig:dataBaseConfig:remove')")
    @Log(title = "环境配置-删除数据库配置信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/removeDataBase")
    public R removeDataBase(@RequestBody @Validated EnvironmentConfig environmentConfig) {
        return R.status(environmentConfigService.removeDataBase(environmentConfig));
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