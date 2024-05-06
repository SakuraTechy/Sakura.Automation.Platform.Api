package com.sakura.system.domain.vo;

import com.sakura.system.domain.Step;
import com.sakura.system.domain.SysSceneCase;
import lombok.Data;

/**
 * @author wutun
 */
@Data
public class DragVo {
    private String id;

    private Integer itemOrder;

    private Integer sortType;

    private SysSceneCase sysSceneCase;

    private Step step;
}
