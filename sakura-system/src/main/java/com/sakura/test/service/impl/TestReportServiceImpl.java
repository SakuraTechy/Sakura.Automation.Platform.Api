package com.sakura.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.common.exception.BizException;
import com.sakura.common.utils.NumberUtils;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.date.DateUtils;
import com.sakura.project.domain.ExecuteConfig;
import com.sakura.system.common.SysErrorCode;
import com.sakura.system.service.ISysUserService;
import com.sakura.test.domain.TestPlan;
import com.sakura.test.domain.TestReport;
import com.sakura.test.mapper.TestReportMapper;
import com.sakura.test.service.TestPlanService;
import com.sakura.test.service.TestReportService;
import com.github.pagehelper.PageInfo;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.sakura.common.utils.jenkins.JenkinsService.getApiJson;

/**
 * 测试管理-测试报告Service业务层处理
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2023-11-02
 */
@Service
@Transactional(readOnly = false)
public class TestReportServiceImpl extends BaseServiceImpl<TestReportMapper, TestReport> implements TestReportService {

    private static final Logger log = LoggerFactory.getLogger(TestReportServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TestReportMapper testReportMapper;

    @Autowired
    private TestPlanService testPlanService;

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
     * @param testReport 测试管理-测试报告
     * @return 测试管理-测试报告
     */
    @Override
    public TestReport get(TestReport testReport) {
        return super.get(testReport);
    }

    /**
     * 获取单条数据
     *
     * @param id 测试管理-测试报告id
     * @return 测试管理-测试报告
     */
    @Override
    public TestReport get(String id) {
        return super.get(id);
    }

    /**
     * 查询测试管理-测试报告列表
     *
     * @param testReport 测试管理-测试报告
     * @return 测试管理-测试报告
     */
    @Override
    public List<TestReport> findList(TestReport testReport) {
        ExecuteConfig executeConfig = JSON.parseObject(testReport.getRunEnvironment(), ExecuteConfig.class);
        testReport.setExecuteConfig(executeConfig);
        return super.findList(testReport);
    }

    /**
     * 分页查询测试管理-测试报告列表
     *
     * @param testReport 测试管理-测试报告
     * @return 测试管理-测试报告
     */
    @Override
    public PageInfo<TestReport> findPage(TestReport testReport) {
        PageInfo<TestReport> testReportPageInfo = super.findPage(testReport);
        testReportPageInfo.getList().forEach((item)->{
//            item.setProjectConfig(JSON.parseObject(item.getProjectConfig(),ExecuteConfig.class));
//            item.setExecuteConfig(JSON.parseObject(item.getRunEnvironment(),ExecuteConfig.class));
            ExecuteConfig.AutomationConfig automationConfig = JSON.parseObject(item.getAutomationConfig(), ExecuteConfig.AutomationConfig.class);
            TestReport.StatisticAnalysis statisticAnalysis  =JSON.parseObject(item.getStatisticAnalysisStr(), TestReport.StatisticAnalysis.class);

            String url = automationConfig.getJenkins().getUrl();
            String userName = automationConfig.getJenkins().getUserName();
            String passWord = automationConfig.getJenkins().getPassWord();
            String jobName = automationConfig.getJenkins().getJob();
            int buildNumber = statisticAnalysis.getUi().getBuildNumber();
            String api = url+"/job/"+jobName+"/"+buildNumber+"/api/json?pretty=true";
            int uiDuration;
            try {
                Response response = getApiJson(api,userName,passWord);
                uiDuration = response.jsonPath().getInt("duration");
            } catch (Exception e) {
                uiDuration = 0;
            }
            statisticAnalysis.getUi().setDuration(String.valueOf(uiDuration));
            item.setRunTime(String.valueOf(uiDuration));
            item.setStatisticAnalysis(statisticAnalysis);
        });
        return  testReportPageInfo;
    }

    /**
     * 保存测试管理-测试报告
     *
     * @param testReport
     * @return 结果
     */
    @Override
    public boolean save(TestReport testReport) {
        return super.save(testReport);
    }

    /**
     * 删除测试管理-测试报告信息
     *
     * @param testReport
     * @return 结果
     */
    @Override
    public boolean remove(TestReport testReport) {
        if (StringUtils.isNotNull(testReport.getIds())) {
            for (String id : testReport.getIds()) {
                testReport.setId(id);
                if (!super.remove(testReport)) {
                    throw new BizException(SysErrorCode.B_TEST_REPORT_DeleteFailed, id);
                }
            }
        }
        return true;
    }

    /**
     * 批量删除测试管理-测试报告
     *
     * @param ids 需要删除的测试管理-测试报告ID
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public int deleteTestReportByIds(String[] ids) {
        return mapper.deleteTestReportByIds(ids, BaseEntity.DEL_FLAG_DELETE);
    }

    /**
     * 上传测试场景结果
     *
     * @param testReport
     * @return
     */
    @Override
    public boolean uploadResults(TestReport testReport) {
        TestReport testReport1 = mapper.get(testReport.getId());
        if (StringUtils.isNull(testReport1)){
            throw new BizException(SysErrorCode.B_TEST_REPORT_NotExisten, testReport.getId());
        }
        TestReport.StatisticAnalysis statisticAnalysis = JSON.parseObject(testReport1.getStatisticAnalysisStr(), TestReport.StatisticAnalysis.class);
//        TestPlan testPlan = testPlanService.get(testReport1.getTestPlanId());
//        Integer sceneTotal = getUiTestSceneList(testPlan).size();
        Integer sceneTotal = statisticAnalysis.getUi().getSceneTotal()+testReport.getStatisticAnalysis().getUi().getSceneTotal();
        Integer scenePass = statisticAnalysis.getUi().getScenePass()+testReport.getStatisticAnalysis().getUi().getScenePass();
        Integer sceneFail = statisticAnalysis.getUi().getSceneFail()+testReport.getStatisticAnalysis().getUi().getSceneFail();
        Integer sceneSkip = statisticAnalysis.getUi().getSceneSkip()+testReport.getStatisticAnalysis().getUi().getSceneSkip();
        statisticAnalysis.getUi().setSceneTotal(sceneTotal);
        statisticAnalysis.getUi().setScenePass(scenePass);
        statisticAnalysis.getUi().setSceneFail(sceneFail);
        statisticAnalysis.getUi().setSceneSkip(sceneSkip);
        String scenePassRate = NumberUtils.formatScale1(scenePass, sceneTotal ,2);
        statisticAnalysis.getUi().setScenePassRate(scenePassRate);

        Integer caseTotal = statisticAnalysis.getUi().getCaseTotal()+testReport.getStatisticAnalysis().getUi().getCaseTotal();
        Integer casePass = statisticAnalysis.getUi().getCasePass()+testReport.getStatisticAnalysis().getUi().getCasePass();
        Integer caseFail = statisticAnalysis.getUi().getCaseFail()+testReport.getStatisticAnalysis().getUi().getCaseFail();
        Integer caseSkip = statisticAnalysis.getUi().getCaseSkip()+testReport.getStatisticAnalysis().getUi().getCaseSkip();
        statisticAnalysis.getUi().setCaseTotal(caseTotal);
        statisticAnalysis.getUi().setCasePass(casePass);
        statisticAnalysis.getUi().setCaseFail(caseFail);
        statisticAnalysis.getUi().setCaseSkip(caseSkip);
        String casePassRate = NumberUtils.formatScale1(casePass, caseTotal ,2);
        statisticAnalysis.getUi().setCasePassRate(casePassRate);

        Integer stepTotal = statisticAnalysis.getUi().getStepTotal()+testReport.getStatisticAnalysis().getUi().getStepTotal();
        Integer stepPass = statisticAnalysis.getUi().getStepPass()+testReport.getStatisticAnalysis().getUi().getStepPass();
        Integer stepFail = statisticAnalysis.getUi().getStepFail()+testReport.getStatisticAnalysis().getUi().getStepFail();
        Integer stepSkip = statisticAnalysis.getUi().getStepSkip()+testReport.getStatisticAnalysis().getUi().getStepSkip();
        statisticAnalysis.getUi().setStepTotal(stepTotal);
        statisticAnalysis.getUi().setStepPass(stepPass);
        statisticAnalysis.getUi().setStepFail(stepFail);
        statisticAnalysis.getUi().setStepSkip(stepSkip);
        String stepPassRate = NumberUtils.formatScale1(stepPass, stepTotal ,2);
        statisticAnalysis.getUi().setStepPassRate(stepPassRate);
        statisticAnalysis.getUi().setExecuteStatus("已完成");
        if (scenePass.equals(sceneTotal)){
            testReport.setStatus("1");
            statisticAnalysis.getUi().setExecuteResult("全部通过");
        }else{
            testReport.setStatus("2");
            statisticAnalysis.getUi().setExecuteResult("不通过");
        }
        String durationStartTime = testReport.getStatisticAnalysis().getUi().getDurationStartTime();
        String durationEndTime = testReport.getStatisticAnalysis().getUi().getDurationEndTime();
        statisticAnalysis.getUi().setDurationStartTime(durationStartTime);
        statisticAnalysis.getUi().setDurationEndTime(durationEndTime);
        long duration = DateUtils.calculateTimeDifference(durationStartTime,durationEndTime,"yyyy-MM-dd HH:mm:ss");
        statisticAnalysis.getUi().setDuration(String.valueOf(duration));
        testReport.setStatisticAnalysisStr(JSON.toJSONString(statisticAnalysis));
        return mapper.uploadResults(testReport);
    }

    private static ArrayList<String> getUiTestSceneList(TestPlan testPlan) {
        String uiTestSceneStr = testPlan.getUiTestScene();
        if (StringUtils.isEmpty(uiTestSceneStr)||StringUtils.equals(uiTestSceneStr,"[]")){
            return new ArrayList<>();
        }else{
            // 去掉方括号
            //uiTestSceneStr = uiTestSceneStr.replaceAll("\\[", "").replaceAll("\\]", "");
            uiTestSceneStr = uiTestSceneStr.substring(1, uiTestSceneStr.length() - 1);
            // 按逗号分割字符串
            //String[] arr = str1.split(",");
            //ArrayList<String> uiTestSceneList = new ArrayList<>(Arrays.asList(arr));
            return new ArrayList<>(Arrays.asList(uiTestSceneStr.split(", ")));
        }
    }

}