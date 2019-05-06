/*
Navicat MySQL Data Transfer

Source Server         : mysqlTest
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : oceandb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-23 23:21:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `student_id` int(12) NOT NULL,
  `student_name` varchar(20) DEFAULT NULL,
  `age` int(5) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('22110001', '张三', '19');
INSERT INTO `student` VALUES ('22110002', '李四', '20');
INSERT INTO `student` VALUES ('22110003', '王五', '19');
