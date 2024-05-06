package com.sakura.project.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.sakura.common.annotation.Excel;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.core.domain.entity.SysUser;
import com.sakura.common.utils.log.annotation.FieldRemark;
import com.sakura.common.utils.log.annotation.LogField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 项目配置对象 project_config
 *
 * @author 刘智
 * @email liuzhi@sakura.com
 * @date 2022-12-22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectConfig extends BaseEntity<ProjectConfig> {
    private static final long serialVersionUID = 1L;

    /** 项目ID */
    @LogField
    @Excel(name = "项目ID")
    @JSONField(ordinal = 1)
    @FieldRemark(name = "项目ID", field = "id")
    @ApiModelProperty(value = "项目ID", required = true, example = "1")
    private String id;

    /** 项目序号 */
    private Integer index;

    /** 项目名称 */
    @Excel(name = "项目名称")
    @LogField
    @FieldRemark(name = "项目名称", field = "name")
    @JSONField(ordinal = 2)
    private String name;

    /** 项目简称 */
    @Excel(name = "项目简称")
    @LogField
    @FieldRemark(name = "项目简称", field = "abbreviate")
    @JSONField(ordinal = 3)
    private String abbreviate;

    /** 项目描述 */
    @Excel(name = "项目描述")
    @LogField
    @FieldRemark(name = "项目描述", field = "description")
    @JSONField(ordinal = 4)
    private String description;

    /** 项目最新域名 */
    @Excel(name = "项目最新域名")
    @LogField
    @FieldRemark(name = "项目最新域名", field = "lastDomain")
    @JSONField(ordinal = 5)
    private String lastDomain;

    /** 项目最新版本 */
    @Excel(name = "项目最新版本")
    @LogField
    @FieldRemark(name = "项目最新版本", field = "lastVersion")
    @JSONField(ordinal = 6)
    private String lastVersion;

    /** 状态 (0关闭 1开启) */
    @Excel(name = "状态 (0关闭 1开启)", dictType = "sys_normal_disable")
    @LogField
    @FieldRemark(name = "状态 (0关闭 1开启)", field = "status")
    @JSONField(ordinal = 7)
    private Integer status;

    @Excel(name = "创建开始时间")
    @JSONField(ordinal = 8)
    private String createStartTime;
    @Excel(name = "创建结束时间")
    @JSONField(ordinal = 9)
    private String createEndTime;

    @Excel(name = "项目成员")
    @JSONField(ordinal = 12)
    private String members;
    private String[] memberIds;
    private List<SysUser> memberList;

    @Excel(name = "批量删除的ID")
    @LogField
    @FieldRemark(name = "批量删除的ID", field = "ids")
    @JSONField(ordinal = 13)
    private String[] ids;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("abbreviate", getAbbreviate())
                .append("description", getDescription())
                .append("lastDomain", getLastDomain())
                .append("lastVersion", getLastVersion())
                .append("status", getStatus())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createByName", getCreateByName())
                .append("createDept", getCreateDept())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateByName", getUpdateByName())
                .append("updateTime", getUpdateTime())
                .append("updateIp", getUpdateIp())
                .append("version", getVersion())
                .append("delFlag", getDelFlag())
                .toString();
    }
}