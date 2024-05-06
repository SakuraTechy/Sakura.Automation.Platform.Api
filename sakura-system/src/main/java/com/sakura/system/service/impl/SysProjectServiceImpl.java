package com.sakura.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.system.domain.*;
import com.sakura.system.mapper.SysProjectMapper;
import com.sakura.system.service.SysEnvironmentService;
import com.sakura.system.service.SysNodeService;
import com.sakura.system.service.SysProjectService;
import com.sakura.system.service.SysVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;

import static com.sakura.common.utils.yaml1.YmlUtil.creatYaml;

/**
 * @author wutun
 * @data 2022/8/24 14:13
 */
@Service
public class SysProjectServiceImpl extends BaseServiceImpl<SysProjectMapper, SysProject> implements SysProjectService {

    @Autowired
    private SysEnvironmentService sysEnvironmentService;

    @Autowired
    private SysNodeService sysNodeService;

    @Autowired
    private SysVersionService sysVersionService;

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
     * 更新数据
     * @param entity
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public boolean save(SysProject entity) {
        //插入中间表数据
         super.save(entity);
         mapper.deleteUserProject(entity);
        return mapper.insertUserProject(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean insert(SysProject entity) {
        super.save(entity);

        SysNode caseNode = creatRoot(entity, SysNode.ROOT_NODE_CASE);
        sysNodeService.save(caseNode);
        SysNode elementNode = creatRoot(entity, SysNode.ROOT_NODE_ELEMENT);
        sysNodeService.save(elementNode);
        SysNode sceneNode = creatRoot(entity, SysNode.ROOT_NODE_SCENE);
        sysNodeService.save(sceneNode);

        SysVersion sysVersion =new SysVersion();
        sysVersion.setProjectId(entity.getId());
        sysVersion.setVersionName(SysVersion.DEFAULT_VERSION);
        sysVersion.setStatus(BaseEntity.STATUS_NORMAL);
        sysVersion.setIsLatest(BaseEntity.STATUS_NORMAL);
        sysVersion.setPublishTime(new Date());
        sysVersion.setEndTime(new Date());
        sysVersionService.save(sysVersion);

        return mapper.insertUserProject(entity);
    }

    @Transactional(readOnly = false)
    @Override
    public int deleteSysProjectByIds(String[] ids) {
        //1.删项目成员中间表
        mapper.deleteUserProjectByIds(ids);
        //2.删除元素、场景、用例等根节点
        sysNodeService.deleteNodeByProjectIds(ids);
        //3.删除相关的环境中间表
        mapper.deleteEnvironmentProjectByProjectIds(ids);
        //4.删除版本表
        sysVersionService.deleteVersionByProjectIds(ids);

        return mapper.deleteSysProjectByIds(ids);
    }

    @Transactional(readOnly = false)
    @Override
    public boolean editEnvironmentConfig(List<SysEnvironment> sysEnvironmentList) {
//        int a = sysEnvironmentList.size();
//        for (SysEnvironment sysEnvironment : sysEnvironmentList) {
//            List<SysCommonConfig> sysCommonConfigList = sysEnvironment.getSysCommonConfigList();
//            sysEnvironment.setCommonConfig(JSON.toJSONString(sysCommonConfigList));
//            List<SysDataBaseConfig> sysDataBaseConfigList = sysEnvironment.getSysDataBaseConfigList();
//            sysEnvironment.setDataBaseConfig(JSON.toJSONString(sysDataBaseConfigList));
//            List<SysServerConfig> sysServerConfigList = sysEnvironment.getSysServerConfigList();
//            sysEnvironment.setServerConfig(JSON.toJSONString(sysServerConfigList));
//            SysEnvironment oldBaseEntity = sysEnvironmentService.get(sysEnvironment);
//            SysEnvironment oldBaseEntityLog = (SysEnvironment) SerializationUtils.clone(oldBaseEntity);
//            BeanUtils.copyBeanProp(oldBaseEntity,sysEnvironment);
//            oldBaseEntity.preUpdate();
//            if (mapper.editEnvironmentConfig(oldBaseEntity) == 0) {
//                throw new SysException("数据失效，请重新更新！");
//            }
//            if (sysEnvironment.isRecordLog()) {
//                ContextHandler.set(BaseEntity.LOG_OLD_DATA, oldBaseEntityLog);
//                ContextHandler.set(BaseEntity.LOG_NEW_DATA, sysEnvironment);
//                ContextHandler.set(BaseEntity.LOG_TYPE, "update");
//            }
//            a--;
//        }
//        if (a<=0){
//            return true;
//        }else {
//            return false;
//        }

        for (SysEnvironment sysEnvironment : sysEnvironmentList) {
            sysEnvironmentService.save(sysEnvironment);
        }
        return true;
    }

//    @Override
//    public List<SysEnvironment> getConfigDetail1(String id) {
//        List<SysEnvironment> list = mapper.getConfigDetail(id);
//        for (SysEnvironment sysEnvironment : list) {
//            if (StringUtils.isNotNull(sysEnvironment.getCommonConfig())){
//                List<SysCommonConfig> sysCommonConfigList = JSON.parseArray(sysEnvironment.getCommonConfig(), SysCommonConfig.class);
//                for (SysCommonConfig sysCommonConfig : sysCommonConfigList) {
//                    String checked = sysCommonConfig.getChecked();
//                    if (StringUtils.isNotNull(checked)&&checked.equals("0")){
//                        sysEnvironment.setEnvironmentDomain(sysCommonConfig.getDomain());
//                    }
//                }
//                sysEnvironment.setSysCommonConfigList(sysCommonConfigList);
//            }
//            if (StringUtils.isNotNull(sysEnvironment.getDatabaseConfig())){
//                List<SysDataBaseConfig> sysDataBaseConfigs = JSON.parseArray(sysEnvironment.getDatabaseConfig(), SysDataBaseConfig.class);
//                sysEnvironment.setSysDataBaseConfigList(sysDataBaseConfigs);
//            }
//            if (StringUtils.isNotNull(sysEnvironment.getServerConfig())){
//                List<SysServerConfig> sysServerConfigList = JSON.parseArray(sysEnvironment.getServerConfig(), SysServerConfig.class);
//                sysEnvironment.setSysServerConfigList(sysServerConfigList);
//            }
//        }
//        return list;
//    }

//    @Transactional(readOnly = false)
//    @Override
//    public List<SysEnvironment> getConfigDetail1(String id) throws IOException, NoSuchFieldException {
//
//        SysYamlConfig sysYamlConfig = new SysYamlConfig();
//        SysYamlConfig.Project project = new SysYamlConfig.Project();
//        SysYamlConfig.Project.Environment environment = new SysYamlConfig.Project.Environment();
//
//        List<SysEnvironment> sysEnvironmentList = mapper.getConfigDetail(id);
//        for (SysEnvironment sysEnvironment : sysEnvironmentList) {
////            project.setId(sysEnvironment.getProjectId());
////            project.setName(sysEnvironment.getProjectName());
////            project.setDesc(sysEnvironment.getProjectDesc());
//            environment.setId(sysEnvironment.getId());
//            environment.setName(sysEnvironment.getEnvironmentName());
//            environment.setVersions(JSON.parseArray(sysEnvironment.getVersionConfig(),SysYamlConfig.Project.Environment.Version.class));
//            environment.setDomains(JSON.parseArray(sysEnvironment.getDomainConfig(),SysYamlConfig.Project.Environment.Domain.class));
//            environment.setAccount(JSON.parseObject(sysEnvironment.getAccountConfig(),SysYamlConfig.Project.Environment.Account.class));
//            environment.setServers(JSON.parseArray(sysEnvironment.getServerConfig(),SysYamlConfig.Project.Environment.Server.class));
//            environment.setDataBase(JSON.parseArray(sysEnvironment.getDataBaseConfig(),SysYamlConfig.Project.Environment.DataBase.class));
//            project.setEnvironment(environment);
//            sysYamlConfig.setProject(project);
//            creatYaml(project);
//
//            sysEnvironment.setVersionList(JSON.parseArray(sysEnvironment.getVersionConfig(),SysYamlConfig.Project.Environment.Version.class));
//            sysEnvironment.setDomainList(JSON.parseArray(sysEnvironment.getDomainConfig(),SysYamlConfig.Project.Environment.Domain.class));
//            sysEnvironment.setAccount(JSON.parseObject(sysEnvironment.getAccountConfig(),SysYamlConfig.Project.Environment.Account.class));
//            sysEnvironment.setServerList(JSON.parseArray(sysEnvironment.getServerConfig(),SysYamlConfig.Project.Environment.Server.class));
//            sysEnvironment.setDataBaseList(JSON.parseArray(sysEnvironment.getDataBaseConfig(),SysYamlConfig.Project.Environment.DataBase.class));
//            sysEnvironment.setProject(project);
//            sysEnvironment.setCommonConfig(JSON.toJSONString(sysYamlConfig));
//            sysEnvironmentService.save(sysEnvironment);
//        }
//        return sysEnvironmentList;
//    }

    @Transactional(readOnly = false)
    @Override
    public SysYamlConfig.Project getConfigDetail(String id) throws IOException, NoSuchFieldException {

        SysYamlConfig.Project project = new SysYamlConfig.Project();
        ArrayList<SysYamlConfig.Project.Environment> environments = new ArrayList<>();

        SysProject sysProject = new SysProject();
        List<SysProject> sysProjectList = mapper.findList(sysProject);
        for (SysProject sysProject1 : sysProjectList){
            if(sysProject1.getId().equals(id)) {
                sysProject = sysProject1;
            }
        }
        project.setIndex(sysProject.getIndex());
        project.setId(sysProject.getId());
        project.setName(sysProject.getProjectName());
        project.setDescription(sysProject.getDescription());

        List<SysEnvironment> sysEnvironmentList = mapper.getConfigDetail(id);
        for (SysEnvironment sysEnvironment : sysEnvironmentList) {
            SysYamlConfig.Project.Environment environment = new SysYamlConfig.Project.Environment();

            List<SysYamlConfig.Project.Environment.Version> versionList = JSON.parseArray(sysEnvironment.getVersionConfig(),SysYamlConfig.Project.Environment.Version.class);
            ArrayList<SysYamlConfig.Project.Environment.Version> versions = new ArrayList<>();
            for (SysYamlConfig.Project.Environment.Version version : versionList) {
                if(version.getStatus() == 1){
                    versions.add(version);
                    if(sysEnvironment.getStatus() == 1){
                        sysProject.setLatestVersion(version.getName());
                        mapper.editProjectVersion(sysProject);
                    }
                }
            }
            List<SysYamlConfig.Project.Environment.Domain> domainList = JSON.parseArray(sysEnvironment.getDomainConfig(),SysYamlConfig.Project.Environment.Domain.class);
            ArrayList<SysYamlConfig.Project.Environment.Domain> domains = new ArrayList<>();
            for (SysYamlConfig.Project.Environment.Domain domain : domainList) {
                if(domain.getStatus() == 1){
                    domains.add(domain);
                }
            }
            List<SysYamlConfig.Project.Environment.Account> accountList = JSON.parseArray(sysEnvironment.getAccountConfig(),SysYamlConfig.Project.Environment.Account.class);
            ArrayList<SysYamlConfig.Project.Environment.Account> accounts = new ArrayList<>();
            for (SysYamlConfig.Project.Environment.Account account : accountList) {
                if(account.getStatus() == 1){
                    accounts.add(account);
                }
            }
            List<SysYamlConfig.Project.Environment.Server> serverList = JSON.parseArray(sysEnvironment.getServerConfig(),SysYamlConfig.Project.Environment.Server.class);
            ArrayList<SysYamlConfig.Project.Environment.Server> servers = new ArrayList<>();
            for (SysYamlConfig.Project.Environment.Server server : serverList) {
                if(server.getStatus() == 1){
                    servers.add(server);
                }
            }
            List<SysYamlConfig.Project.Environment.DataBase> dataBaseList = JSON.parseArray(sysEnvironment.getDataBaseConfig(),SysYamlConfig.Project.Environment.DataBase.class);
            ArrayList<SysYamlConfig.Project.Environment.DataBase> dataBases = new ArrayList<>();
            for (SysYamlConfig.Project.Environment.DataBase dataBase : dataBaseList) {
                if(dataBase.getStatus() == 1){
                    dataBases.add(dataBase);
                }
            }
            environment.setId(sysEnvironment.getId());
            environment.setName(sysEnvironment.getEnvironmentName());
            environment.setStatus(sysEnvironment.getStatus());
            environment.setVersions(versions);
            environment.setDomains(domains);
            environment.setAccounts(accounts);
            environment.setServers(servers);
            environment.setDataBases(dataBases);
            environments.add(environment);

            sysEnvironment.setCommonConfig(JSON.toJSONString(environment));
            sysEnvironmentService.save(sysEnvironment);
        }

        project.setEnvironments(environments);
        creatYaml(project);
        return project;
    }

    @Transactional(readOnly = false)
    @Override
    public boolean removeConfig(String projectId, String environmentId) {
        HashMap<String , String> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("environmentId",environmentId);

        return mapper.removeConfig(map);
    }

    public static SysNode creatRoot(SysProject sysProject,String moduleName){
        SysNode sysNode =new SysNode();
        sysNode.setParentId("0");
        sysNode.setProjectId(sysProject.getId());
        sysNode.setName(moduleName);
        return sysNode;
    }
}
