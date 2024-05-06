package com.sakura.system.domain;


import com.alibaba.fastjson.annotation.JSONField;
import com.sakura.common.core.domain.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * 项目设置 环境管理
 *
 * @author wutun
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysEnvironment extends BaseEntity<SysEnvironment> implements Serializable {

  private String[] projectIds;

  private String projectId;

  @ApiModelProperty(value = "环境序号", required = true, example = "1")
  @JSONField(ordinal = 1)
  private Integer index;

  private String environmentName;

  private Integer status;

//  private String environmentDomain;

//  private String commonConfig;

//  private String databaseConfig;

//  private String serverConfig;

//  private List<SysCommonConfig> sysCommonConfigList;
//
//  private List<SysDataBaseConfig> sysDataBaseConfigList;
//
//  private List<SysServerConfig> sysServerConfigList;

  private List<SysProject> sysProjectList;

  private List<SysYamlConfig.Project.Environment.Version> versionList;
  private String versionName;
  private String versionConfig;

  private List<SysYamlConfig.Project.Environment.Domain> domainList;
  private String domainConfig;

  private List<SysYamlConfig.Project.Environment.Account> accountList;
  private String accountConfig;

  private List<SysYamlConfig.Project.Environment.Server> serverList;
  private String serverConfig;

  private List<SysYamlConfig.Project.Environment.DataBase> dataBaseList;
  private String dataBaseConfig;

  private SysYamlConfig.Project.Environment common;
  private String commonConfig;

  private SysYamlConfig.Project project;

  @Override
  public String toString() {
    return  new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("environmentName",getEnvironmentName())
            .append("commonConfig",getCommonConfig())
            .append("databaseConfig",getDataBaseConfig())
            .toString();
  }
}