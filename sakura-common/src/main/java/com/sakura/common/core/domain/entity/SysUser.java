package com.sakura.common.core.domain.entity;

import com.sakura.common.annotation.Excel;
import com.sakura.common.annotation.Excel.Type;
import com.sakura.common.annotation.Excels;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.utils.spring.SpringUtils;
import com.sakura.common.xss.Xss;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author liuzhi
 */
@Data
@ApiModel(value = "用户对象模型")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity<SysUser> {
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "姓名", required = true, example = "超管")
    @Excel(name = "姓名")
    private String name;

    @ApiModelProperty(value = "登录名", required = true, example = "admin")
    @Excel(name = "登录名")
    private String userName;

    @ApiModelProperty(value = "用户编号", required = true, example = "000000")
    @Excel(name = "用户编号")
    private String no;

    @ApiModelProperty(value = "部门编号", required = true, example = "100")
    @Excel(name = "部门编号", type = Type.IMPORT)
    private String deptId;

    @ApiModelProperty(value = "岗位组(1-董事长,2-项目经理,3-人力资源,4-普通员工)", required = true, example = "['1','2','3']")
    private String[] postIds;

    @ApiModelProperty(value = "用户类型(0-后台,1-前台)", required = true, example = "0")
    @Excel(name = "用户类型", dictType = "sys_user_type")
    private String userType;

    @ApiModelProperty(value = "角色组(0-超级管理员,1-项目管理员,2-系统管理员,3-一般用户)", required = true, example = "['0','1','2']")
    private String[] roleIds;

    // @ApiModelProperty(value = "用户角色", required = true, example = "0")
    private String roleId;

    @ApiModelProperty(value = "用户姓名或登录名", required = true, example = "超管/admin")
    private String userNameOrName;

    @ApiModelProperty(value = "部门编号", required = false, example = "SysDept")
    @Excels({
        @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
        @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    private SysDept sysDept;

    @ApiModelProperty(value = "角色对象", required = false, example = "List<SysRole>")
    private List<SysRole> sysRoles;

    @ApiModelProperty(value = "英文名称", required = false, example = "admin")
    @Excel(name = "英文名称")
    private String nameEn;

    @ApiModelProperty(value = "昵称", required = false, example = "admin")
    @Excel(name = "昵称")
    private String nickName;

    @ApiModelProperty(value = "用户邮箱", required = false, example = "123@qq.com")
    @Excel(name = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码", required = false, example = "18688888888")
    @Excel(name = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "sex(0=男,1=女,2=未知)", required = false, example = "0")
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    @ApiModelProperty(value = "用户头像", required = false, example = "http://localhost:8081/img/profile.473f5971.jpg")
    private String avatar;
    
    @ApiModelProperty(value = "用户密码", required = false, example = "123456")
    private String passWord;
    
    @ApiModelProperty(value = "用户生日", required = false, example = "19990909")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    
    @ApiModelProperty(value = "用户民族", required = false, example = "汉")
    private String nation;
    
    @ApiModelProperty(value = "用户籍贯", required = false, example = "广东省")
    private String birthAddress;

    @ApiModelProperty(value = "政治面貌", required = false, example = "团员")
    private String polity;

    @ApiModelProperty(value = "职称", required = false, example = "测试开发工程师")
    private String title;

    @ApiModelProperty(value = "办公电话", required = false, example = "07558888888")
    private String officeTel;

    @ApiModelProperty(value = "传真号", required = false, example = "07558888888")
    private String fax;

    @ApiModelProperty(value = "工作地点", required = false, example = "广东深圳南山区")
    private String workSpace;

    @ApiModelProperty(value = "排序号", required = false, example = "1")
    private Integer sort;

    @ApiModelProperty(value = "用户姓名全拼和简拼", required = false, example = "chaoguan")
    private String userPinyin;

    @ApiModelProperty(value = "盐加密", required = false, example = "")
    private String salt;

    @ApiModelProperty(value = "帐号状态(0-正常,1-停用)", required = false, example = "0")
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;
 
    @ApiModelProperty(value = "删除标志(0-代表存在,2-代表删除)", required = false, example = "0")
    private String delFlag;

    @ApiModelProperty(value = "最后登录IP", required = false, example = "127.0.0.1")
    @Excel(name = "最后登录IP", type = Type.EXPORT)
    private String loginIp;

    @ApiModelProperty(value = "最后登录时间", required = false, example = "2021-12-07 10:17:34")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date loginDate;
    
    public SysUser() {

    }

    public SysUser(String id) {
        setId(id);
    }

    /**
     * 增加getUserId和setUserId方法为了适配原有代码中主键为user_id
     *
     * @return
     */

    public boolean isAdmin() {
        return isAdmin(getId());
    }

    public static boolean isAdmin(String id) {
        // isNotBlank判断某字符串是否不为空且长度不为0且不由空白符(whitespace)构成，等于!isBlank
        return StringUtils.isNotBlank(id) && "1".equals(id);
    }

    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    @Xss(message = "用户账号不能包含脚本字符")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    @Xss(message = "用户账号不能包含脚本字符")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber() {
        return phoneNumber;
    }

    public void setPhonenumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static void getMenuPermissions(SysUser user) {
        SpringUtils.getBean(SysUserMenu.class).setUserMenu(user);
    }

    @JsonIgnore
    @JsonProperty
    public String getPassword() {
        return passWord;
    }

    public void setPassword(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("deptId", getDeptId())
                .append("userName", getUserName())
                .append("nickName", getNickName())
                .append("email", getEmail())
                .append("phoneNumber", getPhonenumber())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("passWord", getPassword())
                .append("salt", getSalt())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("dept", getSysDept())
                .toString();
    }
}
