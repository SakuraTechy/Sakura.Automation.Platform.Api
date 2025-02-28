package com.sakura.system.domain.vo;

import com.sakura.system.domain.SysSceneCase;
import lombok.Data;

/**
 * @author wutun
 */
@Data
public class SceneCaseVo {

    /**
     * 场景ID
     */
    private String id;
    private String copyId;
    /**
     * 用例信息
     */
    private SysSceneCase sysSceneCase;

    private Integer sortType;

    private Integer itemOrder;
}
