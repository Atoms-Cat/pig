/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.181.130（8.0）
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : 192.168.181.130:3306
 Source Schema         : pig_sip_domain

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 12/06/2022 17:25:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sip_domain
-- ----------------------------
DROP TABLE IF EXISTS `sip_domain`;
CREATE TABLE `sip_domain`  (
  `id` bigint NOT NULL,
  `pid` bigint NOT NULL DEFAULT 0 COMMENT '父级id',
  `domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '域',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0-正常，1-删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '域表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sip_domain
-- ----------------------------
INSERT INTO `sip_domain` VALUES (1, 0, 'atomscat.com', '0', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sip_subscriber
-- ----------------------------
DROP TABLE IF EXISTS `sip_subscriber`;
CREATE TABLE `sip_subscriber`  (
  `id` bigint NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `domain` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `belong_system` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '使用的系统，0：opensips、1：freeswitch、2：OWT',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0-正常，1-删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = 'sip 分机号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sip_subscriber
-- ----------------------------
INSERT INTO `sip_subscriber` VALUES (1, '1001', 'atomscat.com', '1234', '0', '0', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for sip_system_user_subscriber
-- ----------------------------
DROP TABLE IF EXISTS `sip_system_user_subscriber`;
CREATE TABLE `sip_system_user_subscriber`  (
  `id` bigint NOT NULL,
  `subscriber_id` bigint NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT NULL,
  `terminal_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '使用终端类型：0：web',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '0-正常，1-删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统用户使用sip分机号情况' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sip_system_user_subscriber
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_domain
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_domain`;
CREATE TABLE `sys_user_domain`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `domain_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `domain_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与域关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_domain
-- ----------------------------
INSERT INTO `sys_user_domain` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
