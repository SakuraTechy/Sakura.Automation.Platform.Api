package com.sakura.system.domain;

import com.sakura.common.core.domain.BaseEntity;
import com.sakura.common.core.domain.BaseTreeEntity;
import com.sakura.common.utils.log.annotation.FieldRemark;
import com.sakura.common.utils.log.annotation.LogField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 目录节点表 sys_node
 *
 * @author wutun
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysNode extends BaseTreeEntity<SysNode> {

  private static final long serialVersionUID = 1L;

  public static final String ROOT_NODE_CASE = "全部用例";

  public static final String ROOT_NODE_ELEMENT = "全部元素";

  public static final String ROOT_NODE_SCENE = "全部场景";
  /**
   * 文件夹创建默认名称
   */
  public static final String DEFAULT_FILE_NAME = "新建模板";

  private String name;

  private String parentId;

  private String projectId;

  private String versionId;

  private String versionName;

  private Integer number;

  @FieldRemark(name = "创建开始时间",field = "createStartTime")
  @LogField
  private String createStartTime;

  @FieldRemark(name = "创建结束时间",field = "createEndTime")
  @LogField
  private String createEndTime;
}
