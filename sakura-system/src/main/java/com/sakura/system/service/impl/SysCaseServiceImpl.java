package com.sakura.system.service.impl;

import com.sakura.common.core.page.PageDomain;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.common.exception.SysException;
import com.sakura.common.utils.StringUtils;
import com.sakura.common.utils.reflect.ReflectUtils;
import com.sakura.common.utils.sql.SqlUtil;
import com.sakura.system.domain.SysCase;
import com.sakura.system.domain.SysNode;
import com.sakura.system.mapper.SysCaseMapper;
import com.sakura.system.mapper.SysNodeMapper;
import com.sakura.system.service.SysCaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wutun
 */

@Service
public class SysCaseServiceImpl extends BaseServiceImpl<SysCaseMapper, SysCase> implements SysCaseService{

    @Autowired
    private SysNodeMapper sysNodeMapper;


    @Override
    public void refreshCache() {

    }

    @Override
    public void loadingUserCache() {

    }

    @Override
    public void checkUserDataScope(String userId) {

    }

    @Transactional(readOnly = false)
    @Override
    public boolean deleteSysCaseByIds(String[] ids) {
        return mapper.deleteSysCaseByIds(ids);
    }

    @Override
    public PageInfo<SysCase> findPage(SysCase entity) {
        PageDomain page = entity.getPage();
        Integer pageNum = page.getPageNum();
        Integer pageSize = page.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(page.getOrderBy());
            Boolean reasonable = page.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }
        return new PageInfo(findList(entity));
    }

    @Override
    public List<SysCase> findList(SysCase entity) {
        //如果为空，则调用反射方法动态实例化，否则传入参数为空时，无法查询出数据
        if(null == entity){
            Class<SysCase> entityClass = ReflectUtils.getClassGenricType(getClass(), 1);
            try {
                entity = entityClass.getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                throw new SysException(e.getMessage());
            }
        }
        //获取当前树节点下所有子节点数据
        List<SysCase> sysCaseList =new ArrayList<>();
        getCurrentNode(entity,sysCaseList);
        return sysCaseList;
    }

    /**
     * 获取当前节点下所有数据
     * @param sysCase
     * @param sysCaseList
     */
    public void getCurrentNode(SysCase sysCase,List sysCaseList){
        List<SysCase> list = mapper.findList(sysCase);
        sysCaseList.addAll(list);
        List<SysNode> sysNodeList =sysNodeMapper.findChildByParentId(sysCase.getNodeId());
        for (SysNode sysNode : sysNodeList) {
            sysCase.setNodeId(sysNode.getId());
            getCurrentNode(sysCase,sysCaseList);
        }
    }
}
