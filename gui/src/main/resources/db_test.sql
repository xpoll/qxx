/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50709
Source Host           : 127.0.0.1:3306
Source Database       : db_test

Target Server Type    : MYSQL
Target Server Version : 50709
File Encoding         : 65001

Date: 2017-04-25 22:42:01
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `t7_student`;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `birth` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_consumer
-- ----------------------------
DROP TABLE IF EXISTS `t_consumer`;
CREATE TABLE `t_consumer` (
  `id` bigint(20) NOT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `sex` char(2) DEFAULT NULL,
  `method` varchar(50) DEFAULT NULL,
  `job` varchar(50) DEFAULT NULL,
  `company` varchar(50) DEFAULT NULL,
  `province` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `main` varchar(50) DEFAULT NULL,
  `mailaddress` varchar(50) DEFAULT NULL,
  `post_code` varchar(50) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_serve
-- ----------------------------
DROP TABLE IF EXISTS `t_serve`;
CREATE TABLE `t_serve` (
  `name` varchar(50) DEFAULT NULL,
  `month_time` int(11) DEFAULT NULL,
  `vear_time` int(11) DEFAULT NULL,
  `month` int(255) DEFAULT NULL,
  `year` int(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_source
-- ----------------------------
DROP TABLE IF EXISTS `t_source`;
CREATE TABLE `t_source` (
  `name` varchar(255) DEFAULT NULL,
  `month_rent` int(11) DEFAULT NULL,
  `hour_spent` int(11) DEFAULT NULL,
  `introduce` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_tab
-- ----------------------------
DROP TABLE IF EXISTS `t_tab`;
CREATE TABLE `t_tab` (
  `id` int(11) NOT NULL,
  `time` int(11) DEFAULT NULL,
  `spent` int(11) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_unit
-- ----------------------------
DROP TABLE IF EXISTS `t_unit`;
CREATE TABLE `t_unit` (
  `id` int(11) NOT NULL,
  `time` int(11) DEFAULT NULL,
  `spent` int(255) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `dengtime` datetime DEFAULT NULL,
  `tuitime` datetime DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `sex` char(2) NOT NULL COMMENT '性别',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `telephone` varchar(50) DEFAULT NULL COMMENT '电话',
  `mail` varchar(50) DEFAULT NULL,
  `date` datetime NOT NULL,
  `authority` varchar(50) DEFAULT NULL,
  `keyq` varchar(50) DEFAULT NULL,
  `keya` varchar(50) DEFAULT NULL,
  `realname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
