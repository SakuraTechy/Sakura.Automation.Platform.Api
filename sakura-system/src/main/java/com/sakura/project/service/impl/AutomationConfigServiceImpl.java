package com.sakura.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.common.exception.BizException;
import com.sakura.common.utils.date.DateUtils;
import com.sakura.common.utils.SecurityUtils;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.jenkins.JenkinsService;
import com.sakura.common.utils.json.JsonUtils;
import com.sakura.common.utils.uuid.IdUtils;
import com.sakura.common.utils.xml.SceneXmlUtils;
import com.sakura.common.utils.yaml.YamlConfig;
import com.sakura.common.utils.yaml.YmlUtil;
import com.sakura.project.domain.AutomationConfig;
import com.sakura.project.service.ProjectConfigService;
import com.sakura.system.common.SysErrorCode;

import com.sakura.project.mapper.AutomationConfigMapper;
import com.sakura.project.service.AutomationConfigService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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
 * 自动化配置Service业务层处理
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-26
 */
@Service
@Transactional(readOnly = true)
public class AutomationConfigServiceImpl extends BaseServiceImpl<AutomationConfigMapper, AutomationConfig> implements AutomationConfigService {

    private static final Logger log = LoggerFactory.getLogger(AutomationConfigServiceImpl.class);

    @Autowired
    private ProjectConfigService projectConfigService;

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
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Override
    public AutomationConfig get(AutomationConfig automationConfig) {
        return super.get(automationConfig);
    }

    /**
     * 获取单条数据
     *
     * @param id 自动化配置id
     * @return 结果
     */
    @Override
    public AutomationConfig get(String id) {
        return super.get(id);
    }

    /**
     * 查询自动化配置列表
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Override
    public List<AutomationConfig> findList(AutomationConfig automationConfig) {
        return super.findList(automationConfig);
    }

    /**
     * 分页查询自动化配置列表
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Override
    public PageInfo<AutomationConfig> findPage(AutomationConfig automationConfig) {
        return super.findPage(automationConfig);
    }

    /**
     * 保存自动化配置
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Override
    public boolean save(AutomationConfig automationConfig) {
        if (!CollectionUtils.isEmpty(mapper.findListWithUnique(automationConfig))) {
            throw new BizException(SysErrorCode.B_PROJECT_AutomationNameAlreadyExist);
        }

        List<YamlConfig.Automation.Project> projectList = automationConfig.getProjectList();
        if (StringUtils.isNotEmpty(projectList)) {
            automationConfig.setProjectConfig(JSON.toJSONString(projectList));
        }
        
        List<YamlConfig.Automation.Jenkins> jenkinsList = automationConfig.getJenkinsList();
        if (StringUtils.isNotEmpty(jenkinsList)) {
            automationConfig.setJenkinsConfig(JSON.toJSONString(jenkinsList));
        }
        
        List<YamlConfig.Automation.Environment> environmentList = automationConfig.getEnvironmentList();
        if (StringUtils.isNotEmpty(environmentList)) {
            automationConfig.setEnvironmentConfig(JSON.toJSONString(environmentList));
        }
        
        List<YamlConfig.Automation.Browser> browserList = automationConfig.getBrowserList();
        if (StringUtils.isNotEmpty(browserList)) {
            automationConfig.setBrowserConfig(JSON.toJSONString(browserList));
        }

        if(StringUtils.isNotNull(automationConfig.getStatus())&&automationConfig.getStatus()==1){
            automationConfig.preUpdate();
            mapper.closeStatus(automationConfig);
        }
        return super.save(automationConfig);
    }

    /**
     * 修改自动化配置
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean edit(AutomationConfig automationConfig) {
        List<YamlConfig.Automation.Project> projectList = automationConfig.getProjectList();
        if (StringUtils.isNotEmpty(projectList)) {
            automationConfig.setProjectConfig(JSON.toJSONString(projectList));
        }

        List<YamlConfig.Automation.Jenkins> jenkinsList = automationConfig.getJenkinsList();
        if (StringUtils.isNotEmpty(jenkinsList)) {
            automationConfig.setJenkinsConfig(JSON.toJSONString(jenkinsList));
        }
        
        List<YamlConfig.Automation.Environment> environmentList = automationConfig.getEnvironmentList();
        if (StringUtils.isNotEmpty(environmentList)) {
            automationConfig.setEnvironmentConfig(JSON.toJSONString(environmentList));
        }

        List<YamlConfig.Automation.Browser> browserList = automationConfig.getBrowserList();
        if (StringUtils.isNotEmpty(browserList)) {
            automationConfig.setBrowserConfig(JSON.toJSONString(browserList));
        }

        if(StringUtils.isNotNull(automationConfig.getStatus())&&automationConfig.getStatus()==1){
            automationConfig.preUpdate();
            mapper.closeStatus(automationConfig);
        }
        return super.save(automationConfig);
    }

    /**
     * 批量删除自动化配置
     *
     * @param ids 需要删除的自动化配置ID
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public int deleteAutomationConfigByIds(String[] ids) {
        return mapper.deleteAutomationConfigByIds(ids, BaseEntity.DEL_FLAG_DELETE);
    }

    /**
     * 删除自动化配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Override
    public boolean remove(AutomationConfig automationConfig) {
        if (StringUtils.isNotNull(automationConfig.getIds())) {
            for (String id :automationConfig.getIds()){
                automationConfig.setType(super.get(id).getType());
                List<AutomationConfig> automationConfigList = super.findList(automationConfig);
                automationConfigList.forEach((automation)->{
                    if (automation.getId().equals(id) && automation.getStatus() == 1) {
                        throw new BizException(SysErrorCode.B_PROJECT_Automation_StateNotExistent, id);
                    }
                });
                if(automationConfigList.size()>=1){
                    automationConfig.setId(id);
                    if (super.remove(automationConfig)) {
                        automationConfig.setId("");
                    } else {
                        throw new BizException(SysErrorCode.B_PROJECT_AutomationDeleteFailed, id);
                    }
                }else {
                    throw new BizException(SysErrorCode.B_PROJECT_Automation_DeleteNotExistent);
                }
            }
        }
        return true;
    }

    /**
     * 更新自动化配置状态
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public int updateStatus(AutomationConfig automationConfig) {
        automationConfig.setUpdateByName(SecurityUtils.getLoginUser().getUser().getId());
        automationConfig.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
        automationConfig.setUpdateTime(new Date());
        automationConfig.setUpdateIp(SecurityUtils.getLoginUser().getIpaddr());
        if(automationConfig.getStatus()==1){
            mapper.closeStatus(automationConfig);
        }
        return mapper.updateStatus(automationConfig);
    }
    
    /**
     * 导出自动化配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public void export(AutomationConfig automationConfig, HttpServletResponse resp, HttpServletRequest req) throws IOException, NoSuchFieldException {
        YamlConfig yamlConfig = new YamlConfig();
        ArrayList<YamlConfig.Automation> automations = new ArrayList<>();

        if (StringUtils.isNotNull(automationConfig.getIds())) {
            for (String id :automationConfig.getIds()){
                automationConfig = super.get(id);

                YamlConfig.Automation automation = new YamlConfig.Automation();
                automation.setId(automationConfig.getId());
                automation.setName(automationConfig.getName());
                automation.setStatus(automationConfig.getStatus());
                automation.setDescription(automationConfig.getDescription());
                automation.setType(automationConfig.getType());

                List<YamlConfig.Automation.Project> projectList = JSON.parseArray(automationConfig.getProjectConfig(),YamlConfig.Automation.Project.class);
                ArrayList<YamlConfig.Automation.Project> projects = new ArrayList<>();
                for (YamlConfig.Automation.Project project : projectList) {
                    if(project.getStatus() == 1){
                        projects.add(project);
                    }
                }
                List<YamlConfig.Automation.Jenkins> jenkinsList = JSON.parseArray(automationConfig.getJenkinsConfig(),YamlConfig.Automation.Jenkins.class);
                ArrayList<YamlConfig.Automation.Jenkins> jenkinss = new ArrayList<>();
                for (YamlConfig.Automation.Jenkins jenkins : jenkinsList) {
                    if(jenkins.getStatus() == 1){
                        jenkinss.add(jenkins);
                    }
                }
                List<YamlConfig.Automation.Environment> environmentList = JSON.parseArray(automationConfig.getEnvironmentConfig(),YamlConfig.Automation.Environment.class);
                ArrayList<YamlConfig.Automation.Environment> environments = new ArrayList<>();
                for (YamlConfig.Automation.Environment environment : environmentList) {
                    if(environment.getStatus() == 1){
                        environments.add(environment);
                    }
                }
                List<YamlConfig.Automation.Browser> browserList = JSON.parseArray(automationConfig.getBrowserConfig(),YamlConfig.Automation.Browser.class);
                ArrayList<YamlConfig.Automation.Browser> browsers = new ArrayList<>();
                for (YamlConfig.Automation.Browser browser : browserList) {
                    if(browser.getStatus() == 1){
                        browsers.add(browser);
                    }
                }

                automation.setProjectList(projects);
                automation.setJenkinsList(jenkinss);
                automation.setEnvironmentList(environments);
                automation.setBrowserList(browsers);
                automations.add(automation);
            }
            yamlConfig.setAutomations(automations);
            YmlUtil.creatYaml(yamlConfig,resp,req);
        }
    }

    /**
     * 新增项目配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean addProject(AutomationConfig automationConfig) {
        automationConfig.getProject().setId(IdUtils.randomUUID());
        automationConfig.getProject().setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
        automationConfig.getProject().setCreateTime(DateUtils.getTime());
        automationConfig.getProject().setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
        automationConfig.getProject().setUpdateTime(DateUtils.getTime());

        String projectConfig = super.get(automationConfig).getProjectConfig();
        if (StringUtils.isEmpty(projectConfig)) {
            List<YamlConfig.Automation.Project> projectList = new ArrayList<>();
            automationConfig.getProject().setIndex(1);
            projectList.add(automationConfig.getProject());
//            projectList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            automationConfig.setProjectList(projectList);
            if(automationConfig.getProject().getStatus()==1){
                automationConfig.setLastProject(automationConfig.getProject().getName());
            }
            return edit(automationConfig);
        } else {
            List<YamlConfig.Automation.Project> projectList = JSON.parseArray(projectConfig, YamlConfig.Automation.Project.class);
            automationConfig.getProject().setIndex(projectList.size() + 1);
            for (YamlConfig.Automation.Project project : projectList) {
                if(automationConfig.getProject().getStatus()==1){
                    project.setStatus(0);
                    automationConfig.setLastProject(automationConfig.getProject().getName());
                }
            }
            projectList.add(automationConfig.getProject());
            //去重后的集合
            ArrayList<YamlConfig.Automation.Project> collect = projectList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Automation.Project::getName))), ArrayList::new));
            if (projectList.size() == collect.size()) {
//                collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                collect.sort((x, y) -> x.getName().compareTo(y.getName()));
                automationConfig.setProjectList(collect);
                return edit(automationConfig);
            } else {
                throw new BizException(SysErrorCode.B_PROJECT_Automation_ProjectNameAlreadyExist);
            }
        }
    }

    /**
     * 修改项目配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean editProject(AutomationConfig automationConfig) {
        String projectConfig = super.get(automationConfig.getId()).getProjectConfig();
        List<YamlConfig.Automation.Project> projectList = JSON.parseArray(projectConfig, YamlConfig.Automation.Project.class);
        for (YamlConfig.Automation.Project project : projectList) {
            if (project.getId().equals(automationConfig.getProject().getId())) {
                project.setName(automationConfig.getProject().getName());
                project.setDescription(automationConfig.getProject().getDescription());
                project.setUrl(automationConfig.getProject().getUrl());
                project.setPath(automationConfig.getProject().getPath());
                project.setStatus(automationConfig.getProject().getStatus());
                project.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
                project.setUpdateTime(DateUtils.getTime());
            }else{
                if(automationConfig.getProject().getStatus()==1){
                    project.setStatus(0);
                    automationConfig.setLastProject(automationConfig.getProject().getName());
                }
            }
        }
        ArrayList<YamlConfig.Automation.Project> collect = projectList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Automation.Project::getName))), ArrayList::new));
        if (projectList.size() == collect.size()) {
//            collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            collect.sort((x, y) -> x.getName().compareTo(y.getName()));
            automationConfig.setProjectList(collect);
            return edit(automationConfig);
        } else {
            throw new BizException(SysErrorCode.B_PROJECT_Automation_ProjectNameAlreadyExist);
        }
    }

    /**
     * 删除项目配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean removeProject(AutomationConfig automationConfig) {
        String projectConfig = super.get(automationConfig.getId()).getProjectConfig();
        List<YamlConfig.Automation.Project> projectList = JSON.parseArray(projectConfig, YamlConfig.Automation.Project.class);
        for (String id : automationConfig.getIds()) {
            projectList.forEach((project)->{
                if (project.getId().equals(id) && project.getStatus() == 1) {
                    throw new BizException(SysErrorCode.B_PROJECT_Automation_StateNotExistent, id);
                }
            });
            if(projectList.size()>1){
                if(projectList.removeIf(project -> project.getId().equals(id))){
                    projectList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                } else {
                    throw new BizException(SysErrorCode.B_PROJECT_Automation_ProjectIdNotExistent,id);
                }
            }else {
                throw new BizException(SysErrorCode.B_PROJECT_Automation_DeleteNotExistent);
            }
        }
        final Integer[] i = {1};
        projectList.forEach((e) -> {
            e.setIndex(i[0]);
            i[0]++;
        });
        automationConfig.setProjectList(projectList);
        return edit(automationConfig);
    }

    /**
     * 新增Jenkins配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean addJenkins(AutomationConfig automationConfig) {
        automationConfig.getJenkins().setId(IdUtils.randomUUID());
        automationConfig.getJenkins().setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
        automationConfig.getJenkins().setCreateTime(DateUtils.getTime());
        automationConfig.getJenkins().setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
        automationConfig.getJenkins().setUpdateTime(DateUtils.getTime());

        String jenkinsConfig = super.get(automationConfig).getJenkinsConfig();
        if (StringUtils.isEmpty(jenkinsConfig)) {
            List<YamlConfig.Automation.Jenkins> jenkinsList = new ArrayList<>();
            automationConfig.getJenkins().setIndex(1);
            jenkinsList.add(automationConfig.getJenkins());
//            jenkinsList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            automationConfig.setJenkinsList(jenkinsList);
            if(automationConfig.getJenkins().getStatus()==1){
                automationConfig.setLastJenkins(automationConfig.getJenkins().getIp());
            }
            return edit(automationConfig);
        } else {
            List<YamlConfig.Automation.Jenkins> jenkinsList = JSON.parseArray(jenkinsConfig, YamlConfig.Automation.Jenkins.class);
            automationConfig.getJenkins().setIndex(jenkinsList.size() + 1);
            for (YamlConfig.Automation.Jenkins jenkins : jenkinsList) {
                if(automationConfig.getJenkins().getStatus()==1){
                    jenkins.setStatus(0);
                    automationConfig.setLastJenkins(automationConfig.getJenkins().getIp());
                }
            }
            jenkinsList.add(automationConfig.getJenkins());
            //去重后的集合
            ArrayList<YamlConfig.Automation.Jenkins> collect = jenkinsList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Automation.Jenkins::getIp).thenComparing(YamlConfig.Automation.Jenkins::getPort))), ArrayList::new));
            if (jenkinsList.size() == collect.size()) {
//                collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                collect.sort((x, y) -> x.getIp().compareTo(y.getIp()));
                automationConfig.setJenkinsList(collect);
                return edit(automationConfig);
            } else {
                throw new BizException(SysErrorCode.B_PROJECT_Automation_JenkinsIPAlreadyExist);
            }
        }
    }

    /**
     * 修改Jenkins配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean editJenkins(AutomationConfig automationConfig) {
        String jenkinsConfig = super.get(automationConfig.getId()).getJenkinsConfig();
        List<YamlConfig.Automation.Jenkins> jenkinsList = JSON.parseArray(jenkinsConfig, YamlConfig.Automation.Jenkins.class);
        for (YamlConfig.Automation.Jenkins jenkins : jenkinsList) {
            if (jenkins.getId().equals(automationConfig.getJenkins().getId())) {
                jenkins.setIp(automationConfig.getJenkins().getIp());
                jenkins.setPort(automationConfig.getJenkins().getPort());
                jenkins.setUserName(automationConfig.getJenkins().getUserName());
                jenkins.setPassWord(automationConfig.getJenkins().getPassWord());
                jenkins.setDescription(automationConfig.getJenkins().getDescription());
                jenkins.setUrl(automationConfig.getJenkins().getUrl());
                jenkins.setJob(automationConfig.getJenkins().getJob());
                jenkins.setStatus(automationConfig.getJenkins().getStatus());
                jenkins.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
                jenkins.setUpdateTime(DateUtils.getTime());
            }else{
                if(automationConfig.getJenkins().getStatus()==1){
                    jenkins.setStatus(0);
                    automationConfig.setLastJenkins(automationConfig.getJenkins().getIp());
                }
            }
        }
        ArrayList<YamlConfig.Automation.Jenkins> collect = jenkinsList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Automation.Jenkins::getIp).thenComparing(YamlConfig.Automation.Jenkins::getPort))), ArrayList::new));
        if (jenkinsList.size() == collect.size()) {
//            collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            collect.sort((x, y) -> x.getIp().compareTo(y.getIp()));
            automationConfig.setJenkinsList(collect);
            return edit(automationConfig);
        } else {
            throw new BizException(SysErrorCode.B_PROJECT_Automation_JenkinsIPAlreadyExist);
        }
    }

    /**
     * 删除Jenkins配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean removeJenkins(AutomationConfig automationConfig) {
        String jenkinsConfig = super.get(automationConfig.getId()).getJenkinsConfig();
        List<YamlConfig.Automation.Jenkins> jenkinsList = JSON.parseArray(jenkinsConfig, YamlConfig.Automation.Jenkins.class);
        for (String id : automationConfig.getIds()) {
            jenkinsList.forEach((jenkins)->{
                if (jenkins.getId().equals(id) && jenkins.getStatus() == 1) {
                    throw new BizException(SysErrorCode.B_PROJECT_Automation_StateNotExistent, id);
                }
            });
            if(jenkinsList.size()>1){
                if(jenkinsList.removeIf(jenkins -> jenkins.getId().equals(id))){
                    jenkinsList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                } else {
                    throw new BizException(SysErrorCode.B_PROJECT_Automation_JenkinsIdNotExistent,id);
                }
            }else {
                throw new BizException(SysErrorCode.B_PROJECT_Automation_DeleteNotExistent);
            }
        }
        final Integer[] i = {1};
        jenkinsList.forEach((e) -> {
            e.setIndex(i[0]);
            i[0]++;
        });
        automationConfig.setJenkinsList(jenkinsList);
        return edit(automationConfig);
    }

    /**
     * 新增环境配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean addEnvironment(AutomationConfig automationConfig) {
        automationConfig.getEnvironment().setId(IdUtils.randomUUID());
        automationConfig.getEnvironment().setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
        automationConfig.getEnvironment().setCreateTime(DateUtils.getTime());
        automationConfig.getEnvironment().setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
        automationConfig.getEnvironment().setUpdateTime(DateUtils.getTime());

        String environmentConfig = super.get(automationConfig).getEnvironmentConfig();
        if (StringUtils.isEmpty(environmentConfig)) {
            List<YamlConfig.Automation.Environment> environmentList = new ArrayList<>();
            automationConfig.getEnvironment().setIndex(1);
            environmentList.add(automationConfig.getEnvironment());
//            environmentList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            automationConfig.setEnvironmentList(environmentList);
            if(automationConfig.getEnvironment().getStatus()==1){
                automationConfig.setLastEnvironment(automationConfig.getEnvironment().getName());
            }
            return edit(automationConfig);
        } else {
            List<YamlConfig.Automation.Environment> environmentList = JSON.parseArray(environmentConfig, YamlConfig.Automation.Environment.class);
            automationConfig.getEnvironment().setIndex(environmentList.size() + 1);
            for (YamlConfig.Automation.Environment environment : environmentList) {
                if(automationConfig.getEnvironment().getStatus()==1){
                    environment.setStatus(0);
                    automationConfig.setLastEnvironment(automationConfig.getEnvironment().getName());
                }
            }
            environmentList.add(automationConfig.getEnvironment());
            //去重后的集合
            ArrayList<YamlConfig.Automation.Environment> collect = environmentList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Automation.Environment::getName))), ArrayList::new));
            if (environmentList.size() == collect.size()) {
//                collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                collect.sort((x, y) -> x.getName().compareTo(y.getName()));
                automationConfig.setEnvironmentList(collect);
                return edit(automationConfig);
            } else {
                throw new BizException(SysErrorCode.B_PROJECT_Automation_EnvironmentNameAlreadyExist);
            }
        }
    }

    /**
     * 修改环境配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean editEnvironment(AutomationConfig automationConfig) {
        String environmentConfig = super.get(automationConfig.getId()).getEnvironmentConfig();
        List<YamlConfig.Automation.Environment> environmentList = JSON.parseArray(environmentConfig, YamlConfig.Automation.Environment.class);
        for (YamlConfig.Automation.Environment environment : environmentList) {
            if (environment.getId().equals(automationConfig.getEnvironment().getId())) {
                environment.setName(automationConfig.getEnvironment().getName());
                environment.setDescription(automationConfig.getEnvironment().getDescription());
                environment.setConfigList(automationConfig.getEnvironment().getConfigList());
                environment.setStatus(automationConfig.getEnvironment().getStatus());
                environment.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
                environment.setUpdateTime(DateUtils.getTime());
            }else{
                if(automationConfig.getEnvironment().getStatus()==1){
                    environment.setStatus(0);
                    automationConfig.setLastEnvironment(automationConfig.getEnvironment().getName());
                }
            }
        }
        ArrayList<YamlConfig.Automation.Environment> collect = environmentList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Automation.Environment::getName))), ArrayList::new));
        if (environmentList.size() == collect.size()) {
//            collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            collect.sort((x, y) -> x.getName().compareTo(y.getName()));
            automationConfig.setEnvironmentList(collect);
            return edit(automationConfig);
        } else {
            throw new BizException(SysErrorCode.B_PROJECT_Automation_EnvironmentNameAlreadyExist);
        }
    }

    /**
     * 删除环境配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean removeEnvironment(AutomationConfig automationConfig) {
        String environmentConfig = super.get(automationConfig.getId()).getEnvironmentConfig();
        List<YamlConfig.Automation.Environment> environmentList = JSON.parseArray(environmentConfig, YamlConfig.Automation.Environment.class);
        for (String id : automationConfig.getIds()) {
            environmentList.forEach((environment)->{
                if (environment.getId().equals(id) && environment.getStatus() == 1) {
                    throw new BizException(SysErrorCode.B_PROJECT_Automation_StateNotExistent, id);
                }
            });
            if(environmentList.size()>1){
                if(environmentList.removeIf(environment -> environment.getId().equals(id))){
                    environmentList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                } else {
                    throw new BizException(SysErrorCode.B_PROJECT_Automation_EnvironmentIdNotExistent,id);
                }
            }else {
                throw new BizException(SysErrorCode.B_PROJECT_Automation_DeleteNotExistent);
            }
        }
        final Integer[] i = {1};
        environmentList.forEach((e) -> {
            e.setIndex(i[0]);
            i[0]++;
        });
        automationConfig.setEnvironmentList(environmentList);
        return edit(automationConfig);
    }

//    /**
//     * 同步环境配置信息（所有）
//     *
//     * @param automationConfig 自动化配置
//     * @return 结果
//     */
//    @Transactional(readOnly = false)
//    @Override
//    public boolean syncEnvironmentAll(AutomationConfig automationConfig) throws JsonProcessingException {
//        YamlConfig.Automation.Jenkins jenkins = SceneXmlUtils.getAutomationJenkins();
//        String jenkinsUrl = jenkins.getUrl();
//        String jenkinsUserName = jenkins.getUserName();
//        String jenkinsPassWord = jenkins.getPassWord();
//
//        List<YamlConfig.Automation.Environment> environmentList = new ArrayList<>();
//        JsonNode jsonNode = JenkinsService.getJenkinsNodeAll(jenkinsUrl,jenkinsUserName,jenkinsPassWord);
//        int index = 1;
//        for (JsonNode computer : jsonNode.get("computer")) {
//            YamlConfig.Automation.Environment environment = new YamlConfig.Automation.Environment();
//            environment.setIndex(index);
//            environment.setId(IdUtils.randomUUID());;
//            String displayName = computer.get("displayName").asText();
//            if ("Built-In Node".equals(displayName)) {
//                environment.setName("(master)");
////                displayName = "(master)";
//            } else {
//                environment.setName(displayName);
//            }
//            environment.setDescription(computer.get("description").asText());
//            YamlConfig.Automation.Environment.Active active = new YamlConfig.Automation.Environment.Active();
//            YamlConfig.Automation.Environment.Active.Offline offline = new YamlConfig.Automation.Environment.Active.Offline();
//            offline.setStatus(computer.get("offline").asBoolean() ? 0 : 1);
//            offline.setOfflineCauseReason(computer.get("offlineCauseReason").asText());
//            active.setOffline(offline);
//            YamlConfig.Automation.Environment.Active.Idle idle = new YamlConfig.Automation.Environment.Active.Idle();
//            boolean status = computer.get("idle").asBoolean();
//            idle.setStatus(status ? 1 : 0);
//
//            if (!status) {
//                List<YamlConfig.Automation.Environment.Active.Idle.CurrentExecutable> currentExecutableList = new ArrayList<>();
//                for (JsonNode executors : computer.get("executors")) {
//                    JsonNode currentExecutableJn = executors.get("currentExecutable");
//                    if(!currentExecutableJn.isEmpty()){
//                        String url = currentExecutableJn.get("url").asText();
//                        if (StringUtils.isNotEmpty(url)) {
//                            YamlConfig.Automation.Environment.Active.Idle.CurrentExecutable currentExecutable = new YamlConfig.Automation.Environment.Active.Idle.CurrentExecutable();
//                            currentExecutable.setUrl(url);
//                            currentExecutable.setUser(JenkinsService.getJenkinsJobParameters(url,jenkinsUserName,jenkinsPassWord));
//                            currentExecutableList.add(currentExecutable);
//                        }
//                    }
//                }
//                idle.setExecutors(currentExecutableList);
//            }
//            active.setIdle(idle);
//            environment.setActive(active);
//
//            List<YamlConfig.Automation.Environment.Config> configList = new ArrayList<>();
////            if(!"Built-In Node".equals(displayName)){
//                YamlConfig.Automation.Environment.Config config = new YamlConfig.Automation.Environment.Config();
//                try {
//                    JsonNode rootNode = JenkinsService.getJenkinsNodeDetails(jenkinsUrl,jenkinsUserName,jenkinsPassWord,displayName);
//                    config.setParamsName("workDirPath");
//                    config.setParamsValue(rootNode.get("remoteFS").asText());
//                    configList.add(config);
//                    JsonNode toolLocationNode = rootNode.get("nodeProperties").get("hudson.tools.ToolLocationNodeProperty").get("locations").get("hudson.tools.ToolLocationNodeProperty_-ToolLocation");
//                    for (JsonNode tools : toolLocationNode) {
//                        config = new YamlConfig.Automation.Environment.Config();
//                        config.setParamsName(tools.get("name").asText());
//                        config.setParamsValue(tools.get("home").asText());
//                        configList.add(config);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
////            }
//            environment.setConfigList(configList);
//            environment.setStatus(0);
//            environment.setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
//            environment.setCreateTime(DateUtils.getTime());
//            environment.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
//            environment.setUpdateTime(DateUtils.getTime());
//            environmentList.add(environment);
//            index++;
//        }
//        environmentList.sort((x, y) -> x.getName().compareTo(y.getName()));
//        automationConfig.setEnvironmentList(environmentList);
//        return edit(automationConfig);
//    }

    /**
     * 同步环境配置信息（所有）
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean syncEnvironmentAll(AutomationConfig automationConfig) throws JsonProcessingException {
        YamlConfig.Automation.Jenkins jenkins = SceneXmlUtils.getAutomationJenkins();
        String jenkinsUrl = jenkins.getUrl();
        String jenkinsUserName = jenkins.getUserName();
        String jenkinsPassWord = jenkins.getPassWord();


        JsonNode jsonNode = JenkinsService.getJenkinsNodeAll(jenkinsUrl, jenkinsUserName, jenkinsPassWord);
        String environmentConfig = super.get(automationConfig.getId()).getEnvironmentConfig();
        List<YamlConfig.Automation.Environment> environmentList = JSON.parseArray(environmentConfig, YamlConfig.Automation.Environment.class);
        if (jsonNode.get("computer").size() == environmentList.size()) {
            for (JsonNode computer : jsonNode.get("computer")) {
                environmentList.forEach((environment) -> {
                    String displayName = computer.get("displayName").asText();
                    if ("Built-In Node".equals(displayName)) {
                        displayName = "(master)";
                    }
                    if (environment.getName().equals(displayName)) {
                        environment.setUrl("" + jenkinsUrl + "/computer/" + displayName + "/");
                        String descriptionStr = computer.get("description").asText();
                        YamlConfig.Automation.Environment.Description description = new YamlConfig.Automation.Environment.Description();
                        if (JsonUtils.isJson(descriptionStr)) {
                            description = JSON.parseObject(descriptionStr, YamlConfig.Automation.Environment.Description.class);
                        } else {
                            description.setName("Jenkins远程节点描述信息非JSON格式，请检查配置！");
                        }
                        environment.setDescription(description);
                        YamlConfig.Automation.Environment.Active active = new YamlConfig.Automation.Environment.Active();
                        YamlConfig.Automation.Environment.Active.Offline offline = new YamlConfig.Automation.Environment.Active.Offline();
                        offline.setStatus(computer.get("offline").asBoolean() ? 0 : 1);
                        offline.setOfflineCauseReason(computer.get("offlineCauseReason").asText());
                        active.setOffline(offline);
                        YamlConfig.Automation.Environment.Active.Idle idle = new YamlConfig.Automation.Environment.Active.Idle();
                        boolean status = computer.get("idle").asBoolean();
                        idle.setStatus(status ? 1 : 0);
                        if (!status) {
                            List<YamlConfig.Automation.Environment.Active.Idle.CurrentExecutable> currentExecutableList = new ArrayList<>();
//                            List<YamlConfig.Automation.Environment.Active.Idle.CurrentExecutable> currentExecutableList = environment.getActive().getIdle().getCurrentExecutable();
                            for (JsonNode executors : computer.get("executors")) {
                                JsonNode currentExecutableJn = executors.get("currentExecutable");
                                if (!currentExecutableJn.isEmpty()) {
                                    String url = currentExecutableJn.get("url").asText();
                                    if (StringUtils.isNotEmpty(url)) {
                                        YamlConfig.Automation.Environment.Active.Idle.CurrentExecutable currentExecutable = new YamlConfig.Automation.Environment.Active.Idle.CurrentExecutable();
                                        currentExecutable.setUrl(url);
                                        currentExecutable.setUser(JenkinsService.getJenkinsJobParameters(url, jenkinsUserName, jenkinsPassWord));
                                        currentExecutableList.add(currentExecutable);
                                    }
                                }
                            }
                            idle.setExecutors(currentExecutableList);
                        }
                        active.setIdle(idle);
                        environment.setActive(active);

                        List<YamlConfig.Automation.Environment.Config> configList = environment.getConfigList();
                        try {
                            JsonNode rootNode = JenkinsService.getJenkinsNodeDetails(jenkinsUrl, jenkinsUserName, jenkinsPassWord, displayName);
                            configList.stream().filter(o -> "workDirPath".equals(o.getParamsName())).forEach(f -> {
                                f.setParamsValue(rootNode.get("remoteFS").asText());
                            });
                            JsonNode toolLocationNode = rootNode.get("nodeProperties").get("hudson.tools.ToolLocationNodeProperty").get("locations").get("hudson.tools.ToolLocationNodeProperty_-ToolLocation");
                            for (JsonNode tools : toolLocationNode) {
                                configList.stream().filter(o -> o.getParamsName().equals(tools.get("name").asText())).forEach(f -> {
                                    f.setParamsValue(tools.get("home").asText());
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        environment.setConfigList(configList);
//                        environment.setStatus(0);
                        environment.setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
                        environment.setCreateTime(DateUtils.getTime());
                        environment.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
                        environment.setUpdateTime(DateUtils.getTime());
                    }
                });
            }
        } else {
            int index = 1;
            environmentList = new ArrayList<>();
            for (JsonNode computer : jsonNode.get("computer")) {
                YamlConfig.Automation.Environment environment = new YamlConfig.Automation.Environment();
                environment.setIndex(index);
                environment.setId(IdUtils.randomUUID());
                String displayName = computer.get("displayName").asText();
                if ("Built-In Node".equals(displayName)) {
                    displayName = "(master)";
                }
                environment.setName(displayName);
                environment.setUrl(""+jenkinsUrl+"/computer/"+displayName+"/");
                String descriptionStr = computer.get("description").asText();
                YamlConfig.Automation.Environment.Description description = new YamlConfig.Automation.Environment.Description();
                if(JsonUtils.isJson(descriptionStr)){
                    description = JSON.parseObject(descriptionStr,YamlConfig.Automation.Environment.Description.class);
                }else{
                    description.setName("Jenkins远程节点描述信息非JSON格式，请检查配置！");
                }
                environment.setDescription(description);
                YamlConfig.Automation.Environment.Active active = new YamlConfig.Automation.Environment.Active();
                YamlConfig.Automation.Environment.Active.Offline offline = new YamlConfig.Automation.Environment.Active.Offline();
                offline.setStatus(computer.get("offline").asBoolean() ? 0 : 1);
                offline.setOfflineCauseReason(computer.get("offlineCauseReason").asText());
                active.setOffline(offline);
                YamlConfig.Automation.Environment.Active.Idle idle = new YamlConfig.Automation.Environment.Active.Idle();
                boolean status = computer.get("idle").asBoolean();
                idle.setStatus(status ? 1 : 0);
                if (!status) {
                    List<YamlConfig.Automation.Environment.Active.Idle.CurrentExecutable> currentExecutableList = new ArrayList<>();
                    for (JsonNode executors : computer.get("executors")) {
                        JsonNode currentExecutableJn = executors.get("currentExecutable");
                        if(!currentExecutableJn.isEmpty()){
                            String url = currentExecutableJn.get("url").asText();
                            if (StringUtils.isNotEmpty(url)) {
                                YamlConfig.Automation.Environment.Active.Idle.CurrentExecutable currentExecutable = new YamlConfig.Automation.Environment.Active.Idle.CurrentExecutable();
                                currentExecutable.setUrl(url);
                                currentExecutable.setUser(JenkinsService.getJenkinsJobParameters(url,jenkinsUserName,jenkinsPassWord));
                                currentExecutableList.add(currentExecutable);
                            }
                        }
                    }
                    idle.setExecutors(currentExecutableList);
                }
                active.setIdle(idle);
                environment.setActive(active);

                List<YamlConfig.Automation.Environment.Config> configList = new ArrayList<>();
                YamlConfig.Automation.Environment.Config config = new YamlConfig.Automation.Environment.Config();
                try {
                    JsonNode rootNode = JenkinsService.getJenkinsNodeDetails(jenkinsUrl,jenkinsUserName,jenkinsPassWord,displayName);
                    config.setParamsName("workDirPath");
                    config.setParamsValue(rootNode.get("remoteFS").asText());
                    configList.add(config);
                    JsonNode toolLocationNode = rootNode.get("nodeProperties").get("hudson.tools.ToolLocationNodeProperty").get("locations").get("hudson.tools.ToolLocationNodeProperty_-ToolLocation");
                    for (JsonNode tools : toolLocationNode) {
                        config = new YamlConfig.Automation.Environment.Config();
                        config.setParamsName(tools.get("name").asText());
                        config.setParamsValue(tools.get("home").asText());
                        configList.add(config);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                environment.setConfigList(configList);
                environment.setStatus(0);
                environment.setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
                environment.setCreateTime(DateUtils.getTime());
                environment.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
                environment.setUpdateTime(DateUtils.getTime());
                environmentList.add(environment);
                index++;
            }
        }
//        ArrayList<YamlConfig.Automation.Environment> collect = environmentList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Automation.Environment::getName))), ArrayList::new));
//        collect.sort((x, y) -> x.getName().compareTo(y.getName()));
        automationConfig.setEnvironmentList(environmentList);
        return edit(automationConfig);
    }

    /**
     * 同步环境配置信息（单个）
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean syncEnvironment(AutomationConfig automationConfig){
        YamlConfig.Automation.Jenkins jenkins = SceneXmlUtils.getAutomationJenkins();
        String jenkinsUrl = jenkins.getUrl();
        String jenkinsUserName = jenkins.getUserName();
        String jenkinsPassWord = jenkins.getPassWord();

        String environmentConfig = super.get(automationConfig.getId()).getEnvironmentConfig();
        List<YamlConfig.Automation.Environment> environmentList = JSON.parseArray(environmentConfig, YamlConfig.Automation.Environment.class);
        for (String name : automationConfig.getNames()) {
            environmentList.forEach((environment)->{
                if (environment.getName().equals(name)) {
//                    YamlConfig.Automation.Environment environment = new YamlConfig.Automation.Environment();
                    try {
                        JsonNode jsonNode = JenkinsService.getJenkinsNode(jenkinsUrl,jenkinsUserName,jenkinsPassWord,name);
                        String displayName = jsonNode.get("displayName").asText();
                        if ("Built-In Node".equals(displayName)) {
                            displayName = "(master)";
                        }
                        environment.setName(displayName);
                        environment.setUrl(""+jenkinsUrl+"/computer/"+displayName+"/");
                        String descriptionStr = jsonNode.get("description").asText();
                        YamlConfig.Automation.Environment.Description description = new YamlConfig.Automation.Environment.Description();
                        if(JsonUtils.isJson(descriptionStr)){
                            description = JSON.parseObject(descriptionStr,YamlConfig.Automation.Environment.Description.class);
                        }else{
                            description.setName("Jenkins远程节点描述信息非JSON格式，请检查配置！");
                        }
                        environment.setDescription(description);

                        YamlConfig.Automation.Environment.Active active = new YamlConfig.Automation.Environment.Active();
                        YamlConfig.Automation.Environment.Active.Offline offline = new YamlConfig.Automation.Environment.Active.Offline();
                        offline.setStatus(jsonNode.get("offline").asBoolean() ? 0 : 1);
                        offline.setOfflineCauseReason(jsonNode.get("offlineCauseReason").asText());
                        active.setOffline(offline);
                        YamlConfig.Automation.Environment.Active.Idle idle = new YamlConfig.Automation.Environment.Active.Idle();
                        boolean status = jsonNode.get("idle").asBoolean();
                        idle.setStatus(status ? 1 : 0);
                        if (!status) {
                            List<YamlConfig.Automation.Environment.Active.Idle.CurrentExecutable> currentExecutableList = new ArrayList<>();
                            for (JsonNode executors : jsonNode.get("executors")) {
                                JsonNode currentExecutableJn = executors.get("currentExecutable");
                                if(!currentExecutableJn.isEmpty()){
                                    String url = currentExecutableJn.get("url").asText();
                                    if (StringUtils.isNotEmpty(url)) {
                                        YamlConfig.Automation.Environment.Active.Idle.CurrentExecutable currentExecutable = new YamlConfig.Automation.Environment.Active.Idle.CurrentExecutable();
                                        currentExecutable.setUrl(url);
                                        currentExecutable.setUser(JenkinsService.getJenkinsJobParameters(url,jenkinsUserName,jenkinsPassWord));
                                        currentExecutableList.add(currentExecutable);
                                    }
                                }
                            }
                            idle.setExecutors(currentExecutableList);
                        }
                        active.setIdle(idle);
                        environment.setActive(active);

                        List<YamlConfig.Automation.Environment.Config> configList = environment.getConfigList();
//                        if (!"Built-In Node".equals(displayName)) {
                            try {
//                                YamlConfig.Automation.Environment.Config config = new YamlConfig.Automation.Environment.Config();
                                JsonNode rootNode = JenkinsService.getJenkinsNodeDetails(jenkinsUrl, jenkinsUserName, jenkinsPassWord, displayName);
//                                config.setParamsName("workDirPath");
//                                config.setParamsValue(rootNode.get("remoteFS").asText());
//                                configList.add(config);

                                configList.stream().filter(o -> "workDirPath".equals(o.getParamsName())).forEach(f -> {
                                    f.setParamsValue(rootNode.get("remoteFS").asText());
                                });

                                JsonNode toolLocationNode = rootNode.get("nodeProperties").get("hudson.tools.ToolLocationNodeProperty").get("locations").get("hudson.tools.ToolLocationNodeProperty_-ToolLocation");
                                for (JsonNode tools : toolLocationNode) {
//                                    config = new YamlConfig.Automation.Environment.Config();
//                                    config.setParamsName(tools.get("name").asText());
//                                    config.setParamsValue(tools.get("home").asText());
//                                    configList.add(config);
                                    configList.stream().filter(o -> o.getParamsName().equals(tools.get("name").asText())).forEach(f -> {
                                        f.setParamsValue(tools.get("home").asText());
                                    });
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                        }
//                        List<YamlConfig.Automation.Environment.Config> collect = configList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Automation.Environment.Config::getParamsName))), ArrayList::new));
//                        collect.sort((x, y) -> x.getParamsName().compareTo(y.getParamsName()));
//                        environment.setConfigList(collect);
                        environment.setConfigList(configList);

//                        environment.setStatus(0);
                        environment.setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
                        environment.setCreateTime(DateUtils.getTime());
                        environment.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
                        environment.setUpdateTime(DateUtils.getTime());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
//        ArrayList<YamlConfig.Automation.Environment> collect = environmentList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Automation.Environment::getName))), ArrayList::new));
//        collect.sort((x, y) -> x.getName().compareTo(y.getName()));
        automationConfig.setEnvironmentList(environmentList);
        return edit(automationConfig);
    }

    /**
     * 新增浏览器配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean addBrowser(AutomationConfig automationConfig) {
        automationConfig.getBrowser().setId(IdUtils.randomUUID());
        automationConfig.getBrowser().setCreateByName(SecurityUtils.getLoginUser().getUser().getName());
        automationConfig.getBrowser().setCreateTime(DateUtils.getTime());
        automationConfig.getBrowser().setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
        automationConfig.getBrowser().setUpdateTime(DateUtils.getTime());

        String browserConfig = super.get(automationConfig).getBrowserConfig();
        if (StringUtils.isEmpty(browserConfig)) {
            List<YamlConfig.Automation.Browser> browserList = new ArrayList<>();
            automationConfig.getBrowser().setIndex(1);
            browserList.add(automationConfig.getBrowser());
//            browserList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            automationConfig.setBrowserList(browserList);
            if(automationConfig.getBrowser().getStatus()==1){
                automationConfig.setLastBrowser(automationConfig.getBrowser().getName());
            }
            return edit(automationConfig);
        } else {
            List<YamlConfig.Automation.Browser> browserList = JSON.parseArray(browserConfig, YamlConfig.Automation.Browser.class);
            automationConfig.getBrowser().setIndex(browserList.size() + 1);
            for (YamlConfig.Automation.Browser browser : browserList) {
                if(automationConfig.getBrowser().getStatus()==1){
                    browser.setStatus(0);
                    automationConfig.setLastBrowser(automationConfig.getBrowser().getName());
                }
            }
            browserList.add(automationConfig.getBrowser());
            //去重后的集合
            ArrayList<YamlConfig.Automation.Browser> collect = browserList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Automation.Browser::getType).thenComparing(YamlConfig.Automation.Browser::getVersion))), ArrayList::new));
            if (browserList.size() == collect.size()) {
//                collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                collect.sort((x, y) -> x.getName().compareTo(y.getName()));
                automationConfig.setBrowserList(collect);
                return edit(automationConfig);
            } else {
                throw new BizException(SysErrorCode.B_PROJECT_Automation_BrowserNameAlreadyExist);
            }
        }
    }

    /**
     * 修改浏览器配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean editBrowser(AutomationConfig automationConfig) {
        String browserConfig = super.get(automationConfig.getId()).getBrowserConfig();
        List<YamlConfig.Automation.Browser> browserList = JSON.parseArray(browserConfig, YamlConfig.Automation.Browser.class);
        for (YamlConfig.Automation.Browser browser : browserList) {
            if (browser.getId().equals(automationConfig.getBrowser().getId())) {
                browser.setName(automationConfig.getBrowser().getName());
                browser.setVersion(automationConfig.getBrowser().getVersion());
                browser.setOfficialDownload(automationConfig.getBrowser().getOfficialDownload());
                browser.setDriverDownload(automationConfig.getBrowser().getDriverDownload());
                browser.setPath(automationConfig.getBrowser().getPath());
                browser.setDriver(automationConfig.getBrowser().getDriver());
                browser.setProfile(automationConfig.getBrowser().getProfile());
                browser.setStatus(automationConfig.getBrowser().getStatus());
                browser.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
                browser.setUpdateTime(DateUtils.getTime());
            }else{
                if(automationConfig.getBrowser().getStatus()==1){
                    browser.setStatus(0);
                    automationConfig.setLastBrowser(automationConfig.getBrowser().getName());
                }
            }
        }
        ArrayList<YamlConfig.Automation.Browser> collect = browserList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(YamlConfig.Automation.Browser::getType).thenComparing(YamlConfig.Automation.Browser::getVersion))), ArrayList::new));
        if (browserList.size() == collect.size()) {
//            collect.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
            collect.sort((x, y) -> x.getName().compareTo(y.getName()));
            automationConfig.setBrowserList(collect);
            return edit(automationConfig);
        } else {
            throw new BizException(SysErrorCode.B_PROJECT_Automation_BrowserNameAlreadyExist);
        }
    }

    /**
     * 删除浏览器配置信息
     *
     * @param automationConfig 自动化配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean removeBrowser(AutomationConfig automationConfig) {
        String browserConfig = super.get(automationConfig.getId()).getBrowserConfig();
        List<YamlConfig.Automation.Browser> browserList = JSON.parseArray(browserConfig, YamlConfig.Automation.Browser.class);
        for (String id : automationConfig.getIds()) {
            browserList.forEach((browser)->{
                if (browser.getId().equals(id) && browser.getStatus() == 1) {
                    throw new BizException(SysErrorCode.B_PROJECT_Automation_StateNotExistent, id);
                }
            });
            if(browserList.size()>1){
                if(browserList.removeIf(browser -> browser.getId().equals(id))){
                    browserList.sort((x, y) -> Integer.compare(x.getIndex(), y.getIndex()));
                } else {
                    throw new BizException(SysErrorCode.B_PROJECT_Automation_BrowserIdNotExistent,id);
                }
            }else {
                throw new BizException(SysErrorCode.B_PROJECT_Automation_DeleteNotExistent);
            }
        }
        final Integer[] i = {1};
        browserList.forEach((e) -> {
            e.setIndex(i[0]);
            i[0]++;
        });
        automationConfig.setBrowserList(browserList);
        return edit(automationConfig);
    }

    public static void main(String[] args) {
        String str = "ddd";
        YamlConfig.Automation.Environment.Description description = new YamlConfig.Automation.Environment.Description();
        if(JsonUtils.isJson(str)){
            description = JSON.parseObject(str,YamlConfig.Automation.Environment.Description.class);
            log.info(description.toString());
        }else{
            description.setName("Jenkins远程节点描述信息非JSON格式，请检查后重试！");
        }
    }
}