package com.sakura.project.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.project.domain.AutomationConfig;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自动化配置Service接口
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-26
 */
public interface AutomationConfigService extends BaseService<AutomationConfig> {

    /**
     * 修改自动化配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean edit(AutomationConfig automationConfig);

    /**
     * 批量删除自动化配置
     * @param ids 需要删除的自动化配置ID集合
     * @return 结果
     */
    public int deleteAutomationConfigByIds(String[] ids);

    /**
     * 批量删除自动化配置
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Override
    boolean remove(AutomationConfig automationConfig);

    /**
     * 更新自动化配置状态
     * @param automationConfig 自动化配置
     * @return 结果
     */
    public int updateStatus(AutomationConfig automationConfig);

    /**
     * 导出自动化配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    void export(AutomationConfig automationConfig, HttpServletResponse resp, HttpServletRequest req) throws IOException, NoSuchFieldException;

    /**
     * 新增项目配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean addProject(AutomationConfig automationConfig);

    /**
     * 修改项目配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean editProject(AutomationConfig automationConfig);

    /**
     * 删除项目配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean removeProject(AutomationConfig automationConfig);

    /**
     * 新增Jenkins配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean addJenkins(AutomationConfig automationConfig);

    /**
     * 修改Jenkins配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean editJenkins(AutomationConfig automationConfig);

    /**
     * 删除Jenkins配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean removeJenkins(AutomationConfig automationConfig);

    /**
     * 新增环境配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean addEnvironment(AutomationConfig automationConfig);

    /**
     * 修改环境配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean editEnvironment(AutomationConfig automationConfig);

    /**
     * 删除环境配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean removeEnvironment(AutomationConfig automationConfig);

    /**
     * 同步环境配置信息（所有）
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean syncEnvironmentAll(AutomationConfig automationConfig) throws JsonProcessingException;

    /**
     * 同步环境配置信息（单个）
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean syncEnvironment(AutomationConfig automationConfig) throws JsonProcessingException;

    /**
     * 新增浏览器配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean addBrowser(AutomationConfig automationConfig);

    /**
     * 修改浏览器配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean editBrowser(AutomationConfig automationConfig);

    /**
     * 删除浏览器配置信息
     * @param automationConfig 自动化配置
     * @return 结果
     */
    boolean removeBrowser(AutomationConfig automationConfig);
}