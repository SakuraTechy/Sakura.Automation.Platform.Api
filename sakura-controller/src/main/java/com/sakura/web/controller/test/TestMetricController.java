package com.sakura.web.controller.test;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.sakura.common.core.controller.BaseController;
import com.sakura.common.core.domain.R;
import com.sakura.common.exception.BizException;
import com.sakura.common.utils.NumberUtils;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.date.DateUtils;
import com.sakura.system.common.SysErrorCode;
import com.sakura.system.domain.SysAutomation;
import com.sakura.system.domain.SysNode;
import com.sakura.system.domain.SysScene;
import com.sakura.system.service.SysAutomationService;
import com.sakura.system.service.SysNodeService;
import com.sakura.test.domain.TestMetric;
import com.sakura.test.domain.TestReport;
import com.sakura.test.service.TestPlanService;
import com.sakura.test.service.TestReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 测试管理-测试度量Controller
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2024-01-24
 */
@RestController
@RequestMapping("/test/testMetric")
public class TestMetricController extends BaseController {

    @Autowired
    private SysNodeService sysNodeService;

    @Autowired
    private TestPlanService testPlanService;

    @Autowired
    private TestReportService testReportService;

    @Autowired
    private SysAutomationService sysAutomationService;

    int total=0;
    /**
     * 查询测试管理-测试计划表列表
     */
    @PreAuthorize("@ss.hasPermi('test:testMetric:list')")
    @PostMapping("/list")
    public R list(@RequestBody @Validated TestMetric testMetric, HttpServletRequest request, HttpServletResponse response) {
        String[] timeTypeList =  {"历史","本周","本月","本年"};
        String[] customTime = new String[2];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SysNode sysNode = new SysNode();
        sysNode.setProjectId(testMetric.getUi().getProjectId());
        sysNode.setVersionId(testMetric.getUi().getVersionId());
        sysNode.setParentId(testMetric.getUi().getParentId());
        List<SysNode> sysNodeList = sysNodeService.findList(sysNode);
        sysNode.setParentId("");
        List<SysNode> sysNodeAllList = sysNodeService.findList(sysNode);
        TestMetric testMetric1 = new TestMetric();
        TestMetric.UI ui = new TestMetric.UI();
        ui.setProjectId(testMetric.getUi().getProjectId());
        ui.setVersionId(testMetric.getUi().getVersionId());
        ui.setParentId(testMetric.getUi().getParentId());
        TestMetric.UI.FunctionalModule uiFunctionalModule = new TestMetric.UI.FunctionalModule();
        TestMetric.UI.SeriesData seriesData;
        List<TestMetric.UI.SeriesData> seriesDataList = new ArrayList<>();
        TestMetric.CreatedInCustom createdInCustom;
        TestMetric.CreatedInCustom createdInCustomAll = new TestMetric.CreatedInCustom();
        int totalAll = 0;
//        SysNode sysNode = testMetric.getSysNode();
//        int createdInWeek = 0;
//        if(sysNode.getTimeRangeType().equals("本周")) {
//            createdInWeek = getSysNodeCustomTotal(sysNode,getWeekStartAndEnd());
//        }
//        for (SysNode sysNode1 : sysNodeList) {
//            module = new TestMetric.UI.UIFunctionalModule.Module();
//            module.setName(sysNode1.getName());
//            sysNode.setParentId(sysNode1.getId());
//            int total = getSysNodeTotal(sysNode);
//            module.setTotal(total);
//            totalAll += total;
//            moduleList.add(module);
//        };

//        int createdInCustomAll = 0;
//        int createdInCustom;
//        for (SysNode sysNode1 : sysNodeList) {
//            createdInCustom = 0;
//            module = new TestMetric.UI.UIFunctionalModule.Module();
//            module.setName(sysNode1.getName());
//            List<SysNode> children = getChildren(sysNodeAllList, sysNode1.getId());
//            for (SysNode node : children) {
//                if(testMetric.getTimeRangeType().equals("本周")) {
//                    customTime = getDateRange("week");
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    if(isInTimeRange(customTime[0],customTime[1], sdf.format(node.getCreateTime()))){
//                        createdInCustom++;
//                    }
//                }
//            }
//            int total = children.size();
//            module.setTotal(total);
//            totalAll += total;
//            moduleList.add(module);
//            module.setCreatedInCustom(createdInCustom);
//            createdInCustomAll += createdInCustom;
//        };
//        UIFunctionalModule.setTotalAll(totalAll);
//        UIFunctionalModule.setModuleList(moduleList);
//        UIFunctionalModule.setCreatedInCustomAll(createdInCustomAll);
//        testMetric1.setSysNode(sysNode);
//        testMetric1.setUIFunctionalModule(UIFunctionalModule);

        int weekAll = 0;
        int monthAll = 0;
        int yearAll = 0;
        int total;
        int week;
        int month;
        int year;
        for (SysNode sysNode1 : sysNodeList) {
            week = 0;
            month = 0;
            year = 0;
            seriesData = new TestMetric.UI.SeriesData();
            createdInCustom = new TestMetric.CreatedInCustom();
            List<SysNode> children = getChildren(sysNodeAllList, sysNode1.getId());
            total = children.size();
            for (String timeType : timeTypeList){
                switch (timeType) {
                    case "历史":

                        break;
                    case "本周":
                        for (SysNode node : children) {
                            customTime = getDateRange("week");
                            if (isInTimeRange(customTime[0], customTime[1], sdf.format(node.getCreateTime()))) {
                                week++;
                            }
                        }
                        break;
                    case "本月":
                        for (SysNode node : children) {
                            customTime = getDateRange("month");
                            if (isInTimeRange(customTime[0], customTime[1], sdf.format(node.getCreateTime()))) {
                                month++;
                            }
                        }
                        break;
                    case "本年":
                        for (SysNode node : children) {
                            customTime = getDateRange("year");
                            if (isInTimeRange(customTime[0], customTime[1], sdf.format(node.getCreateTime()))) {
                                year++;
                            }
                        }
                        break;
                }
                createdInCustom.setWeek(week);
                createdInCustom.setMonth(month);
                createdInCustom.setYear(year);
            }
            seriesData.setName(sysNode1.getName());
            seriesData.setTotal(total);
            seriesData.setCreatedInCustom(createdInCustom);
            seriesDataList.add(seriesData);
            totalAll += total;
            weekAll += week;
            monthAll += month;
            yearAll += year;
        }
        uiFunctionalModule.setTotalAll(totalAll);
        createdInCustomAll.setWeek(weekAll);
        createdInCustomAll.setMonth(monthAll);
        createdInCustomAll.setYear(yearAll);
        uiFunctionalModule.setCreatedInCustomAll(createdInCustomAll);
        uiFunctionalModule.setSeriesDataList(seriesDataList);
        ui.setFunctionalModule(uiFunctionalModule);
        
//        TestMetric.UI.AutomationScene UI.AutomationScene = new TestMetric.UI.AutomationScene();
//        TestMetric.UI.AutomationScene.Level level;
//        List<TestMetric.UI.AutomationScene.Level> levelList = new ArrayList<>();
//        SysScene sysScene = testMetric.getSysScene();
//        Map<String,String> levelMap = new HashMap<>();
//        levelMap.put("P0","（冒烟）");
//        levelMap.put("P1","（核心）");
//        levelMap.put("P2","（全量）");
//        levelMap.put("P3","（其他）");
//        totalAll = 0;
//        createdInCustomAll = 0;
//        int millisecondAll = 0;
//        for (Map.Entry<String, String> entry : levelMap.entrySet()){
//            int total = 0;
//            createdInCustom = 0;
//            int millisecond = 0;
//            sysScene.setLevel(entry.getKey());
//            List<SysScene> sysSceneList = sysAutomationService.findList(sysScene);
//            for (SysScene sysScene1 : sysSceneList){
//                if(sysScene1.getLevel().equals(entry.getKey())){
//                    total++;
//                }
//                if(testMetric.getTimeRangeType().equals("本周")) {
//                    customTime = getDateRange("week");
//                    if(isInTimeRange(customTime[0],customTime[1], sdf.format(sysScene1.getCreateTime()))){
//                        createdInCustom++;
//                    }
////                    millisecond += sysScene1.getTestRecord().getTotalTime();
//                }
//            }
//            level = new TestMetric.UI.AutomationScene.Level();
//            level.setName(entry.getKey()+entry.getValue());
//            level.setCreatedInCustom(createdInCustom);
//            level.setTotal(total);
//            levelList.add(level);
//            totalAll += sysSceneList.size();;
//            createdInCustomAll += createdInCustom;
//        }
//        UI.AutomationScene.setTotalAll(totalAll);
//        UI.AutomationScene.setLevelList(levelList);
//        UI.AutomationScene.setCreatedInCustomAll(createdInCustomAll);
//        testMetric1.setUI.AutomationScene(UI.AutomationScene);

        seriesDataList = new ArrayList<>();
        TestMetric.UI.AutomationScene uiAutomationScene = new TestMetric.UI.AutomationScene();
        SysScene sysScene = new SysScene();
        sysScene.setProjectId(testMetric.getUi().getProjectId());
        sysScene.setVersionId(testMetric.getUi().getVersionId());
        Map<String,String> levelMap = new HashMap<>();
        levelMap.put("P0","（冒烟）");
        levelMap.put("P1","（核心）");
        levelMap.put("P2","（全量）");
        levelMap.put("P3","（其他）");
        totalAll = 0;
        weekAll = 0;
        monthAll = 0;
        yearAll = 0;
        for (Map.Entry<String, String> entry : levelMap.entrySet()){
            week = 0;
            month = 0;
            year = 0;
            createdInCustom = new TestMetric.CreatedInCustom();
            sysScene.setLevel(entry.getKey());
            List<SysScene> sysSceneList = sysAutomationService.findList(sysScene);
            total = sysSceneList.size();
            for (String timeType : timeTypeList){
                switch (timeType) {
                    case "历史":

                        break;
                    case "本周":
                        for (SysScene sysScene1 : sysSceneList) {
                            customTime = getDateRange("week");
                            if (isInTimeRange(customTime[0], customTime[1], sdf.format(sysScene1.getCreateTime()))) {
                                week++;
                            }
                        }
                        break;
                    case "本月":
                        for (SysScene sysScene1 : sysSceneList) {
                            customTime = getDateRange("month");
                            if (isInTimeRange(customTime[0], customTime[1], sdf.format(sysScene1.getCreateTime()))) {
                                month++;
                            }
                        }
                        break;
                    case "本年":
                        for (SysScene sysScene1 : sysSceneList) {
                            customTime = getDateRange("year");
                            if (isInTimeRange(customTime[0], customTime[1], sdf.format(sysScene1.getCreateTime()))) {
                                year++;
                            }
                        }
                        break;
                }
                createdInCustom.setWeek(week);
                createdInCustom.setMonth(month);
                createdInCustom.setYear(year);
            }
            seriesData = new TestMetric.UI.SeriesData();
            seriesData.setName(entry.getKey()+entry.getValue());
            seriesData.setCreatedInCustom(createdInCustom);
            seriesData.setTotal(total);
            seriesDataList.add(seriesData);
            totalAll += total;
            weekAll += week;
            monthAll += month;
            yearAll += year;
        }
        uiAutomationScene.setTotalAll(totalAll);
        createdInCustomAll.setWeek(weekAll);
        createdInCustomAll.setMonth(monthAll);
        createdInCustomAll.setYear(yearAll);
        uiAutomationScene.setCreatedInCustomAll(createdInCustomAll);
        uiAutomationScene.setSeriesDataList(seriesDataList);
        ui.setAutomationScene(uiAutomationScene);

        TestMetric.UI.AutomationExecute uiAutomationExecute = new TestMetric.UI.AutomationExecute();
        TestMetric.UI.AutomationExecute.RunInCustom runInCustom;
        List<TestMetric.UI.AutomationExecute.RunInCustom> runInCustomList = new ArrayList<>();
        TestMetric.UI.AutomationExecute.ExeInCustom exeInCustom;
        List<TestMetric.UI.AutomationExecute.ExeInCustom> exeInCustomList = new ArrayList<>();
        TestMetric.UI.AutomationExecute.DefectCustom defectCustom;
        List<TestMetric.UI.AutomationExecute.DefectCustom> defectCustomList = new ArrayList<>();
        TestMetric.UI.AutomationExecute.LabInCustom labInCustom;
        List<TestMetric.UI.AutomationExecute.LabInCustom> labInCustomList = new ArrayList<>();
        TestMetric.UI.AutomationExecute.RateInCustom rateInCustom;
        List<TestMetric.UI.AutomationExecute.RateInCustom> rateInCustomList = new ArrayList<>();

        TestReport testReport = new TestReport();
        testReport.setProjectId(testMetric.getUi().getProjectId());
        testReport.setVersionId(testMetric.getUi().getVersionId());
        List<TestReport> testReportList = testReportService.findList(testReport);
        for (String timeType : timeTypeList){
            runInCustom = new TestMetric.UI.AutomationExecute.RunInCustom();
            exeInCustom = new TestMetric.UI.AutomationExecute.ExeInCustom();
            defectCustom = new TestMetric.UI.AutomationExecute.DefectCustom();
            labInCustom = new TestMetric.UI.AutomationExecute.LabInCustom();
            rateInCustom = new TestMetric.UI.AutomationExecute.RateInCustom();
            int runHistoryTotal = 0;
            int runInCustomTotal = 0;
            int exeInCustomTotal = 0;
            int passInCustomTotal = 0;
            int failInCustomTotal = 0;
            long durationInCustomTotal = 0;
            double labInCustomTotal = 0.00;
            for (TestReport testReport1 : testReportList){
                if (StringUtils.isNull(testReport1)){
                    throw new BizException(SysErrorCode.B_TEST_REPORT_NotExisten, testReport.getId());
                }
                TestReport.StatisticAnalysis statisticAnalysis = JSON.parseObject(testReport1.getStatisticAnalysisStr(), TestReport.StatisticAnalysis.class);
                switch (timeType){
                    case "历史":
                        runInCustomTotal++;
                        exeInCustomTotal += statisticAnalysis.getUi().getSceneTotal();
                        passInCustomTotal += statisticAnalysis.getUi().getScenePass();
                        failInCustomTotal += statisticAnalysis.getUi().getSceneFail();
                        durationInCustomTotal += Long.parseLong(statisticAnalysis.getUi().getDuration());
                        labInCustomTotal += (double) statisticAnalysis.getUi().getSceneTotal() / 70;
                        break;
                    case "本周":
                        customTime = getDateRange("week");
                        if(isInTimeRange(customTime[0],customTime[1], sdf.format(testReport1.getCreateTime()))){
                            runInCustomTotal++;
                            exeInCustomTotal += statisticAnalysis.getUi().getSceneTotal();
                            passInCustomTotal += statisticAnalysis.getUi().getScenePass();
                            failInCustomTotal += statisticAnalysis.getUi().getSceneFail();
                            durationInCustomTotal += Long.parseLong(statisticAnalysis.getUi().getDuration());
                            labInCustomTotal += (double) statisticAnalysis.getUi().getSceneTotal() / 70;
                        }
                        break;
                    case "本月":
                        customTime = getDateRange("month");
                        if(isInTimeRange(customTime[0],customTime[1], sdf.format(testReport1.getCreateTime()))){
                            runInCustomTotal++;
                            exeInCustomTotal += statisticAnalysis.getUi().getSceneTotal();
                            passInCustomTotal += statisticAnalysis.getUi().getScenePass();
                            failInCustomTotal += statisticAnalysis.getUi().getSceneFail();
                            durationInCustomTotal += Long.parseLong(statisticAnalysis.getUi().getDuration());
                            labInCustomTotal += (double) statisticAnalysis.getUi().getSceneTotal() / 70;
                        }
                        break;
                    case "本年":
                        customTime = getDateRange("year");
                        if(isInTimeRange(customTime[0],customTime[1], sdf.format(testReport1.getCreateTime()))){
                            runInCustomTotal++;
                            exeInCustomTotal += statisticAnalysis.getUi().getSceneTotal();
                            passInCustomTotal += statisticAnalysis.getUi().getScenePass();
                            failInCustomTotal += statisticAnalysis.getUi().getSceneFail();
                            durationInCustomTotal += Long.parseLong(statisticAnalysis.getUi().getDuration());
                            labInCustomTotal += (double) statisticAnalysis.getUi().getSceneTotal() / 70;
                        }
                }
            }
            runInCustom.setName(timeType);
            runInCustom.setTotal(runInCustomTotal);
            runInCustomList.add(runInCustom);

            exeInCustom.setName(timeType);
            exeInCustom.setTotal(exeInCustomTotal);
            exeInCustomList.add(exeInCustom);

            defectCustom.setName(timeType);
            defectCustom.setTotal(failInCustomTotal);
            defectCustomList.add(defectCustom);

            labInCustom.setName(timeType);
            labInCustom.setTotal(NumberUtil.decimalFormat("0.00",labInCustomTotal));
            labInCustomList.add(labInCustom);

            rateInCustom.setName(timeType);
            double coverRate = (double) exeInCustomTotal /(uiFunctionalModule.getTotalAll()*runInCustomTotal);
            rateInCustom.setCoverRate(NumberUtil.decimalFormat("0.00%",Double.isFinite(coverRate)?coverRate:0));
//            rateInCustom.setCoverRate(NumberUtil.formatPercent(coverRate ,2));
            double passRate = (double) passInCustomTotal/exeInCustomTotal;
            rateInCustom.setPasseRate(NumberUtil.decimalFormat("0.00%",Double.isFinite(passRate)?passRate:0));

            double hours = (double) durationInCustomTotal / 3600000;
//            int executeRate = (int) (exeInCustomTotal / hours);
//            rateInCustom.setExecuteRate(String.valueOf(executeRate));
            double executeRate = exeInCustomTotal / hours;
            rateInCustom.setExecuteRate(NumberUtil.decimalFormat("0.00",Double.isFinite(executeRate)?executeRate:0));

            double defectRate = (double) failInCustomTotal/exeInCustomTotal;
            rateInCustom.setDefectRate(NumberUtil.decimalFormat("0.00%",Double.isFinite(defectRate)?defectRate:0));
            rateInCustomList.add(rateInCustom);
        }
        uiAutomationExecute.setRunInCustomList(runInCustomList);
        uiAutomationExecute.setExeInCustomList(exeInCustomList);
        uiAutomationExecute.setDefectCustomList(defectCustomList);
        uiAutomationExecute.setLabInCustomList(labInCustomList);
        uiAutomationExecute.setRateInCustomList(rateInCustomList);
        ui.setAutomationExecute(uiAutomationExecute);
        testMetric1.setUi(ui);
        return R.data(testMetric1);
    }

    public int getSysNodeTotal(SysNode sysNode) {
        List<SysNode> sysNodeList = sysNodeService.findList(sysNode);
        int total = 0;
        if (!sysNodeList.isEmpty()) {
            total = sysNodeList.size();
            for (SysNode node : sysNodeList) {
                sysNode.setParentId(node.getId());
                total += getSysNodeTotal(sysNode);
            }
        }
        return total;
    }

    public int getSysNodeCustomTotal(SysNode sysNode, String[] customTime) {
        List<SysNode> sysNodeList = sysNodeService.findList(sysNode);
        int total = 0;
        for (SysNode node : sysNodeList) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(isInTimeRange(customTime[0],customTime[1], sdf.format(node.getCreateTime()))){
                total++;
            }
            sysNode.setParentId(node.getId());
            total += getSysNodeCustomTotal(sysNode,customTime);
        }
        return total;
    }

    public static List<SysNode> getChildren(List<SysNode> nodes, String parentId) {
        List<SysNode> children = new ArrayList<>();
        for (SysNode node : nodes) {
            if (Objects.equals(node.getParentId(), parentId)) {
                children.add(node);
                children.addAll(getChildren(nodes, node.getId()));
            }
        }
        return children;
    }

    public static boolean isInTimeRange(String startTime, String endTime, String targetTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);
        LocalDateTime targetDateTime = LocalDateTime.parse(targetTime, formatter);

        return !startDateTime.isAfter(targetDateTime) && !endDateTime.isBefore(targetDateTime);
    }

    public static String[] getWeekStartAndEnd() {
        LocalDate date = LocalDate.now();
        LocalDate startOfWeek = date.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = date.with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startOfWeekStr = formatter.format(startOfWeek.atStartOfDay());
        String endOfWeekStr = formatter.format(endOfWeek.atTime(23, 59, 59));

        return new String[]{startOfWeekStr, endOfWeekStr};
    }

    public static String[] getWeekStartAndEnd1() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        String startOfWeek = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        String endOfWeek = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());

        return new String[]{startOfWeek, endOfWeek};
    }

    public static String[] getDateRange(String type) {
        LocalDate date = LocalDate.now();
        LocalDate start;
        LocalDate end;

        if (type.equals("week")) {
            start = date.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
            end = date.with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY));
        } else if (type.equals("month")) {
            start = date.withDayOfMonth(1);
            end = start.plusMonths(1).minusDays(1);
        } else if (type.equals("year")) {
            start = date.withDayOfYear(1);
            end = start.plusYears(1).minusDays(1);
        } else {
            throw new IllegalArgumentException("Invalid type: " + type);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startStr = formatter.format(start.atStartOfDay());
        String endStr = formatter.format(end.atTime(23, 59, 59));

        return new String[]{startStr, endStr};
    }

    public static void main(String[] args) {
//        List<Integer> list = new ArrayList<>();
//        for (int i = 1; i <= 30; i++) {
//            list.add(i);
//        }
//        int pageSize = 20; // 每页显示的数据条数
//        int currentPage = 2; // 当前页数
//        List<Integer> pageData = getPageData(list,currentPage,pageSize);
//        System.out.println("第" + currentPage + "页数据：" + pageData);
        System.out.println(Arrays.toString(getWeekStartAndEnd()));
        String[] startAndEnd = getDateRange("week");
        System.out.println("Start of week: " + startAndEnd[0]);
        System.out.println("End of week: " + startAndEnd[1]);
        System.out.println(NumberUtil.decimalFormat("0.00%",(double)0/3));
        System.out.println(NumberUtil.formatPercent((double)1/3,2));

        double hours = (double) 1 / 3600000;
        double result = 9 / hours;
        System.out.println(Double.isFinite(result)?result: 0);
        System.out.println(result);
    }

    public static List<Integer> getPageData(List<Integer> list, int currentPage, int pageSize) {
        int startIndex = (currentPage - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());
        return list.subList(startIndex, endIndex);
    }
}
