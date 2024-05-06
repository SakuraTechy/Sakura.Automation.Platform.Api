package com.sakura.system.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.system.domain.SysDataBaseConfig;
import com.sakura.system.domain.SysEnvironment;

/**
 * @author wutun
 */
public interface SysEnvironmentService extends BaseService<SysEnvironment> {
    /**
     * 根据ID批量删除环境信息记录
     * @param ids
     * @return
     */
    boolean deleteSysEnvironmentByIds(String[] ids);

    /**
     * 插入环境信息记录
     * @param entity
     * @return
     */
     boolean insert(SysEnvironment entity);

    /**
     * 严重数据库连接
     * @param sysDataBaseConfig
     * @return
     */
    boolean verifyDataSourceConn(SysDataBaseConfig sysDataBaseConfig);


}
