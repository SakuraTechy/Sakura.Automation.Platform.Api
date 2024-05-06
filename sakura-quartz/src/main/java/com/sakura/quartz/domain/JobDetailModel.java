package com.sakura.quartz.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qx
 * @date 2023/11/27
 * @des 定时内容实体
 */
@Data
public class JobDetailModel implements Serializable {

    private String content;

    private Integer type;

}
