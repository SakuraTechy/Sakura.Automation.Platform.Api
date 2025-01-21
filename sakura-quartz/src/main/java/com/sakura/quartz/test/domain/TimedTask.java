package com.sakura.quartz.test.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.sakura.common.annotation.Excel;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.utils.log.annotation.FieldRemark;
import com.sakura.common.utils.log.annotation.LogField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 测试管理-定时任务表对象 timed_task
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2024-02-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TimedTask extends BaseEntity<TimedTask> {
    private static final long serialVersionUID = 1L;

    /** 所属计划ID */
    @Excel(name = "所属计划ID")
    @LogField
    @FieldRemark(name = "所属计划ID",field = "testPlanId")
    private String testPlanId;

    /** 类型（0 UI自动化 1接口自动化 2性能自动化） */
    @LogField
    @FieldRemark(name = "类型",field = "type")
    private String type;

    /** 名称 */
    @Excel(name = "名称")
    @LogField
    @FieldRemark(name = "名称",field = "name")
    private String name;

    /** 描述 */
    @Excel(name = "描述")
    @LogField
    @FieldRemark(name = "描述",field = "description")
    private String description;

    /** 运行规则（cron执行表达式） */
    @Excel(name = "运行规则（cron执行表达式）")
    @LogField
    @FieldRemark(name = "运行规则（cron执行表达式）",field = "cronExpression")
    private String cronExpression;

    /** 计划执行错误策略（1立即执行 2执行一次 3放弃执行） */
    @LogField
    @FieldRemark(name = "计划执行错误策略（1立即执行 2执行一次 3放弃执行）",field = "misfirePolicy")
    private String misfirePolicy;

    /** 是否并发执行（0允许 1禁止） */
    @LogField
    @FieldRemark(name = "是否并发执行（0允许 1禁止）",field = "concurrent")
    private String concurrent;

    /** 状态 (0开启 1关闭) */
    @LogField
    @FieldRemark(name = "状态 (0开启 1关闭)",field = "status")
    private String status;

    /** 下次执行时间 */
    @LogField
    @FieldRemark(name = "下次执行时间",field = "nextExecTime")
    private String nextExecTime;
    private List<String> nextExecTimeList;

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

    /** 创建者 */
    @LogField
    @FieldRemark(name = "创建者",field = "createBy")
    private String createBy;

    @Excel(name = "批量删除的ID")
    @LogField
    @FieldRemark(name = "批量删除的ID", field = "ids")
    @JSONField(ordinal = 13)
    private String[] ids;
}