package com.sakura.system.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.system.domain.SysCase;

/**
 * @author wutun
 */
public interface SysCaseService extends BaseService<SysCase> {

    /**
     * 批量删除用例信息
     * @param ids
     * @return
     */
    boolean deleteSysCaseByIds(String[] ids);
}
