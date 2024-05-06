package com.sakura.system.mapper;

import com.sakura.common.core.mapper.BaseMapper;
import com.sakura.project.domain.ExecuteConfig;
import com.sakura.system.domain.SysAutomation;
import com.sakura.system.domain.SysScene;
import com.sakura.system.domain.vo.SceneVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wutun
 */
public interface SysAutomationMapper extends BaseMapper<SysScene> {

    /**
     * 删除场景通过版本ID
     * @param versionId
     * @return
     */
    boolean deleteSceneByVersionId(String versionId);

    /**
     * 删除场景通过节点ID
     * @param moduleId
     * @return
     */
    boolean deleteSceneByNodeId(String moduleId);

    /**
     * 复制测试场景
     * @param sceneVo
     * @return
     */
    boolean copySysScene(SceneVo sceneVo);

    /**
     * 批量删除场景
     * @param ids
     * @return
     */
    boolean deleteSysSceneByIds(String[] ids);

    /**
     * 批量执行场景
//     * @param ids 场景ID
     * @param buildNumber jenkins构建编号
     * @return 结果
     */
//    boolean execSysSceneByIds(@Param("ids") String[] ids, @Param("buildNumber") Integer buildNumber, @Param("consoleUrl") String consoleUrl, @Param("testReportUrl") String testReportUrl);
    boolean execSysSceneByIds(@Param("sysSceneList") List<SysScene> sysSceneList, @Param("buildNumber") Integer buildNumber, @Param("consoleUrl") String consoleUrl, @Param("testReportUrl") String testReportUrl);

    /**
     * 清空测试场景结果
     * @param sysScene
     * @return
     */
    boolean clearResults(SysScene sysScene);

    /**
     * 上传测试场景结果
     * @param sysScene
     * @return
     */
    boolean uploadResults(SysScene sysScene);

    /**
     * 更新测试计划
     * @param sysScene
     * @return
     */
    boolean updateTestPlan(SysScene sysScene);

    /**
     * 获取当前模块下所有
     * @param id
     * @return
     */
    int getDateNum(String id);
}
