package com.sakura.system.mapper;

import com.sakura.common.core.mapper.BaseTreeMapper;
import com.sakura.system.domain.SysNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wutun
 */
public interface SysNodeMapper extends BaseTreeMapper<SysNode>{


    /**
     * 通过parentId查询子节点
     * @param id
     * @return
     */
    List<SysNode> findChildByParentId(String id);

    /**
     * 获取用例目录根节点数据
     * @param projectId
     * @param name
     * @return
     */
    SysNode getAll(@Param("projectId") String projectId ,@Param("versionId") String versionId,@Param("name") String name);

    /**
     * 根据项目ID删除相关目录节点
     * @param ids
     */
    void deleteNodeByProjectIds(String[] ids);

    boolean deleteNodeByVersionId(String versionId);

    SysNode getFind(SysNode sysNode);
}
