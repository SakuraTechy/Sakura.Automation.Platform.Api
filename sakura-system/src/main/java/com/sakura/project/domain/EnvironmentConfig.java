package com.sakura.project.domain;

import com.sakura.common.annotation.Excel;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.utils.log.annotation.FieldRemark;
import com.sakura.common.utils.log.annotation.LogField;
import com.sakura.common.utils.yaml.YamlConfig;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 环境配置对象 environment_config
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-26
 */
@Data
public class EnvironmentConfig extends BaseEntity<EnvironmentConfig> {
    private static final long serialVersionUID = 1L;

    /** 环境ID */
    @LogField
    @FieldRemark(name = "环境ID", field = "id")
    private String id;

    /** 环境名称 */
    @LogField
    @FieldRemark(name = "环境名称", field = "name")
    private String name;

    /** 所属项目ID */
    @LogField
    @FieldRemark(name = "所属项目ID", field = "projectId")
    private String projectId;

    /** 版本配置 */
    @Excel(name = "版本配置")
    @LogField
    @FieldRemark(name = "版本配置", field = "versionConfig")
    private String versionConfig;
    private String[] versionIds;
    private YamlConfig.Project.Environment.Version versions;
    private List<YamlConfig.Project.Environment.Version> versionList;

    /** 域名配置 */
    @Excel(name = "域名配置")
    @LogField
    @FieldRemark(name = "域名配置", field = "domainConfig")
    private String domainConfig;
    private String[] domainIds;
    private YamlConfig.Project.Environment.Domain domains;
    private List<YamlConfig.Project.Environment.Domain> domainList;

    /** 帐号配置 */
    @Excel(name = "帐号配置")
    @LogField
    @FieldRemark(name = "帐号配置", field = "accountConfig")
    private String accountConfig;
    private String[] accountIds;
    private YamlConfig.Project.Environment.Account accounts;
    private List<YamlConfig.Project.Environment.Account> accountList;

    /** 服务器配置 */
    @Excel(name = "服务器配置")
    @LogField
    @FieldRemark(name = "服务器配置", field = "serverConfig")
    private String serverConfig;
    private String[] serverIds;
    private YamlConfig.Project.Environment.Server servers;
    private List<YamlConfig.Project.Environment.Server> serverList;

    /** 数据库配置 */
    @Excel(name = "数据库配置")
    @LogField
    @FieldRemark(name = "数据库配置", field = "databaseConfig")
    private String dataBaseConfig;
    private String[] dataBaseIds;
    private YamlConfig.Project.Environment.DataBase dataBases;
    private List<YamlConfig.Project.Environment.DataBase> dataBaseList;

    /** 状态 (0关闭 1开启) */
    @Excel(name = "状态 (0关闭 1开启)", dictType = "sys_normal_disable")
    @FieldRemark(name = "状态 (0关闭 1开启)", field = "status")
    @LogField
    private Integer status;

    @Excel(name = "环境最新域名")
    @FieldRemark(name = "环境最新域名", field = "domain")
    @LogField
    private String lastDomain;

    @Excel(name = "环境最新版本")
    @FieldRemark(name = "环境最新版本", field = "version")
    @LogField
    private String lastVersion;

    @Excel(name = "创建开始时间")
    @FieldRemark(name = "批量删除的ID", field = "createStartTime")
    @LogField
    private String createStartTime;

    @Excel(name = "创建结束时间")
    @FieldRemark(name = "批量删除的ID", field = "createEndTime")
    @LogField
    private String createEndTime;

    @Excel(name = "批量删除的ID")
    @FieldRemark(name = "批量删除的ID", field = "ids")
    @LogField
    private String[] ids;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("projectId", getProjectId())
                .append("versionConfig", getVersionConfig())
                .append("domainConfig", getDomainConfig())
                .append("accountConfig", getAccountConfig())
                .append("serverConfig", getServerConfig())
                .append("databaseConfig", getDataBaseConfig())
                .append("status", getStatus())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createByName", getCreateByName())
                .append("createDept", getCreateDept())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateByName", getUpdateByName())
                .append("updateTime", getUpdateTime())
                .append("updateIp", getUpdateIp())
                .append("version", getVersion())
                .append("delFlag", getDelFlag())
                .toString();
    }
}