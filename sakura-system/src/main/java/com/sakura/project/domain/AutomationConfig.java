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
 * 自动化配置对象 automation_config
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-26
 */
@Data
public class AutomationConfig extends BaseEntity<AutomationConfig> {
    private static final long serialVersionUID = 1L;

    /** 配置类型 */
    @LogField
    @FieldRemark(name = "配置类型", field = "type")
    private String type;

    /** 配置ID */
    @LogField
    @FieldRemark(name = "配置ID", field = "id")
    private String id;

    /** 配置名称 */
    @LogField
    @FieldRemark(name = "配置名称", field = "name")
    private String name;

    /** 配置名称 */
    @LogField
    @FieldRemark(name = "配置描述", field = "description")
    private String description;

    /** 项目配置 */
    @Excel(name = "项目配置")
    @LogField
    @FieldRemark(name = "项目配置", field = "projectConfig")
    private String projectConfig;
    private String[] projectIds;
    private YamlConfig.Automation.Project project;
    private List<YamlConfig.Automation.Project> projectList;

    /** 环境配置 */
    @Excel(name = "Jenkins配置")
    @LogField
    @FieldRemark(name = "Jenkins配置", field = "jenkinsConfig")
    private String jenkinsConfig;
    private String[] jenkinsIds;
    private YamlConfig.Automation.Jenkins jenkins;
    private List<YamlConfig.Automation.Jenkins> jenkinsList;

    /** 环境配置 */
    @Excel(name = "环境配置")
    @LogField
    @FieldRemark(name = "环境配置", field = "environmentConfig")
    private String environmentConfig;
    private String[] environmentIds;
    private YamlConfig.Automation.Environment environment;
    private List<YamlConfig.Automation.Environment> environmentList;

    /** 浏览器配置 */
    @Excel(name = "浏览器配置")
    @LogField
    @FieldRemark(name = "浏览器配置", field = "browserConfig")
    private String browserConfig;
    private String[] browserIds;
    private YamlConfig.Automation.Browser browser;
    private List<YamlConfig.Automation.Browser> browserList;

    @Excel(name = "配置最新项目")
    @FieldRemark(name = "配置最新项目", field = "lastProject")
    @LogField
    private String lastProject;

    @Excel(name = "配置最新Jenkins")
    @FieldRemark(name = "配置最新项目", field = "lastJenkins")
    @LogField
    private String lastJenkins;

    @Excel(name = "配置最新环境")
    @FieldRemark(name = "配置最新环境", field = "lastEnvironment")
    @LogField
    private String lastEnvironment;

    @Excel(name = "配置最新浏览器")
    @FieldRemark(name = "配置最新浏览器", field = "lastBrowser")
    @LogField
    private String lastBrowser;

    /** 状态 (0关闭 1开启) */
    @Excel(name = "状态 (0关闭 1开启)", dictType = "sys_normal_disable")
    @FieldRemark(name = "状态 (0关闭 1开启)", field = "status")
    @LogField
    private Integer status;

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

    @Excel(name = "批量同步的环境名称")
    @FieldRemark(name = "批量删除的ID", field = "ids")
    @LogField
    private String[] names;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("description", getDescription())
                .append("type", getType())
                .append("projectConfig", getProjectConfig())
                .append("environmentConfig", getEnvironmentConfig())
                .append("browserConfig", getBrowserConfig())
                .append("lastProject", getLastProject())
                .append("lastEnvironment", getLastEnvironment())
                .append("lastBrowser", getLastBrowser())
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