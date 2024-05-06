package com.sakura.test.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sakura.common.core.domain.entity.SysUser;
import com.sakura.common.exception.BizException;
import com.sakura.common.utils.StringUtils;
import com.sakura.system.common.SysErrorCode;
import com.sakura.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.pagehelper.PageInfo;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.core.service.BaseServiceImpl;
import com.sakura.test.mapper.TestPlanMapper;
import com.sakura.test.domain.TestPlan;
import com.sakura.test.service.TestPlanService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * 测试管理-测试计划表Service业务层处理
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2023-11-02
 */
@Service
@Transactional(readOnly = false)
public class TestPlanServiceImpl extends BaseServiceImpl<TestPlanMapper, TestPlan> implements TestPlanService {

    private static final Logger log = LoggerFactory.getLogger(TestPlanServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Override
    public void refreshCache() {
    }

    @Override
    public void loadingUserCache() {
    }

    @Override
    public void checkUserDataScope(String userId) {
    }

    /**
     * 获取单条数据
     *
     * @param testPlan 测试管理-测试计划表
     * @return 测试管理-测试计划表
     */
    @Override
    public TestPlan get(TestPlan testPlan) {
        TestPlan dto = super.get(testPlan);
        dto.setMemberList(JSONArray.parseArray(dto.getMembers(), SysUser.class));
        dto.setPrincipalList(JSONArray.parseArray(dto.getPrincipals(), SysUser.class));
        return dto;
    }

    /**
     * 获取单条数据
     *
     * @param id 测试管理-测试计划表id
     * @return 测试管理-测试计划表
     */
    @Override
    public TestPlan get(String id) {
        TestPlan testPlan = super.get(id);
        testPlan.setMemberList(JSONArray.parseArray(testPlan.getMembers(), SysUser.class));
        testPlan.setPrincipalList(JSONArray.parseArray(testPlan.getPrincipals(), SysUser.class));
        return testPlan;
    }

    /**
     * 查询测试管理-测试计划表列表
     *
     * @param testPlan 测试管理-测试计划表
     * @return 测试管理-测试计划表
     */
    @Override
    public List<TestPlan> findList(TestPlan testPlan) {
        List<TestPlan> testPlanList = super.findList(testPlan);
        testPlanList.forEach((e) -> {
            e.setMemberList(JSONArray.parseArray(e.getMembers(), SysUser.class));
            e.setPrincipalList(JSONArray.parseArray(e.getPrincipals(), SysUser.class));
        });
        return testPlanList;
    }

    /**
     * 分页查询测试管理-测试计划表列表
     *
     * @param testPlan 测试管理-测试计划表
     * @return 测试管理-测试计划表
     */
    @Override
    public PageInfo<TestPlan> findPage(TestPlan testPlan) {
        PageInfo<TestPlan> page = super.findPage(testPlan);
        page.getList().forEach((e) -> {
            e.setMemberList(JSONArray.parseArray(e.getMembers(), SysUser.class));
            e.setPrincipalList(JSONArray.parseArray(e.getPrincipals(), SysUser.class));
        });
        return page;
    }

    /**
     * 分页查询测试管理-测试计划-测试场景
     *
     * @param testPlan 测试管理-测试计划
     * @return 测试管理-测试计划-测试场景
     */
    @Override
    public Map<String,Object> findTestScenePage(TestPlan testPlan, List<?> list) {
        int pageNum = testPlan.getTestScene().getPageNum();
        int pageSize = testPlan.getTestScene().getPageSize();
        int pages = (list.size() + pageSize - 1) / pageSize; // 计算总页数

        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, list.size());
        Map<String, Object> map = new HashMap<>();
        map.put("total", list.size());
        map.put("list",list.subList(startIndex, endIndex));
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        map.put("pages", pages);
        return map;
    }

    /**
     * 保存测试管理-测试计划表
     *
     * @param testPlan
     * @return 结果
     */
    @Override
    public boolean save(TestPlan testPlan) {
        if (testPlan.getIsNewRecord() && !CollectionUtils.isEmpty(mapper.findListWithUnique(testPlan))) {
            throw new BizException(SysErrorCode.B_TEST_PLAN_NameAlreadyExist);
        }
        List<SysUser> sysUserList = new ArrayList<>();
        if (StringUtils.isNotEmpty(testPlan.getMemberIds())) {
            for (String userId : testPlan.getMemberIds()) {
                SysUser sysUser = userService.get(userId);
                sysUserList.add(sysUser);
            }
            testPlan.setMembers(JSON.toJSONString(sysUserList));
        }
        if (StringUtils.isNotEmpty(testPlan.getPrincipalIds())) {
            sysUserList = new ArrayList<>();
            for (String userId : testPlan.getPrincipalIds()) {
                SysUser sysUser = userService.get(userId);
                sysUserList.add(sysUser);
            }
            testPlan.setPrincipals(JSON.toJSONString(sysUserList));
        }
        if (StringUtils.isNotNull(testPlan.getExecuteConfig())) {
            testPlan.setTimedTasksConfig(JSON.toJSONString(testPlan.getExecuteConfig()));
        }
        return super.save(testPlan);
    }

    /**
     * 删除测试管理-测试计划表信息
     *
     * @param testPlan
     * @return 结果
     */
    @Override
    public boolean remove(TestPlan testPlan) {
        if (StringUtils.isNotNull(testPlan.getIds())) {
            for (String id : testPlan.getIds()) {
                testPlan = mapper.get(id);
                if (StringUtils.isNotEmpty(testPlan.getUiTestScene())&&!StringUtils.equals(testPlan.getUiTestScene(),"[]")){
                    throw new BizException(SysErrorCode.B_TEST_PLAN_DeleteFailed, id);
                }
                testPlan.setId(id);
                super.remove(testPlan);
            }
        }
        return true;
    }

    /**
     * 批量删除测试管理-测试计划表
     *
     * @param ids 需要删除的测试管理-测试计划表ID
     * @return 结果
     */
    @Transactional(readOnly = false)
    @Override
    public int deleteTestPlanByIds(String[] ids) {
        return mapper.deleteTestPlanByIds(ids, BaseEntity.DEL_FLAG_DELETE);
    }

    /**
     * 删除测试管理-测试计划表信息
     *
     * @param testPlan
     * @return 结果
     */
    @Override
    public boolean addTestScene(TestPlan testPlan) {
        List<TestPlan.TestScene.UI> uiList = testPlan.getTestScene().getUiList();
        if (StringUtils.isEmpty(uiList)) {
            List<String> list = new ArrayList<>();
            for (TestPlan.TestScene.UI ui : uiList) {
                list.add(ui.getId());
            }
            testPlan.setUiTestScene(list.toString());
        }
        return  super.save(testPlan);
    }
}