package com.sakura.system.domain.vo;

import com.sakura.system.domain.Step;
import com.sakura.system.domain.SysSceneCase;
import lombok.Data;

/**
 * @author wutun
 */
@Data
public class StepVo {
    private String id;

    private Step step;

    private Integer sortType;

    private Integer itemOrder;
}
