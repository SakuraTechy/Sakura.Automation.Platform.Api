package com.sakura.test.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.sakura.common.annotation.Excel;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.utils.log.annotation.FieldRemark;
import com.sakura.common.utils.log.annotation.LogField;
import com.sakura.project.domain.ExecuteConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestReport extends BaseEntity<TestReport> {
    private static final long serialVersionUID = 1L;

    @FieldRemark(name = "所属项目ID",field = "projectId")
    @LogField
    private String projectId;

    @FieldRemark(name = "所属版本ID",field = "versionId")
    @LogField
    private String versionId;

    @FieldRemark(name = "所属计划ID",field = "testPlanId")
    @LogField
    private String testPlanId;

    @FieldRemark(name = "名称",field = "name")
    @LogField
    private String name;

    @FieldRemark(name = "描述",field = "description")
    @LogField
    private String description;

    @FieldRemark(name = "触发方式（0 手动触发，1 定时触发）",field = "triggerMode")
    @LogField
    private String triggerMode;

    @FieldRemark(name = "执行方式（0 开发自测，1 版本转测，2 本地调试，3 远程调试，4 定时任务）",field = "executionMode")
    @LogField
    private String executionMode;

    @FieldRemark(name = "项目配置",field = "projectConfig")
    @LogField
    private String projectConfig;

    @FieldRemark(name = "自动化配置",field = "automationConfig")
    @LogField
    private String automationConfig;

    @FieldRemark(name = "运行环境",field = "runEnvironment")
    @LogField
    private String runEnvironment;
    private ExecuteConfig executeConfig;

    @FieldRemark(name = "运行耗时(ms)",field = "runTime")
    @LogField
    private String runTime;

    @FieldRemark(name = "状态（0 生成中，1 通过，2 不通过）",field = "status")
    @LogField
    private String status;

    @FieldRemark(name = "统计分析（功能，UI，接口，性能）",field = "statisticAnalysis")
    @LogField
    private String statisticAnalysisStr;
    private StatisticAnalysis statisticAnalysis;

    @Data
    public static class StatisticAnalysis {
        private UI ui;
        private List<UI> uiList;

        @Data
        public static class UI {
            @JSONField(ordinal = 0)
            private String testPlanId;

            @JSONField(ordinal = 1)
            private Integer buildNumber;
            @JSONField(ordinal = 2)
            private String consoleUrl;
            @JSONField(ordinal = 3)
            private String testReportUrl;

            @FieldRemark(name = "场景总数",field = "sceneTotal")
            @LogField
            @JSONField(ordinal = 4)
            private Integer sceneTotal=0;
            @FieldRemark(name = "场景通过数",field = "scenePass")
            @LogField
            @JSONField(ordinal = 5)
            private Integer scenePass=0;
            @FieldRemark(name = "场景失败数",field = "sceneFail")
            @LogField
            @JSONField(ordinal = 6)
            private Integer sceneFail=0;
            @FieldRemark(name = "场景跳过数",field = "sceneSkip")
            @LogField
            @JSONField(ordinal = 7)
            private Integer sceneSkip=0;
            @FieldRemark(name = "场景通过率",field = "scenePassRate")
            @LogField
            @JSONField(ordinal = 8)
            private String scenePassRate="0%";

            @FieldRemark(name = "用例总数",field = "caseTotal")
            @LogField
            @JSONField(ordinal = 9)
            private Integer caseTotal=0;
            @FieldRemark(name = "用例通过数",field = "casePass")
            @LogField
            @JSONField(ordinal = 10)
            private Integer casePass=0;
            @FieldRemark(name = "用例失败数",field = "caseFail")
            @LogField
            @JSONField(ordinal = 11)
            private Integer caseFail=0;
            @FieldRemark(name = "用例跳过数",field = "caseSkip")
            @LogField
            @JSONField(ordinal = 12)
            private Integer caseSkip=0;
            @FieldRemark(name = "用例通过率",field = "casePassRate")
            @LogField
            @JSONField(ordinal = 13)
            private String casePassRate="0%";

            @FieldRemark(name = "步骤总数",field = "stepTotal")
            @LogField
            @JSONField(ordinal = 14)
            private Integer stepTotal=0;
            @FieldRemark(name = "步骤通过数",field = "stepPass")
            @LogField
            @JSONField(ordinal = 15)
            private Integer stepPass=0;
            @FieldRemark(name = "步骤失败数",field = "stepFail")
            @LogField
            @JSONField(ordinal = 16)
            private Integer stepFail=0;
            @FieldRemark(name = "步骤跳过数",field = "stepSkip")
            @LogField
            @JSONField(ordinal = 17)
            private Integer stepSkip=0;
            @FieldRemark(name = "步骤通过率",field = "stepPassRate")
            @LogField
            @JSONField(ordinal = 18)
            private String stepPassRate="0%";

            @FieldRemark(name = "执行人姓名",field = "executeName")
            @JSONField(ordinal = 19)
            private String executeName;
            @FieldRemark(name = "执行状态（未开始，进行中，已完成）",field = "executeStatus")
            @JSONField(ordinal = 20)
            private String executeStatus;
            @FieldRemark(name = "执行结果（全部通过，不通过）",field = "executeResult")
            @JSONField(ordinal = 21)
            private String executeResult;
            @FieldRemark(name = "持续时间(ms)",field = "duration")
            @JSONField(ordinal = 22)
            private String duration;
            @FieldRemark(name = "持续开始时间",field = "durationStartTime")
            @JSONField(ordinal = 23)
            private String durationStartTime;
            @FieldRemark(name = "持续结束时间",field = "durationEndTime")
            @JSONField(ordinal = 24)
            private String durationEndTime;
        }
    }

    @Data
    public static class runEnvironment {
        private String name;
        private String description;

        public static class ProjectConfig {
            private String name;
            private String abbreviate;
            private String versionName;
            private String versionDescription;
            private String domain;
        }
    }

    @FieldRemark(name = "创建时间区间",field = "createTimeInterval")
    @LogField
    private String[] createTimeInterval;

    @FieldRemark(name = "创建开始时间",field = "createStartTime")
    @LogField
    private String createStartTime;

    @FieldRemark(name = "创建结束时间",field = "createEndTime")
    @LogField
    private String createEndTime;

    @Excel(name = "批量删除的ID")
    @LogField
    @FieldRemark(name = "批量删除的ID", field = "ids")
    @JSONField(ordinal = 13)
    private String[] ids;
}
