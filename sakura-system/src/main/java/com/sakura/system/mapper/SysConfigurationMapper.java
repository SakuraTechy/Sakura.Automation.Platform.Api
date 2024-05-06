package com.sakura.system.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.system.domain.SysConfiguration;
import org.mapstruct.Mapper;

/**
 * @author wutun
 */
public interface SysConfigurationMapper extends BaseMapper<SysConfiguration> {


    /**
     * 批量删除配置信息
     * @param ids
     * @return
     */
    boolean deleteSysConfigurationByIds(String[] ids);

    /**
     * 通过名称获取
     * @param paramsName
     * @return
     */
    String getPath(String paramsName);
}
