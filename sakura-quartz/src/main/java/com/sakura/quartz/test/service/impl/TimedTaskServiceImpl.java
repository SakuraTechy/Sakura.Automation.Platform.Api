package com.sakura.quartz.test.service.impl;

import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.common.exception.BizException;
import com.sakura.common.exception.job.TaskException;
import com.sakura.common.utils.SecurityUtils;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.date.DateUtil;
import com.sakura.common.utils.sql.SqlUtil;
import com.sakura.quartz.domain.SysJob;
import com.sakura.quartz.mapper.SysJobMapper;
import com.sakura.quartz.service.ISysJobService;
import com.sakura.quartz.test.domain.TimedTask;
import com.sakura.quartz.test.mapper.TimedTaskMapper;
import com.sakura.quartz.test.service.TimedTaskService;
import com.sakura.quartz.util.CronUtils;
import com.sakura.system.common.SysErrorCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 测试管理-定时任务表Service业务层处理
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2024-02-28
 */
@Service
@Transactional(readOnly = false)
public class TimedTaskServiceImpl extends BaseServiceImpl<TimedTaskMapper, TimedTask> implements TimedTaskService {

    private static final Logger log = LoggerFactory.getLogger(TimedTaskServiceImpl.class);

    @Autowired
    private ISysJobService jobService;

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
     * @param timedTask 测试管理-定时任务表
     * @return 测试管理-定时任务表
     */
    @Override
    public TimedTask get(TimedTask timedTask) {
        TimedTask e = super.get(timedTask);
        e.setNextExecTime(DateUtil.formatDate(CronUtils.getNextExecution(e.getCronExpression()), "yyyy-MM-dd HH:mm:ss"));
        e.setNextExecTimeList(CronUtils.getNextFiveRunTimes(e.getCronExpression()));
        return e;
    }

    /**
     * 获取单条数据
     *
     * @param id 测试管理-定时任务表id
     * @return 测试管理-定时任务表
     */
    @Override
    public TimedTask get(String id) {
        TimedTask e = super.get(id);
        e.setNextExecTime(DateUtil.formatDate(CronUtils.getNextExecution(e.getCronExpression()), "yyyy-MM-dd HH:mm:ss"));
        e.setNextExecTimeList(CronUtils.getNextFiveRunTimes(e.getCronExpression()));
        return e;
    }

    /**
     * 查询测试管理-定时任务表列表
     *
     * @param timedTask 测试管理-定时任务表
     * @return 测试管理-定时任务表
     */
    @Override
    public List<TimedTask> findList(TimedTask timedTask) {
        List<TimedTask> timedTaskList = super.findList(timedTask);
        timedTaskList.forEach((e) -> {
            e.setNextExecTime(DateUtil.formatDate(CronUtils.getNextExecution(e.getCronExpression()), "yyyy-MM-dd HH:mm:ss"));
            e.setNextExecTimeList(CronUtils.getNextFiveRunTimes(e.getCronExpression()));
        });
        return timedTaskList;
    }

    /**
     * 分页查询测试管理-定时任务表列表
     *
     * @param timedTask 测试管理-定时任务表
     * @return 测试管理-定时任务表
     */
    @Override
    public PageInfo<TimedTask> findPage(TimedTask timedTask) {
        PageInfo<TimedTask> page = super.findPage(timedTask);
        page.getList().forEach((e) -> {
            e.setNextExecTime(DateUtil.formatDate(CronUtils.getNextExecution(e.getCronExpression()), "yyyy-MM-dd HH:mm:ss"));
            e.setNextExecTimeList(CronUtils.getNextFiveRunTimes(e.getCronExpression()));
        });
        return page;
    }

    /**
     * 保存测试管理-定时任务表
     *
     * @param timedTask
     * @return 结果
     */
    @Override
    public boolean save(TimedTask timedTask) {
        if (timedTask.getIsNewRecord() && !CollectionUtils.isEmpty(mapper.findListWithUnique(timedTask))) {
            throw new BizException(SysErrorCode.B_TIMED_TASK_NameAlreadyExist);
        } else if (!CronUtils.isValid(timedTask.getCronExpression())) {
            throw new BizException("运行规则：Cron表达式不正确");
        }
        SysJob job = new SysJob();
        job.setJobGroup("DEFAULT");
        job.setJobName(timedTask.getName());
        job.setRemark(timedTask.getDescription());
        job.setInvokeTarget("testPlan.execute('" + timedTask.getTestPlanId() + "')");
        job.setCronExpression(timedTask.getCronExpression());
        job.setMisfirePolicy(timedTask.getMisfirePolicy());
        job.setConcurrent(timedTask.getConcurrent());
        job.setStatus(timedTask.getStatus());
        job.setCreateBy(SecurityUtils.getLoginUser().getUser().getName());
        return super.save(timedTask) && jobService.insertJob(job) > 0;
    }

    /**
     * 保存测试管理-定时任务表
     *
     * @param timedTask
     * @return 结果
     */
    @Override
    public boolean edit(TimedTask timedTask) throws SchedulerException, TaskException {
        if (timedTask.getIsNewRecord() && !CollectionUtils.isEmpty(mapper.findListWithUnique(timedTask))) {
            throw new BizException(SysErrorCode.B_TIMED_TASK_NameAlreadyExist);
        } else if (!CronUtils.isValid(timedTask.getCronExpression())) {
            throw new BizException("运行规则：Cron表达式不正确");
        }
        SysJob job = new SysJob();
        job.setJobName(timedTask.getName());
        Long jobId = jobService.selectJobList(job).get(0).getJobId();
        job.setJobId(jobId);
        job.setJobGroup("DEFAULT");
        job.setRemark(timedTask.getDescription());
        job.setInvokeTarget("testPlan.execute('" + timedTask.getTestPlanId() + "')");
        job.setCronExpression(timedTask.getCronExpression());
        job.setMisfirePolicy(timedTask.getMisfirePolicy());
        job.setConcurrent(timedTask.getConcurrent());
        job.setStatus(timedTask.getStatus());
        job.setUpdateBy(SecurityUtils.getLoginUser().getUser().getName());
        return super.save(timedTask) && jobService.updateJob(job) > 0 && jobService.changeStatus(job) > 0;
    }

    /**
     * 删除测试管理-定时任务表信息
     *
     * @param timedTask
     * @return 结果
     */
    @SneakyThrows
    @Override
    public boolean remove(TimedTask timedTask) {
        if (StringUtils.isNotNull(timedTask.getIds())) {
            for (String id : timedTask.getIds()) {
                timedTask = mapper.get(id);
                timedTask.setId(id);
                SysJob job = new SysJob();
                job.setJobName(timedTask.getName());
                job.setJobId(jobService.selectJobList(job).get(0).getJobId());
                if(super.remove(timedTask)){
                    jobService.deleteJob(job);
                }
            }
        }
        return true;
    }

    /**
     * 批量删除测试管理-定时任务表
     *
     * @param ids 需要删除的测试管理-定时任务表ID
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public int deleteTimedTaskByIds(String[] ids) {
        return mapper.deleteTimedTaskByIds(ids, BaseEntity.DEL_FLAG_DELETE);
    }

    /**
     * 分页查询获取执行日志
     *
     * @param timedTask 定时任务
     * @return 结果
     */
    @Override
    public PageInfo<TimedTask> getLogList(TimedTask timedTask) {
        PageDomain page = timedTask.getPage();
        Integer pageNum = page.getPageNum();
        Integer pageSize = page.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(page.getOrderBy());
            Boolean reasonable = page.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }
        List<TimedTask> timedTaskList = mapper.getLogList(timedTask);
        return new PageInfo<>(timedTaskList);
    }
}