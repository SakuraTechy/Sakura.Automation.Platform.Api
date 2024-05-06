package com.sakura.project.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.project.domain.EnvironmentConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 环境配置Service接口
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-26
 */
public interface EnvironmentConfigService extends BaseService<EnvironmentConfig> {

    /**
     * 修改环境配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean edit(EnvironmentConfig environmentConfig);

    /**
     * 批量删除环境配置
     * @param ids 需要删除的环境配置ID集合
     * @return 结果
     */
    public int deleteEnvironmentConfigByIds(String[] ids);

    /**
     * 批量删除环境配置
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Override
    boolean remove(EnvironmentConfig environmentConfig);

    /**
     * 更新环境配置状态
     * @param environmentConfig 环境配置
     * @return 结果
     */
    public boolean updateStatus(EnvironmentConfig environmentConfig);

    /**
     * 导出环境配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    void export(EnvironmentConfig environmentConfig, HttpServletResponse resp, HttpServletRequest req) throws IOException, NoSuchFieldException;

    /**
     * 新增版本配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean addVersion(EnvironmentConfig environmentConfig);

    /**
     * 修改版本配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean editVersion(EnvironmentConfig environmentConfig);

    /**
     * 删除版本配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean removeVersion(EnvironmentConfig environmentConfig);

    /**
     * 新增域名配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean addDomain(EnvironmentConfig environmentConfig);

    /**
     * 修改域名配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean editDomain(EnvironmentConfig environmentConfig);

    /**
     * 删除域名配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean removeDomain(EnvironmentConfig environmentConfig);

    /**
     * 新增帐号配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean addAccount(EnvironmentConfig environmentConfig);

    /**
     * 修改帐号配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean editAccount(EnvironmentConfig environmentConfig);

    /**
     * 删除帐号配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean removeAccount(EnvironmentConfig environmentConfig);

    /**
     * 新增服务器配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean addServer(EnvironmentConfig environmentConfig);

    /**
     * 修改服务器配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean editServer(EnvironmentConfig environmentConfig);

    /**
     * 删除服务器配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean removeServer(EnvironmentConfig environmentConfig);

    /**
     * 新增数据库配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean addDataBase(EnvironmentConfig environmentConfig);

    /**
     * 修改数据库配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean editDataBase(EnvironmentConfig environmentConfig);

    /**
     * 删除数据库配置信息
     * @param environmentConfig 环境配置
     * @return 结果
     */
    boolean removeDataBase(EnvironmentConfig environmentConfig);
}