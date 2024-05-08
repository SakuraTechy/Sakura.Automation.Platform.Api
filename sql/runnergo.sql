/*
 Navicat Premium Data Transfer

 Source Server         : 172.19.5.226（runnergo）
 Source Server Type    : MySQL
 Source Server Version : 50740
 Source Host           : 172.19.5.226:3307
 Source Schema         : runnergo

 Target Server Type    : MySQL
 Target Server Version : 50740
 File Encoding         : 65001

 Date: 08/05/2024 15:51:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auto_plan
-- ----------------------------
DROP TABLE IF EXISTS `auto_plan`;
CREATE TABLE `auto_plan`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划ID',
  `rank_id` bigint(10) NOT NULL DEFAULT 0 COMMENT '序号ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `plan_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划名称',
  `task_type` tinyint(2) NOT NULL DEFAULT 1 COMMENT '计划类型：1-普通任务，2-定时任务',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '计划状：1-未开始，2-进行中',
  `create_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人id',
  `run_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '运行人id',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `run_count` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '运行次数',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_plan_id`(`plan_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自动化测试-计划表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auto_plan_email
-- ----------------------------
DROP TABLE IF EXISTS `auto_plan_email`;
CREATE TABLE `auto_plan_email`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '计划ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自动化测计划—收件人邮箱表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auto_plan_report
-- ----------------------------
DROP TABLE IF EXISTS `auto_plan_report`;
CREATE TABLE `auto_plan_report`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `report_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '报告ID',
  `report_name` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '报告名称',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划ID',
  `rank_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '序号ID',
  `plan_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划名称',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `task_type` int(11) NOT NULL DEFAULT 0 COMMENT '任务类型',
  `task_mode` int(11) NOT NULL DEFAULT 0 COMMENT '运行模式：1-按测试用例运行',
  `control_mode` tinyint(2) NOT NULL DEFAULT 0 COMMENT '控制模式：0-集中模式，1-单独模式',
  `scene_run_order` tinyint(2) NOT NULL DEFAULT 1 COMMENT '场景运行次序：1-顺序执行，2-同时执行',
  `test_case_run_order` tinyint(2) NOT NULL DEFAULT 1 COMMENT '测试用例运行次序：1-顺序执行，2-同时执行',
  `run_duration_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '任务运行持续时长',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '报告状态1:进行中，2:已完成',
  `run_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '启动人id',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间（执行时间）',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_report_id`(`report_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自动化测试计划-报告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auto_plan_task_conf
-- ----------------------------
DROP TABLE IF EXISTS `auto_plan_task_conf`;
CREATE TABLE `auto_plan_task_conf`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '计划ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `task_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '任务类型：1-普通模式，2-定时任务',
  `task_mode` tinyint(2) NOT NULL DEFAULT 1 COMMENT '运行模式：1-按照用例执行',
  `scene_run_order` tinyint(2) NOT NULL DEFAULT 1 COMMENT '场景运行次序：1-顺序执行，2-同时执行',
  `test_case_run_order` tinyint(2) NOT NULL DEFAULT 1 COMMENT '用例运行次序：1-顺序执行，2-同时执行',
  `run_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '运行人用户ID',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_plan_id`(`plan_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自动化测试—普通任务配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auto_plan_timed_task_conf
-- ----------------------------
DROP TABLE IF EXISTS `auto_plan_timed_task_conf`;
CREATE TABLE `auto_plan_timed_task_conf`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '计划id',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `frequency` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务执行频次: 0-一次，1-每天，2-每周，3-每月',
  `task_exec_time` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务执行时间',
  `task_close_time` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务结束时间',
  `task_type` tinyint(2) NOT NULL DEFAULT 2 COMMENT '任务类型：1-普通任务，2-定时任务',
  `task_mode` tinyint(2) NOT NULL DEFAULT 1 COMMENT '运行模式：1-按照用例执行',
  `scene_run_order` tinyint(2) NOT NULL DEFAULT 1 COMMENT '场景运行次序：1-顺序执行，2-同时执行',
  `test_case_run_order` tinyint(2) NOT NULL DEFAULT 1 COMMENT '测试用例运行次序：1-顺序执行，2-同时执行',
  `status` tinyint(2) NOT NULL DEFAULT 0 COMMENT '任务状态：0-未启用，1-运行中，2-已过期',
  `run_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '运行人用户ID',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_plan_id`(`plan_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自动化测试-定时任务配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for company
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `company_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '企业id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '企业名称',
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '企业logo',
  `expire_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '服务到期时间',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_company_id`(`company_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '企业表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO `company` VALUES (1, '91a5fb31-0bf7-4407-8810-e39200df68c5', 'runnergo', '', '2024-01-16 05:07:08', '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);

-- ----------------------------
-- Table structure for element
-- ----------------------------
DROP TABLE IF EXISTS `element`;
CREATE TABLE `element`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `element_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全局唯一ID',
  `element_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型：文件夹，元素',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `parent_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '父级ID',
  `locators` json NULL COMMENT '定位元素属性',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '产品版本号',
  `created_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人ID',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `source` tinyint(4) NOT NULL DEFAULT 0 COMMENT '数据来源：0-元素管理，1-场景管理',
  `source_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '引用来源ID',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_element_id`(`element_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '元素表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of element
-- ----------------------------
INSERT INTO `element` VALUES (1, 'c4641597-4ed9-4293-bfef-6a42bd289ab9', 'element', 'a39c5c8d-0df9-4572-9072-81dbdf63fa23', '登录', '0', '[{\"id\": \"6d002d23-c545-4829-b77c-b6df9c6197e4\", \"key\": \"\", \"type\": \"\", \"index\": -1, \"value\": \"xpath=/html/body/div[1]/div[1]/div[1]/div/div[3]/button\", \"method\": \"playwright_selector\", \"is_checked\": 1}]', 0, 0, '7738021d-1534-4752-bfe0-c6fbca2653fc', '', 0, '', '2024-03-29 11:46:48', '2024-03-29 11:48:52', NULL);

-- ----------------------------
-- Table structure for global_variable
-- ----------------------------
DROP TABLE IF EXISTS `global_variable`;
CREATE TABLE `global_variable`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '变量类型：1-全局变量，2-场景变量',
  `var` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '变量名',
  `val` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '变量值',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '开关状态：1-开启，2-关闭',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '全局变量表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for machine
-- ----------------------------
DROP TABLE IF EXISTS `machine`;
CREATE TABLE `machine`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `region` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属区域',
  `ip` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机器IP',
  `port` int(11) UNSIGNED NOT NULL COMMENT '端口',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机器名称',
  `cpu_usage` float UNSIGNED NOT NULL DEFAULT 0 COMMENT 'CPU使用率',
  `cpu_load_one` float UNSIGNED NOT NULL DEFAULT 0 COMMENT 'CPU-1分钟内平均负载',
  `cpu_load_five` float UNSIGNED NOT NULL DEFAULT 0 COMMENT 'CPU-5分钟内平均负载',
  `cpu_load_fifteen` float UNSIGNED NOT NULL DEFAULT 0 COMMENT 'CPU-15分钟内平均负载',
  `mem_usage` float UNSIGNED NOT NULL DEFAULT 0 COMMENT '内存使用率',
  `disk_usage` float UNSIGNED NOT NULL DEFAULT 0 COMMENT '磁盘使用率',
  `max_goroutines` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大协程数',
  `current_goroutines` bigint(20) NOT NULL DEFAULT 0 COMMENT '已用协程数',
  `server_type` tinyint(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '机器类型：1-主力机器，2-备用机器',
  `status` tinyint(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '机器状态：1-使用中，2-已卸载',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `machine_region_ip_status_index`(`region`, `ip`, `status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '压力测试机器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of machine
-- ----------------------------
INSERT INTO `machine` VALUES (16, '北京', '172.17.0.2', 30000, '46722a83e8c4', 2.25847, 0.73, 0.63, 0.61, 72.563, 0, 20005, 0, 1, 1, '2024-05-08 16:38:49', '2024-05-08 22:27:19', NULL);

-- ----------------------------
-- Table structure for migrations
-- ----------------------------
DROP TABLE IF EXISTS `migrations`;
CREATE TABLE `migrations`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `version` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '版本号',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of migrations
-- ----------------------------
INSERT INTO `migrations` VALUES (1, '1.1.2', '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `migrations` VALUES (2, '2.2.0', '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `migrations` VALUES (3, '2.1.0', '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `migrations` VALUES (4, '2.0.0.2', '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `migrations` VALUES (5, '2.0.0.1', '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `migrations` VALUES (6, '2.0.0', '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);

-- ----------------------------
-- Table structure for mock_target
-- ----------------------------
DROP TABLE IF EXISTS `mock_target`;
CREATE TABLE `mock_target`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `target_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全局唯一ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `target_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型：文件夹，接口，分组，场景,测试用例',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `parent_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '父级ID',
  `method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '方法',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `type_sort` int(11) NOT NULL DEFAULT 0 COMMENT '类型排序',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '回收站状态：1-正常，2-回收站',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '产品版本号',
  `created_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人ID',
  `recent_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '最近修改人ID',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `source` tinyint(4) NOT NULL DEFAULT 0 COMMENT '数据来源：0-mock管理',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划id',
  `source_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '引用来源ID',
  `is_checked` tinyint(2) NOT NULL DEFAULT 1 COMMENT '是否开启：1-开启，2-关闭',
  `is_disabled` tinyint(2) NOT NULL DEFAULT 0 COMMENT '运行计划时是否禁用：0-不禁用，1-禁用',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_target_id`(`target_id`) USING BTREE,
  INDEX `idx_plan_id`(`plan_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '创建目标' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mock_target_debug_log
-- ----------------------------
DROP TABLE IF EXISTS `mock_target_debug_log`;
CREATE TABLE `mock_target_debug_log`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `target_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '目标唯一ID',
  `target_type` tinyint(2) NOT NULL COMMENT '目标类型：1-api，2-scene',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_target_id`(`target_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'mock目标调试日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `permission_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '权限ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限内容',
  `mark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限标识',
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限url',
  `type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '类型（1：权限   2：功能）',
  `group_id` int(11) NOT NULL DEFAULT 0 COMMENT '所属权限组（1：企业成员管理  2：团队管理  3：角色管理）',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 101, '创建成员', 'company_save_member', '/permission/api/v1/company/member/save', 1, 1, '2023-05-22 10:31:54', '2023-05-22 14:48:32', NULL);
INSERT INTO `permission` VALUES (2, 102, '批量导入成员', 'company_export_member', '/permission/api/v1/company/member/export', 1, 1, '2023-05-22 10:33:42', '2023-05-24 14:17:06', NULL);
INSERT INTO `permission` VALUES (3, 103, '编辑成员', 'company_update_member', '/permission/api/v1/company/member/update', 1, 1, '2023-05-22 10:33:42', '2023-05-22 14:48:35', NULL);
INSERT INTO `permission` VALUES (4, 104, '删除成员', 'company_remove_member', '/permission/api/v1/company/member/remove', 1, 1, '2023-05-22 10:33:42', '2023-05-22 14:48:37', NULL);
INSERT INTO `permission` VALUES (5, 105, '更改企业角色', 'company_set_role_member', '/permission/api/v1/role/company/set', 1, 1, '2023-05-22 10:33:42', '2023-05-22 16:39:34', NULL);
INSERT INTO `permission` VALUES (6, 201, '新建团队', 'team_save', '/permission/api/v1/team/save', 1, 2, '2023-05-22 10:35:38', '2023-05-22 14:48:40', NULL);
INSERT INTO `permission` VALUES (7, 202, '编辑团队', 'team_update', '/permission/api/v1/team/update', 1, 2, '2023-05-22 10:35:38', '2023-05-22 14:48:41', NULL);
INSERT INTO `permission` VALUES (8, 203, '添加团队成员', 'team_save_member', '/permission/api/v1/team/member/save', 1, 2, '2023-05-22 10:35:38', '2023-05-22 14:48:42', NULL);
INSERT INTO `permission` VALUES (9, 204, '移除团队成员', 'team_remove_member', '/permission/api/v1/team/member/remove', 1, 2, '2023-05-22 10:35:38', '2023-05-22 14:48:44', NULL);
INSERT INTO `permission` VALUES (10, 205, '更改团队角色', 'team_set_role_member', '/permission/api/v1/role/team/set', 1, 2, '2023-05-22 10:35:38', '2023-05-29 14:42:11', NULL);
INSERT INTO `permission` VALUES (11, 206, '解散团队', 'team_disband', '/permission/api/v1/team/disband', 1, 2, '2023-05-22 10:35:38', '2023-05-22 14:48:46', NULL);
INSERT INTO `permission` VALUES (12, 301, '新建角色', 'role_save', '/permission/api/v1/role/save', 1, 3, '2023-05-22 10:36:40', '2023-05-22 14:48:47', NULL);
INSERT INTO `permission` VALUES (13, 302, '设置角色权限', 'role_set', '/permission/api/v1/permission/role/set', 1, 3, '2023-05-22 10:36:40', '2023-05-22 14:48:51', NULL);
INSERT INTO `permission` VALUES (14, 303, '删除角色', 'role_remove', '/permission/api/v1/role/remove', 1, 3, '2023-05-22 10:36:40', '2023-05-22 14:48:54', NULL);
INSERT INTO `permission` VALUES (15, 401, '新建第三方集成', 'notice_save', '/permission/api/v1/notice/save', 1, 4, '2023-07-12 16:56:47', '2023-07-12 16:56:47', NULL);
INSERT INTO `permission` VALUES (16, 402, '修改第三方集成', 'notice_update', '/permission/api/v1/notice/update', 1, 4, '2023-07-12 16:56:47', '2023-07-12 16:56:47', NULL);
INSERT INTO `permission` VALUES (17, 403, '禁用|启用第三方集成', 'notice_set_status', '/permission/api/v1/notice/set_status', 1, 4, '2023-07-12 16:56:47', '2023-07-12 16:56:47', NULL);
INSERT INTO `permission` VALUES (18, 404, '删除第三方集成', 'notice_remove', '/permission/api/v1/notice/remove', 1, 4, '2023-07-12 16:56:47', '2023-07-12 16:56:47', NULL);
INSERT INTO `permission` VALUES (19, 405, '新建通知组', 'notice_group_save', '/permission/api/v1/notice/group/save', 1, 4, '2023-07-12 16:56:47', '2023-07-12 16:56:47', NULL);
INSERT INTO `permission` VALUES (20, 406, '修改通知组', 'notice_group_update', '/permission/api/v1/notice/group/update', 1, 4, '2023-07-12 16:56:47', '2023-07-12 16:56:47', NULL);
INSERT INTO `permission` VALUES (21, 407, '删除通知组', 'notice_group_remove', '/permission/api/v1/notice/group/remove', 1, 4, '2023-07-12 16:56:47', '2023-07-12 16:56:47', NULL);

-- ----------------------------
-- Table structure for preinstall_conf
-- ----------------------------
DROP TABLE IF EXISTS `preinstall_conf`;
CREATE TABLE `preinstall_conf`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `conf_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置名称',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '用户ID',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名称',
  `task_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '任务类型',
  `task_mode` tinyint(2) NOT NULL DEFAULT 0 COMMENT '压测模式',
  `control_mode` tinyint(2) NOT NULL DEFAULT 0 COMMENT '控制模式：0-集中模式，1-单独模式',
  `debug_mode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'stop' COMMENT 'debug模式：stop-关闭，all-开启全部日志，only_success-开启仅成功日志，only_error-开启仅错误日志',
  `mode_conf` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '压测配置详情',
  `timed_task_conf` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '定时任务相关配置',
  `is_open_distributed` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否开启分布式调度：0-关闭，1-开启',
  `machine_dispatch_mode_conf` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分布式压力机配置',
  `created_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` timestamp(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '预设配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for public_function
-- ----------------------------
DROP TABLE IF EXISTS `public_function`;
CREATE TABLE `public_function`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `function` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '函数',
  `function_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '函数名称',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '公共函数表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for report_machine
-- ----------------------------
DROP TABLE IF EXISTS `report_machine`;
CREATE TABLE `report_machine`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `report_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '报告id',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '计划ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机器ip',
  `concurrency` bigint(20) NOT NULL DEFAULT 0 COMMENT '并发数',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_report_id`(`report_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  `role_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '角色分类（1：企业  2：团队）',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `company_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '企业id',
  `level` tinyint(2) NOT NULL DEFAULT 0 COMMENT '角色层级（1:超管/团队管理员 2:管理员/团队成员 3:普通成员/只读成员/自定义角色） ',
  `is_default` tinyint(2) NOT NULL DEFAULT 2 COMMENT '是否是默认角色  1：是   2：自定义角色',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 1, '超管', '91a5fb31-0bf7-4407-8810-e39200df68c5', 1, 1, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role` VALUES (2, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 1, '管理员', '91a5fb31-0bf7-4407-8810-e39200df68c5', 2, 1, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role` VALUES (3, 'e53e16f6-7df0-48b1-95d9-140ed16d37d8', 1, '普通成员', '91a5fb31-0bf7-4407-8810-e39200df68c5', 3, 1, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role` VALUES (4, 'bb219e5f-2ef0-47d0-85c1-192b108dc456', 2, '团队管理员', '91a5fb31-0bf7-4407-8810-e39200df68c5', 1, 1, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role` VALUES (5, 'a5cd5800-12f9-47c2-aaaf-62aa74d5a0bf', 2, '团队成员', '91a5fb31-0bf7-4407-8810-e39200df68c5', 2, 1, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role` VALUES (6, 'e170d857-6675-4ab8-8ea8-a7bff5016b1e', 2, '开发', '91a5fb31-0bf7-4407-8810-e39200df68c5', 2, 2, '2024-01-17 15:50:18', '2024-01-17 15:50:18', NULL);
INSERT INTO `role` VALUES (7, '885f7a8c-d0ef-4f47-9a22-dcbbb1cc7695', 2, '测试', '91a5fb31-0bf7-4407-8810-e39200df68c5', 2, 2, '2024-01-17 15:50:22', '2024-01-17 15:50:22', NULL);

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '权限id',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 101, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (2, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 102, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (3, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 103, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (4, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 104, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (5, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 105, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (6, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 201, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (7, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 202, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (8, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 203, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (9, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 204, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (10, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 205, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (11, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 206, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (12, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 301, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (13, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 302, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (14, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 303, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (15, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 401, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (16, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 402, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (17, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 403, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (18, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 404, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (19, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 405, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (20, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 406, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (21, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', 407, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (22, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 101, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (23, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 102, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (24, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 103, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (25, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 104, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (26, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 105, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (27, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 201, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (28, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 202, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (29, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 203, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (30, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 204, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (31, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 205, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (32, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 206, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (33, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 301, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (34, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 302, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (35, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 303, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (36, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 401, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (37, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 402, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (38, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 403, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (39, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 404, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (40, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 405, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (41, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 406, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (42, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 407, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (43, 'bb219e5f-2ef0-47d0-85c1-192b108dc456', 202, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (44, 'bb219e5f-2ef0-47d0-85c1-192b108dc456', 203, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (45, 'bb219e5f-2ef0-47d0-85c1-192b108dc456', 204, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (46, 'bb219e5f-2ef0-47d0-85c1-192b108dc456', 205, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (47, 'bb219e5f-2ef0-47d0-85c1-192b108dc456', 206, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (48, 'a5cd5800-12f9-47c2-aaaf-62aa74d5a0bf', 203, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `role_permission` VALUES (49, 'e170d857-6675-4ab8-8ea8-a7bff5016b1e', 203, '2024-01-17 15:50:46', '2024-01-17 15:50:46', NULL);
INSERT INTO `role_permission` VALUES (50, '885f7a8c-d0ef-4f47-9a22-dcbbb1cc7695', 203, '2024-01-17 15:50:49', '2024-01-17 15:50:49', NULL);

-- ----------------------------
-- Table structure for role_type_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_type_permission`;
CREATE TABLE `role_type_permission`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '角色分类（1：企业  2：团队）',
  `permission_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '权限id',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色分类可拥有的权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_type_permission
-- ----------------------------
INSERT INTO `role_type_permission` VALUES (1, 1, 101, '2023-05-22 17:13:31', '2023-05-22 17:14:05', NULL);
INSERT INTO `role_type_permission` VALUES (2, 1, 102, '2023-05-22 17:16:00', '2023-05-22 17:16:00', NULL);
INSERT INTO `role_type_permission` VALUES (3, 1, 103, '2023-05-22 17:16:00', '2023-05-22 17:16:00', NULL);
INSERT INTO `role_type_permission` VALUES (4, 1, 104, '2023-05-22 17:16:00', '2023-05-22 17:16:00', NULL);
INSERT INTO `role_type_permission` VALUES (5, 1, 105, '2023-05-22 17:16:00', '2023-05-22 17:16:00', NULL);
INSERT INTO `role_type_permission` VALUES (6, 1, 201, '2023-05-22 17:16:00', '2023-05-22 17:16:00', NULL);
INSERT INTO `role_type_permission` VALUES (7, 1, 202, '2023-05-22 17:16:00', '2023-05-22 17:16:00', NULL);
INSERT INTO `role_type_permission` VALUES (8, 1, 203, '2023-05-22 17:16:00', '2023-05-22 17:16:00', NULL);
INSERT INTO `role_type_permission` VALUES (9, 1, 204, '2023-05-22 17:16:00', '2023-05-22 17:16:00', NULL);
INSERT INTO `role_type_permission` VALUES (10, 1, 205, '2023-05-22 17:16:00', '2023-05-22 17:16:00', NULL);
INSERT INTO `role_type_permission` VALUES (11, 1, 206, '2023-05-25 18:57:51', '2023-05-25 18:57:51', NULL);
INSERT INTO `role_type_permission` VALUES (12, 1, 301, '2023-05-22 17:16:00', '2023-05-22 17:16:00', NULL);
INSERT INTO `role_type_permission` VALUES (13, 1, 302, '2023-05-22 17:16:00', '2023-05-22 17:16:00', NULL);
INSERT INTO `role_type_permission` VALUES (14, 1, 303, '2023-05-22 17:16:00', '2023-05-22 17:16:00', NULL);
INSERT INTO `role_type_permission` VALUES (15, 2, 202, '2023-05-22 17:16:00', '2023-05-22 17:16:00', NULL);
INSERT INTO `role_type_permission` VALUES (16, 2, 203, '2023-05-22 17:16:01', '2023-05-22 17:16:01', NULL);
INSERT INTO `role_type_permission` VALUES (17, 2, 204, '2023-05-24 15:22:43', '2023-05-24 15:22:43', NULL);
INSERT INTO `role_type_permission` VALUES (18, 2, 205, '2023-05-22 17:16:01', '2023-05-22 17:16:01', NULL);
INSERT INTO `role_type_permission` VALUES (19, 2, 206, '2023-05-22 17:16:01', '2023-05-22 17:16:01', NULL);
INSERT INTO `role_type_permission` VALUES (20, 1, 401, '2023-07-12 17:37:50', '2023-07-12 17:37:50', NULL);
INSERT INTO `role_type_permission` VALUES (21, 1, 402, '2023-07-12 17:37:50', '2023-07-12 17:37:50', NULL);
INSERT INTO `role_type_permission` VALUES (22, 1, 403, '2023-07-12 17:37:50', '2023-07-12 17:37:50', NULL);
INSERT INTO `role_type_permission` VALUES (23, 1, 404, '2023-07-12 17:37:50', '2023-07-12 17:37:50', NULL);
INSERT INTO `role_type_permission` VALUES (24, 1, 405, '2023-07-12 17:37:50', '2023-07-12 17:37:50', NULL);
INSERT INTO `role_type_permission` VALUES (25, 1, 406, '2023-07-12 17:37:50', '2023-07-12 17:37:50', NULL);
INSERT INTO `role_type_permission` VALUES (26, 1, 407, '2023-07-12 17:37:50', '2023-07-12 17:37:50', NULL);

-- ----------------------------
-- Table structure for scene_variable
-- ----------------------------
DROP TABLE IF EXISTS `scene_variable`;
CREATE TABLE `scene_variable`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `scene_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场景ID',
  `type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '使用范围：1-全局变量，2-场景变量',
  `var` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '变量名',
  `val` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '变量值',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '开关状态：1-开启，2-关闭',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE,
  INDEX `idx_scene_id`(`scene_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '设置变量表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for setting
-- ----------------------------
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '当前团队id',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setting
-- ----------------------------
INSERT INTO `setting` VALUES (26, '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '220df310-7b6b-487c-a7ac-4e4161bca482', '2024-05-08 18:37:31', '2024-05-08 22:07:39', '2024-05-08 22:07:40');
INSERT INTO `setting` VALUES (27, '7738021d-1534-4752-bfe0-c6fbca2653fc', '220df310-7b6b-487c-a7ac-4e4161bca482', '2024-05-08 18:38:38', '2024-05-08 22:07:39', '2024-05-08 22:07:40');
INSERT INTO `setting` VALUES (28, 'f307073c-37a7-4f20-abdb-24045aeb75c7', '220df310-7b6b-487c-a7ac-4e4161bca482', '2024-05-08 19:00:45', '2024-05-08 22:07:39', '2024-05-08 22:07:40');
INSERT INTO `setting` VALUES (29, '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '785a45fb-d9d8-4f2c-9092-ba157c6326c9', '2024-05-08 22:07:54', '2024-05-08 22:23:11', NULL);
INSERT INTO `setting` VALUES (30, '71852c74-cd08-4d00-b009-471e7bd01bbc', '785a45fb-d9d8-4f2c-9092-ba157c6326c9', '2024-05-08 22:07:59', '2024-05-08 22:18:31', NULL);
INSERT INTO `setting` VALUES (31, 'f307073c-37a7-4f20-abdb-24045aeb75c7', '785a45fb-d9d8-4f2c-9092-ba157c6326c9', '2024-05-08 22:07:59', '2024-05-08 22:07:59', NULL);
INSERT INTO `setting` VALUES (32, 'd4145c27-21c7-4dff-8b34-f6e9de5f04dc', '785a45fb-d9d8-4f2c-9092-ba157c6326c9', '2024-05-08 22:23:15', '2024-05-08 22:26:27', NULL);

-- ----------------------------
-- Table structure for sms_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_log`;
CREATE TABLE `sms_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `type` tinyint(2) NOT NULL COMMENT '短信类型: 1-注册，2-登录，3-找回密码',
  `mobile` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信内容',
  `verify_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '验证码',
  `verify_code_expiration_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '验证码有效时间',
  `client_ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '客户端IP',
  `send_status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '发送状态：1-成功 2-失败',
  `verify_status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '校验状态：1-未校验 2-已校验',
  `send_response` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信服务响应',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_type_mobile_verify_code`(`type`, `mobile`, `verify_code`, `verify_code_expiration_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信发送记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stress_plan
-- ----------------------------
DROP TABLE IF EXISTS `stress_plan`;
CREATE TABLE `stress_plan`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '计划ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `rank_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '序号ID',
  `plan_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划名称',
  `task_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '计划类型：1-普通任务，2-定时任务',
  `task_mode` tinyint(2) NOT NULL DEFAULT 0 COMMENT '压测类型: 1-并发模式，2-阶梯模式，3-错误率模式，4-响应时间模式，5-每秒请求数模式，6-每秒事务数模式',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '计划状态1:未开始,2:进行中',
  `create_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '创建人id',
  `run_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '运行人id',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `run_count` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '运行次数',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_plan_id`(`plan_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '性能计划表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stress_plan_email
-- ----------------------------
DROP TABLE IF EXISTS `stress_plan_email`;
CREATE TABLE `stress_plan_email`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_plan_id`(`plan_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '性能计划收件人' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stress_plan_report
-- ----------------------------
DROP TABLE IF EXISTS `stress_plan_report`;
CREATE TABLE `stress_plan_report`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `report_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '报告ID',
  `report_name` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '报告名称',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划ID',
  `rank_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '序号ID',
  `plan_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划名称',
  `scene_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场景ID',
  `scene_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场景名称',
  `task_type` int(11) NOT NULL COMMENT '任务类型',
  `task_mode` int(11) NOT NULL COMMENT '压测模式',
  `control_mode` tinyint(2) NOT NULL DEFAULT 0 COMMENT '控制模式：0-集中模式，1-单独模式',
  `debug_mode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'stop' COMMENT 'debug模式：stop-关闭，all-开启全部日志，only_success-开启仅成功日志，only_error-开启仅错误日志',
  `run_duration_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '任务运行持续时长',
  `status` tinyint(4) NOT NULL COMMENT '报告状态1:进行中，2:已完成',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `run_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '启动人id',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间（执行时间）',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_report_id`(`report_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '性能测试报告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stress_plan_task_conf
-- ----------------------------
DROP TABLE IF EXISTS `stress_plan_task_conf`;
CREATE TABLE `stress_plan_task_conf`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '计划ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `scene_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场景ID',
  `task_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '任务类型：1-普通模式，2-定时任务',
  `task_mode` tinyint(2) NOT NULL DEFAULT 0 COMMENT '压测模式：1-并发模式，2-阶梯模式，3-错误率模式，4-响应时间模式，5-每秒请求数模式，6-每秒事务数模式',
  `control_mode` tinyint(2) NOT NULL DEFAULT 0 COMMENT '控制模式：0-集中模式，1-单独模式',
  `debug_mode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'stop' COMMENT 'debug模式：stop-关闭，all-开启全部日志，only_success-开启仅成功日志，only_error-开启仅错误日志',
  `mode_conf` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '压测模式配置详情',
  `is_open_distributed` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否开启分布式调度：0-关闭，1-开启',
  `machine_dispatch_mode_conf` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分布式压力机配置',
  `run_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '运行人用户ID',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_plan_id`(`plan_id`) USING BTREE,
  INDEX `idx_scene_id`(`scene_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '性能计划—普通任务配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stress_plan_timed_task_conf
-- ----------------------------
DROP TABLE IF EXISTS `stress_plan_timed_task_conf`;
CREATE TABLE `stress_plan_timed_task_conf`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划id',
  `scene_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场景id',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `frequency` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务执行频次: 0-一次，1-每天，2-每周，3-每月',
  `task_exec_time` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务执行时间',
  `task_close_time` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务结束时间',
  `task_type` tinyint(2) NOT NULL DEFAULT 2 COMMENT '任务类型：1-普通任务，2-定时任务',
  `task_mode` tinyint(2) NOT NULL DEFAULT 1 COMMENT '压测模式：1-并发模式，2-阶梯模式，3-错误率模式，4-响应时间模式，5-每秒请求数模式，6 -每秒事务数模式',
  `control_mode` tinyint(2) NOT NULL DEFAULT 0 COMMENT '控制模式：0-集中模式，1-单独模式',
  `debug_mode` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'stop' COMMENT 'debug模式：stop-关闭，all-开启全部日志，only_success-开启仅成功日志，only_error-开启仅错误日志',
  `mode_conf` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '压测详细配置',
  `is_open_distributed` tinyint(2) NOT NULL DEFAULT 0 COMMENT '是否开启分布式调度：0-关闭，1-开启',
  `machine_dispatch_mode_conf` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分布式压力机配置',
  `run_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '运行人ID',
  `status` tinyint(11) NOT NULL DEFAULT 0 COMMENT '任务状态：0-未启用，1-运行中，2-已过期',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_plan_id`(`plan_id`) USING BTREE,
  INDEX `idx_scene_id`(`scene_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '性能计划-定时任务配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for target
-- ----------------------------
DROP TABLE IF EXISTS `target`;
CREATE TABLE `target`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `target_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全局唯一ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `target_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型：文件夹，接口，分组，场景,测试用例',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `parent_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '父级ID',
  `method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '方法',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `type_sort` int(11) NOT NULL DEFAULT 0 COMMENT '类型排序',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '回收站状态：1-正常，2-回收站',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '产品版本号',
  `created_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人ID',
  `recent_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '最近修改人ID',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `source` tinyint(4) NOT NULL DEFAULT 0 COMMENT '数据来源：0-测试对象，1-场景管理，2-性能，3-自动化测试， 4-mock',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划id',
  `source_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '引用来源ID',
  `is_checked` tinyint(2) NOT NULL DEFAULT 1 COMMENT '是否开启：1-开启，2-关闭',
  `is_disabled` tinyint(2) NOT NULL DEFAULT 0 COMMENT '运行计划时是否禁用：0-不禁用，1-禁用',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_target_id`(`target_id`) USING BTREE,
  INDEX `idx_plan_id`(`plan_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 965 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '创建目标' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for target_debug_log
-- ----------------------------
DROP TABLE IF EXISTS `target_debug_log`;
CREATE TABLE `target_debug_log`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `target_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '目标唯一ID',
  `target_type` tinyint(2) NOT NULL COMMENT '目标类型：1-api，2-scene',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_target_id`(`target_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '目标调试日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '团队描述',
  `company_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '所属企业id',
  `type` tinyint(4) NOT NULL COMMENT '团队类型 1: 私有团队；2: 普通团队',
  `trial_expiration_date` datetime(0) NOT NULL COMMENT '试用有效期',
  `is_vip` tinyint(2) NOT NULL DEFAULT 1 COMMENT '是否为付费团队 1-否 2-是',
  `vip_expiration_date` datetime(0) NOT NULL COMMENT '付费有效期',
  `vum_num` bigint(20) NOT NULL DEFAULT 0 COMMENT '当前可用VUM总数',
  `max_user_num` bigint(20) NOT NULL DEFAULT 0 COMMENT '当前团队最大成员数量',
  `created_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者id',
  `team_buy_version_type` int(10) NOT NULL DEFAULT 1 COMMENT '团队套餐类型：1-个人版，2-团队版，3-企业版，4-私有化部署',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '团队表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of team
-- ----------------------------
INSERT INTO `team` VALUES (5, '220df310-7b6b-487c-a7ac-4e4161bca482', '示例团队', '', '91a5fb31-0bf7-4407-8810-e39200df68c5', 2, '0000-00-00 00:00:00', 1, '0000-00-00 00:00:00', 0, 0, '20a81cd5-4043-43c2-9f7f-a49d91f198d1', 1, '2024-05-08 18:38:00', '2024-05-08 22:07:39', '2024-05-08 22:07:40');
INSERT INTO `team` VALUES (6, '785a45fb-d9d8-4f2c-9092-ba157c6326c9', '示例团队', '', '91a5fb31-0bf7-4407-8810-e39200df68c5', 2, '0000-00-00 00:00:00', 1, '0000-00-00 00:00:00', 0, 0, '20a81cd5-4043-43c2-9f7f-a49d91f198d1', 1, '2024-05-08 22:07:54', '2024-05-08 22:07:54', NULL);

-- ----------------------------
-- Table structure for team_env
-- ----------------------------
DROP TABLE IF EXISTS `team_env`;
CREATE TABLE `team_env`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '环境名称',
  `created_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人id',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '环境管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team_env_database
-- ----------------------------
DROP TABLE IF EXISTS `team_env_database`;
CREATE TABLE `team_env_database`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `team_env_id` bigint(20) NOT NULL COMMENT '环境变量id',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库类型',
  `server_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'mysql服务名称',
  `host` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务地址',
  `port` int(11) NOT NULL COMMENT '端口号',
  `user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `db_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据库名称',
  `charset` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'utf8mb4' COMMENT '字符编码集',
  `created_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人id',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE,
  INDEX `idx_team_env_id`(`team_env_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Sql数据库服务基础信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team_env_service
-- ----------------------------
DROP TABLE IF EXISTS `team_env_service`;
CREATE TABLE `team_env_service`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `team_env_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '环境id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务名称',
  `content` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '服务URL',
  `created_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人id',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idxx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '团队环境服务管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for team_user_queue
-- ----------------------------
DROP TABLE IF EXISTS `team_user_queue`;
CREATE TABLE `team_user_queue`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邀请待注册队列' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for third_notice
-- ----------------------------
DROP TABLE IF EXISTS `third_notice`;
CREATE TABLE `third_notice`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `notice_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '通知名称',
  `channel_id` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '三方通知渠道id',
  `params` json NULL COMMENT '通知参数',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '1:启用 2:禁用',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_notice_id`(`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '三方通知设置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for third_notice_channel
-- ----------------------------
DROP TABLE IF EXISTS `third_notice_channel`;
CREATE TABLE `third_notice_channel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '名称',
  `type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '类型 1:飞书  2:企业微信  3:邮箱  4:钉钉',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '三方通知渠道' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of third_notice_channel
-- ----------------------------
INSERT INTO `third_notice_channel` VALUES (1, '飞书群机器人', 1, '2023-06-21 10:46:03', '2023-06-21 10:46:03', NULL);
INSERT INTO `third_notice_channel` VALUES (2, '飞书企业应用', 1, '2023-06-21 10:46:25', '2023-06-21 10:46:25', NULL);
INSERT INTO `third_notice_channel` VALUES (3, '企业微信应用', 2, '2023-06-21 10:46:39', '2023-06-21 10:46:53', NULL);
INSERT INTO `third_notice_channel` VALUES (4, '企业微信机器人', 2, '2023-06-21 10:47:08', '2023-06-21 10:47:08', NULL);
INSERT INTO `third_notice_channel` VALUES (5, '邮箱', 3, '2023-06-29 11:03:45', '2023-06-29 11:03:45', NULL);
INSERT INTO `third_notice_channel` VALUES (6, '钉钉群机器人', 4, '2023-06-29 11:03:55', '2023-06-29 11:04:00', NULL);
INSERT INTO `third_notice_channel` VALUES (7, '钉钉企业应用', 4, '2023-06-29 11:04:13', '2023-06-29 11:04:13', NULL);

-- ----------------------------
-- Table structure for third_notice_group
-- ----------------------------
DROP TABLE IF EXISTS `third_notice_group`;
CREATE TABLE `third_notice_group`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `group_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知组id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '通知组名称',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '三方通知组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for third_notice_group_event
-- ----------------------------
DROP TABLE IF EXISTS `third_notice_group_event`;
CREATE TABLE `third_notice_group_event`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `group_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '通知组id',
  `event_id` int(11) NOT NULL DEFAULT 0 COMMENT '事件id',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '团队ID',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_id`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '三方通知组触发事件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for third_notice_group_relate
-- ----------------------------
DROP TABLE IF EXISTS `third_notice_group_relate`;
CREATE TABLE `third_notice_group_relate`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `group_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知组id',
  `notice_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知id',
  `params` json NULL COMMENT '通知目标参数',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_notice_id`(`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '三方通知组通知关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ui_plan
-- ----------------------------
DROP TABLE IF EXISTS `ui_plan`;
CREATE TABLE `ui_plan`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '计划ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `rank_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '序号ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划名称',
  `task_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '计划类型：1-普通任务，2-定时任务',
  `create_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '创建人id',
  `head_user_id` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '负责人id ,用分割',
  `run_count` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '运行次数',
  `init_strategy` tinyint(2) NOT NULL DEFAULT 1 COMMENT '初始化策略：1-计划执行前重启浏览器，2-场景执行前重启浏览器，3-无初始化',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `browsers` json NULL COMMENT '浏览器信息',
  `ui_machine_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '指定机器key',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_plan_id`(`plan_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'UI计划表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ui_plan_report
-- ----------------------------
DROP TABLE IF EXISTS `ui_plan_report`;
CREATE TABLE `ui_plan_report`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `report_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '报告ID',
  `report_name` varchar(125) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '报告名称',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划ID',
  `plan_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '计划名称',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `rank_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '序号ID',
  `task_type` int(11) NOT NULL DEFAULT 0 COMMENT '任务类型',
  `scene_run_order` tinyint(2) NOT NULL DEFAULT 1 COMMENT '场景运行次序：1-顺序执行，2-同时执行',
  `run_duration_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '任务运行持续时长',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '报告状态1:进行中，2:已完成',
  `run_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '启动人id',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `browsers` json NULL COMMENT '浏览器信息',
  `ui_machine_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '指定机器key',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间（执行时间）',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_report_id`(`report_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'UI自动化测试计划-报告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ui_plan_task_conf
-- ----------------------------
DROP TABLE IF EXISTS `ui_plan_task_conf`;
CREATE TABLE `ui_plan_task_conf`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '配置ID',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '计划ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队ID',
  `task_type` tinyint(2) NOT NULL DEFAULT 0 COMMENT '任务类型：1-普通模式，2-定时任务',
  `scene_run_order` tinyint(2) NOT NULL DEFAULT 1 COMMENT '场景运行次序：1-顺序执行，2-同时执行',
  `run_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '运行人用户ID',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_plan_id`(`plan_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'UI自动化测试—普通任务配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ui_plan_timed_task_conf
-- ----------------------------
DROP TABLE IF EXISTS `ui_plan_timed_task_conf`;
CREATE TABLE `ui_plan_timed_task_conf`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表id',
  `plan_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '计划id',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `frequency` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务执行频次: 0-一次，1-每天，2-每周，3-每月，4-固定时间间隔',
  `task_exec_time` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务执行时间',
  `task_close_time` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '任务结束时间',
  `fixed_interval_start_time` bigint(20) NOT NULL DEFAULT 0 COMMENT '固定时间间隔开始时间',
  `fixed_interval_time` int(10) NOT NULL DEFAULT 0 COMMENT '固定间隔时间',
  `fixed_run_num` int(10) NOT NULL DEFAULT 0 COMMENT '固定执行次数',
  `fixed_interval_time_type` int(10) NOT NULL DEFAULT 0 COMMENT '固定间隔时间类型：0-分钟，1-小时',
  `task_type` tinyint(2) NOT NULL DEFAULT 2 COMMENT '任务类型：1-普通任务，2-定时任务',
  `scene_run_order` tinyint(2) NOT NULL DEFAULT 1 COMMENT '场景运行次序：1-顺序执行，2-同时执行',
  `status` tinyint(2) NOT NULL DEFAULT 0 COMMENT '任务状态：0-未启用，1-运行中，2-已过期',
  `run_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '运行人用户ID',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_plan_id`(`plan_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'UI自动化测试-定时任务配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ui_scene
-- ----------------------------
DROP TABLE IF EXISTS `ui_scene`;
CREATE TABLE `ui_scene`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `scene_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全局唯一ID',
  `scene_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型：文件夹，场景',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `parent_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '父级ID',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '回收站状态：1-正常，2-回收站',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '产品版本号',
  `source` tinyint(2) NOT NULL DEFAULT 1 COMMENT '数据来源：1-场景管理，2-计划',
  `plan_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '计划ID',
  `created_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人ID',
  `recent_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '最近修改人ID',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '备注',
  `ui_machine_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '指定执行的UI自动化机器key',
  `source_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '引用来源ID',
  `browsers` json NULL COMMENT '浏览器信息',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_scene_id`(`scene_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'UI自动化场景' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ui_scene_element
-- ----------------------------
DROP TABLE IF EXISTS `ui_scene_element`;
CREATE TABLE `ui_scene_element`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `scene_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场景ID',
  `operator_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作ID',
  `element_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '元素ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '状态 1：正常  2：回收站',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_scene_id`(`scene_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'UI自动化场景元素关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ui_scene_operator
-- ----------------------------
DROP TABLE IF EXISTS `ui_scene_operator`;
CREATE TABLE `ui_scene_operator`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `operator_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全局唯一ID',
  `scene_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场景ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `parent_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '父级ID',
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '状态：1-正常，2-禁用',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '步骤类型',
  `action` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '步骤方法',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_scene_id`(`scene_id`) USING BTREE,
  INDEX `idx_operator_id`(`operator_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'UI自动化场景步骤' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ui_scene_sync
-- ----------------------------
DROP TABLE IF EXISTS `ui_scene_sync`;
CREATE TABLE `ui_scene_sync`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `scene_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场景ID',
  `source_scene_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '引用场景ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `sync_mode` tinyint(2) NOT NULL DEFAULT 0 COMMENT '状态：1-实时，2-手动,已场景为准   3-手动,已计划为准',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_scene_id`(`scene_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'UI场景同步关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ui_scene_trash
-- ----------------------------
DROP TABLE IF EXISTS `ui_scene_trash`;
CREATE TABLE `ui_scene_trash`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `scene_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场景ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `created_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人ID',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_scene_id`(`scene_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'UI自动化场景回收站' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `account` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '账号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `mobile` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `wechat_open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '微信开放的唯一id',
  `utm_source` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '渠道来源',
  `last_login_at` datetime(0) NULL DEFAULT NULL COMMENT '最近登录时间',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '20a81cd5-4043-43c2-9f7f-a49d91f198d1', 'runnergo', '', '', '$2a$10$bYkJvocVy88CD2WVvBzBs.WcqpDeIALRtD3l6x3XCM6K0mfcQVJKG', 'runnergo', 'https://apipost.oss-cn-beijing.aliyuncs.com/kunpeng/avatar/default3.png', '', '', '2024-05-08 22:23:11', '2024-01-16 05:07:08', '2024-05-08 22:23:11', NULL);
INSERT INTO `user` VALUES (40, 'f307073c-37a7-4f20-abdb-24045aeb75c7', 'yinghua', '', '', '$2a$10$yL4yAtZfR70ZoaG6ghpO8OWOvDcf8RSIRt.heQsNY9UsgUjtci2/K', '樱花', 'http://172.19.5.226:58888/c931ef0a-a763-4f60-ab0a-42d6d24dc6f5.png', '', '', '2024-05-08 22:07:25', '2024-05-08 18:56:47', '2024-05-08 22:22:11', '2024-05-08 22:22:11');
INSERT INTO `user` VALUES (42, '71852c74-cd08-4d00-b009-471e7bd01bbc', 'guanliyuan', '', '', '$2a$10$1olZUw8aIb0EfUQ4QvcL8uYBEOi76blPRS0oLCvN.H1NUjV.boXvG', '管理员', 'http://172.19.5.226:58888/fe1e83f2-7fb0-4936-92b3-6274dccfab5a.jpg', '', '', '2024-05-08 22:18:31', '2024-05-08 22:06:38', '2024-05-08 22:18:30', NULL);
INSERT INTO `user` VALUES (43, 'd4145c27-21c7-4dff-8b34-f6e9de5f04dc', 'liuzhi', 'liuzhi@sakura.com', '', '$2a$10$FrHdYCKvVkKFNRw5sIebeusUbkM.ROxtW0yDlaXQMJkyDCoymZzbK', '刘智', 'http://172.19.5.226:58888/5060e8a8-dce5-45c5-bfd6-6d02b9f8f12f.jpg', '', '', '2024-05-08 22:26:27', '2024-05-08 22:22:25', '2024-05-08 22:26:27', NULL);

-- ----------------------------
-- Table structure for user_collect_info
-- ----------------------------
DROP TABLE IF EXISTS `user_collect_info`;
CREATE TABLE `user_collect_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `industry` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所属行业',
  `team_size` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队规模',
  `work_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工作岗位',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_company
-- ----------------------------
DROP TABLE IF EXISTS `user_company`;
CREATE TABLE `user_company`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `company_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '企业id',
  `invite_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '邀请人id',
  `invite_time` datetime(0) NULL DEFAULT NULL COMMENT '邀请时间',
  `status` tinyint(2) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态：1-正常，2-已禁用',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_company_id`(`company_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户企业关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_company
-- ----------------------------
INSERT INTO `user_company` VALUES (1, '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '91a5fb31-0bf7-4407-8810-e39200df68c5', '0', '2024-01-16 05:07:08', 1, '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `user_company` VALUES (2, '7738021d-1534-4752-bfe0-c6fbca2653fc', '91a5fb31-0bf7-4407-8810-e39200df68c5', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-01-16 22:47:48', 1, '2024-01-16 22:47:48', '2024-05-08 18:56:50', '2024-05-08 18:56:51');
INSERT INTO `user_company` VALUES (39, '802d082b-1b19-4433-a48f-27382682c6fe', '91a5fb31-0bf7-4407-8810-e39200df68c5', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 18:47:03', 1, '2024-05-08 18:47:03', '2024-05-08 18:49:09', '2024-05-08 18:49:09');
INSERT INTO `user_company` VALUES (40, 'f307073c-37a7-4f20-abdb-24045aeb75c7', '91a5fb31-0bf7-4407-8810-e39200df68c5', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 18:56:48', 1, '2024-05-08 18:56:47', '2024-05-08 22:22:11', '2024-05-08 22:22:11');
INSERT INTO `user_company` VALUES (41, '0fca2a47-d944-4683-b77f-df8958950200', '91a5fb31-0bf7-4407-8810-e39200df68c5', 'f307073c-37a7-4f20-abdb-24045aeb75c7', '2024-05-08 22:05:55', 1, '2024-05-08 22:05:55', '2024-05-08 22:06:31', '2024-05-08 22:06:31');
INSERT INTO `user_company` VALUES (42, '71852c74-cd08-4d00-b009-471e7bd01bbc', '91a5fb31-0bf7-4407-8810-e39200df68c5', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 22:06:39', 1, '2024-05-08 22:06:38', '2024-05-08 22:06:38', NULL);
INSERT INTO `user_company` VALUES (43, 'd4145c27-21c7-4dff-8b34-f6e9de5f04dc', '91a5fb31-0bf7-4407-8810-e39200df68c5', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 22:22:26', 1, '2024-05-08 22:22:25', '2024-05-08 22:22:25', NULL);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户id',
  `company_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '企业id',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '团队id',
  `invite_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '邀请人id',
  `invite_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '邀请时间',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联表（企业角色、团队角色）' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, '9879d8c4-be0b-4066-a8d5-97ddc91d4c07', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '91a5fb31-0bf7-4407-8810-e39200df68c5', '', '0', '2024-01-16 05:07:08', '2024-01-16 05:07:08', '2024-01-16 05:07:08', NULL);
INSERT INTO `user_role` VALUES (2, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', '7738021d-1534-4752-bfe0-c6fbca2653fc', '91a5fb31-0bf7-4407-8810-e39200df68c5', '', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-01-16 22:47:48', '2024-01-16 22:47:48', '2024-05-08 18:56:50', '2024-05-08 18:56:51');
INSERT INTO `user_role` VALUES (95, 'bb219e5f-2ef0-47d0-85c1-192b108dc456', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '', '220df310-7b6b-487c-a7ac-4e4161bca482', '0', '2024-05-08 18:38:00', '2024-05-08 18:38:00', '2024-05-08 22:07:39', '2024-05-08 22:07:40');
INSERT INTO `user_role` VALUES (96, 'a5cd5800-12f9-47c2-aaaf-62aa74d5a0bf', '7738021d-1534-4752-bfe0-c6fbca2653fc', '', '220df310-7b6b-487c-a7ac-4e4161bca482', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 18:38:38', '2024-05-08 18:38:38', '2024-05-08 18:56:50', '2024-05-08 18:56:51');
INSERT INTO `user_role` VALUES (97, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', '802d082b-1b19-4433-a48f-27382682c6fe', '91a5fb31-0bf7-4407-8810-e39200df68c5', '', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 18:47:03', '2024-05-08 18:47:03', '2024-05-08 18:49:09', '2024-05-08 18:49:09');
INSERT INTO `user_role` VALUES (98, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 'f307073c-37a7-4f20-abdb-24045aeb75c7', '91a5fb31-0bf7-4407-8810-e39200df68c5', '', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 18:56:48', '2024-05-08 18:56:47', '2024-05-08 22:22:11', '2024-05-08 22:22:11');
INSERT INTO `user_role` VALUES (99, 'a5cd5800-12f9-47c2-aaaf-62aa74d5a0bf', 'f307073c-37a7-4f20-abdb-24045aeb75c7', '', '220df310-7b6b-487c-a7ac-4e4161bca482', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 19:00:46', '2024-05-08 19:00:45', '2024-05-08 22:07:39', '2024-05-08 22:07:40');
INSERT INTO `user_role` VALUES (100, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', '0fca2a47-d944-4683-b77f-df8958950200', '91a5fb31-0bf7-4407-8810-e39200df68c5', '', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 22:06:15', '2024-05-08 22:05:55', '2024-05-08 22:06:31', '2024-05-08 22:06:31');
INSERT INTO `user_role` VALUES (101, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', '71852c74-cd08-4d00-b009-471e7bd01bbc', '91a5fb31-0bf7-4407-8810-e39200df68c5', '', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 22:06:39', '2024-05-08 22:06:38', '2024-05-08 22:06:38', NULL);
INSERT INTO `user_role` VALUES (102, 'bb219e5f-2ef0-47d0-85c1-192b108dc456', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '', '785a45fb-d9d8-4f2c-9092-ba157c6326c9', '0', '2024-05-08 22:07:55', '2024-05-08 22:07:54', '2024-05-08 22:07:54', NULL);
INSERT INTO `user_role` VALUES (103, 'a5cd5800-12f9-47c2-aaaf-62aa74d5a0bf', '71852c74-cd08-4d00-b009-471e7bd01bbc', '', '785a45fb-d9d8-4f2c-9092-ba157c6326c9', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 22:08:00', '2024-05-08 22:07:59', '2024-05-08 22:07:59', NULL);
INSERT INTO `user_role` VALUES (104, 'a5cd5800-12f9-47c2-aaaf-62aa74d5a0bf', 'f307073c-37a7-4f20-abdb-24045aeb75c7', '', '785a45fb-d9d8-4f2c-9092-ba157c6326c9', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 22:08:00', '2024-05-08 22:07:59', '2024-05-08 22:22:11', '2024-05-08 22:22:11');
INSERT INTO `user_role` VALUES (105, 'e44c9a3d-94aa-478d-8c56-065ad17b2d10', 'd4145c27-21c7-4dff-8b34-f6e9de5f04dc', '91a5fb31-0bf7-4407-8810-e39200df68c5', '', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 22:22:26', '2024-05-08 22:22:25', '2024-05-08 22:22:25', NULL);
INSERT INTO `user_role` VALUES (106, 'a5cd5800-12f9-47c2-aaaf-62aa74d5a0bf', 'd4145c27-21c7-4dff-8b34-f6e9de5f04dc', '', '785a45fb-d9d8-4f2c-9092-ba157c6326c9', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 22:23:15', '2024-05-08 22:23:15', '2024-05-08 22:23:15', NULL);

-- ----------------------------
-- Table structure for user_team
-- ----------------------------
DROP TABLE IF EXISTS `user_team`;
CREATE TABLE `user_team`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id1:超级管理员，2成员，3管理员',
  `team_role_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色id (角色表对应)',
  `invite_user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '邀请人id',
  `invite_time` datetime(0) NULL DEFAULT NULL COMMENT '邀请时间',
  `sort` int(11) NOT NULL DEFAULT 0,
  `is_show` tinyint(2) NOT NULL DEFAULT 1 COMMENT '是否展示到团队列表  1:展示   2:不展示',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户团队关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_team
-- ----------------------------
INSERT INTO `user_team` VALUES (5, '7738021d-1534-4752-bfe0-c6fbca2653fc', '23deb926-39bc-4cf2-9642-3efdc2d23429', 0, '', '0', '0000-00-00 00:00:00', 0, 1, '2024-01-17 10:35:11', '2024-01-17 17:07:51', '2024-01-17 17:07:51');
INSERT INTO `user_team` VALUES (6, '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '23deb926-39bc-4cf2-9642-3efdc2d23429', 0, '', '0', '0000-00-00 00:00:00', 0, 2, '2024-01-17 10:35:11', '2024-01-17 17:07:51', '2024-01-17 17:07:51');
INSERT INTO `user_team` VALUES (57, '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '220df310-7b6b-487c-a7ac-4e4161bca482', 0, '', '0', '0000-00-00 00:00:00', 0, 1, '2024-05-08 18:38:00', '2024-05-08 22:07:39', '2024-05-08 22:07:40');
INSERT INTO `user_team` VALUES (58, '7738021d-1534-4752-bfe0-c6fbca2653fc', '220df310-7b6b-487c-a7ac-4e4161bca482', 0, '', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 18:38:38', 0, 1, '2024-05-08 18:38:38', '2024-05-08 18:56:50', '2024-05-08 18:56:51');
INSERT INTO `user_team` VALUES (59, 'f307073c-37a7-4f20-abdb-24045aeb75c7', '220df310-7b6b-487c-a7ac-4e4161bca482', 0, '', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 19:00:46', 0, 1, '2024-05-08 19:00:45', '2024-05-08 22:07:39', '2024-05-08 22:07:40');
INSERT INTO `user_team` VALUES (60, '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '785a45fb-d9d8-4f2c-9092-ba157c6326c9', 0, '', '0', '0000-00-00 00:00:00', 0, 1, '2024-05-08 22:07:54', '2024-05-08 22:07:54', NULL);
INSERT INTO `user_team` VALUES (61, '71852c74-cd08-4d00-b009-471e7bd01bbc', '785a45fb-d9d8-4f2c-9092-ba157c6326c9', 0, '', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 22:08:00', 0, 1, '2024-05-08 22:07:59', '2024-05-08 22:07:59', NULL);
INSERT INTO `user_team` VALUES (62, 'f307073c-37a7-4f20-abdb-24045aeb75c7', '785a45fb-d9d8-4f2c-9092-ba157c6326c9', 0, '', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 22:08:00', 0, 1, '2024-05-08 22:07:59', '2024-05-08 22:22:11', '2024-05-08 22:22:11');
INSERT INTO `user_team` VALUES (63, 'd4145c27-21c7-4dff-8b34-f6e9de5f04dc', '785a45fb-d9d8-4f2c-9092-ba157c6326c9', 0, '', '20a81cd5-4043-43c2-9f7f-a49d91f198d1', '2024-05-08 22:23:15', 0, 1, '2024-05-08 22:23:15', '2024-05-08 22:23:15', NULL);

-- ----------------------------
-- Table structure for user_team_collection
-- ----------------------------
DROP TABLE IF EXISTS `user_team_collection`;
CREATE TABLE `user_team_collection`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `deleted_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户收藏团队表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for variable
-- ----------------------------
DROP TABLE IF EXISTS `variable`;
CREATE TABLE `variable`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `scene_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '场景ID',
  `type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '使用范围：1-全局变量，2-场景变量',
  `var` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '变量名',
  `val` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '变量值',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '开关状态：1-开启，2-关闭',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE,
  INDEX `idx_scene_id`(`scene_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '设置变量表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for variable_import
-- ----------------------------
DROP TABLE IF EXISTS `variable_import`;
CREATE TABLE `variable_import`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `team_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '团队id',
  `scene_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '场景id',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件地址',
  `uploader_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '上传人id',
  `status` tinyint(2) NOT NULL DEFAULT 1 COMMENT '开关状态：1-开，2-关',
  `created_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updated_at` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `deleted_at` datetime(0) NULL DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_team_id`(`team_id`) USING BTREE,
  INDEX `idx_scene_id`(`scene_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '导入变量表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
