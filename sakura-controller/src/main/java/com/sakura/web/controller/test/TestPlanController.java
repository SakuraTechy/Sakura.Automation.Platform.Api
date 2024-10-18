package com.sakura.web.controller.test;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.sakura.common.annotation.Log;
import com.sakura.common.core.domain.JsonResult;
import com.sakura.common.core.domain.R;
import com.sakura.common.utils.StringUtils;
import com.sakura.project.domain.ExecuteConfig;
import com.sakura.system.domain.SysScene;
import com.sakura.system.service.SysAutomationService;
import com.sakura.test.domain.TestReport;
import com.github.pagehelper.PageInfo;
import com.sakura.common.core.page.PageDomain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sakura.common.enums.BusinessType;
import com.sakura.common.utils.poi.ExcelUtil;
import com.sakura.framework.cache.DictUtils;
import org.springframework.web.bind.annotation.*;
import com.sakura.common.core.controller.BaseController;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import com.sakura.test.domain.TestPlan;
import com.sakura.test.service.TestPlanService;

/**
 * 测试管理-测试计划表Controller
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2023-11-02
 */
@RestController
@RequestMapping("/test/testPlan")
public class TestPlanController extends BaseController {

    @Autowired
    private TestPlanService testPlanService;

    @Autowired
    private SysAutomationService sysAutomationService;

    /**
     * 查询测试管理-测试计划表列表
     */
    @PreAuthorize("@ss.hasPermi('test:testPlan:list')")
    @GetMapping("/list")
    public R<PageInfo> list(TestPlan testPlan, HttpServletRequest request, HttpServletResponse response) {
        testPlan.setPage(new PageDomain(request, response));
        return R.data(testPlanService.findPage(testPlan));
    }

    /**
     * 获取测试管理-测试计划表详细信息
     */
    @PreAuthorize("@ss.hasPermi('test:testPlan:query')")
    @GetMapping(value = "/{id}")
    public R<TestPlan> detail(@PathVariable("id") String id) {
        return R.data(testPlanService.get(id));
    }

    /**
     * 新增测试管理-测试计划表
     */
    @PreAuthorize("@ss.hasPermi('test:testPlan:add')")
    @Log(title = "测试管理-测试计划表", businessType = BusinessType.INSERT)
    @PostMapping
    public R add(@RequestBody @Validated  TestPlan testPlan) {
        return R.status(testPlanService.save(testPlan));
    }

    /**
     * 修改测试管理-测试计划表
     */
    @PreAuthorize("@ss.hasPermi('test:testPlan:edit')")
    @Log(title = "测试管理-测试计划表", businessType = BusinessType.UPDATE)
    @PutMapping
    public R edit(@RequestBody @Validated TestPlan testPlan) {
        return R.status(testPlanService.save(testPlan));
    }

    /**
     * 删除测试管理-测试计划表
     */
    @PreAuthorize("@ss.hasPermi('test:testPlan:remove')")
    @Log(title = "测试管理-测试计划表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R remove(@PathVariable String[] ids) {
        return R.status(testPlanService.deleteTestPlanByIds(ids));
    }

    /**
     * 删除测试管理-测试计划表
     */
    @PreAuthorize("@ss.hasPermi('project:projectConfig:edit')")
    @Log(title = "删除测试管理-测试计划表", businessType = BusinessType.UPDATE)
    @PutMapping("/remove")
    public R remove(@RequestBody TestPlan testPlan) {
        return R.status(testPlanService.remove(testPlan));
    }

    /**
     * 导出测试管理-测试计划表列表
     */
    @PreAuthorize("@ss.hasPermi('test:testPlan:export')")
    @Log(title = "测试管理-测试计划表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R export(TestPlan testPlan) {
        List<TestPlan> list = testPlanService.findList(testPlan);
        ExcelUtil<TestPlan> util = new ExcelUtil<TestPlan>(TestPlan.class);
        return util.exportExcel(list, "测试管理-测试计划表数据");
    }

//    /**
//     * 查询测试场景列表
//     */
//    @PreAuthorize("@ss.hasPermi('test:testScene:list')")
//    @PostMapping("/getTestScene")
//    public R getTestScene(@RequestBody @Validated TestPlan testPlan, HttpServletRequest request, HttpServletResponse response) {
//        PageDomain page = new PageDomain();
//        page.setPageNum(sysScene.getPageNum());
//        page.setPageSize(sysScene.getPageSize());
//        sysScene.setPage(page);
//        return JsonResult.success(sysAutomationService.findPage(sysScene));
//    }

    /**
     * 添加测试场景信息（关联测试场景）
     */
    @PreAuthorize("@ss.hasPermi('test:testPlan:addTestScene')")
    @PostMapping("/addTestScene")
    public R addTestScene(@RequestBody @Validated ExecuteConfig executeConfig, HttpServletRequest request, HttpServletResponse response) {
        List<String> sceneIdList = executeConfig.getSceneIdList();
        if (!sceneIdList.isEmpty()) {
            TestPlan testPlan = testPlanService.get(executeConfig.getTestPlanId());
            ArrayList<String> uiTestSceneList = getUiTestSceneList(testPlan);
            for (String sceneId : sceneIdList){
                if(!uiTestSceneList.contains(sceneId)){
                    uiTestSceneList.add(sceneId);
                }
                ArrayList<String> testPlanList =  new ArrayList<>();
                SysScene sysScene = sysAutomationService.get(sceneId);
                String TestPlanIdStr = sysScene.getTestPlanId();
                if (StringUtils.isNotEmpty(TestPlanIdStr)&&!StringUtils.equals(TestPlanIdStr,"[]")){
                    TestPlanIdStr = TestPlanIdStr.substring(1, TestPlanIdStr.length() - 1);
                    testPlanList = new ArrayList<>(Arrays.asList(TestPlanIdStr.split(", ")));
                }
                if(!testPlanList.contains(executeConfig.getTestPlanId())){
                    testPlanList.add(executeConfig.getTestPlanId());
                }
                sysScene.setTestPlanId(testPlanList.toString());
                List<TestReport.StatisticAnalysis.UI> testRecordUiList = new ArrayList<>();
                if (StringUtils.isNotEmpty(sysScene.getTestRecord())) {
                    testRecordUiList = JSON.parseArray(sysScene.getTestRecord(), TestReport.StatisticAnalysis.UI.class);
                }
                TestReport.StatisticAnalysis.UI ui = new TestReport.StatisticAnalysis.UI();
                ui.setTestPlanId(executeConfig.getTestPlanId());
                ui.setExecuteStatus("未开始");
                ui.setScenePassRate("-");
                ui.setExecuteResult("-");
                ui.setDuration("-");
                ui.setExecuteName("-");
                testRecordUiList.add(ui);
                sysScene.setTestRecord(JSON.toJSONString(testRecordUiList));
                sysAutomationService.updateTestPlan(sysScene);
            }
            testPlan.setUiTestScene(uiTestSceneList.toString());
            int total = uiTestSceneList.size();
            int num = Integer.parseInt(testPlan.getTestProgress());
            if(num==0){
                testPlan.setTestProgress(String.valueOf(sceneIdList.size()));
            }else{
                testPlan.setTestProgress(String.valueOf(num+sceneIdList.size()));
            }
//            testPlan.setStatus("1");
            return R.status(testPlanService.save(testPlan));
        }
        return null;
    }

    public R addTestScene1(@RequestBody @Validated ExecuteConfig executeConfig, HttpServletRequest request, HttpServletResponse response) {
        List<SysScene> sysSceneList = executeConfig.getSysSceneList();
        if (!sysSceneList.isEmpty()) {
            TestPlan testPlan = testPlanService.get(executeConfig.getTestPlanId());
            ArrayList<String> uiTestSceneList = getUiTestSceneList(testPlan);
            for (SysScene sysScene : sysSceneList){
                if(!uiTestSceneList.contains(sysScene.getId())){
                    uiTestSceneList.add(sysScene.getId());
                }
                ArrayList<String> testPlanList =  new ArrayList<>();
                String TestPlanIdStr = sysScene.getTestPlanId();
                if (StringUtils.isNotEmpty(TestPlanIdStr)&&!StringUtils.equals(TestPlanIdStr,"[]")){
                    TestPlanIdStr = TestPlanIdStr.substring(1, TestPlanIdStr.length() - 1);
                    testPlanList = new ArrayList<>(Arrays.asList(TestPlanIdStr.split(", ")));
                }
                if(!testPlanList.contains(executeConfig.getTestPlanId())){
                    testPlanList.add(executeConfig.getTestPlanId());
                }
                sysScene.setTestPlanId(testPlanList.toString());
                sysAutomationService.updateTestPlan(sysScene);
            }
            testPlan.setUiTestScene(uiTestSceneList.toString());
            int total = uiTestSceneList.size();
            int num = Integer.parseInt(testPlan.getTestProgress());
            if(num==0){
                testPlan.setTestProgress(String.valueOf(sysSceneList.size()));
            }else{
                testPlan.setTestProgress(String.valueOf(num+sysSceneList.size()));
            }
//            testPlan.setStatus("1");
            return R.status(testPlanService.save(testPlan));
        }
        return null;
    }

    /**
     * 删除测试场景信息（取消关联测试场景）
     */
    @PreAuthorize("@ss.hasPermi('test:testPlan:removeTestScene')")
    @PostMapping("/removeTestScene")
    public R removeTestScene(@RequestBody @Validated ExecuteConfig executeConfig, HttpServletRequest request, HttpServletResponse response) {
        List<String> sceneIdList = executeConfig.getSceneIdList();
        if (!sceneIdList.isEmpty()) {
            TestPlan testPlan = testPlanService.get(executeConfig.getTestPlanId());
            int total = Integer.parseInt(testPlan.getTestProgress());
            for (String sceneId : sceneIdList){
                ArrayList<String> testPlanList;
                SysScene sysScene = sysAutomationService.get(sceneId);
                String TestPlanIdStr = sysScene.getTestPlanId();
                if (StringUtils.isNotEmpty(TestPlanIdStr)&&!StringUtils.equals(TestPlanIdStr,"[]")){
                    TestPlanIdStr = TestPlanIdStr.substring(1, TestPlanIdStr.length() - 1);
                    testPlanList = new ArrayList<>(Arrays.asList(TestPlanIdStr.split(", ")));
                    testPlanList.remove(executeConfig.getTestPlanId());
                    sysScene.setTestPlanId(testPlanList.toString());
                }

                ArrayList<String> uiTestSceneList = getUiTestSceneList(testPlan);
                if (!uiTestSceneList.isEmpty()) {
                    if (StringUtils.isNotEmpty(sysScene.getTestRecord())) {
                        List<TestReport.StatisticAnalysis.UI> testRecordUiList = JSON.parseArray(sysScene.getTestRecord(), TestReport.StatisticAnalysis.UI.class);
                        if(!testRecordUiList.isEmpty()){
//                            testRecordUiList.sort((x, y) -> y.getBuildNumber().compareTo(x.getBuildNumber()));
                            testRecordUiList.sort((x, y) -> {
                                Optional<String> buildNumberX = Optional.ofNullable(String.valueOf(x.getBuildNumber()));
                                Optional<String> buildNumberY = Optional.ofNullable(String.valueOf(y.getBuildNumber()));
                                return buildNumberX.map(xValue -> buildNumberY.map(xValue::compareTo)
                                                .orElse(-1))
                                        .orElseGet(() -> buildNumberY.map(yValue -> 1).orElse(0));
                            });
                            for (TestReport.StatisticAnalysis.UI testRecord : testRecordUiList) {
                                if (testRecord.getTestPlanId().equals(executeConfig.getTestPlanId()) && !testRecord.getExecuteStatus().equals("已完成")) {
                                    testPlan.setTestProgress(String.valueOf(total - sceneIdList.size()));
                                    break;
                                }
                            }
//                            if (testRecordUiList.removeIf(record -> record.getTestPlanId().equals(executeConfig.getTestPlanId()))) {
//                                sysScene.setTestRecord(JSON.toJSONString(testRecordUiList));
//                            }
                        } else {
                            testPlan.setTestProgress(String.valueOf(total - sceneIdList.size()));
                        }
                    } else {
                        testPlan.setTestProgress(String.valueOf(total - sceneIdList.size()));
                    }
                    if (testPlan.getTestProgress().equals("0")) {
                        testPlan.setStatus("2");
                    }
                }
                uiTestSceneList.remove(sceneId);
                if (uiTestSceneList.isEmpty()) {
                    testPlan.setTestProgress("0");
                    testPlan.setStatus("0");
                }
                testPlan.setUiTestScene(uiTestSceneList.toString());
                testPlanService.save(testPlan);
                sysAutomationService.updateTestPlan(sysScene);
            }
        }
        return null;
    }

    public R removeTestScene1(@RequestBody @Validated ExecuteConfig executeConfig, HttpServletRequest request, HttpServletResponse response) {
        List<SysScene> sysSceneList = executeConfig.getSysSceneList();
        if (!sysSceneList.isEmpty()) {
            TestPlan testPlan = testPlanService.get(executeConfig.getTestPlanId());
            int total = Integer.parseInt(testPlan.getTestProgress());
            for (SysScene sysScene : sysSceneList){
                ArrayList<String> uiTestSceneList = getUiTestSceneList(testPlan);
                uiTestSceneList.remove(sysScene.getId());
                testPlan.setUiTestScene(uiTestSceneList.toString());

                ArrayList<String> testPlanList;
                String  TestPlanIdStr = sysScene.getTestPlanId();
                if (StringUtils.isNotEmpty(TestPlanIdStr)&&!StringUtils.equals(TestPlanIdStr,"[]")){
                    TestPlanIdStr = TestPlanIdStr.substring(1, TestPlanIdStr.length() - 1);
                    testPlanList = new ArrayList<>(Arrays.asList(TestPlanIdStr.split(", ")));
                    testPlanList.remove(executeConfig.getTestPlanId());
                    sysScene.setTestPlanId(testPlanList.toString());
                }

                if (!uiTestSceneList.isEmpty()) {
                    if (StringUtils.isNotEmpty(sysScene.getTestRecord())) {
                        List<TestReport.StatisticAnalysis.UI> testRecordUiList = JSON.parseArray(sysScene.getTestRecord(), TestReport.StatisticAnalysis.UI.class);
                        testRecordUiList.sort((x, y) -> y.getBuildNumber().compareTo(x.getBuildNumber()));
                        if(!testRecordUiList.isEmpty()){
                            for (TestReport.StatisticAnalysis.UI testRecord : testRecordUiList) {
                                if (testRecord.getTestPlanId().equals(executeConfig.getTestPlanId()) && !testRecord.getExecuteStatus().equals("已完成")) {
                                    testPlan.setTestProgress(String.valueOf(total - sysSceneList.size()));
                                    break;
                                }
                            }
                            if (testRecordUiList.removeIf(record -> record.getTestPlanId().equals(executeConfig.getTestPlanId()))) {
                                sysScene.setTestRecord(JSON.toJSONString(testRecordUiList));
                            }
                        } else {
                            testPlan.setTestProgress(String.valueOf(total - sysSceneList.size()));
                        }
                    } else {
                        testPlan.setTestProgress(String.valueOf(total - sysSceneList.size()));
                    }
                    if (testPlan.getTestProgress().equals("0")) {
                        testPlan.setStatus("2");
                    }
                } else {
                    testPlan.setTestProgress("0");
                    testPlan.setStatus("0");
                }
                testPlanService.save(testPlan);
                sysAutomationService.updateTestPlan(sysScene);
            }
        }
        return null;
    }

    private static ArrayList<String> getUiTestSceneList(TestPlan testPlan) {
        String  uiTestSceneStr = testPlan.getUiTestScene();
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
     * 根据字典类型查询字典数据信息等其他自定义信息
     */
    @GetMapping(value = "/getInitData/{dictTypes}")
    public R getInitData(@PathVariable String dictTypes) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.putAll(DictUtils.getMultiDictList(dictTypes));
        return R.data(dataMap);
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            list.add(i);
        }
        int pageSize = 20; // 每页显示的数据条数
        int currentPage = 2; // 当前页数
        List<Integer> pageData = getPageData(list,currentPage,pageSize);
        System.out.println("第" + currentPage + "页数据：" + pageData);
    }

    public static List<Integer> getPageData(List<Integer> list, int currentPage, int pageSize) {
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());
        return list.subList(startIndex, endIndex);
    }
}
