-- liquibase formatted sql

-- changeset chengzi:1
-- comment 初始化能力开放插件
-- 初始化表结构
CREATE TABLE IF NOT EXISTS `sys_app`  (
    `id`              bigint(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`            varchar(255) DEFAULT NULL            COMMENT '应用名称',
    `app_key`         varchar(255) DEFAULT NULL            COMMENT '应用密钥',
    `app_secret`      varchar(255) DEFAULT NULL            COMMENT '应用密码',
    `status`          varchar(255) DEFAULT NULL            COMMENT '应用状态 (0: 未激活；1: 激活)',
    `expiration_time` datetime     DEFAULT NULL            COMMENT '失效时间',
    `app_desc`        varchar(255) DEFAULT NULL            COMMENT '应用描述',
    `secret_status`   varchar(255) DEFAULT NULL            COMMENT '应用密码查看状态 (0: 未查看；1: 已查看)',
    `create_user`     bigint(20)   NOT NULL                COMMENT '创建人',
    `create_time`     datetime     NOT NULL                COMMENT '创建时间',
    `update_user`     bigint(20)   DEFAULT NULL            COMMENT '修改人',
    `update_time`     datetime     DEFAULT NULL            COMMENT '修改时间',
    PRIMARY KEY (`id`),
    INDEX `idx_create_user`(`create_user`),
    INDEX `idx_update_user`(`update_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应用表';

-- 初始化默认菜单
INSERT INTO `sys_menu`
(`id`, `title`, `parent_id`, `type`, `path`, `name`, `component`, `redirect`, `icon`, `is_external`, `is_cache`, `is_hidden`, `permission`, `sort`, `status`, `create_user`, `create_time`, `update_user`, `update_time`)
VALUES
(7000, '能力开放', 0, 1, '/open', 'Open', 'Layout', '/open/app', 'expand', b'0', b'0', b'0', NULL, 7, 1, 1, NOW(), NULL, NULL),
(7010, '应用管理', 7000, 2, '/open/app', 'OpenApp', 'open/app/index', NULL, 'common', b'0', b'0', b'0', NULL, 1, 1, 1, NOW(), NULL, NULL),
(7011, '列表', 7010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'open:app:list', 1, 1, 1, NOW(), NULL, NULL),
(7012, '详情', 7010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'open:app:detail', 2, 1, 1, NOW(), NULL, NULL),
(7013, '新增', 7010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'open:app:add', 3, 1, 1, NOW(), NULL, NULL),
(7014, '修改', 7010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'open:app:update', 4, 1, 1, NOW(), NULL, NULL),
(7015, '删除', 7010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'open:app:delete', 5, 1, 1, NOW(), NULL, NULL),
(7016, '导出', 7010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'open:app:export', 6, 1, 1, NOW(), NULL, NULL);

