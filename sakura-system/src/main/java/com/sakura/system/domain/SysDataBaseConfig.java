package com.sakura.system.domain;

import lombok.Data;

/**
 * 环境配置 数据库配置实体类
 * @author wutun
 */
@Data
public class SysDataBaseConfig {

    private String id;

    private String dataSourceName;

    private String driver;

    private String url;

    private String username;

    private String password;

    private Integer maxActive;

    private Integer timeOut;

    private String checked;
}
