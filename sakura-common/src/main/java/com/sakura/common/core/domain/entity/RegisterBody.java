package com.sakura.common.core.domain.entity;

import com.sakura.common.core.domain.model.LoginBody;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户注册对象
 *
 * @author liuzhi
 */
@Data
@ApiModel(value = "用户注册对象模型")
public class RegisterBody extends LoginBody {
    @ApiModelProperty(value = "姓名", required = true, example = "超管")
    private String name;

    @ApiModelProperty(value = "昵称", required = false, example = "admin")
    private String nickName;

    @ApiModelProperty(value = "用户编号", required = true, example = "000000")
    private String no;

    @ApiModelProperty(value = "部门编号", required = true, example = "100")
    private String deptId;

    @ApiModelProperty(value = "岗位组(1-董事长,2-项目经理,3-人力资源,4-普通员工)", required = true, example = "['1','2','3']")
    private String[] postIds;

    @ApiModelProperty(value = "用户类型(1-后台,2-前台)", required = true, example = "1")
    private String userType;

    @ApiModelProperty(value = "角色组(0-超级管理员,1-项目管理员,2-系统管理员,3-一般用户)", required = true, example = "['0','1','2']")
    private String[] roleIds;

    @ApiModelProperty(value = "用户邮箱", required = true, example = "123@qq.com")
    private String email;

    @ApiModelProperty(value = "手机号码", required = true, example = "18688888888")
    private String phoneNumber;

    @ApiModelProperty(value = "用户性别(0=男,1=女,2=未知)", required = false, example = "0")
    private String sex;

    @ApiModelProperty(value = "帐号状态(0-正常,1-停用)", required = false, example = "0")
    private String status;
}