package com.sakura.system.common;

import com.sakura.common.exception.ErrorCodeI;

/**
 * There are only 3 basic ErrorCode:
 * COLA_ERROR
 * BIZ_ERROR
 * SYS_ERROR
 * <p>
 * Created by fulan.zjf on 2017/12/18.
 */
public enum SysErrorCode implements ErrorCodeI {

    //岗位信息
    B_SYSPOST_PostNameAlreadyExist("B_SYSPOST_PostNameAlreadyExist", "岗位名称已存在"),

    B_SYSPOST_PostCodeAlreadyExist("B_SYSPOST_PostCodeAlreadyExist", "岗位编号已存在"),

    B_SYSDEPT_DeptNameAlreadyExist("B_SYSDEPT_DeptNameAlreadyExist", "部门名称已存在"),

    B_SYSNODE_NodeNameAlreadyExist("B_SYSNODE_NodeNameAlreadyExist", "模块名称已存在"),

    B_SYSDEPT_DeptIdNotEqualParentId("B_SYSDEPT_DeptIdNotEqualParentId", "修改部门失败，上级部门不能是自己"),

    B_SYSDEPT_DeptAlreadyUnNormal("B_SYSDEPT_DeptAlreadyUnNormal", "部门停用，不允许新增子部门"),

    B_SYSDEPT_DeptHasNormalChild("B_SYSDEPT_DeptHasNormalChild", "该部门包含未停用的子部门，不允许标记为停用"),

    B_SYSDEPT_NoAllowUpdateType("B_SYSDEPT_NoAllowUpdateType", "节点下存在公司节点，不允许修改"),

    B_SYSDEPT_DeptHasChildNotDelete("B_SYSDEPT_DeptHasChildNotDelete", "该部门包含子部门，不允许删除"),

    B_SYSDEPT_DeptHasUserNotDelete("B_SYSDEPT_DeptHasUserNotDelete", "该部门包含用户，不允许删除"),

    B_SYSDEPT_DeptMoveUnNormal("B_SYSDEPT_DeptMoveUnNormal", "非法移动，不允许将节点移动到自身或者子节点下"),

    B_SYSDICTTYPE_DictTypeAlreadyExist("B_SYSDICTTYPE_DictTypeAlreadyExist", "数据字典类型已存在"),

    B_SYSDICDATA_DictValueAlreadyExist("B_SYSDICDATA_DictValueAlreadyExist", "数据键值已存在"),

    B_SYSROLE_RoleNameAlreadyExist("B_SYSROLE_RoleNameAlreadyExist", "角色名称已存在"),

    B_SYSROLE_RoleKeyAlreadyExist("B_SYSROLE_RoleKeyAlreadyExist", "角色编号已存在"),

    B_SYSCONFIG_ConfigKeyAlreadyExist("B_SYSCONFIG_ConfigKeyAlreadyExist", "参数键值已存在"),


    B_SYSUSER_UserNameAlreadyExist("B_SYSUSER_UserNameAlreadyExist", "用户名称已存在"),

    B_SYSUSER_PhoneAlreadyExist("B_SYSUSER_PhoneAlreadyExist", "用户手机号已存在"),

    B_SYSUSER_EmailAlreadyExist("B_SYSUSER_EmailAlreadyExist", "用户邮箱已存在"),

    B_SYSMENU_MenuHasChildNotDelete("B_SYSMENU_MenuHasChildNotDelete", "该菜单包含子菜单，不允许删除"),

    B_SYSMENU_MenuAlreadyUnNormal("B_SYSMENU_MenuAlreadyUnNormal", "菜单停用，不允许新增子菜单"),

    B_SYSMENU_MenuMoveUnNormal("B_SYSMENU_MenuMoveUnNormal", "非法移动，不允许将节点移动到自身或者子节点下"),

    B_SYSMENU_MenuIdNotEqualParentId("B_SYSMENU_MenuIdNotEqualParentId", "修改菜单失败，父级菜单不能是自己"),

    B_SYSMENU_MenuHasNormalChild("B_SYSMENU_MenuHasNormalChild", "该菜单包含未停用的子菜单，不允许标记为停用"),

    B_SYSMENU_MenuNameAlreadyExist("B_SYSMENU_MenuNameAlreadyExist", "菜单名称已存在"),

    B_SYSMENU_PathAlreadyExist("B_SYSMENU_PathAlreadyExist", "路由地址已存在"),

    B_SYSMENU_PathStartsWithHttp("B_SYSMENU_PathStartsWithHttp", "路由地址必须以http(s)://开头"),

    B_PROJECT_NotExistent("B_PROJECT_NotExistent", "项目信息不存在"),
    B_PROJECT_DeleteFailed("B_PROJECT_DeleteFailed", "项目删除失败"),
    B_PROJECT_NameAlreadyExist("B_PROJECT_NameAlreadyExist", "项目名称已存在"),

    B_PROJECT_EnvironmentNotExistent("B_PROJECT_EnvironmentNotExistent", "环境信息不存在"),
    B_PROJECT_EnvironmentDeleteFailed("B_PROJECT_EnvironmentDeleteFailed", "环境删除失败"),
    B_PROJECT_EnvironmentNameAlreadyExist("B_PROJECT_EnvironmentNameAlreadyExist", "环境名称已存在"),

//    B_PROJECT_Environment_NameAlreadyExist("B_PROJECT_Environment_NameAlreadyExist", "名称已存在"),
//    B_PROJECT_Environment_IdNotExistent("B_PROJECT_Environment_IdNotExistent", "ID不存在"),
    B_PROJECT_Environment_StateNotExistent("B_PROJECT_Environment_StateNotExistent", "已启用状态下，不可删除"),
    B_PROJECT_Environment_DeleteNotExistent("B_PROJECT_Environment_DeleteNotExistent", "不可全部删除，需保留一个"),
    B_PROJECT_Environment_DisableNotExistent("B_PROJECT_Environment_DisableNotExistent", "不可全部禁用，需保留一个"),

    B_PROJECT_Environment_VersionNameAlreadyExist("B_PROJECT_Environment_VersionNameAlreadyExist", "版本名称已存在"),
    B_PROJECT_Environment_VersionIdNotExistent("B_PROJECT_Environment_VersionIdNotExistent", "版本ID不存在"),
    B_PROJECT_Environment_VersionNotExistent("B_PROJECT_Environment_VersionNotExistent", "环境版本信息不存在"),

    B_PROJECT_Environment_DomainNameAlreadyExist("B_PROJECT_Environment_DomainNameAlreadyExist", "域名名称已存在"),
    B_PROJECT_Environment_DomainIdNotExistent("B_PROJECT_Environment_DomainIdNotExistent", "域名ID不存在"),
    B_PROJECT_Environment_DomainNotExistent("B_PROJECT_Environment_DomainNotExistent", "环境域名信息不存在"),

    B_PROJECT_Environment_AccountNameAlreadyExist("B_PROJECT_Environment_AccountNameAlreadyExist", "该帐号类型下帐号名称已存在"),
    B_PROJECT_Environment_AccountIdNotExistent("B_PROJECT_Environment_AccountIdNotExistent", "帐号ID不存在"),
    B_PROJECT_Environment_AccountNotExistent("B_PROJECT_Environment_AccountNotExistent", "环境帐号信息不存在"),

    B_PROJECT_Environment_AccountSecurityNameAlreadyExist("B_PROJECT_Environment_AccountSecurityNameAlreadyExist", "[Security]类型=>帐号名称已存在"),
    B_PROJECT_Environment_AccountSecurityIdNotExistent("B_PROJECT_Environment_AccountSecurityIdNotExistent", "[Security]类型=>帐号ID不存在"),
    B_PROJECT_Environment_AccountSystemNameAlreadyExist("B_PROJECT_Environment_AccountSystemNameAlreadyExist", "[System]类型=>帐号名称已存在"),
    B_PROJECT_Environment_AccountSystemIdNotExistent("B_PROJECT_Environment_AccountSystemIdNotExistent", "[System]类型=>帐号ID不存在"),
    B_PROJECT_Environment_AccountAuditNameAlreadyExist("B_PROJECT_Environment_AccountAuditNameAlreadyExist", "[Audit]类型=>帐号名称已存在"),
    B_PROJECT_Environment_AccountAuditIdNotExistent("B_PROJECT_Environment_AccountAuditIdNotExistent", "[Audit]类型=>帐号ID不存在"),
    B_PROJECT_Environment_AccountAlreadyExist("B_PROJECT_Environment_AccountAlreadyExist", "帐号信息不能为空"),

    B_PROJECT_Environment_ServerHostlAlreadyExist("B_PROJECT_Environment_ServerHostlAlreadyExist", "该主机名称下用户已存在"),
    B_PROJECT_Environment_ServerIdNotExistent("B_PROJECT_Environment_ServerIdNotExistent", "主机ID不存在"),
    B_PROJECT_Environment_ServerNotExistent("B_PROJECT_Environment_ServerNotExistent", "环境服务器信息不存在"),

    B_PROJECT_Environment_DataBaseNameAlreadyExist("B_PROJECT_Environment_DataBaseNameAlreadyExist", "该数据库名称下端口已存在"),
    B_PROJECT_Environment_DataBaseIdNotExistent("B_PROJECT_Environment_DataBaseIdNotExistent", "数据库ID不存在"),
    B_PROJECT_Environment_DataBaseNotExistent("B_PROJECT_Environment_DataBaseNotExistent", "环境数据库信息不存在"),

    B_PROJECT_Environment_DataBaseOracleNameAlreadyExist("B_PROJECT_Environment_DataBaseOracleNameAlreadyExist", "[Oracle]类型=>数据库名称已存在"),
    B_PROJECT_Environment_DataBaseOracleIdNotExistent("B_PROJECT_Environment_DataBaseOracleIdNotExistent", "[Oracle]类型=>数据库ID不存在"),
    B_PROJECT_Environment_DataBaseMySqlNameAlreadyExist("B_PROJECT_Environment_DataBaseMySqlNameAlreadyExist", "[MySql]类型=>数据库名称已存在"),
    B_PROJECT_Environment_DataBaseMySqlIdNotExistent("B_PROJECT_Environment_DataBaseMySqlIdNotExistent", "[MySql]类型=>数据库ID不存在"),
    B_PROJECT_Environment_DataBaseAlreadyExist("B_PROJECT_Environment_DataBaseAlreadyExist", "数据库信息不能为空"),

    B_PROJECT_AutomationNotExistent("B_PROJECT_AutomationNotExistent", "自动化配置信息不存在"),
    B_PROJECT_AutomationDeleteFailed("B_PROJECT_AutomationDeleteFailed", "自动化配置删除失败"),
    B_PROJECT_AutomationNameAlreadyExist("B_PROJECT_AutomationNameAlreadyExist", "自动化配置名称已存在"),
    B_PROJECT_Automation_StateNotExistent("B_PROJECT_Automation_StateNotExistent", "已启用状态下，不可删除"),
    B_PROJECT_Automation_DeleteNotExistent("B_PROJECT_Automation_DeleteNotExistent", "不可全部删除，需保留一个"),

    B_PROJECT_Automation_ProjectNameAlreadyExist("B_PROJECT_Automation_ProjectNameAlreadyExist", "项目名称已存在"),
    B_PROJECT_Automation_ProjectIdNotExistent("B_PROJECT_Automation_ProjectIdNotExistent", "项目ID不存在"),
    B_PROJECT_Automation_ProjectNotExistent("B_PROJECT_Automation_ProjectNotExistent", "自动化项目信息不存在"),

    B_PROJECT_Automation_JenkinsIPAlreadyExist("B_PROJECT_Automation_JenkinsIPAlreadyExist", "Jenkins主机已存在"),
    B_PROJECT_Automation_JenkinsIdNotExistent("B_PROJECT_Automation_JenkinsIdNotExistent", "JenkinsID不存在"),
    B_PROJECT_Automation_JenkinsNotExistent("B_PROJECT_Automation_JenkinsNotExistent", "自动化Jenkins信息不存在"),

    B_PROJECT_Automation_EnvironmentNameAlreadyExist("B_PROJECT_Automation_EnvironmentNameAlreadyExist", "环境名称已存在"),
    B_PROJECT_Automation_EnvironmentIdNotExistent("B_PROJECT_Automation_EnvironmentIdNotExistent", "环境ID不存在"),
    B_PROJECT_Automation_EnvironmentNotExistent("B_PROJECT_Automation_EnvironmentNotExistent", "自动化环境信息不存在"),

    B_PROJECT_Automation_BrowserNameAlreadyExist("B_PROJECT_Automation_BrowserNameAlreadyExist", "该浏览器类型下浏览器名称已存在"),
    B_PROJECT_Automation_BrowserIdNotExistent("B_PROJECT_Automation_BrowserIdNotExistent", "浏览器ID不存在"),
    B_PROJECT_Automation_BrowserNotExistent("B_PROJECT_Automation_BrowserNotExistent", "自动化浏览器信息不存在"),


    //【测试管理-测试计划】
    B_TEST_PLAN_NameAlreadyExist("B_TEST_PLAN_NameAlreadyExist", "测试计划名称已存在"),
    B_TEST_PLAN_DeleteFailed("B_TEST_PLAN_DeleteFailed", "测试计划删除失败，请先删除关联的测试数据"),

    B_TEST_REPORT_DeleteFailed("B_TEST_REPORT_DeleteFailed", "测试报告删除失败，请先删除关联的测试数据"),
    B_TEST_REPORT_NotExisten("B_TEST_REPORT_NotExisten", "测试报告信息不存在"),

    //【测试管理-定时任务】
    B_TIMED_TASK_NameAlreadyExist("B_TIMED_TASK_NameAlreadyExist", "定时任务名称已存在"),




    B_SYSSCENE_SceneNotExisten("B_SYSSCENE_SceneNotExisten", "测试场景信息不存在"),
//    B_SYSSCENE_SceneIdAlreadyExist("B_SYSSCENE_SceneIdAlreadyExist", "同一个项目的同一模块下，场景ID不能重复"),
//    B_SYSSCENE_SceneNameAlreadyExist("B_SYSSCENE_SceneNameAlreadyExist", "同一个项目的同一模块下，场景名称不能重复"),
//    B_SYSSCENE_SceneInfoAlreadyExist("B_SYSSCENE_SceneIdAlreadyExist", "该版本下，场景信息已存在"),
    B_SYSSCENE_SceneIdAlreadyExist("B_SYSSCENE_SceneIdAlreadyExist", "该场景版本等级下，场景ID已存在"),
    B_SYSSCENE_SceneNameAlreadyExist("B_SYSSCENE_SceneNameAlreadyExist", "该场景版本等级下，场景名称已存在"),
    B_SYSSCENE_SceneCaseIdAlreadyExist("B_SYSSCENE_SceneCaseIdAlreadyExist","该场景下，用例ID已存在"),

    B_SYSSCENE_SceneId_WrongFul("B_SYSSCENE_SceneId_WrongFul", "场景ID不合法"),
    B_SYSSCENE_SceneCaseId_WrongFul("B_SYSSCENE_SceneCaseId_WrongFul", "场景用例ID不合法"),

    B_FILEUPLOADER_UploaderErr("B_FILEUPLOADER_UploaderErr", "上传失败"),

    B_FILEUPLOADER_FileNotExist("B_FILEUPLOADER_FileNotExist", "附件不存在"),

    B_FILEUPLOADER_DownloadFileNotExist("B_FILEUPLOADER_DownloadFileNotExist", "下载文件已不存在"),

    B_FILEUPLOADER_NoFindFileServer("B_FILEUPLOADER_NoFindFileServer", "没有找到附件处理器"),

    BIZ_ERROR("BIZ_ERROR", "通用的业务逻辑错误"),

    COLA_ERROR("COLA_FRAMEWORK_ERROR", "COLA框架错误"),

    JENKINS_CONNECTION_ERROR("JENKINS_CONNECTION_ERROR", "Jenkins连接异常，请检查环境配置！"),

    SYS_ERROR("SYS_ERROR", "未知的系统错误");

    private String errCode;
    private String errDesc;

    private SysErrorCode(String errCode, String errDesc) {
        this.errCode = errCode;
        this.errDesc = errDesc;
    }

    @Override
    public String getErrCode() {
        return errCode;
    }

    @Override
    public String getErrDesc() {
        return errDesc;
    }
}
