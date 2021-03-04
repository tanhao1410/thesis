/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50130
 Source Host           : localhost:3306
 Source Schema         : school

 Target Server Type    : MySQL
 Target Server Version : 50130
 File Encoding         : 65001

 Date: 04/03/2021 14:36:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for alarm
-- ----------------------------
DROP TABLE IF EXISTS `alarm`;
CREATE TABLE `alarm`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_id` bigint(20) NULL DEFAULT NULL,
  `item_id` bigint(20) NULL DEFAULT NULL,
  `value` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `is_normal` tinyint(1) NULL DEFAULT NULL,
  `level` int(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of alarm
-- ----------------------------
INSERT INTO `alarm` VALUES (7, '在线状态', 2, 0, '断线', '2021-03-03 10:37:47', 0, 4);
INSERT INTO `alarm` VALUES (8, '在线状态', 1, 0, '断线', '2021-03-02 14:36:29', 0, 4);
INSERT INTO `alarm` VALUES (9, '内存使用率告警', 1, 2, '76', '2021-03-02 14:33:43', 0, 2);
INSERT INTO `alarm` VALUES (10, 'cpu使用率告警', 1, 1, '15.08', '2021-03-02 14:33:43', 1, 3);

-- ----------------------------
-- Table structure for device
-- ----------------------------
DROP TABLE IF EXISTS `device`;
CREATE TABLE `device`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `group_id` bigint(20) NULL DEFAULT NULL,
  `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `port` int(8) NULL DEFAULT NULL,
  `x` int(11) NULL DEFAULT NULL,
  `y` int(11) NULL DEFAULT NULL,
  `department` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of device
-- ----------------------------
INSERT INTO `device` VALUES (1, '测试设备1', 1, '127.0.0.2', 10001, 60, 60, NULL);
INSERT INTO `device` VALUES (2, '测试设备2', 1, '127.0.0.1', 10002, 300, 60, NULL);

-- ----------------------------
-- Table structure for device_group
-- ----------------------------
DROP TABLE IF EXISTS `device_group`;
CREATE TABLE `device_group`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `x` int(11) NULL DEFAULT NULL,
  `y` int(11) NULL DEFAULT NULL,
  `department` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of device_group
-- ----------------------------
INSERT INTO `device_group` VALUES (1, '财务部', NULL, 233, 77, NULL);
INSERT INTO `device_group` VALUES (2, '数据部', NULL, 589, 88, NULL);
INSERT INTO `device_group` VALUES (3, '智能中心', NULL, 634, 84, NULL);

-- ----------------------------
-- Table structure for history_alarm
-- ----------------------------
DROP TABLE IF EXISTS `history_alarm`;
CREATE TABLE `history_alarm`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_id` bigint(20) NULL DEFAULT NULL,
  `item_id` bigint(20) NULL DEFAULT NULL,
  `value` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `end_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of history_alarm
-- ----------------------------
INSERT INTO `history_alarm` VALUES (40, '在线状态', 1, 0, '断线', '2021-03-02 14:23:22', '2021-03-02 14:23:39');
INSERT INTO `history_alarm` VALUES (41, '在线状态', 1, 0, '断线', '2021-03-02 14:28:51', '2021-03-02 14:33:42');

-- ----------------------------
-- Table structure for monitoring_data
-- ----------------------------
DROP TABLE IF EXISTS `monitoring_data`;
CREATE TABLE `monitoring_data`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_id` bigint(20) NULL DEFAULT NULL,
  `item_id` bigint(20) NULL DEFAULT NULL,
  `value` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of monitoring_data
-- ----------------------------
INSERT INTO `monitoring_data` VALUES (52, '磁盘使用率记录', 1, 3, '66', '2021-03-02 14:33:43');

-- ----------------------------
-- Table structure for monitoring_item
-- ----------------------------
DROP TABLE IF EXISTS `monitoring_item`;
CREATE TABLE `monitoring_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `device_id` bigint(20) NULL DEFAULT NULL,
  `rule_id` bigint(20) NULL DEFAULT NULL,
  `monitoring_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `threshold` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `param` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `alarm_condition` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `level` int(4) NULL DEFAULT NULL,
  `interval_time` int(8) NULL DEFAULT NULL,
  `need_mail` tinyint(1) NULL DEFAULT NULL,
  `is_alarm` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of monitoring_item
-- ----------------------------
INSERT INTO `monitoring_item` VALUES (1, 'cpu使用率告警', 1, 2, 'cpu使用率监控方法', '50', NULL, '>', 1, 120, 1, 1);
INSERT INTO `monitoring_item` VALUES (2, '内存使用率告警', 1, 3, '内存使用率监控方法', '50', NULL, '>', 1, 120, 1, 1);
INSERT INTO `monitoring_item` VALUES (3, '磁盘使用率记录', 1, 4, '磁盘使用率监控方法', NULL, 'c:', NULL, NULL, 120, NULL, NULL);

-- ----------------------------
-- Table structure for monitoring_rule
-- ----------------------------
DROP TABLE IF EXISTS `monitoring_rule`;
CREATE TABLE `monitoring_rule`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `method` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of monitoring_rule
-- ----------------------------
INSERT INTO `monitoring_rule` VALUES (1, '在线状态', NULL, '判断是否在线');
INSERT INTO `monitoring_rule` VALUES (2, 'cpu使用率监控', 'cpu使用率监控方法', NULL);
INSERT INTO `monitoring_rule` VALUES (3, '内存使用率监控', '内存使用率监控方法', NULL);
INSERT INTO `monitoring_rule` VALUES (4, '磁盘使用率', '磁盘使用率监控方法', NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` int(11) NULL DEFAULT NULL,
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `department` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'tanhao', 'e10adc3949ba59abbe56e057f20f883e', '2528865013@qq.com', 1, '', NULL);

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `department` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user_group
-- ----------------------------
INSERT INTO `user_group` VALUES (1, 1, 1, NULL);
INSERT INTO `user_group` VALUES (2, 2, 1, NULL);

SET FOREIGN_KEY_CHECKS = 1;
