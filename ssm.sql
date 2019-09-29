/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50724
Source Host           : localhost:3306
Source Database       : ssm

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-09-29 16:21:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_good`
-- ----------------------------
DROP TABLE IF EXISTS `t_good`;
CREATE TABLE `t_good` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `craeteTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `goodName` varchar(255) NOT NULL,
  `images` text,
  `content` text,
  `stat` smallint(6) DEFAULT '0' COMMENT '0 刚刚发布，未审核,1已发布,2下线',
  `publicUserId` int(11) DEFAULT '0' COMMENT '发布者id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='角色配置表';

-- ----------------------------
-- Records of t_good
-- ----------------------------
INSERT INTO `t_good` VALUES ('1', '2019-09-28 17:09:00', '2019-01-09 09:11:14', '衣服1', '1.jpg', 'center\"><br></p>', '0', '1');
INSERT INTO `t_good` VALUES ('2', '2019-09-28 17:09:39', '2019-01-10 07:56:36', '商品123', '2.jpg', '<p class=', '0', '2');
INSERT INTO `t_good` VALUES ('3', '2019-09-28 17:09:10', '2019-01-12 15:14:14', '汉堡外卖', '4.jpg', '<h3><br></h3><p><br></p>', '0', '2');
INSERT INTO `t_good` VALUES ('4', '2019-09-28 17:09:42', '2019-01-15 16:26:32', '板蓝根', '4.jpg', '<p><img >', '0', '3');
INSERT INTO `t_good` VALUES ('5', '2019-09-28 17:09:48', '2019-01-15 16:32:40', '茅台', '5.jpg', '<p><img src>', '0', '4');

-- ----------------------------
-- Table structure for `t_norm`
-- ----------------------------
DROP TABLE IF EXISTS `t_norm`;
CREATE TABLE `t_norm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `craeteTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `isDel` tinyint(1) DEFAULT '0' COMMENT '是否已删除',
  `name` varchar(255) DEFAULT NULL,
  `unit` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='角色配置表';

-- ----------------------------
-- Records of t_norm
-- ----------------------------
INSERT INTO `t_norm` VALUES ('1', '2019-09-28 17:07:24', '2019-01-09 09:11:14', '0', 'XL', '件');
INSERT INTO `t_norm` VALUES ('2', '2019-09-28 17:07:28', '2019-01-10 07:56:36', '0', 'M', '件');
INSERT INTO `t_norm` VALUES ('3', '2019-09-28 17:07:30', '2019-01-12 15:14:14', '0', 'XXL', '件');
INSERT INTO `t_norm` VALUES ('4', '2019-09-28 17:07:33', '2019-01-15 16:26:32', '0', '大', '包');
INSERT INTO `t_norm` VALUES ('5', '2019-09-28 17:07:35', '2019-01-15 16:32:40', '0', '小', '包');

-- ----------------------------
-- Table structure for `t_order`
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isDel` tinyint(1) DEFAULT '0',
  `userId` int(11) unsigned DEFAULT NULL COMMENT '下单的用户id',
  `contactName` varchar(32) DEFAULT NULL COMMENT '联系人',
  `contactPhone` varchar(255) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(255) DEFAULT NULL,
  `stat` smallint(6) DEFAULT '0' COMMENT '0下单未支付，1已支付，2已发货，3已收货，4已完成，5订单超时失效，6已关闭',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('1', '2019-04-15 15:42:58', '2019-09-28 17:10:51', '0', '1', '的发送到发送到', '10086', '放松的方式', '400', '1123');
INSERT INTO `t_order` VALUES ('2', '2019-04-15 16:15:58', '2019-09-28 17:10:50', '0', '2', '的的撒地方撒旦', '10086', '辅导费', '500', '123');
INSERT INTO `t_order` VALUES ('3', '2019-04-16 14:02:36', '2019-09-28 17:10:54', '0', '4', '电风扇地方', '10086', '胜多负少的', '0', '123士大夫');
INSERT INTO `t_order` VALUES ('4', '2019-04-16 14:52:33', '2019-09-28 17:10:50', '0', '1', '3sad撒多', '10086', '日访问', '100', '123');
INSERT INTO `t_order` VALUES ('5', '2019-04-16 14:52:33', '2019-09-28 17:10:55', '0', '5', '3sad撒多', '10086', '日访问', '100', 'sdfdsf');

-- ----------------------------
-- Table structure for `t_rs_good_norm`
-- ----------------------------
DROP TABLE IF EXISTS `t_rs_good_norm`;
CREATE TABLE `t_rs_good_norm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isDel` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 默认有效，1删除',
  `goodId` int(11) DEFAULT NULL,
  `normId` int(50) DEFAULT NULL,
  `stock` int(11) DEFAULT '0' COMMENT '显示的库存，可空，真实库存取仓库总库存',
  `sellPrice` double DEFAULT '0' COMMENT '这个商品对应此规格的售价',
  `costPrice` double DEFAULT NULL COMMENT '入仓入货价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_rs_good_norm
-- ----------------------------
INSERT INTO `t_rs_good_norm` VALUES ('1', '2019-01-08 09:19:06', '2019-09-28 17:30:34', '0', '1', '1', '1010', '100', '1');
INSERT INTO `t_rs_good_norm` VALUES ('2', '2019-01-09 01:03:28', '2019-09-28 17:30:35', '0', '2', '1', '1200', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('3', '2019-01-09 01:03:28', '2019-09-28 17:30:35', '0', '3', '1', '1200', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('4', '2019-01-09 01:03:28', '2019-09-28 17:30:35', '0', '4', '1', '1200', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('5', '2019-01-09 01:03:28', '2019-09-28 17:30:36', '0', '5', '1', '1200', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('6', '2019-01-09 01:03:28', '2019-09-28 17:30:36', '0', '1', '2', '1200', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('7', '2019-01-09 01:03:28', '2019-09-28 17:30:36', '0', '2', '2', '1200', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('8', '2019-01-09 01:25:39', '2019-09-28 17:30:37', '0', '3', '2', '100', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('9', '2019-01-09 06:00:16', '2019-09-28 17:30:38', '0', '4', '2', '6391', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('10', '2019-01-09 08:18:09', '2019-09-28 17:30:37', '0', '5', '2', '299', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('11', '2019-01-09 09:11:14', '2019-09-28 17:30:39', '0', '1', '3', '235', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('12', '2019-01-10 07:56:36', '2019-09-28 17:30:39', '0', '2', '3', '100', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('13', '2019-01-11 03:44:58', '2019-09-28 17:30:40', '0', '3', '3', '100', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('14', '2019-01-11 03:44:58', '2019-09-28 17:30:41', '0', '4', '3', '300', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('15', '2019-01-11 04:28:14', '2019-09-28 17:30:41', '0', '5', '3', '100', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('16', '2019-01-12 15:14:14', '2019-09-28 17:30:41', '0', '1', '4', '124', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('17', '2019-01-15 16:26:32', '2019-09-28 17:30:42', '0', '2', '4', '100', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('18', '2019-01-15 16:26:32', '2019-09-28 17:30:43', '0', '3', '4', '100', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('19', '2019-01-15 16:26:32', '2019-09-28 17:30:42', '0', '4', '4', '100', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('20', '2019-01-15 16:26:32', '2019-09-28 17:30:43', '0', '5', '4', '100', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('21', '2019-01-15 16:32:40', '2019-09-28 17:30:44', '0', '1', '5', '100', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('22', '2019-01-27 17:21:44', '2019-09-28 17:30:44', '0', '2', '5', '100', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('23', '2019-03-28 09:53:38', '2019-09-28 17:30:45', '0', '3', '5', '101', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('24', '2019-03-28 16:03:05', '2019-09-28 17:30:45', '0', '4', '5', '100', null, '1');
INSERT INTO `t_rs_good_norm` VALUES ('25', '2019-03-28 16:04:51', '2019-09-28 17:30:45', '0', '5', '5', '100', null, '1');

-- ----------------------------
-- Table structure for `t_rs_order_good`
-- ----------------------------
DROP TABLE IF EXISTS `t_rs_order_good`;
CREATE TABLE `t_rs_order_good` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `orderId` bigint(20) unsigned DEFAULT NULL COMMENT '订单id',
  `goodId` int(11) DEFAULT NULL COMMENT '商品id',
  `rsNormId` int(11) DEFAULT NULL COMMENT '选择的规格',
  `buySum` int(11) DEFAULT NULL COMMENT '购买的数量',
  `payPrice` double DEFAULT NULL COMMENT '付款时的价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_rs_order_good
-- ----------------------------
INSERT INTO `t_rs_order_good` VALUES ('1', '2019-04-15 10:07:36', '2019-09-28 18:31:24', '1', '1', '1', '1', '600');
INSERT INTO `t_rs_order_good` VALUES ('2', '2019-04-15 10:07:36', '2019-09-28 18:31:25', '1', '2', '2', '2', '1000');
INSERT INTO `t_rs_order_good` VALUES ('3', '2019-04-15 10:34:19', '2019-09-28 18:31:25', '1', '3', '3', '3', '1000');
INSERT INTO `t_rs_order_good` VALUES ('4', '2019-04-15 10:34:19', '2019-09-28 18:31:26', '1', '4', '4', '4', '800');
INSERT INTO `t_rs_order_good` VALUES ('5', '2019-04-15 11:22:42', '2019-09-28 18:31:29', '2', '2', '6', '1', '200');
INSERT INTO `t_rs_order_good` VALUES ('6', '2019-04-15 11:22:42', '2019-09-28 18:31:30', '2', '2', '7', '1', '200');
INSERT INTO `t_rs_order_good` VALUES ('7', '2019-04-15 11:22:42', '2019-09-28 18:31:31', '2', '3', '8', '1', '200');
INSERT INTO `t_rs_order_good` VALUES ('8', '2019-04-15 14:54:34', '2019-09-28 18:31:35', '2', '4', '10', '5', '1000');
INSERT INTO `t_rs_order_good` VALUES ('9', '2019-04-15 14:54:34', '2019-09-28 18:31:38', '2', '5', '11', '4', '800');
INSERT INTO `t_rs_order_good` VALUES ('10', '2019-04-15 14:54:34', '2019-09-28 18:31:40', '3', '1', '14', '5', '1000');
INSERT INTO `t_rs_order_good` VALUES ('11', '2019-04-15 15:19:17', '2019-09-28 18:31:43', '3', '1', '15', '5', '1000');
INSERT INTO `t_rs_order_good` VALUES ('12', '2019-04-15 15:19:17', '2019-09-28 18:31:53', '3', '2', '16', '4', '800');
INSERT INTO `t_rs_order_good` VALUES ('13', '2019-04-15 15:19:17', '2019-09-28 18:31:55', '3', '5', '17', '4', '800');
INSERT INTO `t_rs_order_good` VALUES ('14', '2019-04-15 15:28:30', '2019-09-28 18:31:58', '5', '4', '18', '4', '800');
INSERT INTO `t_rs_order_good` VALUES ('15', '2019-04-15 15:28:30', '2019-09-28 18:32:00', '5', '4', '19', '4', '800');
INSERT INTO `t_rs_order_good` VALUES ('16', '2019-04-15 15:28:30', '2019-09-28 18:32:07', '5', '5', '1', '4', '800');
INSERT INTO `t_rs_order_good` VALUES ('17', '2019-04-15 15:42:58', '2019-09-28 18:32:08', '5', '5', '1', '3', '600');
INSERT INTO `t_rs_order_good` VALUES ('18', '2019-04-15 15:42:58', '2019-09-28 18:32:09', '5', '1', '1', '3', '600');
INSERT INTO `t_rs_order_good` VALUES ('19', '2019-04-15 15:42:58', '2019-09-28 18:32:12', '5', '1', '1', '3', '600');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updateTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isDel` tinyint(1) DEFAULT '0' COMMENT '1标注 yi删除了',
  `nickName` varchar(128) DEFAULT NULL,
  `headImage` varchar(1024) CHARACTER SET utf8 DEFAULT 'mg_head_001.png' COMMENT '默认头像,名字必须和代码一致:xxxx/img_head_xxx.png',
  `pwd` char(32) CHARACTER SET utf8 DEFAULT NULL,
  `stat` smallint(6) DEFAULT '0' COMMENT '状态 0默认 1黑名单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '2018-12-11 14:22:01', '2019-09-28 15:16:46', '0', '超级管理员', '0415.png', 'fb1a1b701ba544752f8a393e10b79ca8', '0');
INSERT INTO `t_user` VALUES ('2', '2018-12-15 00:38:05', '2019-09-28 15:16:53', '0', 'test3', '123.jpg', 'FB1A1B701BA544752F8A393E10B79CA8', '0');
INSERT INTO `t_user` VALUES ('3', '2018-12-28 01:08:41', '2019-09-28 15:16:48', '0', '测试账号哦', '180316.jpg', 'fb1a1b701ba544752f8a393e10b79ca8', '0');
DROP TRIGGER IF EXISTS `tr_insert_order`;
DELIMITER ;;
CREATE TRIGGER `tr_insert_order` BEFORE INSERT ON `t_order` FOR EACH ROW BEGIN
declare n int;
-- if n is null then
--     begin
--         set n=1;
--     end;
-- end if;
set NEW.id=concat('LO',DATE_FORMAT(CURDATE(),'%Y%m%d%H%M'),right(10000+n,4));
END
;;
DELIMITER ;
