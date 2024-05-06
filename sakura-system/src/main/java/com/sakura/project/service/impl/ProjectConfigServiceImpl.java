package com.sakura.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.core.domain.entity.SysUser;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.common.exception.BizException;
import com.sakura.common.utils.SecurityUtils;
import com.sakura.common.utils.StringUtils;
import com.sakura.system.common.SysErrorCode;
import com.sakura.system.domain.SysNode;
import com.sakura.project.domain.ProjectConfig;
import com.sakura.project.mapper.ProjectConfigMapper;
import com.sakura.system.service.ISysUserService;

import com.sakura.project.service.ProjectConfigService;
import com.sakura.system.service.SysNodeService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 项目配置Service业务层处理
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-22
 */
@Service
@Transactional(readOnly = true)
public class ProjectConfigServiceImpl extends BaseServiceImpl<ProjectConfigMapper, ProjectConfig> implements ProjectConfigService {

    private static final Logger log = LoggerFactory.getLogger(ProjectConfigServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysNodeService sysNodeService;

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
     * @param projectConfig 项目配置
     * @return 项目配置
     */
    @Override
    public ProjectConfig get(ProjectConfig projectConfig) {
        ProjectConfig project = super.get(projectConfig);
        project.setMemberList(JSONArray.parseArray(project.getMembers(),SysUser.class));
        return project;
    }

    /**
     * 获取单条数据
     *
     * @param id 项目配置id
     * @return 项目配置
     */
    @Override
    public ProjectConfig get(String id) {
        ProjectConfig projectConfig = super.get(id);
        projectConfig.setMemberList(JSONArray.parseArray(projectConfig.getMembers(),SysUser.class));
        return projectConfig;
    }

    /**
     * 查询项目配置列表
     *
     * @param projectConfig 项目配置
     * @return 项目配置
     */
    @Override
    public List<ProjectConfig> findList(ProjectConfig projectConfig) {
        List<ProjectConfig> projectConfigList = super.findList(projectConfig);
        projectConfigList.forEach((e)->{
            e.setMemberList(JSONArray.parseArray(e.getMembers(),SysUser.class));
        });
        return projectConfigList;
    }

    /**
     * 分页查询项目配置列表
     *
     * @param projectConfig 项目配置
     * @return 项目配置
     */
    @Override
    public PageInfo<ProjectConfig> findPage(ProjectConfig projectConfig) {
        PageInfo<ProjectConfig> projectConfigPageInfo = super.findPage(projectConfig);
        projectConfigPageInfo.getList().forEach((e)->{
            e.setMemberList(JSONArray.parseArray(e.getMembers(),SysUser.class));
        });
        return projectConfigPageInfo;
    }

    /**
     * 保存项目配置
     *
     * @param projectConfig 项目配置
     * @return 结果
     */
    @Override
    public boolean save(ProjectConfig projectConfig) {
//        boolean success = false;
        if (!CollectionUtils.isEmpty(mapper.findListWithUnique(projectConfig))) {
            throw new BizException(SysErrorCode.B_PROJECT_NameAlreadyExist);
        }
        List<SysUser> sysUserList = new ArrayList<>();
        for(String userId : projectConfig.getMemberIds()){
            SysUser sysUser = userService.get(userId);
            sysUserList.add(sysUser);
        }
        projectConfig.setMembers(JSON.toJSONString(sysUserList));
//        if(super.save(projectConfig)){
//            success = creatRoot(projectConfig, SysNode.ROOT_NODE_CASE);
//            success = creatRoot(projectConfig, SysNode.ROOT_NODE_ELEMENT);
//            success =creatRoot(projectConfig, SysNode.ROOT_NODE_SCENE);
//        }
        return super.save(projectConfig);
//        mapper.deleteUserProject(projectConfig);
//        return mapper.insertUserProject(projectConfig);
    }

    /**
     * 修改项目配置
     *
     * @param projectConfig 项目配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public boolean edit(ProjectConfig projectConfig) {
//        if (CollectionUtils.isEmpty(mapper.findListWithUnique(projectConfig))) {
//            throw new BizException(SysErrorCode.B_PROJECT_NotExistent);
//        }
        if (StringUtils.isNotEmpty(projectConfig.getMemberIds())) {
            List<SysUser> sysUserList = new ArrayList<>();
            for (String userId : projectConfig.getMemberIds()) {
                SysUser sysUser = userService.get(userId);
                sysUserList.add(sysUser);
            }
            projectConfig.setMembers(JSON.toJSONString(sysUserList));
        }
        return super.save(projectConfig);
    }

    /**
     * 批量删除项目配置
     *
     * @param ids 需要删除的项目配置ID
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public int deleteProjectConfigByIds(String[] ids) {
        return mapper.deleteProjectConfigByIds(ids, BaseEntity.DEL_FLAG_DELETE);
    }

    /**
     * 删除项目配置信息
     *
     * @param projectConfig 项目配置
     * @return 结果
     */
    @Override
    public boolean remove(ProjectConfig projectConfig) {
        if (StringUtils.isNotNull(projectConfig.getIds())) {
            for (String id :projectConfig.getIds()){
                projectConfig.setId(id);
                if(!super.remove(projectConfig)){
                    throw new BizException(SysErrorCode.B_PROJECT_DeleteFailed,id);
                }
            }
            sysNodeService.deleteNodeByProjectIds(projectConfig.getIds());
        }
        return true;
    }

    /**
     * 更新项目配置状态
     *
     * @param projectConfig 项目配置
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public int updateStatus(ProjectConfig projectConfig) {
        projectConfig.setUpdateBy(SecurityUtils.getLoginUser().getUser().getId());
        projectConfig.setUpdateByName(SecurityUtils.getLoginUser().getUser().getName());
        projectConfig.setUpdateTime(new Date());
        projectConfig.setUpdateIp(SecurityUtils.getLoginUser().getIpaddr());
        return mapper.updateStatus(projectConfig);
    }

    public boolean creatRoot(ProjectConfig projectConfig, String moduleName){
        SysNode sysNode =new SysNode();
        sysNode.setParentId("0");
        sysNode.setProjectId(projectConfig.getId());
        sysNode.setVersionId(projectConfig.getLastVersion());
        sysNode.setName(moduleName);
        return sysNodeService.save(sysNode);
    }
}