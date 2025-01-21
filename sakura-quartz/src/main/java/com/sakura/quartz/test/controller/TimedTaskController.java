package com.sakura.quartz.test.controller;

import com.sakura.common.annotation.Log;
import com.sakura.common.core.controller.BaseController;
import com.sakura.common.core.domain.R;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.enums.BusinessType;
import com.sakura.common.utils.poi.ExcelUtil;
import com.sakura.framework.cache.DictUtils;
import com.sakura.quartz.test.domain.TimedTask;
import com.sakura.quartz.test.service.TimedTaskService;
import com.sakura.system.service.SysAutomationService;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试管理-定时任务表Controller
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2024-02-28
 */
@RestController
@RequestMapping("/test/timedTask")
public class TimedTaskController extends BaseController {

    @Autowired
    private TimedTaskService timedTaskService;

    @Autowired
    private SysAutomationService sysAutomationService;

    /**
     * 查询测试管理-定时任务表列表
     */
    @PreAuthorize("@ss.hasPermi('test:timedTask:list')")
    @GetMapping("/list")
    public R<PageInfo> list(TimedTask timedTask, HttpServletRequest request, HttpServletResponse response) {
        timedTask.setPage(new PageDomain(request, response));
        return R.data(timedTaskService.findPage(timedTask));
    }

    /**
     * 获取测试管理-定时任务表详细信息
     */
    @PreAuthorize("@ss.hasPermi('test:timedTask:query')")
    @GetMapping(value = "/{id}")
    public R<TimedTask> detail(@PathVariable("id") String id) {
        return R.data(timedTaskService.get(id));
    }

    /**
     * 新增测试管理-定时任务表
     */
    @PreAuthorize("@ss.hasPermi('test:timedTask:add')")
    @Log(title = "测试管理-定时任务表", businessType = BusinessType.INSERT)
    @PostMapping
    public R add(@RequestBody @Validated TimedTask timedTask) {
        return R.status(timedTaskService.save(timedTask));
    }

    /**
     * 修改测试管理-定时任务表
     */
    @SneakyThrows
    @PreAuthorize("@ss.hasPermi('test:timedTask:edit')")
    @Log(title = "测试管理-定时任务表", businessType = BusinessType.UPDATE)
    @PutMapping
    public R edit(@RequestBody @Validated TimedTask timedTask) {
        return R.status(timedTaskService.edit(timedTask));
    }

    /**
     * 删除测试管理-定时任务表
     */
    @PreAuthorize("@ss.hasPermi('test:timedTask:remove')")
    @Log(title = "测试管理-定时任务表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R remove(@PathVariable String[] ids) {
        return R.status(timedTaskService.deleteTimedTaskByIds(ids));
    }

    /**
     * 删除测试管理-定时任务表
     */
    @PreAuthorize("@ss.hasPermi('test:timedTask:remove')")
    @Log(title = "测试管理-定时任务表", businessType = BusinessType.UPDATE)
    @PutMapping("/remove")
    public R remove(@RequestBody TimedTask timedTask) {
        return R.status(timedTaskService.remove(timedTask));
    }

    /**
     * 导出测试管理-定时任务表列表
     */
    @PreAuthorize("@ss.hasPermi('test:timedTask:export')")
    @Log(title = "测试管理-定时任务表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public R export(TimedTask timedTask) {
        List<TimedTask> list = timedTaskService.findList(timedTask);
        ExcelUtil<TimedTask> util = new ExcelUtil<TimedTask>(TimedTask.class);
        return util.exportExcel(list, "测试管理-定时任务表数据");
    }

    /**
     * 分页查询获取执行日志
     */
    @PreAuthorize("@ss.hasPermi('test:timedTask:list')")
    @GetMapping("/getLogList")
    public R<PageInfo> getLogList(TimedTask timedTask, HttpServletRequest request, HttpServletResponse response) {
        timedTask.setPage(new PageDomain(request, response));
        return R.data(timedTaskService.getLogList(timedTask));
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
