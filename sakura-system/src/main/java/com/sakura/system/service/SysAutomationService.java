package com.sakura.system.service;

import com.sakura.common.core.service.BaseService;
import com.sakura.system.domain.Step;
import com.sakura.system.domain.SysScene;
import com.sakura.system.domain.SysSceneCase;
import com.sakura.system.domain.vo.DragVo;
import com.sakura.system.domain.vo.SceneCaseVo;
import com.sakura.system.domain.vo.SceneVo;
import com.sakura.system.domain.vo.StepVo;
import com.sakura.project.domain.ExecuteConfig;
import com.sakura.test.domain.TestPlan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author wutun
 */
public interface SysAutomationService extends BaseService<SysScene> {

    /**
     * 保存测试场景
     * @param sysScene
     * @return
     */
    @Override
    boolean save(SysScene sysScene);

    /**
     * 批量删除测试场景
     * @param ids
     * @return
     */
    boolean deleteSysSceneByIds(String[] ids);

    /**
     * 删除场景通过版本ID
     * @param versionId
     * @return
     */
    boolean deleteSceneByVersionId(String versionId);

    /**
     * 复制测试场景
     * @param sceneVo
     * @return
     */
    boolean copySysScene(SceneVo sceneVo);
    boolean copySysSceneAll(SceneVo sceneVo);

    /**
     * 批量导出测试场景
     * @param ids
     * @return
     */
    void exportSysScene(HttpServletRequest request, HttpServletResponse response, String[] ids, ExecuteConfig executeConfig) throws Exception;
    void exportSysScenes(HttpServletRequest request, HttpServletResponse response, String[] ids, ExecuteConfig executeConfig) throws Exception;
    void exportSysSceneAll(HttpServletRequest request, HttpServletResponse response, SysScene sysScene, ExecuteConfig executeConfig) throws Exception;

    /**
     * 执行测试场景
     * @param executeConfig
     * @return
     */
//    Map<Object,Object> execSysScene(String[] ids) throws Exception;
    Map<Object,Object> execSysScene(ExecuteConfig executeConfig) throws Exception;
//    Map<Object,Object> execSysScene1(String[] ids) throws Exception;

    /**
     * 上传测试场景结果
     * @param sysScene
     * @return
     */
    boolean uploadResults(SysScene sysScene);

    /**
     * 清空测试场景结果
     * @param sysScene
     * @return
     */
    boolean clearResults(SysScene sysScene);

    /**
     * 更新测试计划
     * @param sysScene
     * @return
     */
    boolean updateTestPlan(SysScene sysScene);

    /**
     * 添加用例信息
     * @param sceneCaseVo
     * @return
     */
    boolean addCase(SceneCaseVo sceneCaseVo);

    /**
     * 删除用例信息
     * @param sceneCaseVo
     * @return
     */
    boolean removeCase(SceneCaseVo sceneCaseVo);

    /**
     * 编辑用例信息
     * @param sceneCaseVo
     * @return
     */
    boolean editCase(SceneCaseVo sceneCaseVo);

    /**
     * 获取用例详细信息
     * @param sceneCaseVo
     * @return
     */
    SysSceneCase getCase(SceneCaseVo sceneCaseVo);

    /**
     * 新增步骤信息
     * @param stepVo
     * @return
     */
    boolean addStep(StepVo stepVo);

    /**
     * 删除步骤信息
     * @param stepVo
     * @return
     */
    boolean deleteStep(StepVo stepVo);

    /**
     * 编辑步骤
     * @param stepVo
     * @return
     */
    boolean editStep(StepVo stepVo);

    /**
     * 获取步骤
     * @param stepVo
     * @return
     */
    Step getStep(StepVo stepVo);

    /**
     * 拖拽用例
     * @param dragVo
     * @return
     */
    boolean dragCase(DragVo dragVo);

    /**
     * 拖拽步骤
     * @param dragVo
     * @return
     */
    boolean dragStep(DragVo dragVo);

    /**
     * 编辑前置
     * @param id
     * @return
     */
    List<SysSceneCase> editPre(String id);
}
