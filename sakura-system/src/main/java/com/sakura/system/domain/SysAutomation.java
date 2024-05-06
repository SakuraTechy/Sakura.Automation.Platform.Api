package com.sakura.system.domain;

import com.sakura.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wutun
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "自动化场景对象模型")
public class SysAutomation extends BaseEntity<SysAutomation> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "场景名称" ,required = true )
    private String name;

    @ApiModelProperty(value = "用例等级" ,required = true )
    private String level;

    @ApiModelProperty(value = "状态" , required = true)
    private String status;

    @ApiModelProperty(value = "标签" ,required = true)
    private String label;

    @ApiModelProperty(value = "责任人id" ,required = true)
    private String userId;

    @ApiModelProperty(value = "责任人名称")
    private String userName;

    @ApiModelProperty(value = "步骤数" )
    private String stepTotal;

    @ApiModelProperty(value = "最后结果" )
    private String result;

    @ApiModelProperty(value = "通过率")
    private String passPercent;

    @ApiModelProperty(value = "项目ID" )
    private String projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "版本ID" )
    private String versionId;

    @ApiModelProperty(value = "版本名称" )
    private String versionName;

    @ApiModelProperty(value = "场景步骤")
    private String sceneStep;

    @ApiModelProperty(value = "节点ID")
    private String nodeId;

    private String caseMsg;
}
