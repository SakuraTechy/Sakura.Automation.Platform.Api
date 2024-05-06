package com.sakura.web.controller.test;

import com.sakura.common.annotation.Log;
import com.sakura.common.core.domain.R;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.enums.BusinessType;
import com.sakura.common.utils.poi.ExcelUtil;
import com.sakura.framework.cache.DictUtils;
import com.sakura.test.domain.TestReport;
import com.sakura.test.service.TestReportService;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 测试管理-测试报告Controller
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2023-11-22
 */
@RestController
@RequestMapping("/test/testReport")
public class TestReportController {

    @Autowired
    private TestReportService testReportService;

    /**
     * 查询测试管理-测试报告
     */
    @PreAuthorize("@ss.hasPermi('test:testReport:list')")
    @GetMapping("/list")
    public R<PageInfo> list(TestReport testReport, HttpServletRequest request, HttpServletResponse response) {
        testReport.setPage(new PageDomain(request, response));
        return R.data(testReportService.findPage(testReport));
    }

    /**
     * 获取测试管理-测试报告详细信息
     */
    @PreAuthorize("@ss.hasPermi('test:testReport:query')")
    @GetMapping(value = "/{id}")
    public R<TestReport> detail(@PathVariable("id") String id) {
        return R.data(testReportService.get(id));
    }

    /**
     * 新增测试管理-测试报告
     */
    @PreAuthorize("@ss.hasPermi('test:testReport:add')")
    @Log(title = "测试管理-测试报告", businessType = BusinessType.INSERT)
    @PostMapping
    public R add(@RequestBody @Validated  TestReport testReport) {
        return R.status(testReportService.save(testReport));
    }

    /**
     * 修改测试管理-测试报告
     */
    @PreAuthorize("@ss.hasPermi('test:testReport:edit')")
    @Log(title = "测试管理-测试报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public R edit(@RequestBody @Validated TestReport testReport) {
        return R.status(testReportService.save(testReport));
    }

    /**
     * 删除测试管理-测试报告
     */
    @PreAuthorize("@ss.hasPermi('test:testReport:remove')")
    @Log(title = "测试管理-测试报告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R remove(@PathVariable String[] ids) {
        return R.status(testReportService.deleteTestReportByIds(ids));
    }

    /**
     * 删除测试管理-测试报告
     */
    @PreAuthorize("@ss.hasPermi('test:testReport:remove')")
    @Log(title = "删除测试管理-测试报告", businessType = BusinessType.UPDATE)
    @PutMapping("/remove")
    public R remove(@RequestBody TestReport testReport) {
        return R.status(testReportService.remove(testReport));
    }

    /**
     * 导出测试管理-测试报告
     */
    @PreAuthorize("@ss.hasPermi('test:testReport:export')")
    @Log(title = "测试管理-测试报告", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R export(TestReport testReport) {
        List<TestReport> list = testReportService.findList(testReport);
        ExcelUtil<TestReport> util = new ExcelUtil<TestReport>(TestReport.class);
        return util.exportExcel(list, "测试管理-测试报告数据");
    }

    /**
     * 上传测试管理-测试报告测试场景结果
     */
    @Log(title = "上传测试场景结果", businessType = BusinessType.UPDATE)
    @PutMapping("/uploadResults")
    public R uploadResults(@RequestBody @Validated TestReport testReport) {
        return R.status(testReportService.uploadResults(testReport));
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
