-- liquibase formatted sql

-- changeset chengzi:1
-- comment 初始化能力开放插件
-- 初始化表结构
CREATE TABLE IF NOT EXISTS "sys_app" (
    "id"              int8         DEFAULT NULL,
    "name"            varchar(255) DEFAULT NULL,
    "app_key"         varchar(255) DEFAULT NULL,
    "app_secret"      varchar(255) DEFAULT NULL,
    "status"          varchar(255) DEFAULT NULL,
    "expiration_time" timestamp    DEFAULT NULL,
    "app_desc"        varchar(255) DEFAULT NULL,
    "secret_status"   varchar(255) DEFAULT NULL,
    "create_user"     int8         NOT NULL,
    "create_time"     timestamp    NOT NULL,
    "update_user"     int8         NOT NULL,
    "update_time"     timestamp    NOT NULL,
    PRIMARY KEY ("id")
);
CREATE INDEX "idx_app_create_user" ON "sys_app" ("create_user");
CREATE INDEX "idx_app_update_user" ON "sys_app" ("update_user");
COMMENT ON COLUMN "sys_app"."id"              IS 'ID';
COMMENT ON COLUMN "sys_app"."name"            IS '名称';
COMMENT ON COLUMN "sys_app"."app_key"         IS '应用密钥';
COMMENT ON COLUMN "sys_app"."app_secret"      IS '应用密码';
COMMENT ON COLUMN "sys_app"."status"          IS '应用状态 (0: 未激活；1: 激活)';
COMMENT ON COLUMN "sys_app"."expiration_time" IS '失效时间';
COMMENT ON COLUMN "sys_app"."app_desc"        IS '应用描述';
COMMENT ON COLUMN "sys_app"."secret_status"   IS '应用密码查看状态 (0: 未查看；1: 已查看)';
COMMENT ON COLUMN "sys_app"."create_user"     IS '创建人';
COMMENT ON COLUMN "sys_app"."create_time"     IS '创建时间';
COMMENT ON COLUMN "sys_app"."update_user"     IS '修改人';
COMMENT ON COLUMN "sys_app"."update_time"     IS '修改时间';
COMMENT ON TABLE  "sys_app"                   IS '应用表';

-- 初始化默认菜单
INSERT INTO "sys_menu"
("id", "title", "parent_id", "type", "path", "name", "component", "redirect", "icon", "is_external", "is_cache", "is_hidden", "permission", "sort", "status", "create_user", "create_time", "update_user", "update_time")
VALUES
(7000, '能力开放', 0, 1, '/open', 'Open', 'Layout', '/open/app', 'expand', false, false, false, NULL, 7, 1, 1, NOW(), NULL, NULL),
(7010, '应用管理', 7000, 2, '/open/app', 'OpenApp', 'open/app/index', NULL, 'common', false, false, false, NULL, 1, 1, 1, NOW(), NULL, NULL),
(7011, '列表', 7010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'open:app:list', 1, 1, 1, NOW(), NULL, NULL),
(7012, '详情', 7010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'open:app:detail', 2, 1, 1, NOW(), NULL, NULL),
(7013, '新增', 7010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'open:app:add', 3, 1, 1, NOW(), NULL, NULL),
(7014, '修改', 7010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'open:app:update', 4, 1, 1, NOW(), NULL, NULL),
(7015, '删除', 7010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'open:app:delete', 5, 1, 1, NOW(), NULL, NULL),
(7016, '导出', 7010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'open:app:export', 6, 1, 1, NOW(), NULL, NULL);

