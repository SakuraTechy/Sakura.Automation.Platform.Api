package com.sakura.system.domain;


import com.alibaba.fastjson.annotation.JSONField;
import com.sakura.common.annotation.Excel;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.test.domain.TestReport;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 测试场景记录表 sys_scene
 * @author wutun
 */
@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "自动化场景对象模型")
public class SysScene extends BaseEntity<SysScene> {
  private static final long serialVersionUID = 1L;

  private String sceneId;

  private String name;

  private String description;

  private String projectId;

  private String projectName;

  private String versionId;

  private String versionName;

  private String moduleId;

  private String modulePath;

  private String level;

  private String status;

  private String tags;

  private String principalName;

  private String principalId;

  private String deleteUser;

  private String deleteUserId;

  private String caseMsg;

  private Integer caseTotal;
  private Integer casePass;
  private Integer caseFail;
  private Integer caseSkip;
  private String passRate;

  private String executeStatus;
  private String executeResult;
  private String executeResultType;

  private Integer stepTotal;
  private Integer stepPass;
  private Integer stepFail;
  private Integer stepSkip;

  private String reportId;

  private Integer buildNumber;
  private String consoleUrl;
  private String testReportUrl;

  private String testPlanId;
  private String testSceneState;

  private Integer pageNum;
  private Integer pageSize;

  private List<SysSceneCase> caseList;
  
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String[] moduleIds;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date deleteTime;

  @Excel(name = "创建开始时间")
  @JSONField(ordinal = 8)
  private String createStartTime;
  @Excel(name = "创建结束时间")
  @JSONField(ordinal = 9)
  private String createEndTime;

  private String debugRecord;
  private List<TestReport.StatisticAnalysis> debugRecordList;

  private String testRecord;
  private List<TestReport.StatisticAnalysis> testRecordList;

  private TestReport.StatisticAnalysis statisticAnalysis;
}
