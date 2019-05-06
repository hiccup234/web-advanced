/*
Navicat MySQL Data Transfer

Source Server         : mysqlTest
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : oceandb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-23 23:21:52
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for student_course_rela
-- ----------------------------
DROP TABLE IF EXISTS `student_course_rela`;
CREATE TABLE `student_course_rela` (
  `rela_id` int(12) NOT NULL,
  `student_id` int(12) DEFAULT NULL,
  `course_id` int(12) DEFAULT NULL,
  PRIMARY KEY (`rela_id`),
  KEY `student_id` (`student_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`),
  CONSTRAINT `student_id` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student_course_rela
-- ----------------------------
INSERT INTO `student_course_rela` VALUES ('1', '22110001', '1001');
INSERT INTO `student_course_rela` VALUES ('2', '22110001', '1002');
INSERT INTO `student_course_rela` VALUES ('3', '22110001', '1003');
INSERT INTO `student_course_rela` VALUES ('4', '22110002', '1001');
INSERT INTO `student_course_rela` VALUES ('5', '22110002', '1004');
INSERT INTO `student_course_rela` VALUES ('6', '22110003', '1001');
