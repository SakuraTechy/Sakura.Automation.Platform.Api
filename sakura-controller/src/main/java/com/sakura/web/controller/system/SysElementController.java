package com.sakura.web.controller.system;

import com.sakura.common.annotation.Log;
import com.sakura.common.core.domain.JsonResult;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.enums.BusinessType;
import com.sakura.system.domain.SysCase;
import com.sakura.system.domain.SysElement;
import com.sakura.system.domain.SysNode;
import com.sakura.system.domain.SysVersion;
import com.sakura.system.service.SysElementService;
import com.sakura.system.service.SysNodeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wutun
 */
@RestController
@RequestMapping("/system/element")
public class SysElementController {

    @Autowired
    private SysElementService sysElementService;

    @Autowired
    private SysNodeService sysNodeService;

//    /**
//     * 根据层级展开树表格(展开当前点击)
//     * @param id
//     * @return
//     */
//    @PreAuthorize("@ss.hasPermi('ui:element:list')")
//    @GetMapping("/node/list/{id}")
//    public JsonResult list(@PathVariable("id") String id) {
//        return JsonResult.success(sysNodeService.getNextNodeList(id));
//    }

    /**
     * 新增测试用例树节点
     * @param sysNode
     * @return com.sakura.common.core.domain.R
     */
    @PreAuthorize("@ss.hasPermi('ui:element:list')")
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
    @PreAuthorize("@ss.hasPermi('ui:element:list')")
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
    @PreAuthorize("@ss.hasPermi('ui:element:list')")
    @Log(title = "节点信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/node/{id}")
    public JsonResult remove(@PathVariable  String id) {
        SysNode sysNode =new SysNode();
        sysNode.setId(id);
        return JsonResult.status(sysNodeService.removeElement(sysNode));
    }

    /**
     * 获取元素库信息列表
     * @param sysElement
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:element:list')")
    @GetMapping("/list")
    public JsonResult<PageInfo> list(SysElement sysElement, HttpServletRequest request, HttpServletResponse response)
    {
        sysElement.setPage(new PageDomain(request,response));
        return JsonResult.success(sysElementService.findPage(sysElement));
    }

    /**
     * 获取元素库详细信息
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:element:list')")
    @GetMapping(value = "/{id}")
    public JsonResult<SysElement> detail(@PathVariable("id") String id)
    {
        return JsonResult.success(sysElementService.get(id));
    }


    /**
     * 新增元素库信息记录
     * @param sysElement
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:element:list')")
    @Log(title = "元素库信息记录", businessType = BusinessType.INSERT)
    @PostMapping
    public JsonResult add(@RequestBody @Validated SysElement sysElement)
    {
        return JsonResult.status(sysElementService.save(sysElement));
    }

    /**
     * 修改元素库信息记录
     * @param sysElement
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:element:list')")
    @Log(title = "元素库信息记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public JsonResult edit(@RequestBody @Validated SysElement sysElement)
    {
        return JsonResult.status(sysElementService.save(sysElement));

    }

    /**
     * 删除元素库信息记录
     * @param ids
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:element:list')")
    @Log(title = "元素库信息记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public JsonResult remove(@PathVariable String[] ids)
    {
        return JsonResult.status(sysElementService.deleteSysElementServiceByIds(ids));
    }


}
