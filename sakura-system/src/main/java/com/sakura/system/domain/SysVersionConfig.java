package com.sakura.system.domain;

import lombok.Data;

/**
 * 环境配置 通用配置实体类
 * @author wutun
 */

@Data
public class SysVersionConfig {

    private String id;

    private String ip;

    private String domain;

    private String description;

    /**
     * 状态
     */
    private String checked;
}
