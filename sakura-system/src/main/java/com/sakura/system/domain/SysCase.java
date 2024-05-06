package com.sakura.system.domain;

import com.sakura.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 测试用例表 sys_case
 *
 * @author wutun
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysCase extends BaseEntity<SysCase> {

  private static final long serialVersionUID = 1L;

  private String caseName;

  private String versionId;

  private String versionName;

  private String nodeId;

  private String nodeName;

  private String projectId;

  private String projectName;

  private String userId;

  private String userName;

  private String caseLevel;

  private String caseStatus;

  private String stepMsg;

  private String remark;

  private String refCaseId;

  private String refNeeds;

  private String file;

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("caseName",getCaseName())
            .append("versionId",getVersionId())
            .append("nodeId",getNodeId())
            .append("projectId",getProjectId())
            .append("userId",getUserId())
            .append("caseLevel",getCaseLevel())
            .append("caseStatus",getCaseStatus())
            .append("stepMsg",getStepMsg())
            .append("remark",getRemark())
            .append("refCaseId",getRefCaseId())
            .append("refNeeds",getRefNeeds())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
  }
}
