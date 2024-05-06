package com.sakura.system.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.system.domain.SysElement;

/**
 * @author wutun
 */
public interface SysElementService extends BaseService<SysElement> {
    /**
     * 批量删除元素信息
     * @param ids
     * @return
     */
    boolean deleteSysElementServiceByIds(String[] ids);
}
