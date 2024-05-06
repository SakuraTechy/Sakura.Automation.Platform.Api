package com.sakura.system.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.common.core.mapper.BaseTreeMapper;
import com.sakura.system.domain.SysCase;

/**
 * @author wutun
 */
public interface SysCaseMapper extends BaseMapper<SysCase> {

    /**
     * 批量删除用例信息
     * @param ids
     * @return
     */
    boolean deleteSysCaseByIds(String[] ids);

    /**
     * 删除用例通过节点ID
     * @param nodeId
     * @return
     */
    boolean deleteCaseByNodeId(String nodeId);
}
