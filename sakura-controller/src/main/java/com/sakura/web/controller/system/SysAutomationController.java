package com.sakura.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.sakura.common.annotation.Log;
import com.sakura.common.core.domain.JsonResult;
import com.sakura.common.core.page.PageDomain;
import com.sakura.common.enums.BusinessType;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.uuid.IdUtils;
import com.sakura.system.domain.*;
import com.sakura.system.domain.vo.DragVo;
import com.sakura.system.domain.vo.SceneCaseVo;
import com.sakura.system.domain.vo.SceneVo;
import com.sakura.system.domain.vo.StepVo;
import com.sakura.project.domain.ExecuteConfig;
import com.sakura.project.service.ProjectConfigService;
import com.sakura.system.service.*;

import com.sakura.test.domain.TestPlan;
import com.sakura.test.domain.TestReport;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

/**
 * @author wutun
 */
@RestController
@RequestMapping("/system/automation")
public class SysAutomationController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ProjectConfigService projectConfigService;

    @Autowired
    private SysAutomationService sysAutomationService;

//    @Autowired
//    public SysVersionService sysVersionService;

    @Autowired
    private SysNodeService sysNodeService;

    /**
     * 获取所有节点信息
     * @param projectId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @GetMapping("/node/{projectId}")
    public JsonResult getNodeAll(@PathVariable("projectId") @NotEmpty String projectId) {
        return JsonResult.success(sysNodeService.getAll(projectId,"",SysNode.ROOT_NODE_SCENE));
    }

    /**
     * 获取所有节点信息
     * @param projectId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @GetMapping("/node/{projectId}/{versionId}")
    public JsonResult getNode(@PathVariable("projectId") @NotEmpty String projectId, @PathVariable("versionId") String versionId) {
        return JsonResult.success(sysNodeService.getAll(projectId,versionId,SysNode.ROOT_NODE_SCENE));
    }

    /**
     * 新增测试用例树节点
     * @param sysNode
     * @return com.sakura.common.core.domain.R
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "节点记录信息", businessType = BusinessType.INSERT)
    @PostMapping("/node")
    public JsonResult add(@RequestBody @Validated SysNode sysNode) {
        String id = IdUtils.randomUUID()+"_"+sysNode.getVersionName();
        sysNode.setId(id);
        sysNode.setNewRecord(true);
        return sysNodeService.save(sysNode)?JsonResult.success(id):JsonResult.error();
    }

    /**
     * 修改目录节点
     * @param sysNode
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "节点记录信息", businessType = BusinessType.UPDATE)
    @PutMapping("/node")
    public JsonResult edit(@RequestBody @Validated SysNode sysNode) {
        return JsonResult.status(sysNodeService.save(sysNode));
    }

    /**
     * 删除目录节点
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "节点信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/node/{id}")
    public JsonResult remove(@PathVariable  String id) {
        SysNode sysNode =new SysNode();
        sysNode.setId(id);
        return JsonResult.status(sysNodeService.removeScene(sysNode));
    }

    /**
     * 新增场景信息记录（生成xml文件）
     *
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:add')")
    @Log(title = "场景信息记录", businessType = BusinessType.INSERT)
    @PostMapping
    public JsonResult add(@RequestBody @Validated SysScene sysScene) {
        String abbreviate = String.valueOf(projectConfigService.get(sysScene.getProjectId()).getAbbreviate());
        String principalName = String.valueOf(userService.get(sysScene.getPrincipalId()).getName());
        sysScene.setProjectName(abbreviate);
        sysScene.setPrincipalName(principalName);
        List<TestReport.StatisticAnalysis.UI> testRecordUiList = new ArrayList<>();
        TestReport.StatisticAnalysis.UI ui = new TestReport.StatisticAnalysis.UI();
        ui.setExecuteStatus(sysScene.getStatus());
        ui.setScenePassRate("-");
        ui.setExecuteResult("-");
        ui.setDuration("-");
        ui.setExecuteName("-");
        testRecordUiList.add(ui);
        sysScene.setDebugRecord(JSON.toJSONString(testRecordUiList));
        sysAutomationService.save(sysScene);
        HashMap<String,String> map = new HashMap<>();
        map.put("id",sysScene.getId());
        return JsonResult.success(map);
    }

    /**
     * 修改场景信息记录（修改xml文件）
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:edit')")
    @Log(title = "场景信息记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public JsonResult edit(@RequestBody @Validated SysScene sysScene) {
        List<SysSceneCase> sysSceneCaseList = sysAutomationService.editPre(sysScene.getId());
//        String versionName = String.valueOf(sysVersionService.get(sysScene.getVersionId()).getVersionName());
        String principalName = String.valueOf(userService.get(sysScene.getPrincipalId()).getName());
//        sysScene.setVersionName(versionName);
        sysScene.setCaseList(sysSceneCaseList);
        sysScene.setPrincipalName(principalName);
        return JsonResult.status(sysAutomationService.save(sysScene));
    }

    /**
     * 删除场景信息记录
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:remove')")
    @Log(title = "场景信息记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public JsonResult remove(@PathVariable String[] ids) {
        return JsonResult.status(sysAutomationService.deleteSysSceneByIds(ids));
    }

    /**
     * 获取场景信息列表
     * @param sysScene
     * @param request
     * @param response
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @PostMapping("/list")
    public JsonResult<PageInfo> list(@RequestBody @Validated SysScene sysScene, HttpServletRequest request, HttpServletResponse response){
//        sysScene.setPage(new PageDomain(request,response));
//        PageDomain page = new PageDomain();
//        page.setPageNum(sysScene.getPageNum());
//        page.setPageSize(sysScene.getPageSize());
//        sysScene.setPage(page);
//        if (StringUtils.inStringIgnoreCase(sysScene.getVersionName(),"全部")) {
//            sysScene.setVersionName("");
//        }
//        if (StringUtils.inStringIgnoreCase(sysScene.getCreateByName(),"全部")) {
//            sysScene.setCreateByName("");
//        }
//        if (StringUtils.inStringIgnoreCase(sysScene.getPrincipalName(),"全部")) {
//            sysScene.setPrincipalName("");
//        }
        return JsonResult.success(sysAutomationService.findPage(sysScene));
    }

    /**
     * 获取场景信息详细记录
     * @param id
     * @return JsonResult
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @GetMapping("/{id}")
    public JsonResult get(@PathVariable("id") String id){
        return JsonResult.success(sysAutomationService.get(id));
    }

//    /**
//     * 复制场景信息记录（单个）
//     */
//    @PreAuthorize("@ss.hasPermi('ui:automation:copy')")
//    @Log(title = "场景信息记录", businessType = BusinessType.INSERT)
//    @PostMapping("/copy")
//    public JsonResult copy(@RequestBody @Validated SceneVo sceneVo) {
//        String id = IdUtils.randomUUID();
//        sceneVo.setCopyId(id);
//        String abbreviate = String.valueOf(projectConfigService.get(sceneVo.getProjectId()).getAbbreviate());
//        sceneVo.setProjectName(abbreviate);
//        sysAutomationService.copySysScene(sceneVo);
//        HashMap<String,String> map = new HashMap<>(16);
//        map.put("id",id);
//        return JsonResult.success(map);
//    }

    /**
     * 复制场景信息记录（单个，多个）
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:copy')")
    @Log(title = "场景信息记录", businessType = BusinessType.INSERT)
    @PostMapping("/copy")
    public JsonResult copy(@RequestBody @Validated SceneVo sceneVo) {
        return JsonResult.status(sysAutomationService.copySysScene(sceneVo));
    }

    /**
     * 复制场景信息记录（所有）
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:copy')")
    @Log(title = "场景信息记录", businessType = BusinessType.INSERT)
    @PostMapping("/copyAll")
    public JsonResult copyAll(@RequestBody @Validated SceneVo sceneVo) {
        return JsonResult.status(sysAutomationService.copySysSceneAll(sceneVo));
    }

    /**
     * 导出测试场景（导出单个xml）
     * @param ids
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:export')")
    @RequestMapping(value = "/export/{id}")
    public void export(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String[] ids, ExecuteConfig executeConfig) throws Exception {
        sysAutomationService.exportSysScene(request,response,ids,executeConfig);
    }

    /**
     * 导出测试场景（导出多个xml）
     * @param ids
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:export')")
    @RequestMapping(value = "/exports/{id}")
    public void exports(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String[] ids, ExecuteConfig executeConfig) throws Exception {
        sysAutomationService.exportSysScenes(request,response,ids,executeConfig);
    }

    /**
     * 导出测试场景（项目下所有xml）
     * @param sysScene
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:export')")
    @PostMapping(value = "/exportAll")
    public void exportAll(@RequestBody @Validated SysScene sysScene, ExecuteConfig executeConfig, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        List<SysScene> sysSceneList = sysAutomationService.findList(sysScene);
        sysAutomationService.exportSysSceneAll(request,response,sysScene,executeConfig);
    }

    /**
     * 执行测试场景（批量执行项目对应版本下单个，多个场景）
     */
//    @PreAuthorize("@ss.hasPermi('ui:automation:exec')")
//    @RequestMapping(value = "/exec/{ids}")
//    public JsonResult exec(@PathVariable String[] ids, ExecuteConfig executeConfig) throws Exception {
//       return JsonResult.success(sysAutomationService.execSysScene(ids,executeConfig));
//    }

    @PreAuthorize("@ss.hasPermi('ui:automation:exec')")
    @PostMapping(value = "/exec")
    public JsonResult exec(@RequestBody @Validated ExecuteConfig executeConfig) throws Exception {
        return JsonResult.success(sysAutomationService.execSysScene(executeConfig));
    }

    /**
     * 执行测试场景（项目对应版本下所有场景）
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:exec')")
    @PostMapping(value = "/execAll")
    public JsonResult execAll(@RequestBody @Validated SysScene sysScene, ExecuteConfig executeConfig) throws Exception {
        List<SysScene> sysSceneList = sysAutomationService.findList(sysScene);
        List<String> sceneIdList = new ArrayList<>();
        sysSceneList.sort((x,y)->x.getSceneId().compareTo(y.getSceneId()));
        for(SysScene sysScene1 : sysSceneList){
            sceneIdList.add(sysScene1.getId());
        }
        String[] ids = sceneIdList.toArray(new String[0]);
        executeConfig.setIds(ids);
        return JsonResult.success(sysAutomationService.execSysScene(executeConfig));
    }

    /**
     * 清空测试场景结果
     * @param sysScene
     * @return
     */
    @Log(title = "清空测试场景结果", businessType = BusinessType.UPDATE)
    @PutMapping("/clearResults")
    public JsonResult clearResults(@RequestBody @Validated SysScene sysScene) {
        return JsonResult.status(sysAutomationService.clearResults(sysScene));
    }

    /**
     * 上传测试场景结果
     * @param sysScene
     * @return
     */
    @Log(title = "上传测试场景结果", businessType = BusinessType.UPDATE)
    @PutMapping("/uploadResults")
//    public JsonResult uploadResults1(@RequestBody @Validated SysScene sysScene) {
//        Integer casePass = sysScene.getCasePass();
//        Integer caseFail = sysScene.getCaseFail();
//        Integer caseSkip = sysScene.getCaseSkip();
//        Integer caseTotal = casePass+caseFail+caseSkip;
//
//        Integer stepPass = sysScene.getStepPass();
//        Integer stepFail = sysScene.getStepFail();
//        Integer stepSkip = sysScene.getStepSkip();
//        Integer stepTotal = stepPass+stepFail+stepSkip;
//
//        sysScene.setCasePass(casePass);
//        sysScene.setCaseFail(caseFail);
//        sysScene.setCaseSkip(caseSkip);
//        sysScene.setCaseTotal(caseTotal);
//
//        /******  小于10位数计算百分百 ******/
//        // 创建一个数值格式化对象
//        NumberFormat numberFormat = NumberFormat.getInstance();
//        // 设置精确到小数点后2位
//        numberFormat.setMaximumFractionDigits(2);
//        // 计算百分百，并返回
//        String moreThanMin = numberFormat.format(((float) casePass / (float) caseTotal) * 100);
//        String passRate = moreThanMin +"%";
//
//        /******  大于12位数计算百分百 ******/
//        double moreThanMax = new BigDecimal(casePass)
//                .divide(new BigDecimal(caseTotal), 4, BigDecimal.ROUND_DOWN)
//                .doubleValue();
////        String passRate = String.valueOf(moreThanMax*100)+"%";
//        sysScene.setPassRate(passRate);
//        if (casePass.equals(caseTotal)){
//            sysScene.setLastResult("全部通过");
//            sysScene.setStatus("Completed");
//        }else{
//            sysScene.setLastResult("不通过");
//            sysScene.setStatus("Underway");
//        }
//        sysScene.setStepPass(stepPass);
//        sysScene.setStepFail(stepFail);
//        sysScene.setStepSkip(stepSkip);
//        sysScene.setStepTotal(stepTotal);
//        return JsonResult.status(sysAutomationService.uploadResults(sysScene));
//    }

    public JsonResult uploadResults(@RequestBody @Validated SysScene sysScene) {
        return JsonResult.status(sysAutomationService.uploadResults(sysScene));
    }

    /**
     * 更新测试计划
     * @param sysScene
     * @return
     */
    @Log(title = "更新测试计划", businessType = BusinessType.UPDATE)
    @PutMapping("/updateTestPlan")
    public JsonResult updateTestPlan(@RequestBody @Validated SysScene sysScene) {
        return JsonResult.status(sysAutomationService.updateTestPlan(sysScene));
    }

    /**
     * 添加用例信息
     * @param sceneCaseVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "场景信息记录", businessType = BusinessType.DELETE)
    @PostMapping("/addCase")
    public JsonResult addCase(@RequestBody @Validated SceneCaseVo sceneCaseVo ) {
        return JsonResult.status(sysAutomationService.addCase(sceneCaseVo));
    }

    /**
     * 删除用例信息
     * @param sceneCaseVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "场景信息记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteCase")
    public JsonResult removeCase(@RequestBody @Validated SceneCaseVo sceneCaseVo)
    {
        return JsonResult.status(sysAutomationService.removeCase(sceneCaseVo));
    }

    /**
     * 编辑用例信息
     * @param sceneCaseVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "场景信息记录", businessType = BusinessType.UPDATE)
    @PutMapping("/editCase")
    public JsonResult editCase(@RequestBody @Validated SceneCaseVo sceneCaseVo)
    {
        return JsonResult.status(sysAutomationService.editCase(sceneCaseVo));
    }

    /**
     * 获取用例详细信息
     * @param sceneCaseVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "场景信息记录", businessType = BusinessType.DELETE)
    @PostMapping("/getCase")
    public JsonResult getCase(@RequestBody @Validated SceneCaseVo sceneCaseVo) {
        SysSceneCase Case = sysAutomationService.getCase(sceneCaseVo);
        return JsonResult.success(Case);
    }

    /**
     * 复制用例
     * @param sceneCaseVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "场景信息记录")
    @PostMapping("/copyCase")
    public JsonResult copyCase(@RequestBody @Validated SceneCaseVo sceneCaseVo) {
        SysSceneCase Case = sysAutomationService.getCase(sceneCaseVo);
        String id = sceneCaseVo.getSysSceneCase().getCopyId();
        Case.setId(id);
        Case.setName(sceneCaseVo.getSysSceneCase().getName());
        Case.setRemark(sceneCaseVo.getSysSceneCase().getRemark());
        Case.getStepList().forEach(setp->{
            setp.setPid(id);
            setp.setId(IdUtils.simpleUUID());
        });
        sceneCaseVo.setSysSceneCase(Case);
        sysAutomationService.addCase(sceneCaseVo);
        HashMap<String,String> map = new HashMap<>(16);
        map.put("id",id);
        return JsonResult.success(map);
    }

    /**
     * 添加用例步骤
     * @param stepVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "场景信息记录", businessType = BusinessType.DELETE)
    @PostMapping("/addStep")
    public JsonResult addStep(@RequestBody @Validated StepVo stepVo) {
        return JsonResult.status(sysAutomationService.addStep(stepVo));
    }

    /**
     * 删除步骤信息
     * @param stepVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "场景信息记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/deleteStep")
    public JsonResult deleteStep(@RequestBody @Validated StepVo stepVo ) {
        return JsonResult.status(sysAutomationService.deleteStep(stepVo));
    }

    /**
     * 编辑步骤信息
     * @param stepVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "场景信息记录", businessType = BusinessType.UPDATE)
    @PutMapping("/editStep")
    public JsonResult editStep(@RequestBody @Validated StepVo stepVo ) {
        return JsonResult.status(sysAutomationService.editStep(stepVo));
    }

    /**
     * 获取步骤信息
     * @param stepVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "场景信息记录", businessType = BusinessType.DELETE)
    @PostMapping("/getStep")
    public JsonResult getStep(@RequestBody @Validated StepVo stepVo ) {
        return JsonResult.success(sysAutomationService.getStep(stepVo));
    }

    /**
     * 复制步骤
     * @param stepVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "场景信息记录")
    @PostMapping("/copyStep")
    public JsonResult copyStep(@RequestBody @Validated StepVo stepVo) {
        Step step = sysAutomationService.getStep(stepVo);
        step.setId(stepVo.getStep().getCopyId());
        step.setPid(stepVo.getStep().getCopyPid());
        step.setName(stepVo.getStep().getName());
        step.setOperationName(stepVo.getStep().getOperationName());
        step.setOperationType(stepVo.getStep().getOperationType());
        step.setAction(stepVo.getStep().getAction());
        step.setConfig(stepVo.getStep().getConfig());
        step.setOrder(0);
        stepVo.setStep(step);
        return JsonResult.status(sysAutomationService.addStep(stepVo));
    }

    /**
     * 步骤拖拽功能
     * @param dragVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "节点记录信息", businessType = BusinessType.UPDATE)
    @PutMapping("/dragStep")
    public JsonResult dragStep(@RequestBody @Validated DragVo dragVo) {
        return JsonResult.status(sysAutomationService.dragStep(dragVo));

    }

    /**
     * 用例拖拽功能
     * @param dragVo
     * @return
     */
    @PreAuthorize("@ss.hasPermi('ui:automation:list')")
    @Log(title = "节点记录信息", businessType = BusinessType.UPDATE)
    @PutMapping("/dragCase")
    public JsonResult dragCase(@RequestBody @Validated DragVo dragVo) {
        return JsonResult.status(sysAutomationService.dragCase(dragVo));

    }
}
