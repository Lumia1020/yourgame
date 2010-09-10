/*
MySQL Data Transfer
Source Host: localhost
Source Database: gvp
Target Host: localhost
Target Database: gvp
Date: 2010-5-17 2:28:04
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for t_aids
-- ----------------------------
CREATE TABLE `t_aids` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `aidsName` varchar(100) DEFAULT NULL,
  `originalPrice` varchar(100) DEFAULT NULL,
  `price` varchar(100) DEFAULT NULL,
  `qid` int(11) DEFAULT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_customer
-- ----------------------------
CREATE TABLE `t_customer` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `customerName` varchar(100) DEFAULT NULL,
  `customerType` varchar(100) DEFAULT NULL,
  `productCode` text,
  `address` varchar(100) DEFAULT NULL,
  `tell` varchar(100) DEFAULT NULL,
  `fax` varchar(100) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`cid`),
  UNIQUE KEY `customerName` (`customerName`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_foundry
-- ----------------------------
CREATE TABLE `t_foundry` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `plateMerchant` varchar(100) DEFAULT NULL,
  `attritionRate` varchar(100) DEFAULT NULL,
  `processPrice` double DEFAULT NULL,
  `remark` text,
  `qid` int(11) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_materials
-- ----------------------------
CREATE TABLE `t_materials` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `productsName` varchar(100) DEFAULT NULL,
  `materialsName` varchar(100) DEFAULT NULL,
  `diameter` varchar(100) DEFAULT NULL,
  `weight` varchar(100) DEFAULT NULL,
  `size` varchar(100) DEFAULT NULL,
  `materialPrice` varchar(100) DEFAULT NULL,
  `price` varchar(100) DEFAULT NULL,
  `jiansha` varchar(100) DEFAULT NULL,
  `loss` varchar(100) DEFAULT NULL,
  `qid` int(11) DEFAULT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_process_info
-- ----------------------------
CREATE TABLE `t_process_info` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `models` varchar(100) DEFAULT NULL,
  `processName` varchar(100) DEFAULT NULL,
  `dayOutput` varchar(100) DEFAULT NULL,
  `processTime` varchar(100) DEFAULT NULL,
  `attritionRate` varchar(255) DEFAULT NULL,
  `processPrice` varchar(100) DEFAULT NULL,
  `conditioners` varchar(100) DEFAULT NULL,
  `qid` int(11) DEFAULT NULL,
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_quote_info
-- ----------------------------
CREATE TABLE `t_quote_info` (
  `qid` int(11) NOT NULL AUTO_INCREMENT,
  `cid` int(11) DEFAULT NULL,
  `customerName` varchar(100) DEFAULT NULL,
  `customerType` varchar(100) DEFAULT NULL,
  `productCode` varchar(100) DEFAULT NULL,
  `quoter` varchar(100) DEFAULT NULL,
  `price` varchar(100) DEFAULT NULL,
  `recordTime` datetime DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `dccNo` varchar(255) DEFAULT NULL,
  `pageNo` varchar(255) DEFAULT NULL,
  `ownerId` int(11) DEFAULT NULL,
  PRIMARY KEY (`qid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_ref_files
-- ----------------------------
CREATE TABLE `t_ref_files` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `url` text,
  `remark` text,
  `qid` int(11) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_reference_info
-- ----------------------------
CREATE TABLE `t_reference_info` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `cardKnife` varchar(100) DEFAULT NULL,
  `residualMaterial` varchar(100) DEFAULT NULL,
  `general` varchar(100) DEFAULT NULL,
  `output` varchar(100) DEFAULT NULL,
  `finishedWeight` varchar(100) DEFAULT NULL,
  `machineModel` text,
  `materialSupplier` varchar(100) DEFAULT NULL,
  `moq` varchar(100) DEFAULT NULL,
  `freight` double DEFAULT NULL,
  `qid` int(11) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
CREATE TABLE `t_role` (
  `rid` int(11) NOT NULL AUTO_INCREMENT,
  `r01` bit(1) DEFAULT NULL,
  `r02` bit(1) DEFAULT NULL,
  `r03` bit(1) DEFAULT NULL,
  `r04` bit(1) DEFAULT NULL,
  `r05` bit(1) DEFAULT NULL,
  `r06` bit(1) DEFAULT NULL,
  `r07` bit(1) DEFAULT NULL,
  `r08` bit(1) DEFAULT NULL,
  `r09` bit(1) DEFAULT NULL,
  `r10` bit(1) DEFAULT NULL,
  `r11` bit(1) DEFAULT NULL,
  `r12` bit(1) DEFAULT NULL,
  `r13` bit(1) DEFAULT NULL,
  `r14` bit(1) DEFAULT NULL,
  `r15` bit(1) DEFAULT NULL,
  `r16` bit(1) DEFAULT NULL,
  `r17` bit(1) DEFAULT NULL,
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
CREATE TABLE `t_user` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `rid` int(11) DEFAULT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `t_aids` VALUES ('5', '1', '1', '2', '4');
INSERT INTO `t_aids` VALUES ('7', '23', '23', '23', '4');
INSERT INTO `t_aids` VALUES ('8', '1', '1', '1', '11');
INSERT INTO `t_customer` VALUES ('1', '肖特', '主要客户', '123_,456,', '', '', '', '');
INSERT INTO `t_customer` VALUES ('2', '12', '主要客户', '123_,456,', '2', '2', '2', '');
INSERT INTO `t_customer` VALUES ('3', '333', '其他客户', '23', '', '', '', '');
INSERT INTO `t_customer` VALUES ('4', '1', '主要客户', '234', '234', '234', '', '');
INSERT INTO `t_customer` VALUES ('5', '2', '主要客户', '3', '', '', '', '');
INSERT INTO `t_customer` VALUES ('6', '3', '主要客户', '3', '', '', '', '');
INSERT INTO `t_customer` VALUES ('7', '4', '主要客户', '4', '', '', '', '');
INSERT INTO `t_customer` VALUES ('8', '5', '主要客户', '5', '', '', '', '');
INSERT INTO `t_customer` VALUES ('9', '6', '主要客户', '6', '', '', '', '');
INSERT INTO `t_customer` VALUES ('10', '7', '主要客户', '7', '', '', '', '');
INSERT INTO `t_customer` VALUES ('11', '8', '主要客户', '8', '', '', '', '');
INSERT INTO `t_customer` VALUES ('12', '9', '主要客户', '9', '', '', '', '');
INSERT INTO `t_customer` VALUES ('13', '调度', '其他客户', '33', '', '', '', '');
INSERT INTO `t_customer` VALUES ('15', 'd', '主要客户', 'd', '', '', '', '');
INSERT INTO `t_foundry` VALUES ('6', '2', '2', '1', '2', '4');
INSERT INTO `t_foundry` VALUES ('7', '1', '1', '1', '1', '11');
INSERT INTO `t_materials` VALUES ('3', '233', '11', '22', '33', '44', '77', '2', '1', '66', '1');
INSERT INTO `t_materials` VALUES ('5', '23', '2', '23', '', '', '', '2', '1', '', '3');
INSERT INTO `t_materials` VALUES ('6', '3', '3', '', '', '', '', '4', '4', '', '4');
INSERT INTO `t_materials` VALUES ('7', '233', '11', '22', '33', '44', '77', '2', '1', '66', '8');
INSERT INTO `t_materials` VALUES ('8', '233', '11', '22', '33', '44', '77', '2', '1', '66', '11');
INSERT INTO `t_process_info` VALUES ('4', '23', '23', '23', '23', '23', '23', '23', '3');
INSERT INTO `t_process_info` VALUES ('5', '23', '23', '23', '23', '23', '20', '23', '3');
INSERT INTO `t_process_info` VALUES ('6', '1', '1', '1', '1', '1', '1', '1', '11');
INSERT INTO `t_quote_info` VALUES ('1', '1', '肖特', '主要客户', '1234', '李显强', '1.0', '2010-03-27 00:00:00', '2010-04-07 00:00:00', '7651', 'eee', null);
INSERT INTO `t_quote_info` VALUES ('3', '1', '肖特', '主要客户', 'abc', '李显强', '46.0', '2010-04-07 00:00:00', '2010-04-07 00:00:00', 'ddd', 'ggg', null);
INSERT INTO `t_quote_info` VALUES ('4', '1', '肖特', '主要客户', 'e', '李显强', '27.0', '2010-04-08 00:00:00', null, 'e', 'e', null);
INSERT INTO `t_quote_info` VALUES ('5', '2', '12', '主要客户', '23', '李显强', '0', '2010-04-08 00:00:00', null, '23', '23', null);
INSERT INTO `t_quote_info` VALUES ('6', '1', '肖特', '主要客户', '23', '李显强', '0', '2010-04-08 00:00:00', null, '23', '23', null);
INSERT INTO `t_quote_info` VALUES ('7', '3', '333', '其他客户', '33', '李显强', '0', '2010-04-08 00:00:00', null, '33', '', null);
INSERT INTO `t_quote_info` VALUES ('8', '1', '肖特', '主要客户', '1234', '李显强', '1.0', '2010-03-27 00:00:00', '2010-04-09 00:00:00', '7651', 'eee', '1');
INSERT INTO `t_quote_info` VALUES ('9', '14', 'sd', 'd', '', '李显强', '0', '2010-04-09 00:00:00', null, '', '', null);
INSERT INTO `t_quote_info` VALUES ('10', '2', '12', '主要客户', '', '李显强', '0', '2010-04-10 00:00:00', null, '', '', null);
INSERT INTO `t_quote_info` VALUES ('11', '1', '肖特', '主要客户', '1234', '李显强', '4.0', '2010-03-27 00:00:00', '2010-04-10 00:00:00', '7651', 'eee', '1');
INSERT INTO `t_quote_info` VALUES ('12', '1', '肖特', '主要客户', '3', '李显强', '0', '2010-05-16 00:00:00', null, '232', '434', null);
INSERT INTO `t_quote_info` VALUES ('13', '2', '12', '主要客户', '4567', '李显强', '0', '2010-05-16 00:00:00', '2010-05-16 00:00:00', '234', '234', null);
INSERT INTO `t_ref_files` VALUES ('5', 'upload\\1\\330ee325-1d39-4584-bb72-9c779578f857.jpg', '照片 001.jpg', '8');
INSERT INTO `t_ref_files` VALUES ('6', 'upload\\1\\1861d58a-98e8-4e8d-ad55-8323d755f6f4.jpg', '照片 002.jpg', '8');
INSERT INTO `t_reference_info` VALUES ('1', 'aa', 'bb', 'cc', 'ff', '23ee', '0', null, 'gg', '0', '1');
INSERT INTO `t_reference_info` VALUES ('3', '2', '2', '2', '2', '2', '2', null, '2', '1', '4');
INSERT INTO `t_reference_info` VALUES ('4', '', '', '', '', '2', '', null, '', '2', '3');
INSERT INTO `t_reference_info` VALUES ('5', 'aa', 'bb', 'cc', 'ff', '23ee', '0', null, 'gg', '0', '8');
INSERT INTO `t_reference_info` VALUES ('6', 'aa', 'bb', 'cc', 'ff', '23ee', '022212', null, 'gg', '0', '11');
INSERT INTO `t_role` VALUES ('1', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');
INSERT INTO `t_role` VALUES ('2', '', '', '', '', '', '', '', '', null, '', '', '', '', '', '', '', null);
INSERT INTO `t_user` VALUES ('1', 'a', 'a', 'Jack', '1');
INSERT INTO `t_user` VALUES ('2', 'b', 'b', '廖瀚卿', '2');
