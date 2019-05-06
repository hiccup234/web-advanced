/*
Navicat MySQL Data Transfer

Source Server         : mysqlTest
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : oceandb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-23 16:37:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `order_item_id` bigint(16) NOT NULL,
  `order_id` bigint(16) NOT NULL,
  `price` bigint(12) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_item_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `order` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order_item
-- ----------------------------
INSERT INTO `order_item` VALUES ('1', '234001', '20', '零食');
INSERT INTO `order_item` VALUES ('2', '234001', '50', '书籍');
INSERT INTO `order_item` VALUES ('3', '234001', '30', '袜子');
INSERT INTO `order_item` VALUES ('4', '234002', '120', '台灯');
INSERT INTO `order_item` VALUES ('5', '234002', '80', '笔筒');
