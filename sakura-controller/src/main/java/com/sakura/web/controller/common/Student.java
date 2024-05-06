package com.sakura.web.controller.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Map;

/**
 * 学生实体类
 */
@EntityScan
@Data
@ApiModel("学生实体类")
public class Student {
 
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String id;
 
    @ApiModelProperty(value = "姓名",example="张三")
    private String name;

    @ApiModelProperty(value = "年龄")
    private Integer age;
 
    /**
     * 性别，male：男性，female：女性
     */
    @ApiModelProperty(value = "性别(male：男性，female：女性)",example = "male")
    private String gender;
}