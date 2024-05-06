package com.sakura.system.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wutun
 */
@Data
public class Config implements Serializable {

    private String paramsName;

    private String paramsValue;
}
