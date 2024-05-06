package com.sakura.system.domain;

import com.sakura.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 版本管理表 sys_version
 *
 * @author wutun
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class SysVersion extends BaseEntity<SysVersion> {

  private static final long serialVersionUID = 1L;
  public static final String IS_LATEST_TRUE = "0";
  public static final String IS_LATEST_FALSE = "1";
  public static final String DEFAULT_VERSION = "V1.0.0";

  private String versionName;

  private String status;

  private String latestVersion;

  private String isLatest;

  private String projectId;


  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date startTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date endTime;


  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date publishTime;

  private String description;


  @Override
  public String toString() {
    return  new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("versionName", getVersionName())
            .append("status", getStatus())
            .append("isLatest",getIsLatest())
            .append("projectId",getProjectId())
            .append("startTime",getStartTime())
            .append("endTime",getEndTime())
            .append("publishTime",getPublishTime())
            .append("createTime",getCreateTime())
            .append("createBy",getCreateBy())
            .append("description",getDescription())
            .toString();

  }
}
