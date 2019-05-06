/*
Navicat MySQL Data Transfer

Source Server         : mysqlTest
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : oceandb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-23 16:38:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` bigint(16) NOT NULL,
  `cust_id` bigint(16) NOT NULL,
  `amount` bigint(12) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('234001', '1001', '100', '第一个订单');
INSERT INTO `order` VALUES ('234002', '1002', '200', '第二个订单');
INSERT INTO `order` VALUES ('234003', '1001', '150', '第三个订单');
