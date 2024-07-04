package com.sakura.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sakura.common.core.domain.entity.SysUser;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.common.exception.BizException;
import com.sakura.common.utils.NumberUtils;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.date.DateUtil;
import com.sakura.common.utils.date.DateUtils;
import com.sakura.common.utils.regex.RegexUtli;
import com.sakura.common.utils.sort.DragSortUtil;
import com.sakura.common.utils.sql.SqlUtil;
import com.sakura.common.utils.uuid.IdUtils;
import com.sakura.common.utils.xml.SceneXmlUtils;
import com.sakura.common.utils.xml.ZipUtil;
import com.sakura.project.domain.AutomationConfig;
import com.sakura.system.common.SysErrorCode;
import com.sakura.system.domain.*;
import com.sakura.system.domain.vo.DragVo;
import com.sakura.system.domain.vo.SceneCaseVo;
import com.sakura.system.domain.vo.SceneVo;
import com.sakura.system.domain.vo.StepVo;
import com.sakura.system.mapper.SysAutomationMapper;
import com.sakura.system.mapper.SysConfigurationMapper;
import com.sakura.system.mapper.SysProjectMapper;
import com.sakura.project.domain.ExecuteConfig;
import com.sakura.system.service.SysAutomationService;
import com.sakura.system.service.SysNodeService;
import com.sakura.system.service.SysProjectService;


import com.sakura.test.domain.TestPlan;
import com.sakura.test.domain.TestReport;
import com.sakura.test.service.TestPlanService;
import com.sakura.test.service.TestReportService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author wutun
 */
@Service
@Transactional(readOnly = false)
public class SysAutomationServiceImpl extends BaseServiceImpl<SysAutomationMapper, SysScene> implements SysAutomationService {
    @Autowired
    private SysAutomationMapper sysAutomationMapper;

    @Autowired
    private SysConfigurationMapper sysConfigurationMapper;

    @Autowired
    private SysProjectMapper sysProjectMapper;

    @Autowired
    private SysProjectService sysProjectService;

    @Autowired
    private SysNodeService sysNodeService;

    @Autowired
    private TestPlanService testPlanService;

    @Autowired
    private TestReportService testReportService;

    @Override
    public void refreshCache() {
    }

    @Override
    public void loadingUserCache() {
    }

    @Override
    public void checkUserDataScope(String userId) {
    }

    @Override
    public List<SysScene> findList(SysScene sysScene) {
        return super.findList(sysScene);
    }

    /**
     * 查询分页数据
     *
     * @param sysScene
     * @return
     */
    @Override
//    public PageInfo<SysScene> findPage1(SysScene sysScene) {
//        PageInfo<SysScene> page = super.findPage(sysScene);
//        List<SysScene> sysSceneList = page.getList();
//        if(!sysScene.getExecuteResultType().equals("debug")){
//            List<SysScene> sysSceneList1 = new ArrayList<>();
//            sysSceneList.forEach((e)->{
//                List<TestReport.StatisticAnalysis.UI> recordUiList = JSON.parseArray(e.getTestRecord(), TestReport.StatisticAnalysis.UI.class);
//                recordUiList = recordUiList.stream()
//                        .filter(record -> StringUtils.isNotEmpty(sysScene.getTestPlanId()) && record.getTestPlanId().equals(sysScene.getTestPlanId()))
////                        .filter(record -> StringUtils.isNotEmpty(Collections.singleton(sysScene.getBuildNumber())) && Objects.equals(record.getBuildNumber(), sysScene.getBuildNumber()))
////                        .filter(record -> StringUtils.isNotEmpty(sysScene.getExecuteResult()) && record.getExecuteResult().equals(sysScene.getExecuteResult()))
//                        .filter(record -> sysScene.getExecuteStatus() == null || !sysScene.getExecuteStatus().isEmpty() && record.getExecuteStatus().equals(sysScene.getExecuteStatus()))
//                        .collect(Collectors.toList());
//                if(!recordUiList.isEmpty()){
//                    sysSceneList1.add(e);
//                }
//                e.setTestRecord(JSON.toJSONString(recordUiList));
//            });
//            sysSceneList = sysSceneList1;
//        }
//        page.setList(sysSceneList);
//        return page;
//    }
    public PageInfo<SysScene> findPage(SysScene sysScene) {
        sysScene.setPageNum(null);
        sysScene.setPageSize(null);
        List<SysScene> sysSceneList = super.findList(sysScene);
        List<SysScene> sysSceneList1 = new ArrayList<>();
        sysSceneList.forEach((e) -> {
            List<TestReport.StatisticAnalysis.UI> recordUiList;
            if (sysScene.getExecuteResultType().equals("debug")) {
                recordUiList = JSON.parseArray(e.getDebugRecord(), TestReport.StatisticAnalysis.UI.class);
            } else {
                recordUiList = JSON.parseArray(e.getTestRecord(), TestReport.StatisticAnalysis.UI.class);
            }

            if (StringUtils.isNotEmpty(recordUiList)) {
                Predicate<TestReport.StatisticAnalysis.UI> filterPredicate;
                if (sysScene.getExecuteResultType().equals("plan")) {
                    filterPredicate = record -> {
                        boolean testPlanIdCondition = (StringUtils.isEmpty(sysScene.getTestPlanId()) || StringUtils.isEmpty(record.getTestPlanId()) || record.getTestPlanId().equals(sysScene.getTestPlanId()));
                        return testPlanIdCondition;
                    };
                    recordUiList = recordUiList.stream()
                            .filter(filterPredicate)
                            .collect(Collectors.toList());
                    filterPredicate = record -> {
                        boolean executeStatusCondition = (StringUtils.isEmpty(sysScene.getExecuteStatus()) || StringUtils.isEmpty(record.getExecuteStatus()) || record.getExecuteStatus().equals(sysScene.getExecuteStatus()));
                        boolean executeResultCondition = (StringUtils.isEmpty(sysScene.getExecuteResult()) || StringUtils.isEmpty(record.getExecuteResult()) || record.getExecuteResult().equals(sysScene.getExecuteResult()));
                        return executeStatusCondition && executeResultCondition;
                    };
                    recordUiList.stream()
                            .limit(1) // 限制流中的元素数量为1，即只遍历第一个元素
                            .filter(filterPredicate)
                            .findFirst() // 返回第一个满足条件的元素（如果存在），否则返回Optional.empty()
                            .ifPresent(filteredRecord -> {
                                // 这里应该是recordUiList而不是filteredRecordUiList
                                sysSceneList1.add(e);
                            });
//                    e.setTestRecord(JSON.toJSONString(recordUiList));
                } else if (sysScene.getExecuteResultType().equals("report")) {
                    filterPredicate = record -> {
                        boolean testPlanIdCondition = (StringUtils.isEmpty(sysScene.getTestPlanId()) || StringUtils.isEmpty(record.getTestPlanId()) || record.getTestPlanId().equals(sysScene.getTestPlanId()));
                        boolean buildNumberCondition = (sysScene.getBuildNumber() == null || record.getBuildNumber() == null || Objects.equals(record.getBuildNumber(), sysScene.getBuildNumber()));
                        boolean executeResultCondition = (StringUtils.containsAnyIgnoreCase(record.getExecuteResult(),sysScene.getExecuteResult().split(", ")) || record.getExecuteResult().equals(sysScene.getExecuteResult()));
                        return testPlanIdCondition && buildNumberCondition && executeResultCondition;
                    };
                    recordUiList = recordUiList.stream()
                            .filter(filterPredicate)
                            .collect(Collectors.toList());
                    if (!recordUiList.isEmpty()) {
                        sysSceneList1.add(e);
                    }
//                    e.setTestRecord(JSON.toJSONString(recordUiList));
                } else if (sysScene.getExecuteResultType().equals("debug")) {
                    filterPredicate = record -> {
                        boolean testPlanIdCondition = (StringUtils.isEmpty(sysScene.getTestPlanId()) || StringUtils.isEmpty(record.getTestPlanId()) || record.getTestPlanId().equals(sysScene.getTestPlanId()));
                        boolean buildNumberCondition = (sysScene.getBuildNumber() == null || record.getBuildNumber() == null || Objects.equals(record.getBuildNumber(), sysScene.getBuildNumber()));
                        boolean executeStatusCondition = (StringUtils.isEmpty(sysScene.getExecuteStatus()) || StringUtils.isEmpty(record.getExecuteStatus()) || record.getExecuteStatus().equals(sysScene.getExecuteStatus()));
                        boolean executeResultCondition = (StringUtils.isEmpty(sysScene.getExecuteResult()) || StringUtils.isEmpty(record.getExecuteResult()) || record.getExecuteResult().equals(sysScene.getExecuteResult()));
                        return testPlanIdCondition && buildNumberCondition && executeStatusCondition && executeResultCondition;
                    };
                    recordUiList.stream()
                            .limit(1) // 限制流中的元素数量为1，即只遍历第一个元素
                            .filter(filterPredicate)
                            .findFirst() // 返回第一个满足条件的元素（如果存在），否则返回Optional.empty()
                            .ifPresent(filteredRecord -> {
                                // 这里应该是recordUiList而不是filteredRecordUiList
                                sysSceneList1.add(e);
                            });
                }
            }
        });
        return new PageInfo<>(sysSceneList1);
    }


    @Override
    public boolean deleteSysSceneByIds(String[] ids) {
        //批量删除xml
        for (String id : ids) {
            SysScene sysScene = mapper.get(id);
//            SceneXmlUtils.deleteXml(sysScene);
        }
        return sysAutomationMapper.deleteSysSceneByIds(ids);
    }

    @Override
    public boolean deleteSceneByVersionId(String versionId) {
        return sysAutomationMapper.deleteSceneByVersionId(versionId);
    }

    @Override
    public void exportSysScene(HttpServletRequest request, HttpServletResponse response, String[] ids, ExecuteConfig executeConfig) throws Exception {
        for (String id : ids) {
            SysScene sysScene = mapper.get(id);
            SceneXmlUtils.createXml(sysScene,SceneXmlUtils.getProjectPath(sysScene),executeConfig);
            SceneXmlUtils.exportXml(sysScene, request, response);
        }
    }

    @Override
    public void exportSysScenes(HttpServletRequest request, HttpServletResponse response, String[] ids, ExecuteConfig executeConfig) throws Exception {
        List<String> outDirList = new ArrayList<>();
        for (String id : ids) {
            SysScene sysScene = mapper.get(id);
            String xmlPath = SceneXmlUtils.getProjectPath(sysScene);
            SceneXmlUtils.createXml(sysScene,xmlPath,executeConfig);
            outDirList.add(xmlPath);
        }
        ZipUtil.toZip(outDirList, response.getOutputStream(), true);
    }

//    @Override
//    public void exportSysScenes(HttpServletRequest request, HttpServletResponse response, String[] ids) throws Exception {
//        if (ids.length == 1) {
//            for (String id : ids) {
//                SysScene sysScene = mapper.get(id);
//                SceneXmlUtils.exportXml(sysScene, request, response);
//            }
//        } else {
////            String zipfilePath = "";
////            Map<String, String> files = new HashMap<>(16);
////            for (String id : ids) {
////                SysScene sysScene = mapper.get(id);
////                String fileName = sysScene.getSceneId();
////                String filePath = SceneXmlUtils.getProjectPath("Remote_ProjectPath" , sysScene.getProjectId()) + sysScene.getVersionName() + "/TestCaseXml/";
////                zipfilePath = SceneXmlUtils.getProjectPath("Remote_ProjectPath" , sysScene.getProjectId());
////                files.put(fileName, filePath);
////            }
////            SceneXmlUtils.exportXml(files, zipfilePath, "TestCaseXml.zip");
////            SceneXmlUtils.exportZip(zipfilePath + "TestCaseXml.zip", response);
//
//            List<String> outDirList = new ArrayList<>();
//            for (String id : ids) {
//                SysScene sysScene = mapper.get(id);
//                String filePath = SceneXmlUtils.getProjectPath("Remote_ProjectPath" , sysScene.getProjectId()) + sysScene.getVersionName();
//                outDirList.add(filePath);
//            }
//            ZipUtil.toZip(outDirList, response.getOutputStream(),true);
//        }
//    }

    @Override
    public void exportSysSceneAll(HttpServletRequest request, HttpServletResponse response, SysScene sysScene, ExecuteConfig executeConfig) throws Exception {
        List<String> outDirList = new ArrayList<>();
        List<SysScene> sysSceneList = sysAutomationMapper.findList(sysScene);
        for (SysScene sysScene1 : sysSceneList) {
            String xmlPath = SceneXmlUtils.getProjectPath(sysScene1);
            SceneXmlUtils.createXml(sysScene1,xmlPath,executeConfig);
            outDirList.add(xmlPath);
        }
        ZipUtil.toZip(outDirList, response.getOutputStream(), true);
    }

    @Override
    public Map<Object, Object> execSysScene(ExecuteConfig executeConfig) throws Exception {
        List<SysScene> sysSceneList = executeConfig.getSysSceneList();
        Map<Object, Object> execSysScene = new HashMap<>();
        if (!sysSceneList.isEmpty()) {
            SceneXmlUtils.createTestngReportXml(executeConfig);
            if (SceneXmlUtils.createExtentReportXml(executeConfig)) {
                execSysScene = SceneXmlUtils.execSysScene(executeConfig);
                Integer buildNumber = Integer.parseInt(String.valueOf(execSysScene.get("buildNumber")));
                String consoleUrl = String.valueOf(execSysScene.get("consoleUrl"));
                String testReportUrl = String.valueOf(execSysScene.get("testReportUrl"));

                TestReport.StatisticAnalysis.UI ui = new TestReport.StatisticAnalysis.UI();
                ui.setTestPlanId(executeConfig.getTestPlanId());
                ui.setBuildNumber(buildNumber);
                ui.setConsoleUrl(consoleUrl);
                ui.setTestReportUrl(testReportUrl);
                ui.setExecuteName(executeConfig.getExecuteName());
                ui.setExecuteStatus("进行中");
                ui.setExecuteResult("-");
                ui.setDuration("-");

                sysSceneList.forEach((sysScene) -> {
                    sysScene = mapper.get(sysScene.getId());
                    List<TestReport.StatisticAnalysis.UI> recordUiList = getRecordUiList(sysScene,ui);
//                    recordUiList.sort((x, y) -> y.getBuildNumber().compareTo(x.getBuildNumber()));
                    recordUiList.sort((x, y) -> {
                        Integer buildNumberX = x.getBuildNumber();
                        Integer buildNumberY = y.getBuildNumber();
                        // 如果两者都为空，则认为相等
                        if (buildNumberX == null && buildNumberY == null) {
                            return 0;
                        }
                        // 如果只有x为空，则y排在前面
                        if (buildNumberX == null) {
                            return 1;
                        }
                        // 如果只有y为空，则x排在前面
                        if (buildNumberY == null) {
                            return -1;
                        }
                        // 如果两者都不为空，则按照buildNumber降序排序
                        return buildNumberY.compareTo(buildNumberX);
                    });
//                    TestReport.StatisticAnalysis testReport = new TestReport.StatisticAnalysis();
//                    testReport.setUiList(recordUiList);
                    if (StringUtils.equals(executeConfig.getTestReport().getExecutionMode(), "2") || StringUtils.equals(executeConfig.getTestReport().getExecutionMode(), "3")) {
                        sysScene.setDebugRecord(JSON.toJSONString(recordUiList));
                    } else {
                        sysScene.setTestRecord(JSON.toJSONString(recordUiList));
                    }
                    sysScene.setStatus("进行中");
                    super.save(sysScene);
                });
//                sysAutomationMapper.execSysSceneByIds(sysSceneList, buildNumber, consoleUrl, testReportUrl);

                if(StringUtils.isNotEmpty(executeConfig.getTestPlanId())){
                    TestReport testReport = getTestReport(executeConfig,buildNumber);
//                    TestReport.StatisticAnalysis testReportStatisticAnalysis = getTestReportStatisticAnalysis(buildNumber, consoleUrl, testReportUrl);
                    TestReport.StatisticAnalysis testReportStatisticAnalysis = new TestReport.StatisticAnalysis();
                    TestReport.StatisticAnalysis.UI testReportStatisticAnalysisUI = new TestReport.StatisticAnalysis.UI();
                    testReportStatisticAnalysisUI.setBuildNumber(buildNumber);
                    testReportStatisticAnalysisUI.setConsoleUrl(consoleUrl);
                    testReportStatisticAnalysisUI.setTestReportUrl(testReportUrl);
                    testReportStatisticAnalysisUI.setExecuteName(executeConfig.getExecuteName());
                    testReportStatisticAnalysisUI.setExecuteStatus("-");
                    testReportStatisticAnalysisUI.setExecuteResult("-");
                    testReportStatisticAnalysisUI.setDurationStartTime("-");
                    testReportStatisticAnalysisUI.setDurationEndTime("-");
                    testReportStatisticAnalysisUI.setDuration("0");
                    testReportStatisticAnalysis.setUi(testReportStatisticAnalysisUI);
                    testReport.setStatisticAnalysisStr(JSON.toJSONString(testReportStatisticAnalysis));
                    testReportService.save(testReport);

                    TestPlan testPlan = testPlanService.get(executeConfig.getTestPlanId());
                    int total = Integer.parseInt(testPlan.getTestProgress());
                    if(total==0){
                        testPlan.setTestProgress(String.valueOf(total+sysSceneList.size()));
                    }
                    testPlan.setStatus("1");
//                    testPlan.setTimedTasksConfig(JSON.toJSONString(executeConfig));
                    testPlanService.save(testPlan);

//                    SysScene sysScene = new SysScene();
//                    sysScene.setTestPlanId(executeConfig.getTestPlanId());
//                    sysAutomationMapper.clearResults(sysScene);
                }
            }
        } else {
            throw new BizException(SysErrorCode.B_SYSSCENE_SceneNotExisten);
        }
        return execSysScene;
    }

//    public Map<Object, Object> execSysScene1(ExecuteConfig executeConfig) throws Exception {
//        List<String> sceneIdList = executeConfig.getSceneIdList();
//        Map<Object, Object> execSysScene = new HashMap<>();
//        if (!sceneIdList.isEmpty()) {
////            sysSceneList.sort((x, y) -> x.getSceneId().compareTo(y.getSceneId()));
////            executeConfig.setSysSceneList(sysSceneList);
//            SceneXmlUtils.createTestngReportXml(executeConfig);
//            if (SceneXmlUtils.createExtentReportXml(executeConfig)) {
//                execSysScene = SceneXmlUtils.execSysScene(executeConfig);
//                Integer buildNumber = Integer.parseInt(String.valueOf(execSysScene.get("buildNumber")));
//                String consoleUrl = String.valueOf(execSysScene.get("consoleUrl"));
//                String testReportUrl = String.valueOf(execSysScene.get("testReportUrl"));
//
//                TestReport.StatisticAnalysis.UI ui = new TestReport.StatisticAnalysis.UI();
//                ui.setTestPlanId(executeConfig.getTestPlanId());
//                ui.setBuildNumber(buildNumber);
//                ui.setConsoleUrl(consoleUrl);
//                ui.setTestReportUrl(testReportUrl);
//                ui.setExecuteName(executeConfig.getExecuteName());
//                ui.setExecuteStatus("进行中");
//
//                sceneIdList.forEach((sceneId) -> {
//                    SysScene  sysScene = mapper.get(sceneId);
//                    List<TestReport.StatisticAnalysis.UI> recordUiList = getRecordUiList(sysScene,ui);
//                    recordUiList.sort((x, y) -> y.getBuildNumber().compareTo(x.getBuildNumber()));
////                    TestReport.StatisticAnalysis testReport = new TestReport.StatisticAnalysis();
////                    testReport.setUiList(recordUiList);
//                    if (StringUtils.equals(executeConfig.getTestReport().getExecutionMode(), "2") || StringUtils.equals(executeConfig.getTestReport().getExecutionMode(), "3")) {
//                        sysScene.setDebugRecord(JSON.toJSONString(recordUiList));
//                    } else {
//                        sysScene.setTestRecord(JSON.toJSONString(recordUiList));
//                    }
//                    sysScene.setStatus("Underway");
//                    super.save(sysScene);
//                });
////                sysAutomationMapper.execSysSceneByIds(sysSceneList, buildNumber, consoleUrl, testReportUrl);
//
//                if(StringUtils.isNotEmpty(executeConfig.getTestPlanId())){
//                    TestReport testReport = getTestReport(executeConfig);
//                    TestReport.StatisticAnalysis testReportStatisticAnalysis = getTestReportStatisticAnalysis(buildNumber, consoleUrl, testReportUrl);
//                    testReport.setStatisticAnalysisStr(JSON.toJSONString(testReportStatisticAnalysis));
//                    testReportService.save(testReport);
//
//                    TestPlan testPlan = testPlanService.get(executeConfig.getTestPlanId());
//                    int total = Integer.parseInt(testPlan.getTestProgress());
//                    if(total==0){
//                        testPlan.setTestProgress(String.valueOf(total+sceneIdList.size()));
//                    }
//                    testPlan.setStatus("1");
//                    testPlanService.save(testPlan);
//
////                    SysScene sysScene = new SysScene();
////                    sysScene.setTestPlanId(executeConfig.getTestPlanId());
////                    sysAutomationMapper.clearResults(sysScene);
//                }
//            }
//        } else {
//            throw new BizException(SysErrorCode.B_SYSSCENE_SceneNotExisten);
//        }
//        return execSysScene;
//    }

    private List<TestReport.StatisticAnalysis.UI> getRecordUiList(SysScene sysScene, TestReport.StatisticAnalysis.UI ui) {
        List<TestReport.StatisticAnalysis.UI> recordUiList = new ArrayList<>();
        String record = StringUtils.isEmpty(ui.getTestPlanId()) ? sysScene.getDebugRecord() : sysScene.getTestRecord();
        if (StringUtils.isNotEmpty(record)) {
            recordUiList = JSON.parseArray(record, TestReport.StatisticAnalysis.UI.class);
        }
        TestReport.StatisticAnalysis.UI testReportUi = new TestReport.StatisticAnalysis.UI();
        testReportUi.setTestPlanId(ui.getTestPlanId());
        testReportUi.setBuildNumber(ui.getBuildNumber());
        testReportUi.setConsoleUrl(ui.getConsoleUrl());
        testReportUi.setTestReportUrl(ui.getTestReportUrl());
        testReportUi.setExecuteName(ui.getExecuteName());
        testReportUi.setExecuteStatus(ui.getExecuteStatus());
        testReportUi.setExecuteResult(ui.getExecuteResult());
        testReportUi.setDuration(ui.getDuration());
        recordUiList.add(testReportUi);
        return recordUiList;
    }

    private static TestReport getTestReport(ExecuteConfig executeConfig,Integer buildNumber) {
        TestReport testReport = new TestReport();
        testReport.setNewRecord(true);
        testReport.setId(executeConfig.getTestReport().getId());
        testReport.setProjectId(executeConfig.getProjectConfig().getId());
        testReport.setVersionId(executeConfig.getProjectConfig().getVersion().getId());
        testReport.setTestPlanId(executeConfig.getTestPlanId());
        testReport.setName(executeConfig.getTestReport().getName()+"_"+buildNumber);
        testReport.setDescription(executeConfig.getTestReport().getDescription());
        testReport.setTriggerMode(executeConfig.getTestReport().getTriggerMode());
        testReport.setExecutionMode(executeConfig.getTestReport().getExecutionMode());
        ExecuteConfig runEnvironment = new ExecuteConfig();
//        runEnvironment.setProjectConfig(executeConfig.getProjectConfig());
//        runEnvironment.setAutomationConfig(executeConfig.getAutomationConfig());
//        testReport.setRunEnvironment(JSON.toJSONString(runEnvironment));
        testReport.setProjectConfig(JSON.toJSONString(executeConfig.getProjectConfig()));
        testReport.setAutomationConfig(JSON.toJSONString(executeConfig.getAutomationConfig()));
        testReport.setStatus("0");
        return testReport;
    }

    private static TestReport.StatisticAnalysis getTestReportStatisticAnalysis(Integer buildNumber, String consoleUrl, String testReportUrl) {
        TestReport.StatisticAnalysis testReportStatisticAnalysis = new TestReport.StatisticAnalysis();
        TestReport.StatisticAnalysis.UI testReportStatisticAnalysisUI = new TestReport.StatisticAnalysis.UI();
        testReportStatisticAnalysisUI.setBuildNumber(buildNumber);
        testReportStatisticAnalysisUI.setConsoleUrl(consoleUrl);
        testReportStatisticAnalysisUI.setTestReportUrl(testReportUrl);
        testReportStatisticAnalysis.setUi(testReportStatisticAnalysisUI);
        return testReportStatisticAnalysis;
    }

//    @Override
//    public Map<Object, Object> execSysScene(ExecuteConfig executeConfig) throws Exception {
//        List<SysScene> sysSceneList = new ArrayList<>();
//        List<List<SysScene>> arrayList = new ArrayList<>();
//        String versionName = "";
//        Map<Object, Object> execSysScene = new HashMap<>();
//        String[] ids = executeConfig.getIds();
//        Arrays.sort(ids);
//        for (String id : ids) {
//            SysScene sysScene = mapper.get(id);
//            if (StringUtils.equals(versionName, sysScene.getVersionName())) {
//                sysSceneList.add(sysScene);
//            } else {
//                sysSceneList = new ArrayList<>();
//                sysSceneList.add(sysScene);
//                arrayList.add(sysSceneList);
//            }
//            versionName = sysScene.getVersionName();
//        }
//        if (arrayList.size() > 0) {
//            for (List<SysScene> sceneList : arrayList) {
//                SceneXmlUtils.createTestngReportXml(sceneList,executeConfig);
//            }
//            if (SceneXmlUtils.createExtentReportXml(arrayList,executeConfig)) {
//                execSysScene = SceneXmlUtils.execSysScene(executeConfig);
//                Integer buildNumber = Integer.parseInt(String.valueOf(execSysScene.get("buildNumber")));
//                String consoleUrl = String.valueOf(execSysScene.get("consoleUrl"));
//                String testReportUrl = String.valueOf(execSysScene.get("testReportUrl"));
//                sysAutomationMapper.execSysSceneByIds(ids, buildNumber, consoleUrl, testReportUrl);
//            }
//        } else {
//            throw new BizException(SysErrorCode.B_SYSSCENE_SceneNotExisten);
//        }
//        return execSysScene;
//    }

//    @Override
//    public Map<Object, Object> execSysScene1(String[] ids) throws Exception {
//        List<SysScene> sysSceneList = new ArrayList<>();
//        List<List<SysScene>> arrayList = new ArrayList<>();
//        String projectId = "";
//        String projectName = "";
//        String versionName = "";
//        String ip = "";
//        Map<Object, Object> execSysScene = new HashMap<>();
//        Arrays.sort(ids);
//        for (String id : ids) {
//            SysScene sysScene = mapper.get(id);
//            if (StringUtils.equals(versionName, sysScene.getVersionName())) {
//                sysSceneList.add(sysScene);
//            } else {
//                sysSceneList = new ArrayList<>();
//                sysSceneList.add(sysScene);
//                arrayList.add(sysSceneList);
//            }
//            projectId = sysScene.getProjectId();
//            projectName = sysScene.getProjectName();
//            versionName = sysScene.getVersionName();
//        }
//        if (arrayList.size() > 0) {
//            for (List<SysScene> sceneList : arrayList) {
//                SceneXmlUtils.createTestngReportXml(sceneList);
//            }
//            if (SceneXmlUtils.createExtentReportXml(arrayList)) {
//                execSysScene = SceneXmlUtils.execSysScene(projectName, versionName, projectId);
//                Integer buildNumber = Integer.parseInt(String.valueOf(execSysScene.get("buildNumber")));
//                String consoleUrl = String.valueOf(execSysScene.get("consoleUrl"));
//                String testReportUrl = String.valueOf(execSysScene.get("testReportUrl"));
//                sysAutomationMapper.execSysSceneByIds(ids, buildNumber, consoleUrl, testReportUrl);
//            }
//        } else {
//            throw new BizException(SysErrorCode.B_SYSSCENE_SceneNotExisten);
//        }
//        return execSysScene;
//    }

    /**
     * 保存场景信息并生成XML文件
     *
     * @param sysScene
     * @return
     */
    @Override
    public boolean save(SysScene sysScene) {
        if (!RegexUtli.isClassMethod(sysScene.getSceneId())) {
            throw new BizException(SysErrorCode.B_SYSSCENE_SceneId_WrongFul);
        }
        //如果数据为新插入数据
        if (sysScene.getIsNewRecord()) {
            checkSceneUnique(sysScene);
        } else {//数据为修改数据
            SysScene sysScene1 = mapper.get(sysScene.getId());
            //如果修改的场景ID有变动则进行校验
            if (!sysScene.getSceneId().equals(sysScene1.getSceneId())) {
                checkSceneIdUnique(sysScene);
            }
            //如果名称有变动则进行校验
            if (!sysScene.getName().equals(sysScene1.getName())) {
                checkSceneNameUnique(sysScene);
            }
        }

        List<SysSceneCase> sysSceneCases = sysScene.getCaseList();

//        SceneXmlUtils.createXml(sysScene);
//        SceneXmlUtils.createJava(sysScene,"true");
//        SceneXmlUtils.createTestngReportXml(sysScene);
//        SceneXmlUtils.createExtentReportXml(sysScene);
        if (sysSceneCases != null && !sysSceneCases.isEmpty()) {
            for (SysSceneCase sysSceneCase : sysSceneCases) {
                sysSceneCase.setStepMsg(JSON.toJSONString(sysSceneCase.getStepList()));
            }
            sysSceneCases.sort((x, y) -> Integer.compare(x.getOrder(), y.getOrder()));
            String caseMsg = JSON.toJSONString(sysSceneCases);
            sysScene.setCaseMsg(caseMsg);
        } else {
            sysScene.setCaseMsg(null);
        }
        return super.save(sysScene);
    }

    /**
     * 清空测试场景结果
     *
     * @param sysScene
     * @return
     */
    @Override
    public boolean clearResults(SysScene sysScene) {
        return sysAutomationMapper.clearResults(sysScene);
    }

    /**
     * 上传测试场景结果
     *
     * @param sysScene
     * @return
     */
    @Override
    public boolean uploadResults(SysScene sysScene) {
//        String TestRecord = String.valueOf(mapper.findList(sysScene).get(0).getTestReport());
        List<TestReport.StatisticAnalysis.UI> testReportUiList = new ArrayList<>();
        if(StringUtils.isEmpty(sysScene.getTestPlanId())){
            testReportUiList = JSON.parseArray(mapper.findList(sysScene).get(0).getDebugRecord(),TestReport.StatisticAnalysis.UI.class);
        }else{
            testReportUiList = JSON.parseArray(mapper.findList(sysScene).get(0).getTestRecord(),TestReport.StatisticAnalysis.UI.class);
        }
        if(StringUtils.isEmpty(testReportUiList)){
            throw new BizException("调试或测试记录为空！");
        }
        for (TestReport.StatisticAnalysis.UI testReport : testReportUiList){
            if(Objects.equals(testReport.getBuildNumber(), sysScene.getStatisticAnalysis().getUi().getBuildNumber())){
                Integer sceneTotal = sysScene.getStatisticAnalysis().getUi().getSceneTotal();
                Integer scenePass = sysScene.getStatisticAnalysis().getUi().getScenePass();
                Integer sceneFail = sysScene.getStatisticAnalysis().getUi().getSceneFail();
                Integer sceneSkip = sysScene.getStatisticAnalysis().getUi().getSceneSkip();
                testReport.setSceneTotal(sceneTotal);
                testReport.setScenePass(scenePass);
                testReport.setSceneFail(sceneFail);
                testReport.setSceneSkip(sceneSkip);
                String scenePassRate = NumberUtils.formatScale1(scenePass, sceneTotal ,2);
                testReport.setScenePassRate(scenePassRate);
                if (scenePass.equals(sceneTotal)){
                    testReport.setExecuteResult("全部通过");
                    sysScene.setStatus("已完成");
                }else{
                    testReport.setExecuteResult("不通过");
                    sysScene.setStatus("进行中");
                }
                testReport.setExecuteStatus("已完成");
                String durationStartTime = sysScene.getStatisticAnalysis().getUi().getDurationStartTime();
                String durationEndTime = sysScene.getStatisticAnalysis().getUi().getDurationEndTime();
                testReport.setDurationStartTime(durationStartTime);
                testReport.setDurationEndTime(durationEndTime);
                long duration = DateUtils.calculateTimeDifference(durationStartTime,durationEndTime,"yyyy-MM-dd HH:mm:ss");
                testReport.setDuration(String.valueOf(duration));

                if(StringUtils.isNotEmpty(sysScene.getTestPlanId())){
                    TestPlan testPlan = testPlanService.get(sysScene.getTestPlanId());
                    int total = Integer.parseInt(testPlan.getTestProgress());
                    int num = 1;
                    if(total-num<=0){
//                    ArrayList<String> uiTestSceneList = getUiTestSceneList(testPlan);
//                    testPlan.setTestProgress(String.valueOf(uiTestSceneList.size()));
                        testPlan.setTestProgress("0");
                        testPlan.setStatus("2");
                    }else{
                        testPlan.setTestProgress(String.valueOf(total-num));
                    }
                    testPlanService.save(testPlan);
                }

                Integer caseTotal = sysScene.getStatisticAnalysis().getUi().getCaseTotal();
                Integer casePass = sysScene.getStatisticAnalysis().getUi().getCasePass();
                Integer caseFail = sysScene.getStatisticAnalysis().getUi().getCaseFail();
                Integer caseSkip = sysScene.getStatisticAnalysis().getUi().getCaseSkip();
                testReport.setCaseTotal(caseTotal);
                testReport.setCasePass(casePass);
                testReport.setCaseFail(caseFail);
                testReport.setCaseSkip(caseSkip);
                String casePassRate = NumberUtils.formatScale1(casePass, caseTotal ,2);
                testReport.setCasePassRate(casePassRate);

                Integer stepTotal = sysScene.getStatisticAnalysis().getUi().getStepTotal();
                Integer stepPass = sysScene.getStatisticAnalysis().getUi().getStepPass();
                Integer stepFail = sysScene.getStatisticAnalysis().getUi().getStepFail();
                Integer stepSkip = sysScene.getStatisticAnalysis().getUi().getStepSkip();
                testReport.setStepTotal(stepTotal);
                testReport.setStepPass(stepPass);
                testReport.setStepFail(stepFail);
                testReport.setStepSkip(stepSkip);
                String stepPassRate = NumberUtils.formatScale1(stepPass, stepTotal ,2);
                testReport.setStepPassRate(stepPassRate);
                break;
            }
        };
        TestReport.StatisticAnalysis testReport = new TestReport.StatisticAnalysis();
        testReport.setUiList(testReportUiList);
        if(StringUtils.isEmpty(sysScene.getTestPlanId())){
            sysScene.setDebugRecord(JSON.toJSONString(testReport.getUiList()));
        }else{
            sysScene.setTestRecord(JSON.toJSONString(testReport.getUiList()));
        }
        return mapper.uploadResults(sysScene);
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

    /**
     * 更新测试计划
     *
     * @param sysScene
     * @return
     */
    @Override
    public boolean updateTestPlan(SysScene sysScene) {
        return sysAutomationMapper.updateTestPlan(sysScene);
    }

    /**
     * 复制场景信息记录（单个，多个）
     */
    @Override
    public boolean copySysScene(SceneVo sceneVo1) {
        boolean status = false;
        for(SysScene sysScene : sceneVo1.getSysSceneList()) {
            SceneVo sceneVo = new SceneVo();
            sysScene.setProjectId(sceneVo1.getProjectId());
            sysScene.setVersionId(sceneVo1.getVersionId());
            if (StringUtils.isBlank(sceneVo1.getModuleId())) {
                sceneVo.setModuleId(sysScene.getModuleId()+"_"+sceneVo1.getVersionName());
            } else {
                sceneVo.setModuleId(sceneVo1.getModuleId());
            }
            if ("保留原等级".equals(sceneVo1.getLevel())) {
                sceneVo.setLevel(sysScene.getLevel());
            } else {
                sceneVo.setLevel(sceneVo1.getLevel());
            }
            if (StringUtils.isBlank(sceneVo1.getSceneId())) {
                sceneVo.setSceneId(sysScene.getSceneId());
            } else {
                sceneVo.setSceneId(sceneVo1.getSceneId());
            }
            if (StringUtils.isBlank(sceneVo1.getName())) {
                sceneVo.setName(sysScene.getName());
            } else {
                sceneVo.setName(sceneVo1.getName());
            }
            sysScene.setLevel(sceneVo.getLevel());
            sysScene.setSceneId(sceneVo.getSceneId());
            sysScene.setName(sceneVo.getName());
            sysScene.setStatus("未开始");
            checkSceneUnique(sysScene);
            sceneVo.setId(sysScene.getId());
            sceneVo.setProjectId(sceneVo1.getProjectId());
            sceneVo.setProjectName(sceneVo1.getProjectName());
            sceneVo.setVersionId(sceneVo1.getVersionId());
            sceneVo.setVersionName(sceneVo1.getVersionName());
            sceneVo.setCopyId(IdUtils.randomUUID());
            sceneVo.setStatus("未开始");

            List<TestReport.StatisticAnalysis.UI> testRecordUiList = new ArrayList<>();
            TestReport.StatisticAnalysis.UI ui = new TestReport.StatisticAnalysis.UI();
            ui.setExecuteStatus("未开始");
            ui.setScenePassRate("-");
            ui.setExecuteResult("-");
            ui.setDuration("-");
            ui.setExecuteName("-");
            testRecordUiList.add(ui);
            sceneVo.setDebugRecord(JSON.toJSONString(testRecordUiList));
            status = sysAutomationMapper.copySysScene(sceneVo);
        }
        return status;
    }

//    public boolean copySysScene(SceneVo sceneVo1) {
//        boolean status = false;
//        for(SysScene sysScene : sceneVo1.getSysSceneList()) {
//            SceneVo sceneVo = new SceneVo();
//            sysScene.setProjectId(sceneVo1.getProjectId());
//            sysScene.setVersionId(sceneVo1.getVersionId());
//
//            SysNode sysNode = sysNodeService.get(sysScene.getModuleId());
//            String name = sysNode.getName();
//
////            sysNode.setId(IdUtils.randomUUID());
//            sysNode.setProjectId(sceneVo1.getProjectId());
//            sysNode.setVersionId(sceneVo1.getVersionId());
//            sysNode.setNewRecord(true);
//            while (sysNodeService.save(sysNode)){
//                if("0".equals(sysNode.getParentId())){
//                    break;
//                }
//                sysNode = sysNodeService.get(sysNode.getParentId());
//                if("0".equals(sysNode.getParentId())){
//                    break;
//                }
////                sysNode.setId(IdUtils.randomUUID());
//                sysNode.setProjectId(sceneVo1.getProjectId());
//                sysNode.setVersionId(sceneVo1.getVersionId());
//                sysNode.setNewRecord(true);
//            }
//            if (StringUtils.isBlank(sceneVo1.getModuleId())) {
//                sceneVo.setModuleId(sysScene.getModuleId());
//            } else {
//                sceneVo.setModuleId(sceneVo1.getModuleId());
//            }
//            if (StringUtils.isBlank(sceneVo1.getLevel())) {
//                sceneVo.setLevel(sysScene.getLevel());
//            } else {
//                sceneVo.setLevel(sceneVo1.getLevel());
//            }
//            if (StringUtils.isBlank(sceneVo1.getSceneId())) {
//                sceneVo.setSceneId(sysScene.getSceneId());
//            } else {
//                sceneVo.setSceneId(sceneVo1.getSceneId());
//            }
//            if (StringUtils.isBlank(sceneVo1.getName())) {
//                sceneVo.setName(sysScene.getName());
//            } else {
//                sceneVo.setName(sceneVo1.getName());
//            }
//            sysScene.setLevel(sceneVo.getLevel());
//            sysScene.setSceneId(sceneVo.getSceneId());
//            sysScene.setName(sceneVo.getName());
//            sysScene.setStatus("Prepare");
//            checkSceneUnique(sysScene);
//            sceneVo.setId(sysScene.getId());
//            sceneVo.setProjectId(sceneVo1.getProjectId());
//            sceneVo.setProjectName(sceneVo1.getProjectName());
//            sceneVo.setVersionId(sceneVo1.getVersionId());
//            sceneVo.setVersionName(sceneVo1.getVersionName());
//            sceneVo.setCopyId(IdUtils.randomUUID());
//            sceneVo.setStatus("Prepare");
//            status = sysAutomationMapper.copySysScene(sceneVo);
//        }
//        return status;
//    }

//    public boolean copySysScene(SceneVo sceneVo1) {
//        boolean status = false;
//        for(SysScene sysScene : sceneVo1.getSysSceneList()) {
//            SceneVo sceneVo = new SceneVo();
//            sysScene.setProjectId(sceneVo1.getProjectId());
//            sysScene.setVersionId(sceneVo1.getVersionId());
//
//            SysNode sysNode = sysNodeService.getId(sysScene.getModuleId());
//            String name = sysNode.getName();
//            if("0".equals(sysNode.getParentId())) {
//                String moduleId = sysNode.getId()+"_"+sceneVo1.getVersionName();
//                sysNode.setProjectId(sceneVo1.getProjectId());
//                sysNode.setVersionId(sceneVo1.getVersionId());
//                SysNode sysNode1 = sysNodeService.checkModuleNameUnique1(sysNode);
//                if (sysNode1!=null){
//                    if(!sysNode1.getId().equals(moduleId)){
//                        sysNodeService.remove(sysNode1);
//                        SysScene sysScene1 = new SysScene();
//                        sysScene1.setModuleId(sysNode1.getId());
//                        List<SysScene> sysSceneList = sysAutomationMapper.findList(sysScene1);
//                        for (SysScene sysScene2 : sysSceneList){
//                            sysScene2.setModuleId(moduleId);
//                            save(sysScene2);
//                        }
//                    }
//                }
//                sysNode.setId(moduleId);
//                SysNode sysNode2 = sysNodeService.get(sysNode);
//                if (sysNode2==null){
//                    sysNode.setNewRecord(true);
//                    sysNodeService.save(sysNode);
//                }
//                sysScene.setModuleId(sysNode.getId());
//            } else {
//                List<SysNode> sysNodeList = new ArrayList<>();
//                sysNode = sysNodeService.getId(sysScene.getModuleId());
//                while (!"0".equals(sysNode.getParentId())){
//                    SysNode sysNode2 = new SysNode();
//                    sysNode2.setId(sysNode.getId()+"_"+sceneVo1.getVersionName());
//                    sysNode2.setName(sysNode.getName());
//                    sysNode2.setParentId(sysNode.getParentId()+"_"+sceneVo1.getVersionName());
//                    sysNode2.setProjectId(sceneVo1.getProjectId());
//                    sysNode2.setVersionId(sceneVo1.getVersionId());
//                    SysNode sysNode3 = sysNodeService.get(sysNode2);
//                    if(sysNode3 == null){
//                        sysNodeList.add(sysNode2);
//                    }
//                    if(sysNode2.getName().equals(name)){
//                        sysScene.setModuleId(sysNode2.getId());
//                    }
//                    sysNode = sysNodeService.getId(sysNode.getParentId());
//                }
//                if(sysNodeList.size()>0){
//                    //对list集合进行逆序
//                    Collections.reverse(sysNodeList);
//                    for (int i = 0; i < sysNodeList.size() - 1; i++) {
//                        SysNode current = sysNodeList.get(i);
//                        SysNode next = sysNodeList.get(i + 1);
//                        next.setParentId(current.getId());
//                    }
//                    for (SysNode sysNode2: sysNodeList) {
//                        sysNode2.setNewRecord(true);
//                        sysNodeService.save(sysNode2);
//                    }
//                }
//            }
//            if(StringUtils.isBlank(sceneVo1.getModuleId())){
//                sceneVo.setModuleId(sysScene.getModuleId());
//            }else{
//                sceneVo.setModuleId(sceneVo1.getModuleId());
//            }
//            if(StringUtils.isBlank(sceneVo1.getLevel())){
//                sceneVo.setLevel(sysScene.getLevel());
//            }else{
//                sceneVo.setLevel(sceneVo1.getLevel());
//            }
//            if(StringUtils.isBlank(sceneVo1.getSceneId())){
//                sceneVo.setSceneId(sysScene.getSceneId());
//            }else{
//                sceneVo.setSceneId(sceneVo1.getSceneId());
//            }
//            if(StringUtils.isBlank(sceneVo1.getName())){
//                sceneVo.setName(sysScene.getName());
//            }else{
//                sceneVo.setName(sceneVo1.getName());
//            }
//            sysScene.setLevel(sceneVo.getLevel());
//            sysScene.setSceneId(sceneVo.getSceneId());
//            sysScene.setName(sceneVo.getName());
//            sysScene.setStatus("Prepare");
//            checkSceneUnique(sysScene);
//            sceneVo.setId(sysScene.getId());
//            sceneVo.setProjectId(sceneVo1.getProjectId());
//            sceneVo.setProjectName(sceneVo1.getProjectName());
//            sceneVo.setVersionId(sceneVo1.getVersionId());
//            sceneVo.setVersionName(sceneVo1.getVersionName());
//            sceneVo.setCopyId(IdUtils.randomUUID());
//            sceneVo.setStatus("Prepare");
//            status = sysAutomationMapper.copySysScene(sceneVo);
//        }
//        return status;
//    }

    /**
     * 复制场景信息记录（所有）
     */
    @Override
    public boolean copySysSceneAll(SceneVo sceneVo) {
        boolean status = false;
        for(SysScene sysScene : sceneVo.getSysSceneList()){
            sysScene.setProjectId(sceneVo.getProjectId());
            SysNode sysNode = sysNodeService.get(sysScene.getModuleId());
            String name = sysNode.getName();
            if("0".equals(sysNode.getParentId())) {
                sysNode.setProjectId(sceneVo.getProjectId());
                sysNode.setVersionId(sceneVo.getVersionId());
                sysNode = sysNodeService.getFind(sysNode);
                sysScene.setModuleId(sysNode.getId());
            } else {
                List<SysNode> sysNodeList = new ArrayList<>();
                sysNode.setProjectId(sceneVo.getProjectId());
                sysNode.setVersionId(sceneVo.getVersionId());
                sysNode.setId(IdUtils.randomUUID());
                SysNode sysNode1 = sysNodeService.getFind(sysNode);
                if(sysNode1 == null){
                    sysNodeList.add(sysNode);
                }else{
                    sysScene.setModuleId(sysNode1.getId());
                }
                do {
                    sysNode = sysNodeService.get(sysNode.getParentId());
                    sysNode.setProjectId(sceneVo.getProjectId());
                    sysNode.setVersionId(sceneVo.getVersionId());
                    SysNode sysNode2 = sysNodeService.getFind(sysNode);
                    if(sysNode2 == null){
                        if(!"全部场景".equals(sysNode.getName())){
                            sysNode.setId(IdUtils.randomUUID());
                            sysNodeList.add(sysNode);
                        }
                    }else if("全部场景".equals(sysNode.getName())){
                        sysNodeList.add(sysNode2);
                    }
                } while (!"0".equals(sysNode.getParentId()));
                if(sysNodeList.size()>1){
                    Collections.reverse(sysNodeList);
                    for (int i = 0; i < sysNodeList.size() - 1; i++) {
                        SysNode current = sysNodeList.get(i);
                        SysNode next = sysNodeList.get(i + 1);
                        next.setParentId(current.getId());
                    }
                    for (SysNode sysNode3: sysNodeList) {
                        if(sysNode3.getName().equals(name)){
                            sysScene.setModuleId(sysNode3.getId());
                        }
                        sysNode3.setNewRecord(true);
                        if(!"全部场景".equals(sysNode3.getName())){
                            sysNodeService.save(sysNode3);
                        }
                    }
                }
            }
            if(StringUtils.isBlank(sceneVo.getModuleId())){
                sceneVo.setModuleId(sysScene.getModuleId());
            }
            if(StringUtils.isBlank(sceneVo.getLevel())){
                sceneVo.setLevel(sysScene.getLevel());
            }
            sysScene.setLevel(sceneVo.getLevel());
            checkSceneUnique(sysScene);
            sceneVo.setId(sysScene.getId());
            sceneVo.setCopyId(IdUtils.randomUUID());
            sceneVo.setName(sysScene.getName());
            sceneVo.setStatus("Prepare");

            List<TestReport.StatisticAnalysis.UI> testRecordUiList = new ArrayList<>();
            TestReport.StatisticAnalysis.UI ui = new TestReport.StatisticAnalysis.UI();
            ui.setExecuteStatus("未开始");
            ui.setScenePassRate("-");
            ui.setExecuteResult("-");
            ui.setDuration("-");
            ui.setExecuteName("-");
            testRecordUiList.add(ui);
            sceneVo.setDebugRecord(JSON.toJSONString(testRecordUiList));
            status = sysAutomationMapper.copySysScene(sceneVo);
        }
        return status;
    }

    /**
     * 添加用例信息
     *
     * @param sceneCaseVo
     * @return
     */
    @Override
    public boolean addCase(SceneCaseVo sceneCaseVo) {
        String id = sceneCaseVo.getId();
        SysScene sysScene = mapper.get(id);
        String caseMsg = sysScene.getCaseMsg();
        if (!RegexUtli.isClassMethod(sceneCaseVo.getSysSceneCase().getId())) {
            throw new BizException(SysErrorCode.B_SYSSCENE_SceneCaseId_WrongFul);
        }
        if (caseMsg == null || StringUtils.isEmpty(caseMsg)) {
            List<SysSceneCase> caseList = new ArrayList<>();
            sceneCaseVo.getSysSceneCase().setOrder(1);
            caseList.add(sceneCaseVo.getSysSceneCase());
            sysScene.setCaseList(caseList);
            return save(sysScene);
        } else {
            List<SysSceneCase> sysSceneList = JSON.parseArray(caseMsg, SysSceneCase.class);
            sceneCaseVo.getSysSceneCase().setOrder(sysSceneList.size() + 1);
            sysSceneList.add(sceneCaseVo.getSysSceneCase());
            //去重后的集合
            ArrayList<SysSceneCase> collect = sysSceneList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SysSceneCase::getId))), ArrayList::new));
            if (sysSceneList.size() == collect.size()) {
                if (sceneCaseVo.getSortType() != null) {
                    if (sceneCaseVo.getSortType() == 1) {
                        DragSortUtil.swap(collect, sceneCaseVo.getSysSceneCase().getOrder() - 1, sceneCaseVo.getItemOrder() - 1);
                        log.info("交换位置排序：" + collect);
                    } else if (sceneCaseVo.getSortType() == 2) {
                        DragSortUtil.move(collect, sceneCaseVo.getSysSceneCase().getOrder() - 1, sceneCaseVo.getItemOrder() - 1);
                        log.info("移动位置排序：" + collect);
                    }
                    collect.forEach(DragSortUtil.getIndex((item, index) -> {
                        item.setOrder(index + 1);
                    }));
                }
                sysScene.setCaseList(collect);
                return save(sysScene);
            } else {
                throw new BizException(SysErrorCode.B_SYSSCENE_SceneCaseIdAlreadyExist);
            }
        }
    }

    /**
     * 删除用例信息
     *
     * @param sceneCaseVo
     * @return
     */
    @Override
    public boolean removeCase(SceneCaseVo sceneCaseVo) {
        //获取整条场景信息信息
        SysScene sysScene = mapper.get(sceneCaseVo.getId());
        //获取用例信息字符串
        String caseMsg = sysScene.getCaseMsg();
        //将用例信息转换为list集合
        List<SysSceneCase> sysSceneCaseList = JSONObject.parseArray(caseMsg, SysSceneCase.class);
        List<SysSceneCase> removeList = new ArrayList<>();
        //遍历删除指定元素
        Iterator<SysSceneCase> iterator = sysSceneCaseList.iterator();
        Integer order = 0;
        while (iterator.hasNext()) {
            SysSceneCase next = iterator.next();
            if (next.getId().equals(sceneCaseVo.getSysSceneCase().getId())) {
                removeList.add(next);
                //删除元素不是最后一位
                if (next.getOrder() != null && !next.equals(sysSceneCaseList.size())) {
                    order = next.getOrder();
                }
            }
        }
        sysSceneCaseList.removeAll(removeList);
        for (SysSceneCase sysSceneCase : sysSceneCaseList) {
            Integer caseOrder = sysSceneCase.getOrder();
            if (caseOrder > order) {
                sysSceneCase.setOrder(caseOrder - 1);
            }
        }
        sysScene.setCaseList(sysSceneCaseList);
        return save(sysScene);
    }

    /**
     * 编辑用例信息
     *
     * @param sceneCaseVo
     * @return
     */
    @Override
    public boolean editCase(SceneCaseVo sceneCaseVo) {
        //获取整条场景信息信息
        SysScene sysScene = mapper.get(sceneCaseVo.getId());
        //获取用例信息字符串
        String caseMsg = sysScene.getCaseMsg();
        //将用例信息转换为list集合
        List<SysSceneCase> sysSceneCaseList = JSONObject.parseArray(caseMsg, SysSceneCase.class);
        Iterator<SysSceneCase> iterator = sysSceneCaseList.iterator();
        while (iterator.hasNext()) {
            SysSceneCase next = iterator.next();
            if (next.getId().equals(sceneCaseVo.getSysSceneCase().getId())) {
                next.setName(sceneCaseVo.getSysSceneCase().getName());
                next.setCancel(sceneCaseVo.getSysSceneCase().getCancel());
                next.setRemark(sceneCaseVo.getSysSceneCase().getRemark());
            }
        }
        if (sceneCaseVo.getSortType()!=null) {
            if (sceneCaseVo.getSortType() == 1) {
                DragSortUtil.swap(sysSceneCaseList, sceneCaseVo.getSysSceneCase().getOrder() - 1, sceneCaseVo.getItemOrder() - 1);
                log.info("交换位置排序：" + sysSceneCaseList);
            } else if (sceneCaseVo.getSortType() == 2) {
                DragSortUtil.move(sysSceneCaseList, sceneCaseVo.getSysSceneCase().getOrder() - 1, sceneCaseVo.getItemOrder() - 1);
                log.info("移动位置排序：" + sysSceneCaseList);
            }
            sysSceneCaseList.forEach(DragSortUtil.getIndex((item, index) -> {
                item.setOrder(index + 1);
            }));
        }
        sysScene.setCaseList(sysSceneCaseList);
        return save(sysScene);
    }

    /**
     * 获取用例详细信息
     *
     * @param sceneCaseVo
     * @return
     */
    @Override
    public SysSceneCase getCase(SceneCaseVo sceneCaseVo) {
        //获取整条场景信息信息
        SysScene sysScene = mapper.get(sceneCaseVo.getId());
        //获取用例信息字符串
        String caseMsg = sysScene.getCaseMsg();
        //将用例信息转换为list集合
        List<SysSceneCase> sysSceneCaseList = JSONObject.parseArray(caseMsg, SysSceneCase.class);
        Iterator<SysSceneCase> iterator = sysSceneCaseList.iterator();
        while (iterator.hasNext()) {
            SysSceneCase next = iterator.next();
            if (next.getId().equals(sceneCaseVo.getSysSceneCase().getId())) {
                return next;
            }
        }
        throw new BizException(SysErrorCode.SYS_ERROR);
    }

    /**
     * 新增步骤
     *
     * @param stepVo
     * @return
     */
    @Override
    public boolean addStep(StepVo stepVo) {
        //获取整条场景信息信息
        SysScene sysScene = mapper.get(stepVo.getId());
        //获取用例信息字符串
        String caseMsg = sysScene.getCaseMsg();
        //将用例信息转换为list集合
        List<SysSceneCase> sysSceneCaseList = JSONObject.parseArray(caseMsg, SysSceneCase.class);
        Iterator<SysSceneCase> iterator = sysSceneCaseList.iterator();
        while (iterator.hasNext()) {
            SysSceneCase next = iterator.next();
            if (next.getId().equals(stepVo.getStep().getPid())) {
                String stepMsg1 = next.getStepMsg();
                //步骤信息为空
                if (stepMsg1.equals("null") || stepMsg1 == null || stepMsg1.isEmpty()) {
                    List<Step> stepList = new ArrayList<>();
                    stepVo.getStep().setOrder(1);
                    stepList.add(stepVo.getStep());
                    next.setStepList(stepList);
                } else {
                    List<Step> stepList = JSONObject.parseArray(stepMsg1, Step.class);
                    stepVo.getStep().setOrder(stepList.size() + 1);
                    stepList.add(stepVo.getStep());
                    if (stepVo.getSortType()!=null) {
                        if (stepVo.getSortType() == 1) {
                            DragSortUtil.swap(stepList, stepVo.getStep().getOrder() - 1, stepVo.getItemOrder() - 1);
                            log.info("交换位置排序：" + stepList);
                        } else if (stepVo.getSortType() == 2) {
                            DragSortUtil.move(stepList, stepVo.getStep().getOrder() - 1, stepVo.getItemOrder() - 1);
                            log.info("移动位置排序：" + stepList);
                        }
                        stepList.forEach(DragSortUtil.getIndex((item, index) -> {
                            item.setOrder(index + 1);
                        }));
                    }
                    next.setStepList(stepList);
                }
            }
        }
        sysScene.setCaseList(sysSceneCaseList);
        return save(sysScene);
    }

    /**
     * 删除步骤信息
     *
     * @param stepVo
     * @return
     */
    @Override
    public boolean deleteStep(StepVo stepVo) {
        //获取整条场景信息信息
        SysScene sysScene = mapper.get(stepVo.getId());
        //获取用例信息字符串
        String caseMsg = sysScene.getCaseMsg();
        //将用例信息转换为list集合
        List<SysSceneCase> sysSceneCaseList = JSONObject.parseArray(caseMsg, SysSceneCase.class);
        Iterator<SysSceneCase> iterator = sysSceneCaseList.iterator();
        Integer order = 0;
        while (iterator.hasNext()) {
            SysSceneCase next = iterator.next();
            if (next.getId().equals(stepVo.getStep().getPid())) {
                String stepMsg1 = next.getStepMsg();
                List<Step> stepList = JSONObject.parseArray(stepMsg1, Step.class);
                Iterator<Step> iterator1 = stepList.iterator();
                while (iterator1.hasNext()) {
                    Step next1 = iterator1.next();
                    if (next1.getId().equals(stepVo.getStep().getId())) {
                        iterator1.remove();
                        order = next1.getOrder();
                    }
                }
                for (Step step : stepList) {
                    Integer stepOrder = step.getOrder();
                    if (stepOrder > order) {
                        step.setOrder(stepOrder - 1);
                    }
                }
                next.setStepList(stepList);
            }
        }
        sysScene.setCaseList(sysSceneCaseList);
        return save(sysScene);
    }

    /**
     * 获取步骤
     *
     * @param stepVo
     * @return
     */
    @Override
    public Step getStep(StepVo stepVo) {
        SysScene sysScene = mapper.get(stepVo.getId());
        //获取用例信息字符串
        String caseMsg = sysScene.getCaseMsg();
        //将用例信息转换为list集合
        List<SysSceneCase> sysSceneCaseList = JSONObject.parseArray(caseMsg, SysSceneCase.class);
        Iterator<SysSceneCase> iterator = sysSceneCaseList.iterator();
        while (iterator.hasNext()) {
            SysSceneCase next = iterator.next();
            if (next.getId().equals(stepVo.getStep().getPid())) {
//                String stepMsg1 = next.getStepMsg();
//                List<Step> stepList = JSONObject.parseArray(stepMsg1, Step.class);
//                Iterator<Step> iterator1 = stepList.iterator();
                Iterator<Step> iterator1 = next.getStepList().iterator();
                while (iterator1.hasNext()) {
                    Step next1 = iterator1.next();
                    if (next1.getId().equals(stepVo.getStep().getId())) {
                        return next1;
                    }
                }
            }
        }
        throw new BizException(SysErrorCode.SYS_ERROR);
    }

    /**
     * 编辑步骤
     *
     * @param stepVo
     * @return
     */
    @Override
    public boolean editStep(StepVo stepVo) {
        //获取整条场景信息信息
        SysScene sysScene = mapper.get(stepVo.getId());
        //获取用例信息字符串
        String caseMsg = sysScene.getCaseMsg();
        //将用例信息转换为list集合
        List<SysSceneCase> sysSceneCaseList = JSONObject.parseArray(caseMsg, SysSceneCase.class);
        Iterator<SysSceneCase> iterator = sysSceneCaseList.iterator();
        while (iterator.hasNext()) {
            SysSceneCase next = iterator.next();
            if (next.getId().equals(stepVo.getStep().getPid())) {
                String stepMsg1 = next.getStepMsg();
                List<Step> stepList = JSONObject.parseArray(stepMsg1, Step.class);
                Iterator<Step> iterator1 = stepList.iterator();
                while (iterator1.hasNext()) {
                    Step next1 = iterator1.next();
                    Step step = stepVo.getStep();
                    if (next1.getId().equals(step.getId())) {
                        next1.setName(step.getName());
                        next1.setOperationType(step.getOperationType());
                        next1.setOperationName(step.getOperationName());
                        next1.setAction(step.getAction());
                        next1.setConfig(step.getConfig());
                    }
                }
                if (stepVo.getSortType()!=null) {
                    if (stepVo.getSortType() == 1) {
                        DragSortUtil.swap(stepList, stepVo.getStep().getOrder() - 1, stepVo.getItemOrder() - 1);
                        log.info("交换位置排序：" + stepList);
                    } else if (stepVo.getSortType() == 2) {
                        DragSortUtil.move(stepList, stepVo.getStep().getOrder() - 1, stepVo.getItemOrder() - 1);
                        log.info("移动位置排序：" + stepList);
                    }
                    stepList.forEach(DragSortUtil.getIndex((item, index) -> {
                        item.setOrder(index + 1);
                    }));
                }
                next.setStepList(stepList);
            }
        }
        sysScene.setCaseList(sysSceneCaseList);
        return save(sysScene);
    }

    /**
     * 拖拽用例
     *
     * @param dragVo
     * @return
     */
    @Override
    public boolean dragCase(DragVo dragVo) {
        //获取整条场景信息信息
        SysScene sysScene = mapper.get(dragVo.getId());
        //获取用例信息字符串
        String caseMsg = sysScene.getCaseMsg();
        //将用例信息转换为list集合
        List<SysSceneCase> sysSceneCaseList = JSONObject.parseArray(caseMsg, SysSceneCase.class);

        if (dragVo.getSortType()==0){
            DragSortUtil.swap(sysSceneCaseList, dragVo.getSysSceneCase().getOrder()-1, dragVo.getItemOrder()-1);
            System.out.println("交换位置排序：" + sysSceneCaseList);
        }else{
            DragSortUtil.move(sysSceneCaseList, dragVo.getSysSceneCase().getOrder()-1, dragVo.getItemOrder()-1);
            System.out.println("移动位置排序：" + sysSceneCaseList);
        }
        sysSceneCaseList.forEach(DragSortUtil.getIndex((item,index)->{
            item.setOrder(index+1);
        }));

//        Iterator<SysSceneCase> iterator = sysSceneCaseList.iterator();
//        Integer order = 0;
//        while (iterator.hasNext()) {
//            SysSceneCase next = iterator.next();
//            Step step = dragVo.getStep();
//            if (next.getOrder() <= dragVo.getItemOrder()) {
//                if (next.getId().equals(step.getPid())) {
//                    next.setOrder(dragVo.getItemOrder());
//                } else if (next.getOrder() > 1 ) {
//                    next.setOrder(next.getOrder() - 1);
//                }
//            }
//        }
//        sysSceneCaseList.sort((x, y)->Integer.compare(x.getOrder(),y.getOrder()));

        sysScene.setCaseList(sysSceneCaseList);
        return save(sysScene);
    }

    /**
     * 拖拽步骤
     *
     * @param dragVo
     * @return
     */
    @Override
    public boolean dragStep(DragVo dragVo) {
        //获取整条场景信息信息
        SysScene sysScene = mapper.get(dragVo.getId());
        //获取用例信息字符串
        String caseMsg = sysScene.getCaseMsg();
        //将用例信息转换为list集合
        List<SysSceneCase> sysSceneCaseList = JSONObject.parseArray(caseMsg, SysSceneCase.class);
        Iterator<SysSceneCase> iterator = sysSceneCaseList.iterator();
        Integer order = 0;
        while (iterator.hasNext()) {
            SysSceneCase next = iterator.next();
            if (next.getId().equals(dragVo.getStep().getPid())) {
                String stepMsg1 = next.getStepMsg();
                List<Step> stepList = JSONObject.parseArray(stepMsg1, Step.class);
                Iterator<Step> iterator1 = stepList.iterator();
                while (iterator1.hasNext()) {
                    Step next1 = iterator1.next();
                    Step step = dragVo.getStep();
                    if (step.getOrder() > dragVo.getItemOrder()) {
                        if (next1.getOrder() < step.getOrder() && next1.getOrder() >= dragVo.getItemOrder() && !step.getOrder().equals(dragVo.getItemOrder()) && !next1.getId().equals(step.getId())) {
                            next1.setOrder(next1.getOrder() + 1);
                        } else if (next1.getId().equals(step.getId())) {
                            next1.setOrder(dragVo.getItemOrder());
                        }
                    } else if (step.getOrder() < dragVo.getItemOrder()) {
                        if (step.getOrder() < next1.getOrder() && next1.getOrder() <= dragVo.getItemOrder() && !step.getOrder().equals(dragVo.getItemOrder()) && !next1.getId().equals(step.getId())) {
                            next1.setOrder(next1.getOrder() - 1);
                        } else if (next1.getId().equals(step.getId())) {
                            next1.setOrder(dragVo.getItemOrder());
                        }
                    }
                }
                stepList.sort((x, y) -> Integer.compare(x.getOrder(), y.getOrder()));
                next.setStepList(stepList);
            }
        }
        sysScene.setCaseList(sysSceneCaseList);
        return save(sysScene);
    }

    /**
     * 编辑前置
     *
     * @param id
     * @return
     */
    @Override
    public List<SysSceneCase> editPre(String id) {
        SysScene sysScene = mapper.get(id);
        String caseMsg = sysScene.getCaseMsg();
        if (caseMsg != null && !caseMsg.isEmpty()) {
            List<SysSceneCase> sysSceneCaseList = JSONObject.parseArray(caseMsg, SysSceneCase.class);
            return sysSceneCaseList;
        }
        return null;
    }

    /**
     * 校验唯一性
     *
     * @param sysScene
     */
    public void checkSceneUnique(SysScene sysScene) {
        checkSceneIdUnique(sysScene);
        checkSceneNameUnique(sysScene);
    }

    /**
     * 校验场景ID唯一性
     *
     * @param sysScene
     */
    public void checkSceneIdUnique(SysScene sysScene) {
        SysScene sysSceneUnique = new SysScene();
        sysSceneUnique.setNotEqualId(sysScene.getId());
        sysSceneUnique.setProjectId(sysScene.getProjectId());
//        sysSceneUnique.setModuleId(sysScene.getModuleId());
        sysSceneUnique.setVersionId(sysScene.getVersionId());
        sysSceneUnique.setLevel(sysScene.getLevel());
        sysSceneUnique.setSceneId(sysScene.getSceneId());
        if (!CollectionUtils.isEmpty(mapper.findListWithUnique(sysSceneUnique))) {
            throw new BizException("该场景版本等级下，场景ID【"+ sysScene.getSceneId() +"】已存在");
        }
    }

    /**
     * 校验场景名称唯一性
     *
     * @param sysScene
     */
    public void checkSceneNameUnique(SysScene sysScene) {
        SysScene sysSceneUnique = new SysScene();
        sysSceneUnique.setProjectId(sysScene.getProjectId());
        sysSceneUnique.setModuleId(sysScene.getModuleId());
        sysSceneUnique.setVersionId(sysScene.getVersionId());
        sysSceneUnique.setLevel(sysScene.getLevel());
        sysSceneUnique.setName(sysScene.getName());
        if (!CollectionUtils.isEmpty(mapper.findListWithUnique(sysSceneUnique))) {
            throw new BizException("该场景版本等级下，场景名称【"+ sysScene.getName() +"】已存在");
        }
    }

    /**
     * 校验场景和用例ID合法性
     *
     * @param sysScene
     */
    public void checkID(SysScene sysScene) {
        if (!RegexUtli.isClassMethod(sysScene.getSceneId())) {
            throw new BizException(SysErrorCode.B_SYSSCENE_SceneId_WrongFul);
        }
    }

    public String getProjectPath(String projectId) {
        String name = "自动化项目路径";
        String path = sysConfigurationMapper.getPath(name);
        SysProject sysProject = sysProjectMapper.get(projectId);
        String description = sysProject.getDescription();
        String latestVersion = sysProject.getLatestVersion();
        StringBuilder stringBuilder = new StringBuilder();
        String append = stringBuilder.append(path).append("\\").append(description).append("\\").append(latestVersion).toString();
        return append;
    }

    public static void main(String[] args) {
//        SysAutomationServiceImpl sysAutomationService = new SysAutomationServiceImpl();
//        sysAutomationService.getProjectPath("28349927d1b74a5ba0070832756471b3");

        String passRate = NumberUtils.formatScale(1,3, 2);
        String passRate1 = NumberUtils.formatScale1(1,3, 2);
        System.out.println(passRate);
        System.out.println(passRate1);
    }
}
