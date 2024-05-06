package com.sakura.system.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.system.domain.SysElement;

/**
 * @author wutun
 */
public interface SysElementMapper extends BaseMapper<SysElement> {
    /**
     * 通过节点ID删除元素信息
     * @param id
     * @return
     */
    boolean deleteElementByNodeId(String id);

    /**
     * 批量删除元素库
     * @param ids
     * @return
     */
    boolean deleteSysElementServiceByIds(String[] ids);
}
