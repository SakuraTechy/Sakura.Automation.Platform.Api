package com.sakura.system.service;

import com.sakura.common.core.service.BaseTreeService;
import com.sakura.system.domain.SysNode;
import com.sakura.system.domain.vo.DragVo;

import java.util.List;

/**
 * @author wutun
 */
public interface SysNodeService extends BaseTreeService<SysNode> {


    /**
     * 根据展开层级和父节点递归获取展示的数据
     * @param level
     * @param id
     * @return
     */
//    List<SysNode> listDataByLevel(int level, String id);

    /**
     * 点击展开获取下一级目录
     * @param id
     * @return
     */
//    List<SysNode> getNextNodeList(String id);

    /**
     * 删除测试用例节点及其子节点数据
     * @param sysNode
     * @return
     */
    boolean removeCase(SysNode sysNode);

    /**
     * 删除自动化场景节点及其子节点数据
     * @param sysNode
     * @return
     */
    boolean removeScene(SysNode sysNode);

    /**
     * 删除元素库节点及子节点数据
     * @param sysNode
     * @return
     */
    boolean removeElement(SysNode sysNode);


    /**
     * 获取根节点
     * @param projectId
     * @param name
     * @return
     */
    SysNode getAll(String projectId,String versionId, String name);

    /**
     * 通过项目ID删除节点信息
     * @param ids
     */
    void deleteNodeByProjectIds(String[] ids);

    boolean deleteNodeByVersionId(String versionId);

    SysNode checkModuleNameUnique1(SysNode sysNode);

    SysNode getFind(SysNode sysNode);
}
