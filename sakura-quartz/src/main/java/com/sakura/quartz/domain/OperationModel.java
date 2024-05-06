package com.sakura.quartz.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qx
 * @date 2023/11/27
 * @des 操作类型实体
 */
@Data
public class OperationModel implements Serializable {

    private String jobName;

    private String jobGroup;

}