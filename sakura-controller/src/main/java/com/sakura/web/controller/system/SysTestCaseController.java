package com.sakura.web.controller.system;

import com.sakura.common.annotation.Log;
import com.sakura.common.constant.Constants;
import com.sakura.common.core.domain.JsonResult;
import com.sakura.common.core.domain.R;
import com.sakura.common.core.domain.entity.SysDept;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.enums.BusinessType;
import com.sakura.common.utils.StringUtils;
import com.sakura.system.domain.SysCase;
import com.sakura.system.domain.SysNode;
import com.sakura.system.domain.SysProject;
import com.sakura.system.domain.SysVersion;
import com.sakura.system.service.SysCaseService;
import com.sakura.system.service.SysNodeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 测试用例
 *
 * @author wutun
 */
@RestController
@RequestMapping("/system/case")
public class SysTestCaseController {

    @Autowired
    private SysNodeService sysNodeService;

    @Autowired
    private SysCaseService sysCaseService;


//    /**
//     * 根据层级展开树表格(展开当前点击)
//     * @param id
//     * @return
//     */
//    @PreAuthorize("@ss.hasPermi('case:function:list')")
//    @GetMapping("/node/list/{id}")
//    public JsonResult list( @PathVariable("id") String id) {
//        return JsonResult.success(sysNodeService.getNextNodeList(id));
//    }

    @PreAuthorize("@ss.hasPermi('case:function:list')")
    @GetMapping("/node/{projectId}/{versionName}")
    public JsonResult getRoot(@PathVariable("projectId") String projectId,@PathVariable("versionId") String versionId) {
        return JsonResult.success(sysNodeService.getAll(projectId,versionId,SysNode.ROOT_NODE_CASE));
    }

    /**
     * 新增测试用例树节点
     * @param sysNode
     * @return com.sakura.common.core.domain.R
     */
    @PreAuthorize("@ss.hasPermi('case:function:list')")
    @Log(title = "节点记录信息", businessType = BusinessType.INSERT)
    @PostMapping("/node")
    public JsonResult add(@RequestBody @Validated SysNode sysNode) {
        return JsonResult.status(sysNodeService.save(sysNode));
    }


    /**
     * 修改目录节点名称
     * @param sysNode
     * @return
     */
    @PreAuthorize("@ss.hasPermi('case:function:list')")
    @Log(title = "节点记录信息", businessType = BusinessType.UPDATE)
    @PutMapping("/node")
    public JsonResult edit(@RequestBody @Validated SysNode sysNode)
    {
        return JsonResult.status(sysNodeService.save(sysNode));

    }

    /**
     * 删除目录节点
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('case:function:list')")
    @Log(title = "节点信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/node/{id}")
    public JsonResult removeCase(@PathVariable  String id) {
        SysNode sysNode =new SysNode();
        sysNode.setId(id);
        return JsonResult.status(sysNodeService.removeCase(sysNode));
    }

    /**
     * 获取测试用例列表
     * @param sysCase
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize("@ss.hasPermi('case:function:list')")
    @GetMapping("/list")
    public JsonResult<PageInfo> list(SysCase sysCase, HttpServletRequest request, HttpServletResponse response)
    {
        sysCase.setPage(new PageDomain(request,response));
        return JsonResult.success(sysCaseService.findPage(sysCase));
    }


    /**
     * 获取用例信息详细记录
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('case:function:list')")
    @GetMapping("/{id}")
    public JsonResult<SysCase> get(@PathVariable("id") String id)
    {
        return JsonResult.success(sysCaseService.get(id));
    }

    /**
     * 新增用例详细信息
     * @param sysCase
     * @return
     */
    @PreAuthorize("@ss.hasPermi('case:function:list')")
    @PostMapping()
    public JsonResult add(@RequestBody @Validated SysCase sysCase)
    {
        return JsonResult.status(sysCaseService.save(sysCase));
    }

    /**
     * 编辑用例详细信息
     * @param sysCase
     * @return
     */
    @PreAuthorize("@ss.hasPermi('case:function:list')")
    @PutMapping()
    public JsonResult edit(@RequestBody @Validated SysCase sysCase)
    {
        return JsonResult.status(sysCaseService.save(sysCase));
    }

    /**
     * 批量删除用例信息
     * @param ids
     * @return
     */
    @PreAuthorize("@ss.hasPermi('case:function:list')")
    @DeleteMapping("/{ids}")
    public JsonResult delete(@PathVariable("ids") String[] ids)
    {
        return JsonResult.status(sysCaseService.deleteSysCaseByIds(ids));
    }

}
