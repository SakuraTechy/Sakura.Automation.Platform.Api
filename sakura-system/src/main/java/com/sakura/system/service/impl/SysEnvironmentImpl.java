package com.sakura.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.common.exception.SysException;
import com.sakura.common.utils.DataSourceUtils;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.reflect.ReflectUtils;
import com.sakura.common.utils.sql.SqlUtil;
import com.sakura.system.domain.*;
import com.sakura.system.mapper.SysEnvironmentMapper;
import com.sakura.system.mapper.SysProjectMapper;
import com.sakura.system.service.SysEnvironmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wutun
 */
@Service
public class SysEnvironmentImpl extends BaseServiceImpl<SysEnvironmentMapper, SysEnvironment > implements SysEnvironmentService {

    @Autowired
    private SysProjectMapper sysProjectMapper;

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
     * 插入环境信息记录
     * @param entity
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public boolean insert(SysEnvironment entity) {
        List<SysYamlConfig.Project.Environment.Version> versionList = entity.getVersionList();
        entity.setVersionConfig(JSON.toJSONString(versionList));

        List<SysYamlConfig.Project.Environment.Domain> domainList = entity.getDomainList();
        entity.setDomainConfig(JSON.toJSONString(domainList));

        List<SysYamlConfig.Project.Environment.Account> accountList = entity.getAccountList();
        entity.setAccountConfig(JSON.toJSONString(accountList));

        List<SysYamlConfig.Project.Environment.Server> serverList = entity.getServerList();
        entity.setServerConfig(JSON.toJSONString(serverList));

        List<SysYamlConfig.Project.Environment.DataBase> dataBaseList = entity.getDataBaseList();
        entity.setDataBaseConfig(JSON.toJSONString(dataBaseList));

        SysYamlConfig.Project.Environment environment = entity.getCommon();
        entity.setCommonConfig(JSON.toJSONString(environment));

        //先完成添加
        super.save(entity);
        //添加完成获取到id完成中间表插入
        return mapper.insertProjectEnvironment(entity);
    }

    /**
     * 编辑环境信息记录
     * @param entity
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public boolean save(SysEnvironment entity) {
        List<SysYamlConfig.Project.Environment.Version> versionList = entity.getVersionList();
        entity.setVersionConfig(JSON.toJSONString(versionList));

        List<SysYamlConfig.Project.Environment.Domain> domainList = entity.getDomainList();
        entity.setDomainConfig(JSON.toJSONString(domainList));

        List<SysYamlConfig.Project.Environment.Account> accountList = entity.getAccountList();
        entity.setAccountConfig(JSON.toJSONString(accountList));

        List<SysYamlConfig.Project.Environment.Server> serverList = entity.getServerList();
        entity.setServerConfig(JSON.toJSONString(serverList));

        List<SysYamlConfig.Project.Environment.DataBase> dataBaseList = entity.getDataBaseList();
        entity.setDataBaseConfig(JSON.toJSONString(dataBaseList));

        super.save(entity);
        mapper.deleteProjectEnvironment(entity);
        return mapper.insertProjectEnvironment(entity);
    }

    /**
     * 因为重写了findList方法，所以也需要重写findPage才能调用当前重写的findList
     * @param entity
     * @return
     */
    @Override
    public PageInfo<SysEnvironment> findPage(SysEnvironment entity) {
        PageDomain page = entity.getPage();
        Integer pageNum = page.getPageNum();
        Integer pageSize = page.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(page.getOrderBy());
            Boolean reasonable = page.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }
        return new PageInfo(findList(entity));
    }

    @Override
    public SysEnvironment get(String id) {
        SysEnvironment sysEnvironment = super.get(id);
        sysEnvironment.setVersionList(JSON.parseArray(sysEnvironment.getVersionConfig(),SysYamlConfig.Project.Environment.Version.class));
        sysEnvironment.setDomainList(JSON.parseArray(sysEnvironment.getDomainConfig(),SysYamlConfig.Project.Environment.Domain.class));
        sysEnvironment.setAccountList(JSON.parseArray(sysEnvironment.getAccountConfig(),SysYamlConfig.Project.Environment.Account.class));
        sysEnvironment.setServerList(JSON.parseArray(sysEnvironment.getServerConfig(),SysYamlConfig.Project.Environment.Server.class));
        sysEnvironment.setDataBaseList(JSON.parseArray(sysEnvironment.getDataBaseConfig(),SysYamlConfig.Project.Environment.DataBase.class));
        return sysEnvironment;
    }

    /**
     * 将查询获取的数据做处理再返回给前端
     * @param entity
     * @return
     */
    @Override
    public List<SysEnvironment> findList(SysEnvironment entity) {
        //如果为空，则调用反射方法动态实例化，否则传入参数为空时，无法查询出数据
        if(null == entity){
            Class<SysEnvironment> entityClass = ReflectUtils.getClassGenricType(getClass(), 1);
            try {
                entity = entityClass.getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                throw new SysException(e.getMessage());
            }
        }
        //将字符串转换成对象返回给前端
        List<SysEnvironment> sysEnvironmentList = mapper.findList(entity);
        for (SysEnvironment sysEnvironment : sysEnvironmentList) {
            sysEnvironment.setVersionList(JSON.parseArray(sysEnvironment.getVersionConfig(),SysYamlConfig.Project.Environment.Version.class));
            sysEnvironment.setDomainList(JSON.parseArray(sysEnvironment.getDomainConfig(),SysYamlConfig.Project.Environment.Domain.class));
            sysEnvironment.setAccountList(JSON.parseArray(sysEnvironment.getAccountConfig(),SysYamlConfig.Project.Environment.Account.class));
            sysEnvironment.setServerList(JSON.parseArray(sysEnvironment.getServerConfig(),SysYamlConfig.Project.Environment.Server.class));
            sysEnvironment.setDataBaseList(JSON.parseArray(sysEnvironment.getDataBaseConfig(),SysYamlConfig.Project.Environment.DataBase.class));
            sysEnvironment.setCommon(JSON.parseObject(sysEnvironment.getCommonConfig(),SysYamlConfig.Project.Environment.class));
        }
        return sysEnvironmentList;
    }

    @Transactional(readOnly = false)
    @Override
    public boolean deleteSysEnvironmentByIds(String[] ids) {
        mapper.deleteProjectEnvironmentByIds(ids);
        return mapper.deleteSysEnvironmentByIds(ids);
    }

    @Override
    public boolean verifyDataSourceConn(SysDataBaseConfig sysDataBaseConfig) {
        return DataSourceUtils.verifyDataSourceConn(sysDataBaseConfig.getDriver()
                ,sysDataBaseConfig.getUrl()
                ,sysDataBaseConfig.getUsername()
                ,sysDataBaseConfig.getPassword()
        );
    }
}