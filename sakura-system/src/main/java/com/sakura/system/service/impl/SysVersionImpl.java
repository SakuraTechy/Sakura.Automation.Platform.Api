package com.sakura.system.service.impl;

import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.common.exception.SysException;
import com.sakura.common.utils.bean.BeanUtils;
import com.sakura.common.utils.log.ContextHandler;
import com.sakura.system.domain.SysThemeConfig;
import com.sakura.system.domain.SysVersion;
import com.sakura.system.mapper.SysProjectMapper;
import com.sakura.system.mapper.SysVersionMapper;
import com.sakura.system.service.SysVersionService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目设置 版本管理service业务层处理
 * @author wutun
 *
 */
@Service
public class SysVersionImpl extends BaseServiceImpl<SysVersionMapper, SysVersion> implements SysVersionService
{
    private static final Logger log = LoggerFactory.getLogger(SysVersionImpl.class);


    @Autowired
    private SysProjectMapper sysProjectMapper;
    /**
     * 获取单条数据
     * @param sysVersion
     * @return
     */
    @Override
    public SysVersion get(SysVersion sysVersion)
    {
        SysVersion dto = super.get(sysVersion);
        return dto;
    }

    /**
     * id获取单条数据
     * @param id
     * @return
     */
    @Override
    public SysVersion get(String id)
    {
        SysVersion dto = super.get(id);
        return dto;
    }

    /**
     * 查询版本管理列表
     * @param entity
     * @return
     */
    @Override
    public List<SysVersion> findList(SysVersion entity) {
        return super.findList(entity);
    }

    /**
     * 分页查询版本管理列表
     * @param entity
     * @return
     */
    @Override
    public PageInfo<SysVersion> findPage(SysVersion entity) {

        return super.findPage(entity);
    }

    /**
     * 保存版本信息
     * @param entity
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public boolean save(SysVersion entity) {
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            //设置默认开启状态
            entity.setStatus(SysVersion.STATUS_NORMAL);
            //当前插入数据是否为设置最新版本状态
            if(entity.getIsLatest().equals(SysVersion.IS_LATEST_TRUE)){
                mapper.editLatestToOld(entity.getProjectId());
                sysProjectMapper.editProjectVersion(entity);
            }
            int count =  mapper.insert(entity) ;
            //
            if (entity.isRecordLog()) {
                ContextHandler.set(BaseEntity.LOG_NEW_DATA, entity);
                ContextHandler.set(BaseEntity.LOG_TYPE, "insert");
            }
            return count > 0;
        } else {
            SysVersion oldBaseEntity = mapper.get(entity);
            //使用序列化方式进行深度拷贝避免对于引用赋值
            SysVersion oldBaseEntityLog = (SysVersion) SerializationUtils.clone(oldBaseEntity);
            BeanUtils.copyBeanProp(oldBaseEntity,entity);
            oldBaseEntity.preUpdate();
            if (mapper.update(oldBaseEntity) == 0) {
                throw new SysException("数据失效，请重新更新！");
            }
            //修改设置为最新状态
            if(entity.getIsLatest().equals(SysVersion.IS_LATEST_TRUE)){
                editSysVersionIsLatestById(entity);
            }
            if (entity.isRecordLog()) {
                ContextHandler.set(BaseEntity.LOG_OLD_DATA, oldBaseEntityLog);
                ContextHandler.set(BaseEntity.LOG_NEW_DATA, entity);
                ContextHandler.set(BaseEntity.LOG_TYPE, "update");
            }
            return Boolean.TRUE;
        }

    }

    /**
     * 删除版本信息
     * @param entity
     * @return
     */
    @Override
    public boolean remove(SysVersion entity) {
        return super.remove(entity);
    }

    @Override
    public void refreshCache() {

    }

    @Override
    public void loadingUserCache() {

    }

    @Override
    public void checkUserDataScope(String userId) {

    }

    /**
     * 根据id批量删除版本信息
     * @param ids 需要删除的数据ID
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public int deleteSysVersionServiceByIds(String[] ids) {
        return mapper.deleteSysVersionServiceByIds(ids);
    }

    /**
     * 根据id关闭版本状态
     * @param id
     * @return
     */
    @Override
    public boolean editSysVersionClosedById(String id) {
        return mapper.editSysVersionClosedById(id);
    }

    /**
     * 开启版本状态
     * @param id
     * @return
     */
    @Override
    public boolean editSysVersionOpenById(String id) {
        return mapper.editSysVersionOpenById(id);
    }

    /**
     * 开启最新版本
     * @param sysVersion
     * @return
     */
    @Transactional(rollbackFor = Exception.class,readOnly = false)
    @Override
    public boolean editSysVersionIsLatestById(SysVersion sysVersion) {
        mapper.editLatestToOld(sysVersion.getProjectId());
        sysProjectMapper.editProjectVersion(sysVersion);
        return mapper.editSysVersionIsLatestById(sysVersion.getId());
    }

    @Transactional(rollbackFor = Exception.class,readOnly = false)
    @Override
    public boolean editSysVersionStatusById(String id) {
        SysVersion sysVersion = mapper.get(id);
        //查询状态，置为相反状态
        String status =null;
        if (sysVersion.getStatus().equals(SysVersion.STATUS_NORMAL)){
            sysVersion.setStatus(SysVersion.STATUS_DISABLE);
        }else {
            sysVersion.setStatus(SysVersion.STATUS_NORMAL);
        }
        return mapper.editSysVersionStatusById(sysVersion);
    }

    @Override
    public void deleteVersionByProjectIds(String[] ids) {
        mapper.deleteVersionByProjectIds(ids);
    }
}
