package com.sakura.system.domain.vo;

import com.sakura.common.core.domain.BaseEntity;
import com.sakura.system.domain.Step;
import com.sakura.system.domain.SysScene;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author liuzhi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SceneVo extends BaseEntity<SceneVo> {

    List<SysScene> sysSceneList;

    private String id;

    private String projectId;

    private String projectName;

    private String moduleId;

    private String versionId;

    private String versionName;

    private String level;

    private String sceneId;

    private String name;

    private String copyId;

    private String status;

    private String debugRecord;
}
