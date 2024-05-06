package com.sakura.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.common.exception.BizException;
import com.sakura.common.utils.date.DateUtils;
import com.sakura.common.utils.SecurityUtils;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.uuid.IdUtils;
import com.sakura.common.utils.yaml.YamlConfig;
import com.sakura.common.utils.yaml.YmlUtil;
import com.sakura.project.domain.EnvironmentConfig;
import com.sakura.system.common.SysErrorCode;

import com.sakura.system.domain.SysNode;
import com.sakura.project.domain.ProjectConfig;
import com.sakura.project.mapper.EnvironmentConfigMapper;
import com.sakura.project.service.EnvironmentConfigService;
import com.sakura.project.service.ProjectConfigService;
import com.sakura.system.service.SysAutomationService;
import com.sakura.system.service.SysNodeService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 环境配置Service业务层处理
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-26
 */
@Service
@Transactional(readOnly = true)
public class EnvironmentConfigServiceImpl extends BaseServiceImpl<EnvironmentConfigMapper, EnvironmentConfig> implements EnvironmentConfigService {

    private static final Logger log = LoggerFactory.getLogger(EnvironmentConfigServiceImpl.class);

    @Autowired
    private ProjectConfigService projectConfigService;

    @Autowired
    private SysNodeService sysNodeService;

    @Autowired
    private SysAutomationService sysAutomationService;

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
     * 获取单条数据
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Override
    public EnvironmentConfig get(EnvironmentConfig environmentConfig) {
        return super.get(environmentConfig);
    }

    /**
     * 获取单条数据
     *
     * @param id 环境配置id
     * @return 结果
     */
    @Override
    public EnvironmentConfig get(String id) {
        return super.get(id);
    }

    /**
     * 查询环境配置列表
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Override
    public List<EnvironmentConfig> findList(EnvironmentConfig environmentConfig) {
        return super.findList(environmentConfig);
    }

    /**
     * 分页查询环境配置列表
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Override
    public PageInfo<EnvironmentConfig> findPage(EnvironmentConfig environmentConfig) {
        return super.findPage(environmentConfig);
    }

    /**
     * 保存环境配置
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Override
    public boolean save(EnvironmentConfig environmentConfig) {
        if (!CollectionUtils.isEmpty(mapper.findListWithUnique(environmentConfig))) {
            throw new BizException(SysErrorCode.B_PROJECT_EnvironmentNameAlreadyExist);
        }
        List<YamlConfig.Project.Environment.Version> versionList = environmentConfig.getVersionList();
        if (StringUtils.isNotEmpty(versionList)) {
            environmentConfig.setVersionConfig(JSON.toJSONString(versionList));
        }else{
            environmentConfig.setVersionConfig("[]");
        }

        List<YamlConfig.Project.Environment.Domain> domainList = environmentConfig.getDomainList();
        if (StringUtils.isNotEmpty(domainList)) {
            environmentConfig.setDomainConfig(JSON.toJSONString(domainList));
        }else{
            environmentConfig.setDomainConfig("[]");
        }

//        YamlConfig.Project.Environment.Account account = environmentConfig.getAccount();
//        if (StringUtils.isNotNull(account)) {
//            environmentConfig.setAccountConfig(JSON.toJSONString(account));
//        }

        List<YamlConfig.Project.Environment.Account> accountList = environmentConfig.getAccountList();
        if (StringUtils.isNotEmpty(accountList)) {
            environmentConfig.setAccountConfig(JSON.toJSONString(accountList));
        }else{
            environmentConfig.setAccountConfig("[]");
        }

        List<YamlConfig.Project.Environment.Server> serverList = environmentConfig.getServerList();
        if (StringUtils.isNotEmpty(serverList)) {
            environmentConfig.setServerConfig(JSON.toJSONString(serverList));
        }else{
            environmentConfig.setServerConfig("[]");
        }

        List<YamlConfig.Project.Environment.DataBase> dataBaseList = environmentConfig.getDataBaseList();
        if (StringUtils.isNotNull(dataBaseList)) {
            environmentConfig.setDataBaseConfig(JSON.toJSONString(dataBaseList));
        }else{
            environmentConfig.setDataBaseConfig("[]");
        }

        if(StringUtils.isNotNull(environmentConfig.getStatus())&&environmentConfig.getStatus()==1){
            environmentConfig.preUpdate();
            mapper.closeStatus(environmentConfig);
        }
        return super.save(environmentConfig);
    }

    /**
     * 修改环境配置
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean edit(EnvironmentConfig environmentConfig) {
//        if (CollectionUtils.isEmpty(mapper.findListWithUnique(environmentConfig))) {
//            throw new BizException(SysErrorCode.B_PROJECT_EnvironmentNotExistent);
//        }
        List<YamlConfig.Project.Environment.Version> versionList = environmentConfig.getVersionList();
        if (StringUtils.isNotEmpty(versionList)) {
            environmentConfig.setVersionConfig(JSON.toJSONString(versionList));
        }

        List<YamlConfig.Project.Environment.Domain> domainList = environmentConfig.getDomainList();
        if (StringUtils.isNotEmpty(domainList)) {
            environmentConfig.setDomainConfig(JSON.toJSONString(domainList));
        }

//        YamlConfig.Project.Environment.Account account = environmentConfig.getAccount();
//        if (StringUtils.isNotNull(account)) {
//            environmentConfig.setAccountConfig(JSON.toJSONString(account));
//        }

        List<YamlConfig.Project.Environment.Account> accountList = environmentConfig.getAccountList();
        if (StringUtils.isNotEmpty(accountList)) {
            environmentConfig.setAccountConfig(JSON.toJSONString(accountList));
        }

        List<YamlConfig.Project.Environment.Server> serverList = environmentConfig.getServerList();
        if (StringUtils.isNotEmpty(serverList)) {
            environmentConfig.setServerConfig(JSON.toJSONString(serverList));
        }

        List<YamlConfig.Project.Environment.DataBase> dataBaseList = environmentConfig.getDataBaseList();
        if (StringUtils.isNotNull(dataBaseList)) {
            environmentConfig.setDataBaseConfig(JSON.toJSONString(dataBaseList));
        }

        if(StringUtils.isNotNull(environmentConfig.getStatus())&&environmentConfig.getStatus()==1){
            environmentConfig.preUpdate();
            mapper.closeStatus(environmentConfig);
        }
        return super.save(environmentConfig);
    }

    /**
     * 批量删除环境配置
     *
     * @param ids 需要删除的环境配置ID
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public int deleteEnvironmentConfigByIds(String[] ids) {
        return mapper.deleteEnvironmentConfigByIds(ids, BaseEntity.DEL_FLAG_DELETE);
    }

    /**
     * 删除环境配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Override
    public boolean remove(EnvironmentConfig environmentConfig) {
        if (StringUtils.isNotNull(environmentConfig.getIds())) {
            for (String id :environmentConfig.getIds()){
                environmentConfig.setProjectId(super.get(id).getProjectId());
//                environmentConfig.setProjectId(environmentConfig.getProjectId());
                List<EnvironmentConfig> environmentConfigList = super.findList(environmentConfig);
                environmentConfigList.forEach((environment)->{
                    if (environment.getId().equals(id) && environment.getStatus() == 1) {
                        throw new BizException(SysErrorCode.B_PROJECT_Environment_StateNotExistent, id);
                    }
                });
                if(environmentConfigList.size()>=1){
                    environmentConfig.setId(id);
                    if (super.remove(environmentConfig)) {
                        environmentConfig.setId("");
                    } else {
                        throw new BizException(SysErrorCode.B_PROJECT_EnvironmentDeleteFailed, id);
                    }
                }else {
                    throw new BizException(SysErrorCode.B_PROJECT_Environment_DeleteNotExistent);
                }
            }
        }
        return true;
    }

    /**
     * 更新环境配置状态
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean updateStatus(EnvironmentConfig environmentConfig) {
        environmentConfig.setUpdateBy(SecurityUtils.getLoginUser().getUser().getId());
        environmentConfig.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
        environmentConfig.setUpdateTime(new Date());
        environmentConfig.setUpdateIp(SecurityUtils.getLoginUser().getIpaddr());
        if(environmentConfig.getStatus()==0){
            if(getStatusNum(environmentConfig)<=1){
                throw new BizException(SysErrorCode.B_PROJECT_Environment_DisableNotExistent);
            }
        }else{
            mapper.closeStatus(environmentConfig);
        }
        return mapper.updateStatus(environmentConfig);
    }

    /**
     * 获取当前项目下已启用的环境数量
     * @param environmentConfig 环境配置
     * @return 结果
     */
    public int getStatusNum(EnvironmentConfig environmentConfig) {
        return mapper.getStatusNum(environmentConfig);
    }

//    /**
//     * 导出环境配置信息
//     *
//     * @param environmentConfig 环境配置
//     * @return 结果
//     */
//    @Transactional(readOnly = false)
//    @Override
//    public boolean export1(EnvironmentConfig environmentConfig) throws IOException, NoSuchFieldException {
//        YamlConfig yamlConfig = new YamlConfig();
//        YamlConfig.Project project = new YamlConfig.Project();
//        ArrayList<YamlConfig.Project> projects = new ArrayList<>();
//
//        YamlConfig.Project.Environment environment = new YamlConfig.Project.Environment();
//        ArrayList<YamlConfig.Project.Environment> environments = new ArrayList<>();
//        if (StringUtils.isNotNull(environmentConfig.getIds())) {
//            for (String id :environmentConfig.getIds()){
//                environmentConfig = super.get(id);
//                ProjectConfig projectConfig = projectConfigService.get(environmentConfig.getProjectId());
//                project.setId(projectConfig.getId());
//                project.setName(projectConfig.getName());
//                project.setAbbreviate(projectConfig.getAbbreviate());
//                project.setDescription(projectConfig.getDescription());
//
//                List<YamlConfig.Project.Environment.Version> versionList = JSON.parseArray(environmentConfig.getVersionConfig(),YamlConfig.Project.Environment.Version.class);
//                ArrayList<YamlConfig.Project.Environment.Version> versions = new ArrayList<>();
//                for (YamlConfig.Project.Environment.Version version : versionList) {
//                    if(version.getStatus() == 1){
//                        versions.add(version);
//                    }
//                }
//                List<YamlConfig.Project.Environment.Domain> domainList = JSON.parseArray(environmentConfig.getDomainConfig(),YamlConfig.Project.Environment.Domain.class);
//                ArrayList<YamlConfig.Project.Environment.Domain> domains = new ArrayList<>();
//                for (YamlConfig.Project.Environment.Domain domain : domainList) {
//                    if(domain.getStatus() == 1){
//                        domains.add(domain);
//                    }
//                }
//                List<YamlConfig.Project.Environment.Account> accountList = JSON.parseArray(environmentConfig.getAccountConfig(),YamlConfig.Project.Environment.Account.class);
//                ArrayList<YamlConfig.Project.Environment.Account> accounts = new ArrayList<>();
//                for (YamlConfig.Project.Environment.Account account : accountList) {
//                    if(account.getStatus() == 1){
//                        accounts.add(account);
//                    }
//                }
//                List<YamlConfig.Project.Environment.Server> serverList = JSON.parseArray(environmentConfig.getServerConfig(),YamlConfig.Project.Environment.Server.class);
//                ArrayList<YamlConfig.Project.Environment.Server> servers = new ArrayList<>();
//                for (YamlConfig.Project.Environment.Server server : serverList) {
//                    if(server.getStatus() == 1){
//                        servers.add(server);
//                    }
//                }
//                List<YamlConfig.Project.Environment.DataBase> dataBaseList = JSON.parseArray(environmentConfig.getDataBaseConfig(),YamlConfig.Project.Environment.DataBase.class);
//                ArrayList<YamlConfig.Project.Environment.DataBase> dataBases = new ArrayList<>();
//                for (YamlConfig.Project.Environment.DataBase dataBase : dataBaseList) {
//                    if(dataBase.getStatus() == 1){
//                        dataBases.add(dataBase);
//                    }
//                }
//                environment = new YamlConfig.Project.Environment();
//                environment.setId(environmentConfig.getId());
//                environment.setName(environmentConfig.getName());
//                environment.setStatus(environmentConfig.getStatus());
//                environment.setVersions(versions);
//                environment.setDomains(domains);
//                environment.setAccounts(accounts);
//                environment.setServers(servers);
//                environment.setDataBases(dataBases);
//                environments.add(environment);
//            }
//            project.setEnvironments(environments);
//            YmlUtil.creatYaml(project);
//        }
//        return true;
//    }

    /**
     * 导出环境配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public void export(EnvironmentConfig environmentConfig, HttpServletResponse resp, HttpServletRequest req) throws IOException, NoSuchFieldException {
        YamlConfig yamlConfig = new YamlConfig();
        ArrayList<YamlConfig.Project> projects = new ArrayList<>();

        YamlConfig.Project project = new YamlConfig.Project();
        YamlConfig.Project.Environment environment = new YamlConfig.Project.Environment();
        ArrayList<YamlConfig.Project.Environment> environments = new ArrayList<>();
        String ProjectId1 = "";
        if (StringUtils.isNotNull(environmentConfig.getIds())) {
            for (String id :environmentConfig.getIds()){
                environmentConfig = super.get(id);
                String ProjectId =  environmentConfig.getProjectId();
                ProjectConfig projectConfig = projectConfigService.get(ProjectId);
                if(!ProjectId.equals(ProjectId1)&&StringUtils.isNotEmpty(ProjectId1)){
                    project.setEnvironments(environments);
                    projects.add(project);
                    project = new YamlConfig.Project();
                    environments = new ArrayList<>();
                }
                project.setId(projectConfig.getId());
                project.setName(projectConfig.getName());
                project.setAbbreviate(projectConfig.getAbbreviate());
                project.setDescription(projectConfig.getDescription());
                project.setStatus(projectConfig.getStatus());
                List<YamlConfig.Project.Environment.Version> versionList = JSON.parseArray(environmentConfig.getVersionConfig(),YamlConfig.Project.Environment.Version.class);
                ArrayList<YamlConfig.Project.Environment.Version> versions = new ArrayList<>();
                for (YamlConfig.Project.Environment.Version version : versionList) {
                    if(version.getStatus() == 1){
                        versions.add(version);
                    }
                }
                List<YamlConfig.Project.Environment.Domain> domainList = JSON.parseArray(environmentConfig.getDomainConfig(),YamlConfig.Project.Environment.Domain.class);
                ArrayList<YamlConfig.Project.Environment.Domain> domains = new ArrayList<>();
                for (YamlConfig.Project.Environment.Domain domain : domainList) {
                    if(domain.getStatus() == 1){
                        domains.add(domain);
                    }
                }
                List<YamlConfig.Project.Environment.Account> accountList = JSON.parseArray(environmentConfig.getAccountConfig(),YamlConfig.Project.Environment.Account.class);
                ArrayList<YamlConfig.Project.Environment.Account> accounts = new ArrayList<>();
                for (YamlConfig.Project.Environment.Account account : accountList) {
                    if(account.getStatus() == 1){
                        accounts.add(account);
                    }
                }
                List<YamlConfig.Project.Environment.Server> serverList = JSON.parseArray(environmentConfig.getServerConfig(),YamlConfig.Project.Environment.Server.class);
                ArrayList<YamlConfig.Project.Environment.Server> servers = new ArrayList<>();
                for (YamlConfig.Project.Environment.Server server : serverList) {
                    if(server.getStatus() == 1){
                        servers.add(server);
                    }
                }
                List<YamlConfig.Project.Environment.DataBase> dataBaseList = JSON.parseArray(environmentConfig.getDataBaseConfig(),YamlConfig.Project.Environment.DataBase.class);
                ArrayList<YamlConfig.Project.Environment.DataBase> dataBases = new ArrayList<>();
                for (YamlConfig.Project.Environment.DataBase dataBase : dataBaseList) {
                    if(dataBase.getStatus() == 1){
                        dataBases.add(dataBase);
                    }
                }
                environment = new YamlConfig.Project.Environment();
                environment.setId(environmentConfig.getId());
                environment.setName(environmentConfig.getName());
                environment.setStatus(environmentConfig.getStatus());
                environment.setVersions(versions);
                environment.setDomains(domains);
                environment.setAccounts(accounts);
                environment.setServers(servers);
                environment.setDataBases(dataBases);
                environments.add(environment);

                ProjectId1 = ProjectId;
            }
            project.setEnvironments(environments);
            projects.add(project);
            yamlConfig.setProjects(projects);
            YmlUtil.creatYaml(yamlConfig,resp,req);
        }
    }

    /**
     * 新增版本配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
//    public boolean addVersion(EnvironmentConfig environmentConfig) {
//        environmentConfig.getVersions().setId(IdUtils.randomUUID());
//        environmentConfig.getVersions().setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
//        environmentConfig.getVersions().setCreateTime(DateUtils.getTime());
//        environmentConfig.getVersions().setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
//        environmentConfig.getVersions().setUpdateTime(DateUtils.getTime());
//
//        String versionConfig = super.get(environmentConfig).getVersionConfig();
//        int status = super.get(environmentConfig.getId()).getStatus();
//        String projectId = super.get(environmentConfig.getId()).getProjectId();
//        boolean success = false;
//        if (StringUtils.isEmpty(versionConfig)) {
//            List<YamlConfig.Project.Environment.Version> versionList = new ArrayList<>();
//            environmentConfig.getVersions().setIndex(1);
//            environmentConfig.getVersions().setDelFlag(0);
//            versionList.add(environmentConfig.getVersions());
////            versionList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
//            environmentConfig.setVersionList(versionList);
//            if(environmentConfig.getVersions().getStatus()==1){
//                environmentConfig.setLastVersion(environmentConfig.getVersions().getName());
//                if(status==1){
//                    ProjectConfig projectConfig = new ProjectConfig();
//                    projectConfig.setId(projectId);
//                    projectConfig.setLastVersion(environmentConfig.getVersions().getName());
//                    projectConfigService.edit(projectConfig);
//                }
//            }
//            if(edit(environmentConfig)){
//                String[] nodeNames = {"全部用例","全部元素","全部场景"};
//                for(String name : nodeNames){
//                    SysNode sysNode =new SysNode();
//                    sysNode.setParentId("0");
//                    sysNode.setProjectId(projectId);
//                    sysNode.setVersionId(environmentConfig.getVersions().getId());
//                    sysNode.setName(name);
//                    success = sysNodeService.save(sysNode);
//                }
//            }
//            return success;
//        } else {
//            List<YamlConfig.Project.Environment.Version> versionList = JSON.parseArray(versionConfig, YamlConfig.Project.Environment.Version.class);
//            environmentConfig.getVersions().setIndex(versionList.size() + 1);
//            for (YamlConfig.Project.Environment.Version version : versionList) {
//                if(environmentConfig.getVersions().getStatus()==1){
//                    version.setStatus(0);
//                    environmentConfig.setLastVersion(environmentConfig.getVersions().getName());
//                    if(status==1){
//                        ProjectConfig projectConfig = new ProjectConfig();
//                        projectConfig.setId(projectId);
//                        projectConfig.setLastVersion(environmentConfig.getVersions().getName());
//                        projectConfigService.edit(projectConfig);
//                    }
//                }
//            }
//            versionList.add(environmentConfig.getVersions());
//            //去重后的集合
//            ArrayList<YamlConfig.Project.Environment.Version> collect = versionList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Project.Environment.Version::getName))), ArrayList::new));
//            if (versionList.size() == collect.size()) {
////                collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
//                collect.sort((x, y) -> y.getName().compareTo(x.getName()));
////                Collections.sort(collect);
////                Collections.sort(collect,Collections.reverseOrder());
////                Collections.sort(collect, new Comparator<YamlConfig.Project.Environment.Version>() {
////                    @Override
////                    public int compare(YamlConfig.Project.Environment.Version o1, YamlConfig.Project.Environment.Version o2) {
////                            return o2.getName().compareTo(o1.getName());
////                    }
////                });
//                environmentConfig.setVersionList(collect);
//                if(edit(environmentConfig)){
//                    String[] nodeNames = {"全部用例","全部元素","全部场景"};
//                    for(String name : nodeNames){
//                        SysNode sysNode =new SysNode();
//                        sysNode.setParentId("0");
//                        sysNode.setProjectId(projectId);
//                        sysNode.setVersionId(environmentConfig.getVersions().getId());
//                        sysNode.setName(name);
//                        success = sysNodeService.save(sysNode);
//                    }
//                }
//                return success;
//            } else {
//                throw new BizException(SysErrorCode.B_PROJECT_Environment_VersionNameAlreadyExist);
//            }
//        }
//    }

    public boolean addVersion(EnvironmentConfig environmentConfig) {
        String versionId = environmentConfig.getVersions().getId();
        String versionConfig = super.get(environmentConfig).getVersionConfig();
        int status = super.get(environmentConfig.getId()).getStatus();
        String projectId = super.get(environmentConfig.getId()).getProjectId();
        if (StringUtils.isEmpty(versionConfig)) {
            List<YamlConfig.Project.Environment.Version> versionList = new ArrayList<>();
            environmentConfig.getVersions().setIndex(1);
            environmentConfig.getVersions().setDelFlag(0);
            versionList.add(environmentConfig.getVersions());
//            versionList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            environmentConfig.setVersionList(versionList);
            if(environmentConfig.getVersions().getStatus()==1){
                environmentConfig.setLastVersion(environmentConfig.getVersions().getName());
                if(status==1){
                    ProjectConfig projectConfig = new ProjectConfig();
                    projectConfig.setId(projectId);
                    projectConfig.setLastVersion(environmentConfig.getVersions().getName());
                    projectConfigService.edit(projectConfig);
                }
            }
        } else {
            List<YamlConfig.Project.Environment.Version> versionList = JSON.parseArray(versionConfig, YamlConfig.Project.Environment.Version.class);
            environmentConfig.getVersions().setIndex(versionList.size() + 1);
            environmentConfig.getVersions().setDelFlag(0);
            for (YamlConfig.Project.Environment.Version version : versionList) {
                if (version.getName().equals(environmentConfig.getVersions().getName())&&version.getDelFlag().equals(environmentConfig.getVersions().getDelFlag())) {
                    throw new BizException(SysErrorCode.B_PROJECT_Environment_VersionNameAlreadyExist);
                }
                if(environmentConfig.getVersions().getStatus()==1){
                    version.setStatus(0);
                    environmentConfig.setLastVersion(environmentConfig.getVersions().getName());
                    if(status==1){
                        ProjectConfig projectConfig = new ProjectConfig();
                        projectConfig.setId(projectId);
                        projectConfig.setLastVersion(environmentConfig.getVersions().getName());
                        projectConfigService.edit(projectConfig);
                    }
                }
            }
            versionList.add(environmentConfig.getVersions());
            versionList.sort((x, y) -> y.getName().compareTo(x.getName()));
            environmentConfig.setVersionList(versionList);
        }
        environmentConfig.getVersions().setId(IdUtils.randomUUID());
        environmentConfig.getVersions().setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
        environmentConfig.getVersions().setCreateTime(DateUtils.getTime());
        environmentConfig.getVersions().setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
        environmentConfig.getVersions().setUpdateTime(DateUtils.getTime());
        boolean success = false;
        if(edit(environmentConfig)){
            SysNode sysNode = new SysNode();
            if (StringUtils.isBlank(versionId)){
                String[] nodeNames = {"全部场景"};
                for(String name : nodeNames){
                    sysNode.setId(IdUtils.randomUUID()+"_"+environmentConfig.getVersions().getName());
                    sysNode.setParentId("0");
                    sysNode.setProjectId(projectId);
                    sysNode.setVersionId(environmentConfig.getVersions().getId());
                    sysNode.setName(name);
                    sysNode.setNewRecord(true);
                    success = sysNodeService.save(sysNode);
                }
            }else{
                sysNode.setVersionId(versionId);
                List<SysNode> sysNodeList = sysNodeService.findList(sysNode);
                if (sysNodeList.size()>0){
                    for (SysNode sysNode1: sysNodeList) {
                        sysNode1.setNewRecord(true);
                        sysNode1.setId(sysNode1.getId()+"_"+environmentConfig.getVersions().getName());
                        if (!"0".equals(sysNode1.getParentId())){
                            sysNode1.setParentId(sysNode1.getParentId()+"_"+environmentConfig.getVersions().getName());
                        }
                        sysNode1.setProjectId(projectId);
                        sysNode1.setVersionId(environmentConfig.getVersions().getId());
                        success = sysNodeService.save(sysNode1);
                    }
                }
            }
        }
        return success;
    }

    /**
     * 修改版本配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
//    public boolean editVersion(EnvironmentConfig environmentConfig) {
//        String versionConfig = super.get(environmentConfig.getId()).getVersionConfig();
//        int status = super.get(environmentConfig.getId()).getStatus();
//        String projectId = super.get(environmentConfig.getId()).getProjectId();
//        List<YamlConfig.Project.Environment.Version> versionList = JSON.parseArray(versionConfig, YamlConfig.Project.Environment.Version.class);
//        for (YamlConfig.Project.Environment.Version version : versionList) {
//            if (version.getId().equals(environmentConfig.getVersions().getId())) {
//                version.setName(environmentConfig.getVersions().getName());
//                version.setDescription(environmentConfig.getVersions().getDescription());
//                version.setConfigList(environmentConfig.getVersions().getConfigList());
//                version.setStatus(environmentConfig.getVersions().getStatus());
//                version.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
//                version.setUpdateTime(DateUtils.getTime());
//            }else{
//                if(environmentConfig.getVersions().getStatus()==1){
//                    version.setStatus(0);
//                    environmentConfig.setLastVersion(environmentConfig.getVersions().getName());
//                    if(status==1){
//                        ProjectConfig projectConfig = new ProjectConfig();
//                        projectConfig.setId(projectId);
//                        projectConfig.setLastVersion(environmentConfig.getVersions().getName());
//                        projectConfigService.edit(projectConfig);
//                    }
//                }
//            }
//        }
//        ArrayList<YamlConfig.Project.Environment.Version> collect = versionList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Project.Environment.Version::getName))), ArrayList::new));
//        if (versionList.size() == collect.size()) {
////            collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
//            collect.sort((x, y) -> y.getName().compareTo(x.getName()));
//            environmentConfig.setVersionList(collect);
//            return edit(environmentConfig);
//        } else {
//            throw new BizException(SysErrorCode.B_PROJECT_Environment_VersionNameAlreadyExist);
//        }
//    }

    public boolean editVersion(EnvironmentConfig environmentConfig) {
        String versionConfig = super.get(environmentConfig.getId()).getVersionConfig();
        List<YamlConfig.Project.Environment.Version> versionList = JSON.parseArray(versionConfig, YamlConfig.Project.Environment.Version.class);
        int num = 0;
        for (YamlConfig.Project.Environment.Version version : versionList) {
            if(version.getStatus()==0){
                num++;
            }
        }
        if(environmentConfig.getVersions().getStatus()==0&&num>=versionList.size()-1){
            throw new BizException(SysErrorCode.B_PROJECT_Environment_DisableNotExistent);
        }else{
            int status = super.get(environmentConfig.getId()).getStatus();
            String projectId = super.get(environmentConfig.getId()).getProjectId();
            for (YamlConfig.Project.Environment.Version version : versionList) {
                if (version.getName().equals(environmentConfig.getVersions().getName()) && version.getDelFlag()==0 && version.getStatus().equals(environmentConfig.getVersions().getStatus())) {
                    throw new BizException(SysErrorCode.B_PROJECT_Environment_VersionNameAlreadyExist);
                }
                if (version.getId().equals(environmentConfig.getVersions().getId())) {
                    version.setName(environmentConfig.getVersions().getName());
                    version.setDescription(environmentConfig.getVersions().getDescription());
                    version.setConfigList(environmentConfig.getVersions().getConfigList());
                    version.setStatus(environmentConfig.getVersions().getStatus());
                    version.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
                    version.setUpdateTime(DateUtils.getTime());
                }else{
                    if(environmentConfig.getVersions().getStatus()==1){
                        version.setStatus(0);
                        environmentConfig.setLastVersion(environmentConfig.getVersions().getName());
                        if(status==1){
                            ProjectConfig projectConfig = new ProjectConfig();
                            projectConfig.setId(projectId);
                            projectConfig.setLastVersion(environmentConfig.getVersions().getName());
                            projectConfigService.edit(projectConfig);
                        }
                    }
                }
            }
            versionList.sort((x, y) -> y.getName().compareTo(x.getName()));
            environmentConfig.setVersionList(versionList);
            return edit(environmentConfig);
        }

    }

    /**
     * 删除版本配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean removeVersion(EnvironmentConfig environmentConfig) {
        String versionConfig = super.get(environmentConfig.getId()).getVersionConfig();
        List<YamlConfig.Project.Environment.Version> versionList = JSON.parseArray(versionConfig, YamlConfig.Project.Environment.Version.class);
        for (String id : environmentConfig.getIds()) {
//            versionList.forEach((version)->{
//                if (version.getId().equals(id) && version.getStatus() == 1) {
//                    throw new BizException(SysErrorCode.B_PROJECT_Environment_StateNotExistent, id);
//                }
//            });
//            if(versionList.size()>1){
//                if(versionList.removeIf(version -> version.getId().equals(id))){
//                    versionList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
//                } else {
//                    throw new BizException(SysErrorCode.B_PROJECT_Environment_VersionIdNotExistent,id);
//                }
//            }else {
//                throw new BizException(SysErrorCode.B_PROJECT_Environment_DeleteNotExistent);
//            }

            if (versionList.size() >0) {
                int num = 0;
                for (YamlConfig.Project.Environment.Version version : versionList){
                    if (version.getDelFlag()==1) {
                        num++;
                    }
                }
                if (versionList.size() - num > 1) {
                    versionList.forEach((version) -> {
                        if (version.getId().equals(id)) {
                            if (version.getStatus() == 1) {
                                throw new BizException(SysErrorCode.B_PROJECT_Environment_StateNotExistent, id);
                            } else {
                                version.setDelFlag(1);
                                sysNodeService.deleteNodeByVersionId(version.getId());
                                sysAutomationService.deleteSceneByVersionId(version.getId());
                            }
                        }
                    });
                } else {
                    throw new BizException(SysErrorCode.B_PROJECT_Environment_DeleteNotExistent);
                }
            } else {
                throw new BizException(SysErrorCode.B_PROJECT_Environment_VersionNotExistent);
            }
        }
        final Integer[] i = {1};
        versionList.forEach((e) -> {
            e.setIndex(i[0]);
            i[0]++;
        });
        environmentConfig.setVersionList(versionList);
        return edit(environmentConfig);
    }

    /**
     * 新增域名配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean addDomain(EnvironmentConfig environmentConfig) {
        environmentConfig.getDomains().setId(IdUtils.randomUUID());
        environmentConfig.getDomains().setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
        environmentConfig.getDomains().setCreateTime(DateUtils.getTime());
        environmentConfig.getDomains().setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
        environmentConfig.getDomains().setUpdateTime(DateUtils.getTime());

        String domainConfig = super.get(environmentConfig).getDomainConfig();
        int status = super.get(environmentConfig.getId()).getStatus();
        String projectId = super.get(environmentConfig.getId()).getProjectId();
        if (StringUtils.isEmpty(domainConfig)) {
            List<YamlConfig.Project.Environment.Domain> domainList = new ArrayList<>();
            environmentConfig.getDomains().setIndex(1);
            domainList.add(environmentConfig.getDomains());
//            domainList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            environmentConfig.setDomainList(domainList);
            if(environmentConfig.getDomains().getStatus()==1){
                environmentConfig.setLastDomain(environmentConfig.getDomains().getUrl());
                if(status==1){
                    ProjectConfig projectConfig = new ProjectConfig();
                    projectConfig.setId(projectId);
                    projectConfig.setLastDomain(environmentConfig.getDomains().getUrl());
                    projectConfigService.edit(projectConfig);
                }
            }
            return edit(environmentConfig);
        } else {
            List<YamlConfig.Project.Environment.Domain> domainList = JSON.parseArray(domainConfig, YamlConfig.Project.Environment.Domain.class);
            environmentConfig.getDomains().setIndex(domainList.size() + 1);
            for (YamlConfig.Project.Environment.Domain domain : domainList) {
                if(environmentConfig.getDomains().getStatus()==1){
                    domain.setStatus(0);
                    environmentConfig.setLastDomain(environmentConfig.getDomains().getUrl());
                    if(status==1){
                        ProjectConfig projectConfig = new ProjectConfig();
                        projectConfig.setId(projectId);
                        projectConfig.setLastDomain(environmentConfig.getDomains().getUrl());
                        projectConfigService.edit(projectConfig);
                    }
                }
            }
            domainList.add(environmentConfig.getDomains());
            //去重后的集合
            ArrayList<YamlConfig.Project.Environment.Domain> collect = domainList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Project.Environment.Domain::getName))), ArrayList::new));
            if (domainList.size() == collect.size()) {
                collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                environmentConfig.setDomainList(collect);
                return edit(environmentConfig);
            } else {
                throw new BizException(SysErrorCode.B_PROJECT_Environment_DomainNameAlreadyExist);
            }
        }
    }

    /**
     * 修改域名配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean editDomain(EnvironmentConfig environmentConfig) {
        String domainConfig = super.get(environmentConfig.getId()).getDomainConfig();
        int status = super.get(environmentConfig.getId()).getStatus();
        String projectId = super.get(environmentConfig.getId()).getProjectId();
        List<YamlConfig.Project.Environment.Domain> domainList = JSON.parseArray(domainConfig, YamlConfig.Project.Environment.Domain.class);
        for (YamlConfig.Project.Environment.Domain domain : domainList) {
            if (domain.getId().equals(environmentConfig.getDomains().getId())) {
                domain.setName(environmentConfig.getDomains().getName());
                domain.setUrl(environmentConfig.getDomains().getUrl());
                domain.setStatus(environmentConfig.getDomains().getStatus());
                domain.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
                domain.setUpdateTime(DateUtils.getTime());
            }else{
                if(environmentConfig.getDomains().getStatus()==1){
                    domain.setStatus(0);
                    environmentConfig.setLastDomain(environmentConfig.getDomains().getUrl());
                    if(status==1){
                        ProjectConfig projectConfig = new ProjectConfig();
                        projectConfig.setId(projectId);
                        projectConfig.setLastDomain(environmentConfig.getDomains().getUrl());
                        projectConfigService.edit(projectConfig);
                    }
                }
            }
        }
        ArrayList<YamlConfig.Project.Environment.Domain> collect = domainList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Project.Environment.Domain::getName))), ArrayList::new));
        if (domainList.size() == collect.size()) {
            collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            environmentConfig.setDomainList(collect);
            return edit(environmentConfig);
        } else {
            throw new BizException(SysErrorCode.B_PROJECT_Environment_DomainNameAlreadyExist);
        }
    }

    /**
     * 删除域名配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean removeDomain(EnvironmentConfig environmentConfig) {
        String domainConfig = super.get(environmentConfig.getId()).getDomainConfig();
        List<YamlConfig.Project.Environment.Domain> domainList = JSON.parseArray(domainConfig, YamlConfig.Project.Environment.Domain.class);
        for (String id : environmentConfig.getIds()) {
            domainList.forEach((domain)->{
                if (domain.getId().equals(id) && domain.getStatus() == 1) {
                    throw new BizException(SysErrorCode.B_PROJECT_Environment_StateNotExistent, id);
                }
            });
            if(domainList.size()>1){
                if(domainList.removeIf(domain -> domain.getId().equals(id))){
                    domainList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                } else {
                    throw new BizException(SysErrorCode.B_PROJECT_Environment_DomainIdNotExistent,id);
                }
            }else {
                throw new BizException(SysErrorCode.B_PROJECT_Environment_DeleteNotExistent);
            }
        }
        final Integer[] i = {1};
        domainList.forEach((e) -> {
            e.setIndex(i[0]);
            i[0]++;
        });
        environmentConfig.setDomainList(domainList);
        return edit(environmentConfig);
    }

    /**
     * 新增帐号配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean addAccount(EnvironmentConfig environmentConfig) {
        environmentConfig.getAccounts().setId(IdUtils.randomUUID());
        environmentConfig.getAccounts().setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
        environmentConfig.getAccounts().setCreateTime(DateUtils.getTime());
        environmentConfig.getAccounts().setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
        environmentConfig.getAccounts().setUpdateTime(DateUtils.getTime());

        String accountConfig = super.get(environmentConfig).getAccountConfig();
        if (StringUtils.isEmpty(accountConfig)) {
            List<YamlConfig.Project.Environment.Account> accountList = new ArrayList<>();
            environmentConfig.getAccounts().setIndex(1);
            accountList.add(environmentConfig.getAccounts());
//            domainList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            environmentConfig.setAccountList(accountList);
            return edit(environmentConfig);
        } else {
            List<YamlConfig.Project.Environment.Account> accountList = JSON.parseArray(accountConfig, YamlConfig.Project.Environment.Account.class);
            environmentConfig.getAccounts().setIndex(accountList.size() + 1);
            for (YamlConfig.Project.Environment.Account account : accountList) {
                if(account.getType().equals(environmentConfig.getAccounts().getType())&&environmentConfig.getAccounts().getStatus()==1){
                    account.setStatus(0);
                }
            }
            accountList.add(environmentConfig.getAccounts());
            //去重后的集合
            ArrayList<YamlConfig.Project.Environment.Account> collect = accountList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Project.Environment.Account::getType).thenComparing(YamlConfig.Project.Environment.Account::getUserName))), ArrayList::new));
            if (accountList.size() == collect.size()) {
                collect.sort((x, y) -> x.getType().compareTo(y.getType()));
                environmentConfig.setAccountList(collect);
                return edit(environmentConfig);
            } else {
                throw new BizException(SysErrorCode.B_PROJECT_Environment_AccountNameAlreadyExist);
            }
        }
    }

    /**
     * 修改帐号配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean editAccount(EnvironmentConfig environmentConfig) {
        String accountConfig = super.get(environmentConfig.getId()).getAccountConfig();
        List<YamlConfig.Project.Environment.Account> accountList = JSON.parseArray(accountConfig, YamlConfig.Project.Environment.Account.class);
        for (YamlConfig.Project.Environment.Account account : accountList) {
            if (account.getId().equals(environmentConfig.getAccounts().getId())) {
                account.setType(environmentConfig.getAccounts().getType());
                account.setUserName(environmentConfig.getAccounts().getUserName());
                account.setPassWord(environmentConfig.getAccounts().getPassWord());
                account.setDescription(environmentConfig.getAccounts().getDescription());
                account.setStatus(environmentConfig.getAccounts().getStatus());
                account.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
                account.setUpdateTime(DateUtils.getTime());
            }else{
                if(account.getType().equals(environmentConfig.getAccounts().getType())&&environmentConfig.getAccounts().getStatus()==1){
                    account.setStatus(0);
                }
            }
        }
        ArrayList<YamlConfig.Project.Environment.Account> collect = accountList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Project.Environment.Account::getType).thenComparing(YamlConfig.Project.Environment.Account::getUserName))), ArrayList::new));
        if (accountList.size() == collect.size()) {
            collect.sort((x, y) -> x.getType().compareTo(y.getType()));
            environmentConfig.setAccountList(collect);
            return edit(environmentConfig);
        } else {
            throw new BizException(SysErrorCode.B_PROJECT_Environment_AccountNameAlreadyExist);
        }
    }

    /**
     * 删除帐号配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean removeAccount(EnvironmentConfig environmentConfig) {
        String accountConfig = super.get(environmentConfig.getId()).getAccountConfig();
        List<YamlConfig.Project.Environment.Account> accountList = JSON.parseArray(accountConfig, YamlConfig.Project.Environment.Account.class);
        for (String id : environmentConfig.getIds()) {
            accountList.forEach((account)->{
                if (account.getId().equals(id) && account.getStatus() == 1) {
                    throw new BizException(SysErrorCode.B_PROJECT_Environment_StateNotExistent, id);
                }
            });
            if(accountList.size()>1){
                if(accountList.removeIf(account -> account.getId().equals(id))){
                    accountList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                } else {
                    throw new BizException(SysErrorCode.B_PROJECT_Environment_AccountIdNotExistent,id);
                }
            }else {
                throw new BizException(SysErrorCode.B_PROJECT_Environment_DeleteNotExistent);
            }
        }
        final Integer[] i = {1};
        accountList.forEach((e) -> {
            e.setIndex(i[0]);
            i[0]++;
        });
        environmentConfig.setAccountList(accountList);
        return edit(environmentConfig);
    }

    /**
     * 新增服务器配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean addServer(EnvironmentConfig environmentConfig) {
        environmentConfig.getServers().setId(IdUtils.randomUUID());
        environmentConfig.getServers().setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
        environmentConfig.getServers().setCreateTime(DateUtils.getTime());
        environmentConfig.getServers().setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
        environmentConfig.getServers().setUpdateTime(DateUtils.getTime());

        String serverConfig = super.get(environmentConfig).getServerConfig();
        if (StringUtils.isEmpty(serverConfig)) {
            List<YamlConfig.Project.Environment.Server> serverList = new ArrayList<>();
            environmentConfig.getServers().setIndex(1);
            serverList.add(environmentConfig.getServers());
            serverList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            environmentConfig.setServerList(serverList);
            return edit(environmentConfig);
        } else {
            List<YamlConfig.Project.Environment.Server> serverList = JSON.parseArray(serverConfig, YamlConfig.Project.Environment.Server.class);
            environmentConfig.getServers().setIndex(serverList.size() + 1);
            for (YamlConfig.Project.Environment.Server server : serverList) {
                if(environmentConfig.getServers().getStatus()==1){
                    server.setStatus(0);
                }
            }
            serverList.add(environmentConfig.getServers());
            //去重后的集合
            ArrayList<YamlConfig.Project.Environment.Server> collect = serverList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Project.Environment.Server::getHost).thenComparing(YamlConfig.Project.Environment.Server::getUserName))), ArrayList::new));
            if (serverList.size() == collect.size()) {
//                collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                collect.sort((x, y) -> x.getHost().compareTo(y.getHost()));
                environmentConfig.setServerList(collect);
                return edit(environmentConfig);
            } else {
                throw new BizException(SysErrorCode.B_PROJECT_Environment_ServerHostlAlreadyExist);
            }
        }
    }

    /**
     * 修改服务器配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean editServer(EnvironmentConfig environmentConfig) {
        String serverConfig = super.get(environmentConfig.getId()).getServerConfig();
        List<YamlConfig.Project.Environment.Server> serverList = JSON.parseArray(serverConfig, YamlConfig.Project.Environment.Server.class);
        for (YamlConfig.Project.Environment.Server server : serverList) {
            if (server.getId().equals(environmentConfig.getServers().getId())) {
                server.setType(environmentConfig.getServers().getType());
                server.setVersion(environmentConfig.getServers().getVersion());
                server.setHost(environmentConfig.getServers().getHost());
                server.setPort(environmentConfig.getServers().getPort());
                server.setUserName(environmentConfig.getServers().getUserName());
                server.setPassWord(environmentConfig.getServers().getPassWord());
                server.setDescription(environmentConfig.getServers().getDescription());
                server.setConfigList(environmentConfig.getServers().getConfigList());
                server.setStatus(environmentConfig.getServers().getStatus());
                server.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
                server.setUpdateTime(DateUtils.getTime());
            }else{
                if(environmentConfig.getServers().getStatus()==1){
                    server.setStatus(0);
                }
            }
        }
        ArrayList<YamlConfig.Project.Environment.Server> collect = serverList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Project.Environment.Server::getHost).thenComparing(YamlConfig.Project.Environment.Server::getUserName))), ArrayList::new));
        if (serverList.size() == collect.size()) {
//            collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            collect.sort((x, y) -> x.getHost().compareTo(y.getHost()));
            environmentConfig.setServerList(collect);
            return edit(environmentConfig);
        } else {
            throw new BizException(SysErrorCode.B_PROJECT_Environment_ServerHostlAlreadyExist);
        }
    }

    /**
     * 删除服务器配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean removeServer(EnvironmentConfig environmentConfig) {
        String serverConfig = super.get(environmentConfig.getId()).getServerConfig();
        List<YamlConfig.Project.Environment.Server> serverList = JSON.parseArray(serverConfig, YamlConfig.Project.Environment.Server.class);
        for(String id : environmentConfig.getIds()){
            serverList.forEach((server)->{
                if (server.getId().equals(id) && server.getStatus() == 1) {
                    throw new BizException(SysErrorCode.B_PROJECT_Environment_StateNotExistent, id);
                }
            });
            if(serverList.size()>1){
                if(serverList.removeIf(server -> server.getId().equals(id))){
                    serverList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                } else {
                    throw new BizException(SysErrorCode.B_PROJECT_Environment_ServerIdNotExistent,id);
                }
            }else {
                throw new BizException(SysErrorCode.B_PROJECT_Environment_DeleteNotExistent);
            }
        }
        final Integer[] i = {1};
        serverList.forEach((e)->{
            e.setIndex(i[0]);
            i[0]++;
        });
        environmentConfig.setServerList(serverList);
        return edit(environmentConfig);
    }

    /**
     * 新增数据库配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean addDataBase(EnvironmentConfig environmentConfig) {
        environmentConfig.getDataBases().setId(IdUtils.randomUUID());
        environmentConfig.getDataBases().setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
        environmentConfig.getDataBases().setCreateTime(DateUtils.getTime());
        environmentConfig.getDataBases().setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
        environmentConfig.getDataBases().setUpdateTime(DateUtils.getTime());

        String dataBaseConfig = super.get(environmentConfig).getDataBaseConfig();
        if (StringUtils.isEmpty(dataBaseConfig)) {
            List<YamlConfig.Project.Environment.DataBase> dataBaseList = new ArrayList<>();
            environmentConfig.getDataBases().setIndex(1);
            dataBaseList.add(environmentConfig.getDataBases());
            dataBaseList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            environmentConfig.setDataBaseList(dataBaseList);
            return edit(environmentConfig);
        } else {
            List<YamlConfig.Project.Environment.DataBase> dataBaseList = JSON.parseArray(dataBaseConfig, YamlConfig.Project.Environment.DataBase.class);
            environmentConfig.getDataBases().setIndex(dataBaseList.size() + 1);
//            for (YamlConfig.Project.Environment.DataBase dataBase : dataBaseList) {
//                if(dataBase.getName().equals(environmentConfig.getDataBases().getName()) && environmentConfig.getDataBases().getStatus()==1){
//                    dataBase.setStatus(0);
//                }
//            }
            dataBaseList.add(environmentConfig.getDataBases());
            //去重后的集合
            ArrayList<YamlConfig.Project.Environment.DataBase> collect = dataBaseList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Project.Environment.DataBase::getName).thenComparing(YamlConfig.Project.Environment.DataBase::getPort))), ArrayList::new));
            if (dataBaseList.size() == collect.size()) {
//                collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                collect.sort((x, y) -> y.getName().compareTo(x.getName()));
                environmentConfig.setDataBaseList(collect);
                return edit(environmentConfig);
            } else {
                throw new BizException(SysErrorCode.B_PROJECT_Environment_DataBaseNameAlreadyExist);
            }
        }
    }

    /**
     * 修改数据库配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean editDataBase(EnvironmentConfig environmentConfig) {
        String dataBaseConfig = super.get(environmentConfig.getId()).getDataBaseConfig();
        List<YamlConfig.Project.Environment.DataBase> dataBaseList = JSON.parseArray(dataBaseConfig, YamlConfig.Project.Environment.DataBase.class);
        for (YamlConfig.Project.Environment.DataBase dataBase : dataBaseList) {
            if (dataBase.getId().equals(environmentConfig.getDataBases().getId())) {
                dataBase.setType(environmentConfig.getDataBases().getType());
                dataBase.setVersion(environmentConfig.getDataBases().getVersion());
                dataBase.setName(environmentConfig.getDataBases().getName());
                dataBase.setPort(environmentConfig.getDataBases().getPort());
                dataBase.setDriver(environmentConfig.getDataBases().getDriver());
                dataBase.setUrl(environmentConfig.getDataBases().getUrl());
                dataBase.setUserName(environmentConfig.getDataBases().getUserName());
                dataBase.setPassWord(environmentConfig.getDataBases().getPassWord());
                dataBase.setMaxActive(environmentConfig.getDataBases().getMaxActive());
                dataBase.setMaxWait(environmentConfig.getDataBases().getMaxWait());
                dataBase.setDescription(environmentConfig.getDataBases().getDescription());
                dataBase.setStatus(environmentConfig.getDataBases().getStatus());
                dataBase.setConfigList(environmentConfig.getDataBases().getConfigList());
                dataBase.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
                dataBase.setUpdateTime(DateUtils.getTime());
            }else{
//                if(dataBase.getName().equals(environmentConfig.getDataBases().getName()) && environmentConfig.getDataBases().getStatus()==1){
//                    dataBase.setStatus(0);
//                }
            }
        }
        ArrayList<YamlConfig.Project.Environment.DataBase> collect = dataBaseList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Project.Environment.DataBase::getName).thenComparing(YamlConfig.Project.Environment.DataBase::getPort))), ArrayList::new));
        if (dataBaseList.size() == collect.size()) {
//            collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            collect.sort((x, y) -> y.getName().compareTo(x.getName()));
            environmentConfig.setDataBaseList(collect);
            return edit(environmentConfig);
        } else {
            throw new BizException(SysErrorCode.B_PROJECT_Environment_DataBaseNameAlreadyExist);
        }
    }

    /**
     * 删除数据库配置信息
     *
     * @param environmentConfig 环境配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean removeDataBase(EnvironmentConfig environmentConfig) {
        String dataBaseConfig = super.get(environmentConfig.getId()).getDataBaseConfig();
        List<YamlConfig.Project.Environment.DataBase> dataBaseList = JSON.parseArray(dataBaseConfig, YamlConfig.Project.Environment.DataBase.class);
        for(String id : environmentConfig.getIds()){
            dataBaseList.forEach((dataBase)->{
                if (dataBase.getId().equals(id) && dataBase.getStatus() == 1) {
                    throw new BizException(SysErrorCode.B_PROJECT_Environment_StateNotExistent, id);
                }
            });
            if(dataBaseList.size()>1){
                if(dataBaseList.removeIf(dataBase -> dataBase.getId().equals(id))){
//                    dataBaseList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                    dataBaseList.sort((x, y) -> y.getName().compareTo(x.getName()));
                } else {
                    throw new BizException(SysErrorCode.B_PROJECT_Environment_DataBaseIdNotExistent,id);
                }
            }else {
                throw new BizException(SysErrorCode.B_PROJECT_Environment_DeleteNotExistent);
            }
        }
        final Integer[] i = {1};
        dataBaseList.forEach((e)->{
            e.setIndex(i[0]);
            i[0]++;
        });
        environmentConfig.setDataBaseList(dataBaseList);
        return edit(environmentConfig);
    }
}