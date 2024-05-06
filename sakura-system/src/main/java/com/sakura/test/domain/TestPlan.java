package com.sakura.test.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.sakura.project.domain.ExecuteConfig;
import com.sakura.system.domain.SysSceneCase;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import com.sakura.common.utils.log.annotation.FieldRemark;
import com.sakura.common.utils.log.annotation.LogField;
import com.sakura.common.annotation.Excel;
import com.sakura.common.core.domain.entity.SysUser;
import com.sakura.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 测试管理-测试计划表对象 test_plan
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2023-11-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TestPlan extends BaseEntity<TestPlan> {
    private static final long serialVersionUID = 1L;

    /** 所属项目ID */
    @Excel(name = "所属项目ID")
    @LogField
    @FieldRemark(name = "所属项目ID",field = "projectId")
    private String projectId;

    /** 类型（0 冒烟测试 1系统测试 2回归测试） */
    @LogField
    @FieldRemark(name = "类型",field = "type")
    private String type;

    /** 名称 */
    @Excel(name = "名称")
    @LogField
    @FieldRemark(name = "名称",field = "name")
    private String name;

    /** 简称 */
    @Excel(name = "简称")
    @LogField
    @FieldRemark(name = "简称",field = "abbreviate")
    private String abbreviate;

    /** 描述 */
    @Excel(name = "描述")
    @LogField
    @FieldRemark(name = "描述",field = "description")
    private String description;

    /** 成员 */
    @Excel(name = "成员")
    @LogField
    @FieldRemark(name = "成员",field = "members")
    private String members;
    private String[] memberIds;
    private List<SysUser> memberList;

    /** 定时任务配置 */
    @Excel(name = "定时任务配置")
    @LogField
    @FieldRemark(name = "定时任务配置",field = "timedTasksConfig")
    private String timedTasksConfig;
    private ExecuteConfig executeConfig;

    /** 环境配置 */
    @Excel(name = "环境配置")
    @LogField
    @FieldRemark(name = "环境配置",field = "environmentConfig")
    private String projectConfig;

    /** 自动化配置 */
    @Excel(name = "自动化配置")
    @LogField
    @FieldRemark(name = "自动化配置",field = "automationConfig")
    private String automationConfig;

    /** 功能测试用例 */
    @Excel(name = "功能测试用例")
    @LogField
    @FieldRemark(name = "功能测试用例",field = "functionalTestCase")
    private String functionalTestScene;

    /** UI测试用例 */
    @LogField
    @FieldRemark(name = "UI测试用例",field = "uiTestCase")
    private String uiTestScene;

    /** 测试进度 */
    @LogField
    @FieldRemark(name = "测试进度",field = "testProgress")
    private String testProgress="0";

    /** 运行耗时(ms) */
    @LogField
    @FieldRemark(name = "运行耗时(ms)",field = "runTime")
    private String runTime;

    /** 状态 (0未开始 1进行中 2已完成 3已结束 4已归档) */
    @LogField
    @FieldRemark(name = "状态 (0未开始 1进行中 2已完成 3已结束 4已归档)",field = "status")
    private String status;

    /** 责任人 */
    @Excel(name = "责任人")
    @LogField
    @FieldRemark(name = "责任人",field = "principals")
    private String principals;
    private String[] principalIds;
    private List<SysUser> principalList;

    /** 计划开始时间 */
    @LogField
    @FieldRemark(name = "计划开始时间",field = "plannedStartTime")
    private String plannedStartTime;

    /** 计划结束时间 */
    @LogField
    @FieldRemark(name = "计划结束时间",field = "plannedEndTime")
    private String plannedEndTime;

    /** 实际开始时间 */
    @LogField
    @FieldRemark(name = "实际开始时间",field = "actualStartTime")
    private String actualStartTime;

    /** 实际结束时间 */
    @LogField
    @FieldRemark(name = "实际结束时间",field = "actualEndTime")
    private String actualEndTime;

    /** 创建时间区间*/
    @LogField
    @FieldRemark(name = "创建时间区间",field = "createTimeInterval")
    private String[] createTimeInterval;

    /** 创建开始时间 */
    @LogField
    @FieldRemark(name = "创建开始时间",field = "createStartTime")
    private String createStartTime;

    /** 创建结束时间 */
    @LogField
    @FieldRemark(name = "创建结束时间",field = "createEndTime")
    private String createEndTime;

    /** 更新开始时间 */
    @LogField
    @FieldRemark(name = "更新开始时间",field = "updateStartTime")
    private String updateStartTime;

    /** 更新结束时间 */
    @LogField
    @FieldRemark(name = "更新结束时间",field = "updateEndTime")
    private String updateEndTime;

    private TestScene testScene;

    @Data
    public static class TestScene {
        private String state;

        private UI ui;
        private List<UI> uiList;
        private List<String> uiIds;

        private int pageNum = 1;
        private int pageSize = 10;

        @Data
        public static class UI {
            private String id;
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
            private String executeResult;
            private Integer stepTotal;
            private Integer stepPass;
            private Integer stepFail;
            private Integer stepSkip;
            private String reportId;
            private Integer buildNumber;
            private String consoleUrl;
            private String testReportUrl;
            private int pageNum = 1;
            private int pageSize = 10;

            private List<SysSceneCase> caseList;

            @JsonInclude(JsonInclude.Include.NON_NULL)
            private String[] moduleIds;

            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            private Date deleteTime;
        }
    }

    @Excel(name = "批量删除的ID")
    @LogField
    @FieldRemark(name = "批量删除的ID", field = "ids")
    @JSONField(ordinal = 13)
    private String[] ids;
}