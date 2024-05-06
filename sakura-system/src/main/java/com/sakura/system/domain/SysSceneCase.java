package com.sakura.system.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.sakura.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author wutun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysSceneCase implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "场景用例id")
    private String id;
    private String copyId;

    @ApiModelProperty(value = "场景用例名称")
    private String name;

    @ApiModelProperty(value = "取消状态")
    private String cancel;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    private String stepMsg;

    private List<Step> stepList;

    private Integer order;
}
