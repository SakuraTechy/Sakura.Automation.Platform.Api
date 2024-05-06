package com.sakura.system.domain;

import lombok.Data;

import java.util.List;

/**
 * 步骤信息
 * @author wutun
 */
@Data
public class StepMsg {

    private String preCondition;

    private List<Step> stepList;
}
