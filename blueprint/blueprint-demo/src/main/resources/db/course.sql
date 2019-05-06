/*
Navicat MySQL Data Transfer

Source Server         : mysqlTest
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : oceandb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-23 23:22:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `course_id` int(12) NOT NULL,
  `course_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1001', 'Java SE');
INSERT INTO `course` VALUES ('1002', 'Java EE');
INSERT INTO `course` VALUES ('1003', 'Android');
INSERT INTO `course` VALUES ('1004', 'Object-c');
