/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : gtd_idata

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-29 18:50:16
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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of document
-- ----------------------------
INSERT INTO `document` VALUES ('2', '项目过程监控概念模型.cdm', '1517028431684', 'contract', '1', '1');
INSERT INTO `document` VALUES ('7', '项目过程监控概念模型.cdb', '1517031552844', 'contract', '2', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;

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

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from` varchar(255) DEFAULT NULL,
  `to` varchar(255) DEFAULT NULL,
  `to_scop` varchar(255) DEFAULT '' COMMENT 'actor个人、group项目组',
  `title` varchar(255) DEFAULT NULL,
  `body` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT 'normal 普通消息(任务发布、任务延期（系统发送）)，audit 针对项目审批、confirm针对任务确认',
  `relation_id` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `is_read` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', '雪亮工程一期', '', '', '1', '0', '2017-12-17 00:00:00', '2018-01-01 00:00:00', '2018-05-01 00:00:00', '2', '', '500', 'init', '2');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_contract
-- ----------------------------
INSERT INTO `project_contract` VALUES ('1', '1', '1', '监理合同1');
INSERT INTO `project_contract` VALUES ('2', '1', '2', '与供应商1的合同');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_task
-- ----------------------------
INSERT INTO `project_task` VALUES ('1', 'Stage1_1', '2018-01-01 00:00:00', '2018-01-31 00:00:00', '0', '1', null, null, '0.00', '0', null, '2', '0', '0', '0', null, '0', '1', 'task', null, null);
INSERT INTO `project_task` VALUES ('2', '任务p1s1_1', '2018-01-09 00:00:00', '2018-01-12 00:00:00', '1', '1', '这个总的来说比较着急', '80', '0.00', '0', '2', '3', '10', '0', '0', '', '0', '1', 'task', null, null);
INSERT INTO `project_task` VALUES ('3', '任务p1s1_2', '2018-01-01 00:00:00', '2018-01-08 00:00:00', '1', '1', '测试附件上传', '60', '0.80', '0', '2', '2', '50', '0', '0', '', '0', '1', 'task', null, null);

-- ----------------------------
-- Table structure for stage_check
-- ----------------------------
DROP TABLE IF EXISTS `stage_check`;
CREATE TABLE `stage_check` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stage_id` int(11) DEFAULT NULL,
  `check_staff_id` int(11) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stage_check
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

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
