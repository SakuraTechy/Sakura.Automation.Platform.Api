package com.sakura.system.service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.sakura.common.core.domain.entity.SysUser;
import com.sakura.common.core.domain.model.LoginBody;
import com.sakura.common.core.service.BaseService;
import com.sakura.system.domain.SysUserRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


/**
 * 用户业务层
 *
 * @author liuzhi
 */
//@SuppressWarnings("all")
public interface ISysUserService extends BaseService<SysUser> {

    @Override
    public PageInfo<SysUser> findPage(SysUser sysUser);

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    public boolean registerUser(SysUser user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    public SysUser selectUserByUserName(String userName);

    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    public String selectUserRoleGroup(String userName);

    /**
     * 根据用户ID查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    public String selectUserPostGroup(String userName);

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户名称
     * @return 结果
     */
    public void checkUserNameUnique(SysUser user);
    public String checkUserNameUniquen(SysUser user);


    /**
     * 检查用户名是否存在
     *
     * @param loginBody 登录的身体
     * @return {@link String}
     */
    public String checkWhetherUserNameExists(LoginBody loginBody);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public void checkPhoneUnique(SysUser user);

    public String checkPhoneUniquen(SysUser user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public void checkEmailUnique(SysUser user);

    public String checkEmailUniquen(SysUser user);

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    public void checkUserAllowed(SysUser user);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Transactional(readOnly = false)
    public boolean insertUser(SysUser user);

    public boolean insertUsern(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Transactional(readOnly = false)
    public boolean updateUser(SysUser user);

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Transactional(readOnly = false)
    public boolean updateUserStatus(SysUser user);

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Transactional(readOnly = false)
    public boolean updateUserProfile(SysUser user);


    /**
     * 记录登录信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Transactional(readOnly = false)
    public boolean updateUserLoginInfo(SysUser user);

    /**
     * 修改用户头像
     *
     * @param userName 用户名
     * @param avatar   头像地址
     * @return 结果
     */
    @Transactional(readOnly = false)
    public boolean updateUserAvatar(String userName, String avatar);

    /**
     * 重置用户密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return 结果
     */
    public int resetUserPwd(String userName, String password);

    /**
     * 修改用户密码
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    public int updatePassWord(LoginBody loginBody);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Transactional(readOnly = false)
    public boolean deleteUserById(String userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Transactional(readOnly = false)
    public int deleteUserByIds(String[] userIds);

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);

    /**
     * 通用选人页面根据用户ID
     *
     * @param userIdsObj
     * @return
     */
    public List<Map<String, Object>> getUserInfoByIds(JSONObject userIdsObj);

    /**
     * 获取角色下所有用户信息
     *
     * @param sysUser
     * @return
     */
    public PageInfo<SysUser> findRoleUserPage(SysUser sysUser);

    /**
     * 新增用户角色
     *
     * @param sysUserRole
     * @return
     */
    @Transactional(readOnly = false)
    public boolean insertRoleUser(SysUserRole sysUserRole);

    /**
     * 新增用户与岗位管理
     *
     * @param user
     */
    public void insertUserPost(SysUser user);

    /**
     * 新增用户与角色管理
     *
     * @param user
     */
    public void insertUserRole(SysUser user);

    /**
     * 刷新缓存
     * @return 结果
     */
    public void refreshCache();

    /**
     * 加载参数缓存数据
     */
    public void loadingUserCache();

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    public void checkUserDataScope(String userId);

}
