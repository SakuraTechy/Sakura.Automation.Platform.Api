package com.sakura.system.domain;


import com.alibaba.fastjson.annotation.JSONField;
import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.core.domain.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 项目管理表 sys_project
 *
 * @author wutun
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysProject extends BaseEntity<SysProject> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "项目序号", required = true, example = "1")
    @JSONField(ordinal = 1)
    private Integer index;

    @ApiModelProperty(value = "项目名称", required = true, example = "1")
    @JSONField(ordinal = 2)
    private String projectName;

    @ApiModelProperty(value = "项目描述", required = true, example = "1")
    @JSONField(ordinal = 3)
    private String description;

    @ApiModelProperty(value = "项目状态", required = true, example = "1")
    @JSONField(ordinal = 4)
    private Integer status;

    /**
     * 项目下的多个成员
     */
    private List<SysUser> members;

    private String[] memberIds;

    /**
     * 最新版本
     */
    private String latestVersion;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("projectName", getProjectName())
                .append("description", getDescription())
                .append("member", getMembers())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .toString();
    }
}
