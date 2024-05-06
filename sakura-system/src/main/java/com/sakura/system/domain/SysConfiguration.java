package com.sakura.system.domain;

import com.sakura.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 配置管理表 sys_configuration
 * @author wutun
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysConfiguration extends BaseEntity<SysConfiguration> {

  private static final long serialVersionUID = 1L;

  private String id;

  private String configName;

  private String paramsName;

  private String paramsValue;


}
