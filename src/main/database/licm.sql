/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50724
Source Host           : 127.0.0.1:3306
Source Database       : licm

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2020-04-15 20:43:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for msg
-- ----------------------------
DROP TABLE IF EXISTS `msg`;
CREATE TABLE `msg` (
  `id` varchar(255) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  `reply_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of msg
-- ----------------------------
INSERT INTO `msg` VALUES ('039808e7-8985-4d0f-8664-1d25558c289b', 'dd', 'dd', '2018-10-26 14:01:46');
INSERT INTO `msg` VALUES ('056a552e-0dfd-4a35-ba56-50a795b11949', 'xx', 'xx', '2018-10-26 22:03:50');
INSERT INTO `msg` VALUES ('07cc7bbb-5ad1-4623-be01-de7c04fa7c7c', 'void', 'static', '2018-10-26 14:00:13');
INSERT INTO `msg` VALUES ('1', '11', 'licm1', '2019-06-04 21:06:09');
INSERT INTO `msg` VALUES ('1668f056-d53c-4fd6-aba8-8e4333fd2b50', 'druid', 'druid 不起作用了？？？', '2018-10-25 15:17:40');
INSERT INTO `msg` VALUES ('2288072b-cf60-47ae-b20c-a77f1828f360', 'zzz', 'xxxx', '2018-10-26 21:46:39');
INSERT INTO `msg` VALUES ('285d5e4a-8cee-44a5-a188-21a567434053', 'date', 'date', '2018-10-26 13:27:23');
INSERT INTO `msg` VALUES ('3e3b6504-c9bf-48e9-9485-0a84fa18b50d', 'wow', 'wow', '2018-10-26 13:38:45');
INSERT INTO `msg` VALUES ('5e405011-6a58-428c-a8cf-99f6cab37cfa', null, '==========', '2020-04-15 16:38:18');
INSERT INTO `msg` VALUES ('62b2bee7-831c-4d6e-9f94-c681fad13ccc', 'zhangzq', 'pkusoft', '2018-10-26 21:53:36');
INSERT INTO `msg` VALUES ('6387d55e-9250-4b54-8f21-5f7a484d9746', 'zhangzq', 'licm i love you', '2018-10-25 15:12:31');
INSERT INTO `msg` VALUES ('67b66106-43ce-4a67-a09f-98670478ae1b', 'zhangzq', '?????', '2018-11-07 22:49:17');
INSERT INTO `msg` VALUES ('6aa3bf27-1a6a-4ddc-a386-4b343eaf77ae', '123', '321', '2018-10-25 15:02:04');
INSERT INTO `msg` VALUES ('724e5bf8-2c7d-4def-86d3-509cf7d5a5de', '张子强', '李春梦', '2018-10-26 12:59:22');
INSERT INTO `msg` VALUES ('7df81836-042d-41b0-a3ac-39a718c0b9ac', null, '==========', '2020-04-15 16:34:21');
INSERT INTO `msg` VALUES ('8ed8cbdd-71ee-4176-87a1-68452fef135a', 'zhangzq', 'licm', '2018-10-26 21:41:41');
INSERT INTO `msg` VALUES ('8ed8cbdd-71ee-4176-87a1-68452fef135e', 'xx', 'xx', '2018-10-26 13:30:26');
INSERT INTO `msg` VALUES ('9050b13a-65df-47d3-adc9-219c87f4f986', '张子强', '李春梦，我爱你！', '2018-10-25 15:14:33');
INSERT INTO `msg` VALUES ('9993bb21-05cc-4c22-9a85-e34581782bae', 'null', '==========', '2020-04-15 16:06:33');
INSERT INTO `msg` VALUES ('9993bb21-05cc-4c22-9a85-e34581782baf', 'null', '==========', '2020-04-15 16:06:33');
INSERT INTO `msg` VALUES ('9a080319-a6cf-4334-8e46-28ffff07856b', 'null', '==========', '2020-04-15 16:09:12');
INSERT INTO `msg` VALUES ('a1172faf-ffb7-4240-9f03-7235c6f5ab82', '张子强', '李春梦', '2018-10-26 12:59:29');
INSERT INTO `msg` VALUES ('ad3841d9-40ae-489b-b07b-512f03d5cbe4', '&lt;script&gt;', '&lt;zhangzq&gt;', '2018-10-26 13:14:53');
INSERT INTO `msg` VALUES ('bc467b54-eccb-413a-bc44-2f7ca67fa83e', 'licm', 'zhangzq i love you', '2018-10-25 15:14:09');
INSERT INTO `msg` VALUES ('c82b5670-8e8e-4628-89d6-14a5818494da', 'pub', 'lic', '2018-10-26 21:56:45');
INSERT INTO `msg` VALUES ('cd4a5cf8-5fa2-4899-bad7-ec8d7b9a154d', 'datea', 'datec', '2018-10-26 13:28:17');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `uid` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('559e4329-b6e4-4f38-90b4-6197aaaaba6c', 'zhangzq', 'winterme', '=======>');
INSERT INTO `user_info` VALUES ('7e0beba8-c5ae-4967-8c53-22441970fe72', 'zhangzq', 'winterme', '=======>');
INSERT INTO `user_info` VALUES ('ff8169c9-c9c5-448e-8db2-92bed7e212b0', 'filter', 'filter', '/getAll');
INSERT INTO `user_info` VALUES ('ff817588-24b4-40dd-8d9c-bac766b5b7fa', 'handle', 'handle', '/getAll');
INSERT INTO `user_info` VALUES ('ff875e00-2f7c-4890-ae49-4a2d5413b774', 'handle', 'handle', '/getAll');
INSERT INTO `user_info` VALUES ('ff88402b-90d0-426a-84b1-a3155ff17409', 'handle', 'handle', '/getAll');
INSERT INTO `user_info` VALUES ('ff91d0fd-9340-41ba-bedc-7e8e11daa07a', 'filter', 'filter', '/getAll');
INSERT INTO `user_info` VALUES ('ff943293-a7a6-476a-93e3-135329f6ff60', 'filter', 'filter', '/getAll');
INSERT INTO `user_info` VALUES ('ff959de7-07cc-4deb-9a77-40c8fbaafcda', 'handle', 'handle', '/getAll');
INSERT INTO `user_info` VALUES ('ff962764-03bc-47ca-866c-497f762b94ad', 'filter', 'filter', '/getAll');
