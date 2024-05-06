package com.sakura.system.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.system.domain.SysVersion;

/**
 * 项目设置 版本管理Mapper借口
 * @author wutun
 * @data 2022/8/19 17:34
 */
public interface SysVersionMapper extends BaseMapper<SysVersion> {
    /**
     * 根据id批量删除
     * @param ids 需要删除的数据ID
     * @return
     */
    public int deleteSysVersionServiceByIds(String[] ids);

    /**
     * 根据id关闭版本状态
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
     * 将最新状态的置为否
     */
    void editLatestToOld(String projectId);

    /**
     * 将需要置为最新的置为新
     * @param id
     * @return
     */
    boolean editSysVersionIsLatestById(String id);

    /**
     * 改变版本状态
     * @param status
     * @return
     */
    boolean editSysVersionStatusById(SysVersion status);

    /**
     * 根据项目ID批量删除版本西悉尼
     * @param ids
     */
    void deleteVersionByProjectIds(String[] ids);
}
