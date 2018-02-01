/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : gtd_idata

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-02-01 19:27:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dep_name` varchar(255) DEFAULT NULL,
  `dep_desc` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('1', '甲方', '', '0');
INSERT INTO `department` VALUES ('2', '部门1', '', '1');
INSERT INTO `department` VALUES ('3', '部门2', '', '1');

-- ----------------------------
-- Table structure for document
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `belong_to` varchar(255) DEFAULT NULL COMMENT 'contract、task',
  `source_id` int(11) DEFAULT NULL,
  `category` varchar(255) DEFAULT '1' COMMENT '文档分类：1普通文档、2任务要求、3任务结果',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of document
-- ----------------------------
INSERT INTO `document` VALUES ('9', '黄埔公安局项目管理软件需求V1.2.doc', '1517394074451', 'contract', '3', '1');
INSERT INTO `document` VALUES ('12', 'commerceLAB whitepaper1203 n.pdf', '1517396967815', 'task', '6', '2');
INSERT INTO `document` VALUES ('13', '轻量级数据分析.pptx', '1517470451023', 'task', '7', '2');
INSERT INTO `document` VALUES ('14', '黄埔公安项目管理软件汇报-V1.1.pptx', '1517472131344', 'task', '7', '3');
INSERT INTO `document` VALUES ('15', 'templates_06.png', '1517473600289', 'task', '8', '3');
INSERT INTO `document` VALUES ('16', '精通D3.js交互式数据可视化高级编程.pdf', '1517477083313', 'task', '9', '3');
INSERT INTO `document` VALUES ('17', '抽样样本量的确定.ppt', '1517478476700', 'task', '10', '2');
INSERT INTO `document` VALUES ('18', 'matplotlib Plotting Cookbook.pdf', '1517479042869', 'task', '11', '2');
INSERT INTO `document` VALUES ('19', '中低频量化交易策略研发（上）.pdf', '1517479254338', 'task', '11', '3');

-- ----------------------------
-- Table structure for document_version
-- ----------------------------
DROP TABLE IF EXISTS `document_version`;
CREATE TABLE `document_version` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_id` int(11) DEFAULT NULL,
  `owner_staff_id` int(11) DEFAULT NULL,
  `upload_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of document_version
-- ----------------------------

-- ----------------------------
-- Table structure for ducument_file
-- ----------------------------
DROP TABLE IF EXISTS `ducument_file`;
CREATE TABLE `ducument_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `file_nam` varchar(255) DEFAULT NULL,
  `file_md5` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ducument_file
-- ----------------------------

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL COMMENT '甲方 o_id，乙方s_id',
  `emp_name` varchar(255) DEFAULT NULL,
  `emp_gender` varchar(255) DEFAULT NULL,
  `emp_mobile` varchar(255) DEFAULT NULL,
  `emp_email` varchar(255) DEFAULT NULL,
  `emp_title` varchar(255) DEFAULT NULL,
  `emp_grade` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_emp_mobile` (`emp_mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', '0', 'o0', '超级用户', '0', '000', 'root@system', 'root', 'roor', '000');
INSERT INTO `employee` VALUES ('2', '2', 'o', '部门1员工1', '1', '13800010001', '', '', '', '123456');
INSERT INTO `employee` VALUES ('3', '2', 'o', '部门1员工2', '1', '13800010002', '', '', '', '123456');
INSERT INTO `employee` VALUES ('4', '3', 'o', '部门2员工1', '1', '13800020001', '', '', '', '123456');
INSERT INTO `employee` VALUES ('5', '3', 'o', '部门2员工2', '1', '13800020002', '', '', '', '123456');
INSERT INTO `employee` VALUES ('6', '1', 'o', '甲方领导1', '0', '13800000001', '', '', '', '123456');
INSERT INTO `employee` VALUES ('7', '1', 'o', '甲方领导2', '1', '13800000002', '', '', '', '123456');
INSERT INTO `employee` VALUES ('9', '2', 'o', '部门1员工3', '1', '13800010003', '', '', '', '123456');
INSERT INTO `employee` VALUES ('10', '1', 's', '监理1员工1', '0', '13900000001', '', '', '', '123456');
INSERT INTO `employee` VALUES ('11', '1', 's', '监理1员工2', '1', '13900000002', '', '', '', '123456');
INSERT INTO `employee` VALUES ('12', '1', 's', '监理1员工3', '1', '13900000003', '', '', '', '123456');
INSERT INTO `employee` VALUES ('13', '2', 's', '供应商1员工1', '1', '13900010001', '', '', '', '123456');
INSERT INTO `employee` VALUES ('14', '2', 's', '供应商1员工2', '0', '13900010002', '', '', '', '123456');
INSERT INTO `employee` VALUES ('15', '2', 's', '供应商1员工3', '1', '13900010003', '', '', '', '123456');
INSERT INTO `employee` VALUES ('16', '2', 's', '供应商1员工4', '1', '13900010004', '', '', '', '123456');
INSERT INTO `employee` VALUES ('17', '3', 's', '供应商2员工1', '1', '13900020001', '', '', '', '123456');
INSERT INTO `employee` VALUES ('18', '3', 's', '供应商2员工2', '1', '13900020002', '', '', '', '123456');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actor` varchar(255) DEFAULT NULL,
  `op_time` datetime DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('1', '0', '2018-01-24 14:25:49', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('2', '0', '2018-01-24 14:26:59', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('3', '1', '2018-01-24 14:28:21', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('4', '1', '2018-01-24 16:17:42', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('5', '1', '2018-01-25 17:44:38', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('6', '1', '2018-01-26 11:10:28', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('7', '1', '2018-01-26 16:55:44', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('8', '1', '2018-01-26 18:05:40', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('9', '1', '2018-01-26 19:03:06', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('10', '1', '2018-01-27 12:23:52', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('11', '2', '2018-01-27 13:36:09', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('12', '3', '2018-01-27 14:56:36', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('13', '2', '2018-01-27 18:09:20', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('14', '3', '2018-01-27 22:46:43', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('15', '1', '2018-01-27 23:34:08', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('16', '3', '2018-01-27 23:36:29', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('17', '2', '2018-01-28 00:46:46', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('18', '3', '2018-01-28 00:47:56', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('19', '2', '2018-01-28 00:49:48', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('20', '1', '2018-01-28 12:34:46', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('21', '1', '2018-01-28 13:19:45', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('22', '2', '2018-01-28 13:21:01', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('23', '2', '2018-01-28 13:23:36', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('24', '1', '2018-01-28 13:57:12', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('25', '1', '2018-01-28 13:59:49', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('26', '2', '2018-01-28 14:01:43', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('27', '2', '2018-01-28 14:04:55', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('28', '1', '2018-01-28 14:05:25', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('29', '1', '2018-01-28 14:14:48', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('30', '1', '2018-01-28 14:23:12', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('31', '1', '2018-01-28 14:44:29', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('32', '1', '2018-01-28 14:47:28', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('33', '6', '2018-01-28 14:47:53', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('34', '0', '2018-01-28 14:49:55', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('35', '0', '2018-01-28 14:50:25', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('36', '1', '2018-01-28 14:51:39', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('37', '6', '2018-01-28 14:51:52', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('38', '6', '2018-01-28 14:53:40', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('39', '7', '2018-01-28 14:55:19', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('40', '7', '2018-01-28 14:59:01', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('41', '3', '2018-01-28 15:01:41', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('42', '10', '2018-01-28 15:02:05', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('43', '2', '2018-01-28 15:03:53', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('44', '13', '2018-01-28 15:04:21', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('45', '1', '2018-01-28 15:04:47', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('46', '0', '2018-01-28 15:57:07', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('47', '0', '2018-01-28 15:57:09', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('48', '0', '2018-01-28 15:57:18', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('49', '0', '2018-01-28 15:57:19', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('50', '0', '2018-01-28 15:57:57', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('51', '0', '2018-01-28 15:57:58', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('52', '1', '2018-01-28 15:58:03', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('53', '0', '2018-01-28 15:58:17', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('54', '2', '2018-01-28 15:58:21', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('55', '1', '2018-01-28 16:21:46', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('56', '1', '2018-01-28 16:22:29', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('57', '0', '2018-01-28 16:22:52', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('58', '0', '2018-01-28 16:23:28', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('59', '0', '2018-01-28 16:23:40', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('60', '0', '2018-01-28 16:24:02', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('61', '0', '2018-01-28 16:24:10', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('62', '0', '2018-01-28 16:24:16', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('63', '0', '2018-01-28 16:26:09', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('64', '0', '2018-01-28 16:27:03', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('65', '0', '2018-01-28 16:27:14', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('66', '1', '2018-01-28 16:27:21', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('67', '1', '2018-01-28 16:27:41', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('68', '0', '2018-01-28 16:28:52', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('69', '1', '2018-01-28 16:28:56', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('70', '10', '2018-01-28 16:29:28', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('71', '0', '2018-01-28 16:31:44', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('72', '2', '2018-01-28 16:31:47', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('73', '0', '2018-01-29 11:00:55', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('74', '1', '2018-01-29 11:01:00', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('75', '2', '2018-01-29 11:01:32', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('76', '1', '2018-01-29 11:12:02', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('77', '2', '2018-01-29 12:09:42', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('78', '2', '2018-01-30 07:58:51', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('79', '7', '2018-01-30 09:15:50', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('80', '2', '2018-01-30 10:05:38', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('81', '6', '2018-01-30 10:12:25', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('82', '7', '2018-01-30 10:12:50', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('83', '1', '2018-01-30 10:17:25', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('84', '7', '2018-01-30 10:45:08', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('85', '1', '2018-01-30 11:33:29', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('86', '6', '2018-01-30 11:35:56', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('87', '7', '2018-01-30 11:36:12', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('88', '1', '2018-01-30 14:31:51', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('89', '7', '2018-01-30 14:38:47', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('90', '1', '2018-01-30 15:08:20', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('91', '7', '2018-01-30 15:11:01', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('92', '2', '2018-01-30 16:06:59', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('93', '7', '2018-01-30 17:52:30', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('94', '2', '2018-01-30 17:54:39', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('95', '3', '2018-01-30 17:58:59', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('96', '2', '2018-01-30 19:04:39', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('97', '3', '2018-01-30 19:26:10', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('98', '2', '2018-01-30 19:27:17', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('99', '1', '2018-01-31 11:21:41', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('100', '0', '2018-01-31 11:51:01', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('101', '2', '2018-01-31 11:51:09', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('102', '1', '2018-01-31 15:06:44', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('103', '2', '2018-01-31 15:13:57', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('104', '6', '2018-01-31 15:27:44', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('105', '0', '2018-01-31 15:28:57', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('106', '2', '2018-01-31 15:29:01', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('107', '2', '2018-01-31 15:48:35', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('108', '2', '2018-01-31 16:23:58', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('109', '3', '2018-01-31 16:35:23', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('110', '2', '2018-01-31 16:43:04', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('111', '2', '2018-01-31 18:14:12', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('112', '3', '2018-01-31 18:38:20', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('113', '2', '2018-01-31 18:48:22', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('114', '3', '2018-01-31 18:54:35', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('115', '2', '2018-01-31 19:06:17', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('116', '3', '2018-01-31 19:09:42', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('117', '2', '2018-01-31 19:55:24', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('118', '2', '2018-02-01 09:35:23', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('119', '2', '2018-02-01 09:37:54', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('120', '6', '2018-02-01 14:40:48', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('121', '0', '2018-02-01 14:41:07', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('122', '7', '2018-02-01 14:41:15', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('123', '2', '2018-02-01 14:51:19', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('124', '7', '2018-02-01 14:57:32', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('125', '2', '2018-02-01 15:15:54', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('126', '7', '2018-02-01 15:16:21', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('127', '2', '2018-02-01 15:19:38', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('128', '7', '2018-02-01 15:24:22', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('129', '2', '2018-02-01 15:26:13', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('130', '3', '2018-02-01 15:34:59', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('131', '2', '2018-02-01 16:08:44', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('132', '2', '2018-02-01 16:15:43', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('133', '3', '2018-02-01 16:15:56', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('134', '2', '2018-02-01 16:24:42', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('135', '3', '2018-02-01 16:28:08', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('136', '2', '2018-02-01 16:49:53', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('137', '3', '2018-02-01 16:53:29', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('138', '2', '2018-02-01 17:01:55', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('139', '2', '2018-02-01 17:07:17', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('140', '3', '2018-02-01 17:07:33', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('141', '2', '2018-02-01 17:08:41', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('142', '3', '2018-02-01 17:23:08', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('143', '0', '2018-02-01 17:25:47', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('144', '2', '2018-02-01 17:25:53', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('145', '10', '2018-02-01 17:31:58', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('146', '2', '2018-02-01 17:46:41', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('147', '3', '2018-02-01 17:48:15', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('148', '2', '2018-02-01 17:49:24', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('149', '10', '2018-02-01 17:50:20', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('150', '3', '2018-02-01 17:51:00', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('151', '2', '2018-02-01 17:54:20', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('152', '3', '2018-02-01 17:58:23', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('153', '2', '2018-02-01 17:58:59', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('154', '3', '2018-02-01 18:00:01', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('155', '2', '2018-02-01 18:01:31', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('156', '10', '2018-02-01 18:05:14', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('157', '1', '2018-02-01 18:21:29', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('158', '10', '2018-02-01 18:23:13', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('159', '2', '2018-02-01 18:35:32', '登录', '系统', '成功');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from` int(11) DEFAULT NULL,
  `to` int(11) DEFAULT NULL,
  `to_scope` varchar(255) DEFAULT 'actor' COMMENT 'actor个人、group项目组',
  `title` varchar(255) DEFAULT NULL,
  `body` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT 'normal' COMMENT 'normal 普通消息(任务发布、任务延期（系统发送）)，audit 针对项目审批、confirm针对任务确认',
  `relation_id` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `is_read` int(11) DEFAULT '0',
  `is_exec` int(11) DEFAULT '0',
  `note` varchar(255) DEFAULT NULL COMMENT '用来临时储存信息，属于辅助字段。',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('10', '2', '3', 'actor', '任务分配', '您被分配了一条新任务，任务名称为【Task_1_1】', 'normal', '6', '2018-01-31 19:09:27', '1', '1', null);
INSERT INTO `message` VALUES ('12', '3', '2', 'actor', '任务完成确认', '您分配的一条任务已完成，需要确认。任务名称为【Task_1_1】', 'confirm', '6', '2018-01-31 19:54:37', '1', '1', null);
INSERT INTO `message` VALUES ('13', '2', '7', 'actor', '项目计划审批', '部门1员工1已完成项目 【雪亮工程一期】的项目计划，请您进行审批', 'audit', '1', '2018-02-01 14:39:02', '1', '1', null);
INSERT INTO `message` VALUES ('14', '2', '7', 'actor', '时间太长了', '部门1员工1已完成项目 【雪亮工程一期】的项目计划，请您进行审批', 'audit', '1', '2018-02-01 14:56:47', '1', '1', null);
INSERT INTO `message` VALUES ('15', '2', '7', 'actor', '项目计划审批', '部门1员工1已完成项目 【雪亮工程一期】的项目计划，请您进行审批', 'audit', '1', '2018-02-01 15:16:05', '1', '1', '时间太长了');
INSERT INTO `message` VALUES ('16', '7', '2', 'actor', '项目计划审核被拒绝', '您提交的项目【雪亮工程一期的计划审核申请被拒绝，理由是【时间太长了】', 'normal', '1', '2018-02-01 15:19:17', '1', '1', null);
INSERT INTO `message` VALUES ('17', '2', '7', 'actor', '项目计划审批', '部门1员工1已完成项目 【雪亮工程一期】的项目计划，请您进行审批', 'audit', '1', '2018-02-01 15:24:05', '1', '1', null);
INSERT INTO `message` VALUES ('18', '2', '3', 'actor', '任务分配', '您被分配了一条新任务，任务名称为【Task_1_2】', 'normal', '7', '2018-02-01 15:34:11', '1', '1', null);
INSERT INTO `message` VALUES ('19', '3', '2', 'actor', '任务完成确认', '您分配的一条任务已完成，需要确认。任务名称为【Task_1_2】', 'confirm', '7', '2018-02-01 16:05:41', '1', '1', null);
INSERT INTO `message` VALUES ('20', '3', '2', 'actor', '任务分配', '您被分配了一条新任务，任务名称为【Task_1_3】', 'normal', '8', '2018-02-01 16:23:11', '1', '1', null);
INSERT INTO `message` VALUES ('21', '2', '3', 'actor', '任务完成确认', '您分配的一条任务已完成，需要确认。任务名称为【Task_1_2_1】', 'confirm', '8', '2018-02-01 16:27:47', '1', '1', '就是想恶心你一下');
INSERT INTO `message` VALUES ('22', '3', '2', 'actor', '任务检查未通过', '您标记完成的任务检查没有通过，请查看详情。', 'normal', '8', '2018-02-01 16:58:58', '1', '1', null);
INSERT INTO `message` VALUES ('23', '3', '2', 'actor', '任务检查未通过', '您标记完成的任务检查没有通过，原因是【就是想恶心你一下】', 'normal', '8', '2018-02-01 17:01:27', '1', '1', null);
INSERT INTO `message` VALUES ('24', '2', '3', 'actor', '任务完成确认', '您分配的一条任务已完成，需要确认。任务名称为【Task_1_2_1】', 'confirm', '8', '2018-02-01 17:06:47', '1', '1', null);
INSERT INTO `message` VALUES ('25', '3', '2', 'actor', '任务完成', '任务完成', 'normal', '8', '2018-02-01 17:07:57', '1', '1', null);
INSERT INTO `message` VALUES ('26', '2', '3', 'actor', '任务分配', '您被分配了一条新任务，任务名称为【Task_1_3】', 'normal', '9', '2018-02-01 17:22:23', '1', '1', null);
INSERT INTO `message` VALUES ('27', '3', '2', 'actor', '任务完成确认', '您分配的一条任务已完成，需要确认。任务名称为【Task_1_3】', 'confirm', '9', '2018-02-01 17:25:11', '1', '1', '10');
INSERT INTO `message` VALUES ('28', '2', '10', 'actor', '任务完成确认', '您分配的一条任务已完成，需要确认。任务名称为【Task_1_3】', 'confirm', '9', '2018-02-01 17:26:45', '1', '1', null);
INSERT INTO `message` VALUES ('29', '10', '3', 'actor', '任务完成', '您承接的任务【Task_1_3】确认完成', 'normal', '9', '2018-02-01 17:32:26', '1', '1', null);
INSERT INTO `message` VALUES ('30', '2', '3', 'actor', '任务分配', '您被分配了一条新任务，任务名称为【Tasj_1_3_1】', 'normal', '10', '2018-02-01 17:47:56', '1', '1', null);
INSERT INTO `message` VALUES ('31', '3', '2', 'actor', '任务完成确认', '您分配的一条任务已完成，需要确认。任务名称为【Tasj_1_3_1】', 'confirm', '10', '2018-02-01 17:48:58', '1', '1', '10');
INSERT INTO `message` VALUES ('32', '2', '10', 'actor', '任务完成确认', '任务【Tasj_1_3_1】已完成，需要您再次确认。', 'confirm', '10', '2018-02-01 17:49:50', '1', '1', null);
INSERT INTO `message` VALUES ('33', '10', '3', 'actor', '任务完成', '您承接的任务【Tasj_1_3_1】确认完成', 'normal', '10', '2018-02-01 17:50:39', '1', '1', null);
INSERT INTO `message` VALUES ('34', '2', '14', 'actor', '任务分配', '您被分配了一条新任务，任务名称为【task_1_3_2】', 'normal', '11', '2018-02-01 17:57:22', '0', '0', null);
INSERT INTO `message` VALUES ('35', '2', '3', 'actor', '任务分配', '您被分配了一条新任务，任务名称为【task_1_3_2】', 'normal', '11', '2018-02-01 17:59:20', '1', '1', null);
INSERT INTO `message` VALUES ('36', '3', '2', 'actor', '任务完成确认', '您分配的一条任务已完成，需要确认。任务名称为【task_1_3_2】', 'confirm', '11', '2018-02-01 18:00:56', '1', '1', '10');
INSERT INTO `message` VALUES ('37', '2', '10', 'actor', '任务完成确认', '任务【task_1_3_2】已完成，需要您再次确认。', 'confirm', '11', '2018-02-01 18:02:21', '1', '1', null);
INSERT INTO `message` VALUES ('38', '10', '3', 'actor', '任务完成', '您承接的任务【task_1_3_2】确认完成', 'normal', '11', '2018-02-01 18:06:23', '0', '0', null);
INSERT INTO `message` VALUES ('39', '10', '3', 'actor', '任务完成', '您承接的任务【task_1_3_2】确认完成', 'normal', '11', '2018-02-01 18:27:06', '0', '0', null);

-- ----------------------------
-- Table structure for param_key
-- ----------------------------
DROP TABLE IF EXISTS `param_key`;
CREATE TABLE `param_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(255) DEFAULT NULL,
  `param_key_name` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of param_key
-- ----------------------------
INSERT INTO `param_key` VALUES ('1', 'category', '项目类型', 'String');
INSERT INTO `param_key` VALUES ('2', 'built-in-role', '内置角色', 'String');

-- ----------------------------
-- Table structure for param_value
-- ----------------------------
DROP TABLE IF EXISTS `param_value`;
CREATE TABLE `param_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `param_id` int(11) DEFAULT NULL,
  `param_value` varchar(255) DEFAULT NULL,
  `param_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of param_value
-- ----------------------------
INSERT INTO `param_value` VALUES ('1', '1', '1', '软件实施项目');
INSERT INTO `param_value` VALUES ('2', '1', '2', '土木工程建筑');
INSERT INTO `param_value` VALUES ('3', '2', 'R01', '分管领导');
INSERT INTO `param_value` VALUES ('4', '2', 'R02', '项目经理');
INSERT INTO `param_value` VALUES ('5', '2', 'R03', '项目助理');
INSERT INTO `param_value` VALUES ('6', '2', 'R04', '监理');
INSERT INTO `param_value` VALUES ('7', '2', 'R10', '常规项目成员');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_name` varchar(255) DEFAULT NULL,
  `project_desc` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `is_key` int(11) DEFAULT NULL,
  `is_forzen` int(11) DEFAULT '0',
  `create_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `actor_staff_id` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `audit_state` varchar(255) DEFAULT 'init' COMMENT 'init（初始化）、wait(等待审核)、refuse(审核拒绝)、ready(审核通过就绪)',
  `dep_id` int(11) DEFAULT NULL,
  `owner_id` int(11) DEFAULT NULL COMMENT '项目创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', '雪亮工程一期', '', '', '1', '0', '2017-12-17 00:00:00', '2018-01-01 00:00:00', '2018-05-01 00:00:00', '2', '', '500', 'ready', '2', '2');

-- ----------------------------
-- Table structure for project_contract
-- ----------------------------
DROP TABLE IF EXISTS `project_contract`;
CREATE TABLE `project_contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) DEFAULT NULL,
  `vendor_id` int(11) DEFAULT NULL,
  `contract_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_contract
-- ----------------------------
INSERT INTO `project_contract` VALUES ('1', '1', '1', '监理合同1');
INSERT INTO `project_contract` VALUES ('2', '1', '2', '与供应商1的合同');
INSERT INTO `project_contract` VALUES ('3', '1', '3', '与供应商2的合同');

-- ----------------------------
-- Table structure for project_group
-- ----------------------------
DROP TABLE IF EXISTS `project_group`;
CREATE TABLE `project_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `group_role` varchar(255) DEFAULT NULL,
  `group_desc` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_group
-- ----------------------------
INSERT INTO `project_group` VALUES ('1', '1', '分管领导', 'R01', '单独组，一个人。项目阶段变更的审批人', '0');
INSERT INTO `project_group` VALUES ('2', '1', '项目经理', 'R02', '单独组，一个人。项目阶段定义和以及任务分配', '1');
INSERT INTO `project_group` VALUES ('3', '1', '项目助理', 'R03', '单独组，一个人。协助项目经理工作', '2');
INSERT INTO `project_group` VALUES ('4', '1', '监理', 'R04', '单独组，若干人。参与任务的二次审核', '2');
INSERT INTO `project_group` VALUES ('5', '1', '实施组1', 'R10', '常规的项目组。一般增加实施组即可', '2');

-- ----------------------------
-- Table structure for project_staff
-- ----------------------------
DROP TABLE IF EXISTS `project_staff`;
CREATE TABLE `project_staff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_id` int(11) DEFAULT NULL,
  `emp_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_staff
-- ----------------------------
INSERT INTO `project_staff` VALUES ('1', '1', '7');
INSERT INTO `project_staff` VALUES ('5', '4', '10');
INSERT INTO `project_staff` VALUES ('6', '2', '2');
INSERT INTO `project_staff` VALUES ('7', '3', '3');
INSERT INTO `project_staff` VALUES ('44', '5', '13');
INSERT INTO `project_staff` VALUES ('45', '5', '14');

-- ----------------------------
-- Table structure for project_stage
-- ----------------------------
DROP TABLE IF EXISTS `project_stage`;
CREATE TABLE `project_stage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `has_supervisor` int(11) DEFAULT NULL,
  `progress` int(11) DEFAULT NULL,
  `actor_staff_id` int(11) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `note` varchar(255) DEFAULT NULL,
  `parent` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_stage
-- ----------------------------

-- ----------------------------
-- Table structure for project_tag
-- ----------------------------
DROP TABLE IF EXISTS `project_tag`;
CREATE TABLE `project_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) DEFAULT NULL,
  `tag_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_tag
-- ----------------------------

-- ----------------------------
-- Table structure for project_task
-- ----------------------------
DROP TABLE IF EXISTS `project_task`;
CREATE TABLE `project_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `parent` int(11) DEFAULT '0',
  `project_id` int(11) DEFAULT '0',
  `task_desc` varchar(255) DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `progress` float(4,2) DEFAULT '0.00',
  `state` int(11) DEFAULT '0' COMMENT '未开始0，进行中1，已完成2，待检查3，确认完成4',
  `assign_staff_id` int(11) DEFAULT NULL,
  `actor_staff_id` int(255) DEFAULT NULL,
  `plan_workload` int(11) DEFAULT '0',
  `real_workload` int(11) DEFAULT '0',
  `is_delay` int(11) DEFAULT '0',
  `delay_reason` varchar(255) DEFAULT NULL,
  `readonly` int(11) DEFAULT '0',
  `editable` int(11) DEFAULT '1',
  `type` varchar(255) DEFAULT 'task',
  `real_start_date` datetime DEFAULT NULL,
  `real_end_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_task
-- ----------------------------
INSERT INTO `project_task` VALUES ('1', 'Stage1_1', '2018-01-01 00:00:00', '2018-01-31 00:00:00', '0', '1', null, null, '0.00', '1', null, '2', '0', '0', '0', null, '0', '1', 'task', '2018-02-01 15:23:43', null, '2018-01-01 09:12:33');
INSERT INTO `project_task` VALUES ('6', 'Task_1_1', '2018-01-31 00:00:00', '2018-02-03 00:00:00', '1', '1', '按照要求填写', '60', '1.00', '4', '2', '3', '10', '0', '0', null, '0', '1', 'task', '2018-01-31 19:40:59', null, '2018-01-31 19:09:27');
INSERT INTO `project_task` VALUES ('7', 'Task_1_2', '2018-01-01 00:00:00', '2018-01-15 00:00:00', '1', '1', '根据附件要求完成', '60', '1.00', '3', '2', '3', '10', '0', '0', null, '0', '1', 'task', '2018-02-01 15:36:06', '2018-02-01 16:12:56', '2018-02-01 15:33:27');
INSERT INTO `project_task` VALUES ('8', 'Task_1_2_1', '2018-01-08 00:00:00', '2018-01-13 00:00:00', '7', '1', '去买一杯咖啡回来', '60', '1.00', '1', '3', '2', '20', '0', '0', null, '0', '1', 'task', '2018-02-01 17:18:09', '2018-02-01 17:07:57', '2018-02-01 16:23:11');
INSERT INTO `project_task` VALUES ('9', 'Task_1_3', '2018-01-18 00:00:00', '2018-01-25 00:00:00', '1', '1', '这是一个多次确认的任务', '60', '1.00', '1', '2', '3', '20', '0', '0', null, '0', '1', 'task', '2018-02-01 17:48:28', '2018-02-01 17:32:26', '2018-02-01 17:22:23');
INSERT INTO `project_task` VALUES ('10', 'Tasj_1_3_1', '2018-02-01 00:00:00', '2018-02-07 00:00:00', '9', '1', '继续测试多重确认', '60', '1.00', '1', '2', '3', '5', '0', '0', null, '0', '1', 'task', '2018-02-01 17:51:05', '2018-02-01 17:50:39', '2018-02-01 17:47:56');
INSERT INTO `project_task` VALUES ('11', 'task_1_3_2', '2018-02-13 00:00:00', '2018-02-17 00:00:00', '9', '1', '没啥要求，就是想看看多重确认', '60', '1.00', '3', '2', '3', '13', '0', '0', null, '0', '1', 'task', '2018-02-01 18:00:11', '2018-02-01 18:27:06', '2018-02-01 17:57:22');

-- ----------------------------
-- Table structure for task_check
-- ----------------------------
DROP TABLE IF EXISTS `task_check`;
CREATE TABLE `task_check` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) DEFAULT NULL,
  `checker` int(11) DEFAULT NULL,
  `result` varchar(255) DEFAULT '通过',
  `check_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_check
-- ----------------------------
INSERT INTO `task_check` VALUES ('1', '7', '2', '通过', '2018-02-01 16:12:22');
INSERT INTO `task_check` VALUES ('2', '8', '3', '任务完成确认', '2018-02-01 16:38:45');
INSERT INTO `task_check` VALUES ('3', '8', '3', '莫须有嘛', '2018-02-01 16:46:47');
INSERT INTO `task_check` VALUES ('4', '8', '3', '不知道为啥，就是想拒绝', '2018-02-01 16:55:59');
INSERT INTO `task_check` VALUES ('5', '8', '3', '你猜猜', '2018-02-01 16:58:58');
INSERT INTO `task_check` VALUES ('6', '8', '3', '就是想恶心你一下', '2018-02-01 17:01:27');
INSERT INTO `task_check` VALUES ('7', '8', '3', '通过', '2018-02-01 17:07:57');
INSERT INTO `task_check` VALUES ('8', '9', '2', '通过', '2018-02-01 17:26:43');
INSERT INTO `task_check` VALUES ('9', '9', '10', '通过', '2018-02-01 17:32:26');
INSERT INTO `task_check` VALUES ('10', '10', '2', '通过', '2018-02-01 17:49:50');
INSERT INTO `task_check` VALUES ('11', '10', '10', '通过', '2018-02-01 17:50:39');
INSERT INTO `task_check` VALUES ('12', '11', '2', '通过', '2018-02-01 18:02:10');
INSERT INTO `task_check` VALUES ('13', '11', '10', '通过', '2018-02-01 18:06:20');
INSERT INTO `task_check` VALUES ('14', '11', '10', '通过', '2018-02-01 18:27:06');

-- ----------------------------
-- Table structure for task_link
-- ----------------------------
DROP TABLE IF EXISTS `task_link`;
CREATE TABLE `task_link` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_task` varchar(255) DEFAULT NULL,
  `to_task` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL COMMENT '开始开始1，开始结束2，结束开始3，结束结束4',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_link
-- ----------------------------

-- ----------------------------
-- Table structure for tpl_group
-- ----------------------------
DROP TABLE IF EXISTS `tpl_group`;
CREATE TABLE `tpl_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tpl_id` int(11) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `group_role` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tpl_group
-- ----------------------------
INSERT INTO `tpl_group` VALUES ('1', '1', '分管领导', null, '0');
INSERT INTO `tpl_group` VALUES ('2', '1', '项目经理', null, '1');
INSERT INTO `tpl_group` VALUES ('3', '1', '项目助理', null, '2');
INSERT INTO `tpl_group` VALUES ('4', '1', '监理', null, '2');
INSERT INTO `tpl_group` VALUES ('5', '1', '施工单位A', null, '2');

-- ----------------------------
-- Table structure for tpl_project
-- ----------------------------
DROP TABLE IF EXISTS `tpl_project`;
CREATE TABLE `tpl_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tpl_name` varchar(255) DEFAULT NULL,
  `tpl_category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tpl_project
-- ----------------------------
INSERT INTO `tpl_project` VALUES ('1', '项目基础模板', '系统模板');

-- ----------------------------
-- Table structure for tpl_stage
-- ----------------------------
DROP TABLE IF EXISTS `tpl_stage`;
CREATE TABLE `tpl_stage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tpl_id` int(11) DEFAULT NULL,
  `stage_name` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tpl_stage
-- ----------------------------
INSERT INTO `tpl_stage` VALUES ('1', '1', '立项', '0');
INSERT INTO `tpl_stage` VALUES ('2', '1', '实施', '0');
INSERT INTO `tpl_stage` VALUES ('3', '1', '运维', '0');

-- ----------------------------
-- Table structure for vendor
-- ----------------------------
DROP TABLE IF EXISTS `vendor`;
CREATE TABLE `vendor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vendor_name` varchar(255) DEFAULT NULL,
  `vendor_full_name` varchar(255) DEFAULT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `contact_mobile` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vendor
-- ----------------------------
INSERT INTO `vendor` VALUES ('1', '监理1', '', '监理1员工1', '13900000001');
INSERT INTO `vendor` VALUES ('2', '供应商1', '', '供应商1员工1', '13900010001');
INSERT INTO `vendor` VALUES ('3', '供应商2', '', '供应商2员工1', '13900020001');
