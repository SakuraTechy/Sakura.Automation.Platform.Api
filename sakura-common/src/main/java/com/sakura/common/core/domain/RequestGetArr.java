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
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2020/09/13 17:36
 * @since:knife4j-spring-boot-demo 1.0
 */
@ApiModel("RequestGetArr类")
public class RequestGetArr {
    @ApiModelProperty(value = "id集合", example = "id集合示例")
    private List<Integer> ids;
    @ApiModelProperty(value = "名称集合", example = "名称集合示例")
    private List<String> names;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
