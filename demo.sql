/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.100.129
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 192.168.100.129:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 15/08/2019 17:10:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `country_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `uid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of country
-- ----------------------------
INSERT INTO `country` VALUES (1, '中国', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', 25);
INSERT INTO `user` VALUES (2, 'test', 'test', 5);
INSERT INTO `user` VALUES (3, 'test', 'test', 5);
INSERT INTO `user` VALUES (4, 'test', 'test', 5);
INSERT INTO `user` VALUES (5, 'test', 'test', 5);
INSERT INTO `user` VALUES (6, 'test', 'test', 5);
INSERT INTO `user` VALUES (7, 'test', 'test', 5);
INSERT INTO `user` VALUES (8, 'test', 'test', 5);
INSERT INTO `user` VALUES (9, 'test', 'test', 5);
INSERT INTO `user` VALUES (10, 'test', 'test', 5);
INSERT INTO `user` VALUES (11, 'test', 'test', 5);
INSERT INTO `user` VALUES (12, 'test', 'test', 5);
INSERT INTO `user` VALUES (13, 'test', 'test', 5);
INSERT INTO `user` VALUES (14, 'test', 'test', 5);
INSERT INTO `user` VALUES (15, 'test', 'test', 5);
INSERT INTO `user` VALUES (16, 'test', 'test', 5);
INSERT INTO `user` VALUES (17, 'test', 'test', 5);
INSERT INTO `user` VALUES (18, 'test', 'test', 5);
INSERT INTO `user` VALUES (19, 'test', 'test', 5);
INSERT INTO `user` VALUES (20, 'test', 'test', 5);
INSERT INTO `user` VALUES (21, 'test', 'test', 5);
INSERT INTO `user` VALUES (22, 'test', 'test', 5);
INSERT INTO `user` VALUES (23, 'test', 'test', 5);
INSERT INTO `user` VALUES (24, 'test', 'test', 5);

SET FOREIGN_KEY_CHECKS = 1;
