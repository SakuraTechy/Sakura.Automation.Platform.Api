package com.sakura.system.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.system.domain.SysVersion;

/**
 * @author wutun
 * @data 2022/8/19 17:27
 */
public interface SysVersionService extends BaseService<SysVersion> {
    /**
     * 根据id批量删除版本信息
     * @param ids
     * @return
     */
    public int deleteSysVersionServiceByIds(String[] ids);

    /**
     * 根据id修改版本状态(0 开启，1 关闭)
     * @param id
     * @return
     */
    public boolean editSysVersionClosedById(String id);


    /**
     * 开启版本状态
     * @param id
     * @return
     */
    boolean editSysVersionOpenById(String id);


    /**
     * 编辑版本最新状态
     * @param sysVersion
     * @return
     */
    boolean editSysVersionIsLatestById(SysVersion sysVersion);

    boolean editSysVersionStatusById(String id);

    /**
     * 通过项目ID删除相关环境
     * @param ids
     */
    void deleteVersionByProjectIds(String[] ids);
}
