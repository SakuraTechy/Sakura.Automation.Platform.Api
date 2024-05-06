package com.sakura.system.domain;

import lombok.Data;

/**
 * 服务器配置实体类
 * @author wutun
 */
@Data
public class SysServerConfig {

    private String id;

    /**
     * 主机
     */
    private String host;

    /**
     * 端口号
     */
    private String port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 选中状态
     */
    private String checked;
}
