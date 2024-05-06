package com.sakura.system.service.impl;

import com.sakura.common.constant.Constants;
import com.sakura.common.constant.UserConstants;
import com.sakura.common.core.domain.entity.SysDept;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.common.core.service.BaseTreeServiceImpl;
import com.sakura.common.exception.BizException;
import com.sakura.common.utils.PinYin4JCn;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.uuid.IdUtils;
import com.sakura.system.common.SysErrorCode;
import com.sakura.system.domain.SysNode;
import com.sakura.system.domain.SysScene;
import com.sakura.system.mapper.SysAutomationMapper;
import com.sakura.system.mapper.SysCaseMapper;
import com.sakura.system.mapper.SysElementMapper;
import com.sakura.system.mapper.SysNodeMapper;
import com.sakura.system.service.SysNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.sakura.system.domain.SysNode.DEFAULT_FILE_NAME;

/**
 * @author wutun
 */
@Service
@Transactional(readOnly = false)
public class SysNodeServiceImpl extends BaseTreeServiceImpl<SysNodeMapper, SysNode> implements SysNodeService {

    @Autowired
    private SysCaseMapper sysCaseMapper;

    @Autowired
    private SysAutomationMapper sysAutomationMapper;

    @Autowired
    private SysElementMapper sysElementMapper;

    @Override
    public void refreshCache() {

    }

    @Override
    public void loadingUserCache() {

    }

    @Override
    public void checkUserDataScope(String userId) {

    }


//    /**
//     * 根据展开层级和父节点递归获取展示的数据
//     *
//     * @param level
//     * @param parentId
//     * @return
//     */
//    @Override
//    public List<SysNode> listDataByLevel(int level, String parentId) {
//        List<SysNode> listData = new ArrayList<SysNode>();
//        level--;
//        List<SysNode> childrenList = findChildListByParentId(parentId);
//        for (SysNode dto : childrenList) {
//            if ("1".equals(dto.getTreeLeaf()) && level > 0) {
//                dto.setChildren(this.listDataByLevel(level, dto.getId()));
//            } else {
//                dto.setChildren(new ArrayList<>());
//            }
//            listData.add(dto);
//        }
//        return listData;
//    }

//    /**
//     * 点击展开获取下级目录
//     * @param id
//     * @return
//     */
//    @Override
//    public List<SysNode> getNextNodeList(String id) {
//        return findChildListByParentId(id);
//    }


    /**
     * 保存节点信息
     * @param sysNode
     * @return
     */
    @Override
    public boolean save(SysNode sysNode) {
        if (sysNode.getIsNewRecord()){
            //新增节点
            checkModuleNameUnique(sysNode);
            return super.save(sysNode);
        }else {//操作为修改数据
            SysNode sysNode1 = mapper.get(sysNode.getId());
            //如果修改的名称或者父节点ID有变动则进行校验
            if (!sysNode.getName().equals(sysNode1.getName()) || !sysNode.getParentId().equals(sysNode1.getParentId())){
                //如果名称有变动则进行校验
                checkModuleNameUnique(sysNode);
                return super.save(sysNode);
            }else {
                return Boolean.TRUE;
            }
        }
    }

    public void checkModuleNameUnique(SysNode sysNode) {
        SysNode sysNodeUnique = new SysNode();
        sysNodeUnique.setNotEqualId(sysNode.getId());
        sysNodeUnique.setName(sysNode.getName());
        sysNodeUnique.setParentId(sysNode.getParentId());
        sysNodeUnique.setProjectId(sysNode.getProjectId());
        sysNodeUnique.setVersionId(sysNode.getVersionId());
        if (!CollectionUtils.isEmpty(mapper.findListWithUnique(sysNodeUnique))) {
            throw new BizException(SysErrorCode.B_SYSNODE_NodeNameAlreadyExist);
        }
    }

    @Override
    public SysNode checkModuleNameUnique1(SysNode sysNode) {
        SysNode sysNodeUnique = new SysNode();
        sysNodeUnique.setNotEqualId(sysNode.getId());
        sysNodeUnique.setName(sysNode.getName());
        sysNodeUnique.setParentId(sysNode.getParentId());
        sysNodeUnique.setProjectId(sysNode.getProjectId());
        sysNodeUnique.setVersionId(sysNode.getVersionId());
        CollectionUtils.isEmpty(mapper.findListWithUnique(sysNodeUnique));
        if (!CollectionUtils.isEmpty(mapper.findListWithUnique(sysNodeUnique))) {
            sysNodeUnique = mapper.get(sysNodeUnique);
        }
        return sysNodeUnique;
    }

    @Override
    public SysNode getFind(SysNode sysNode) {
        return mapper.getFind(sysNode);
    }

    /**
     * 删除用例节点以及节点下所有子节点和节点上的数据
     * @param entity
     * @return
     */
    @Override
    public boolean removeCase(SysNode entity) {
        //获取子节点数据
        List<SysNode> sysNodeList =mapper.findChildByParentId(entity.getId());
        //当前节点数据
        super.remove(entity);
        sysCaseMapper.deleteCaseByNodeId(entity.getId());
        if (sysNodeList.size()==0||sysNodeList==null){
            return Boolean.TRUE;
        }else {
            for (SysNode sysNode : sysNodeList) {
                removeCase(sysNode);
                sysCaseMapper.deleteCaseByNodeId(sysNode.getId());
            }
            return Boolean.TRUE;
        }
    }

    /**
     * 删除场景目录节点及其子节点下所有子节点及其子节点上的数据
     * @param entity
     * @return
     */
    @Override
    public boolean removeScene(SysNode entity) {
        List<SysNode> sysNodeList =mapper.findChildByParentId(entity.getId());
        //删除场景信息通过节点ID
        super.remove(entity);
        sysAutomationMapper.deleteSceneByNodeId(entity.getId());
        if (sysNodeList.size()==0||sysNodeList==null){
            return Boolean.TRUE;
        }else {
            for (SysNode sysNode : sysNodeList) {
                removeScene(sysNode);
                sysAutomationMapper.deleteSceneByNodeId(entity.getId());
            }
            return Boolean.TRUE;
        }
    }

    @Override
    public boolean removeElement(SysNode entity) {
        List<SysNode> sysNodeList =mapper.findChildByParentId(entity.getId());
        //删除元素信息通过节点ID
        sysElementMapper.deleteElementByNodeId(entity.getId());
        if (sysNodeList.size()==0||sysNodeList==null){
            return Boolean.TRUE;
        }else {
            for (SysNode sysNode : sysNodeList) {
                removeElement(sysNode);
//                sysElementMapper.deleteElementByNodeId(entity.getId());
            }
            return Boolean.TRUE;
        }
    }

    /**
     * 获取所有节点信息
     * @param projectId
     * @param name
     * @return
     */
    @Override
    public SysNode getAll(String projectId,String versionId,String name) {
        SysNode sysNode = mapper.getAll(projectId,versionId, name);
        Integer dateNum = getDateNum(sysNode);
        sysNode.setNumber(dateNum);
        return mapper.getAll(projectId,versionId,name);
    }

    @Override
    public void deleteNodeByProjectIds(String[] ids) {
        mapper.deleteNodeByProjectIds(ids);
    }

    @Override
    public boolean deleteNodeByVersionId(String versionId) {
        return mapper.deleteNodeByVersionId(versionId);
    }

    /**
     * 获取节点下数据条数方法
     * @param sysNode
     * @return
     */
    public Integer getDateNum(SysNode sysNode){
        int num = 0;
        int totalNum = sysAutomationMapper.getDateNum(sysNode.getId());
        num = num+totalNum;
        List<SysNode> sysNodeList = (List<SysNode>) sysNode.getChildren();
        if (sysNodeList.size() != 0) {
            for (SysNode sysNode2 : sysNodeList) {
                Integer dateNum = getDateNum(sysNode2);
                sysNode2.setNumber(dateNum);
//                if("全部场景".equals(sysNode2.getName())){
//                    num = num+dateNum;
//                }
                num = num + dateNum;
            }
        }
        return num;
    }
}
