package com.sakura.project.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.utils.yaml.YamlConfig;
import com.sakura.system.domain.Config;
import com.sakura.system.domain.SysScene;
import com.sakura.test.domain.TestPlan;
import com.sakura.test.domain.TestReport;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 场景执行配置信息实体类
 *
 * @author liuzhi
 * @date 2023-08-08 11:58:40
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "场景执行配置信息模型")
public class ExecuteConfig extends BaseEntity<ExecuteConfig> {
  private static final long serialVersionUID = 1L;

  private String[] ids;
  private ProjectConfig projectConfig;
  private AutomationConfig automationConfig;
  private List<SysScene> sysSceneList;
  private List<String> sceneIdList;

  private TestPlan testPlan;
  private String testPlanId;
  private List<String> testPlanIdList;

  private String TestReportId;
  private String executeName;
  private String executeEmail;
  private TestReport testReport;

  @Data
  public static class ProjectConfig {
    private String id;
    private String name;
    private String abbreviate;
    private Version version;
    private YamlConfig.Project.Environment.Account account;
    private Server server;
    private YamlConfig.Project.Environment.DataBase dataBases;

    @Data
    public static class Version {
      private String id;
      private String name;
      private String description;
    }

    @Data
    public static class Server {
      private String type;
      private String host;
      private String port;
      private String userName;
      private String passWord;
      private String description;
      private String domain;
      private List<Config> configList;
    }
  }

  @Data
  public static class AutomationConfig {
    private String name;
    private String description;
    private Project project;
    private Jenkins jenkins;
//    private Environment environment;
    private YamlConfig.Automation.Environment environment;
    private YamlConfig.Automation.Browser browser;

    @Data
    public static class Project {
      private String name;
      private String url;
      private String path;
    }

    @Data
    public static class Jenkins {
      private String ip;
      private String port;
      private String userName;
      private String passWord;
      private String url;
      private String job;
    }

    @Data
    public static class Environment {
      private String name;
    }
  }
}
