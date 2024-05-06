package com.sakura.system.domain;

import lombok.Data;
import org.yaml.snakeyaml.events.Event;

/**
 * 环境配置 通用配置实体类
 * @author wutun
 */

@Data
public class SysCommonConfig {

    private String id;

    private String ip;

    private String domain;

    private String description;

    /**
     * 状态
     */
    private String checked;
}
