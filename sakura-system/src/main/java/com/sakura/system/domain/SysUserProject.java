package com.sakura.system.domain;

import com.sakura.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 项目用户关联表  sys_user_project
 * @author wutun
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserProject extends BaseEntity<SysUserProject> {

    /** 用户ID */
    private String userId;

    /** 岗位ID */
    private String projectId;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("postId", getProjectId())
                .toString();
    }
}
