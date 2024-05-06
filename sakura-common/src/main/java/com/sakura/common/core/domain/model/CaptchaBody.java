package com.sakura.common.core.domain.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 刘智
 * @date 2021/6/15
 * @Description:
 */

@Data
@ApiModel(value = "验证码对象模型")
public class CaptchaBody {
    @ApiModelProperty(value = "唯一标识", required = true, example = "1d2d249387cc4572a27271d04294e656")
    private String uuid;

    @ApiModelProperty(value = "图片地址",required = true, example = "data:image/gif;base64,xxxxx")
    private String img;
}