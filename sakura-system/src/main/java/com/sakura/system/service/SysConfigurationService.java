package com.sakura.system.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.system.domain.SysConfiguration;

/**
 * @author wutun
 */
public interface SysConfigurationService extends BaseService<SysConfiguration> {

    /**
     * 批量删除配置信息
     * @param ids
     * @return
     */
    boolean deleteSysConfigurationByIds(String[] ids);


     String getPath(String name);
}
