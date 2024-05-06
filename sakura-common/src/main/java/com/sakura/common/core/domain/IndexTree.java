/*
 * Copyright (C) 2018 Zhejiang xiaominfo Technology CO.,LTD.
 * All rights reserved.
 * Official Web Site: http://www.xiaominfo.com.
 * Developer Web Site: http://open.xiaominfo.com.
 */

package com.sakura.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 用于描述一个数据模型的信息，即我们常用的实体、VO类、DTO类等描述
 * value–表示对象名 
 * description–描述
 * parent：父类
 * 
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2021/05/04 15:46
 * @since:knife4j-spring-boot-demo 1.0
 */
@ApiModel(value="IndexTree对象",description="IndexTree对象")
public class IndexTree {
    @ApiModelProperty(name = "children", value = "子菜单")
    private List<IndexTree> children;

    @ApiModelProperty(name = "dimensionList", value = "维度")
    private List<DicDimension> dimensionList;

    @ApiModelProperty(name = "id", value = "ID")
    private Long id;

    @ApiModelProperty(name = "code", value = "代码")
    private String code;

    @ApiModelProperty(name = "description", value = "描述")
    private String description;

    @ApiModelProperty(name = "parent", value = "父级")
    private Long parent;

    @ApiModelProperty(name = "type", value = "类型")
    private String type;

    @ApiModelProperty(name = "typeDesc", value = "类型描述")
    private String typeDesc;

    public List<IndexTree> getChildren() {
        return children;
    }

    public void setChildren(List<IndexTree> children) {
        this.children = children;
    }

    public List<DicDimension> getDimensionList() {
        return dimensionList;
    }

    public void setDimensionList(List<DicDimension> dimensionList) {
        this.dimensionList = dimensionList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
}
