-- liquibase formatted sql

-- changeset kai:1
-- comment 初始化任务调度插件
-- 初始化默认菜单
INSERT INTO `sys_menu`
(`id`, `title`, `parent_id`, `type`, `path`, `name`, `component`, `redirect`, `icon`, `is_external`, `is_cache`, `is_hidden`, `permission`, `sort`, `status`, `create_user`, `create_time`, `update_user`, `update_time`)
VALUES
(3000, '任务调度', 0, 1, '/schedule', 'Schedule', 'Layout', '/schedule/job', 'schedule', b'0', b'0', b'0', NULL, 3, 1, 1, NOW(), NULL, NULL),
(3010, '任务管理', 3000, 2, '/schedule/job', 'ScheduleJob', 'schedule/job/index', NULL, 'select-all', b'0', b'0', b'0', NULL, 1, 1, 1, NOW(), NULL, NULL),
(3011, '列表', 3010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'schedule:job:list', 1, 1, 1, NOW(), NULL, NULL),
(3012, '详情', 3010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'schedule:job:detail', 2, 1, 1, NOW(), NULL, NULL),
(3013, '新增', 3010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'schedule:job:add', 3, 1, 1, NOW(), NULL, NULL),
(3014, '修改', 3010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'schedule:job:update', 4, 1, 1, NOW(), NULL, NULL),
(3015, '删除', 3010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'schedule:job:delete', 5, 1, 1, NOW(), NULL, NULL),
(3016, '执行', 3010, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'schedule:job:trigger', 6, 1, 1, NOW(), NULL, NULL),
(3020, '任务日志', 3000, 2, '/schedule/log', 'ScheduleLog', 'schedule/log/index', NULL, 'find-replace', b'0', b'0', b'0', NULL, 2, 1, 1, NOW(), NULL, NULL),
(3021, '列表', 3020, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'schedule:log:list', 1, 1, 1, NOW(), NULL, NULL),
(3022, '详情', 3020, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'schedule:log:detail', 2, 1, 1, NOW(), NULL, NULL),
(3023, '停止', 3020, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'schedule:log:stop', 3, 1, 1, NOW(), NULL, NULL),
(3024, '重试', 3020, 3, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'schedule:log:retry', 4, 1, 1, NOW(), NULL, NULL);
