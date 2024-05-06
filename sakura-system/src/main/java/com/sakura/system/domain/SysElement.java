package com.sakura.system.domain;

import com.sakura.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 元素库
 * @author wutun
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "元素库对象模型")
public class SysElement extends BaseEntity<SysElement>{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "元素名称")
    private String elementName;

    @ApiModelProperty(value = "定位类型")
    private String locationType;

    @ApiModelProperty(value = "元素定位")
    private String elementLocation;

    @ApiModelProperty(value = "所属模块Id")
    private String nodeId;
}
