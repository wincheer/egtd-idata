/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : gtd_idata

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2018-01-25 14:15:55
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
  `belong_to` varchar(255) DEFAULT NULL,
  `source_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of document
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('1', '0', '2018-01-24 14:25:49', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('2', '0', '2018-01-24 14:26:59', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('3', '1', '2018-01-24 14:28:21', '登录', '系统', '成功');
INSERT INTO `log` VALUES ('4', '1', '2018-01-24 16:17:42', '登录', '系统', '成功');

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
INSERT INTO `param_value` VALUES ('7', '2', 'R10', '普通项目成员');

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
  `is_forzen` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `actor_staff_id` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `is_approval` int(11) DEFAULT NULL,
  `dep_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES ('1', '雪亮工程一期', '', '', '0', '0', null, '2018-01-01 00:00:00', '2018-05-01 00:00:00', '2', '', '500', null, null);

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
INSERT INTO `project_contract` VALUES ('1', '1', '1', '监理合同');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_group
-- ----------------------------
INSERT INTO `project_group` VALUES ('1', '1', '分管领导', null, '', '0');

-- ----------------------------
-- Table structure for project_staff
-- ----------------------------
DROP TABLE IF EXISTS `project_staff`;
CREATE TABLE `project_staff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `is_vendor` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `emp_id` int(11) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `staff_name` varchar(255) DEFAULT NULL,
  `staff_email` varchar(255) DEFAULT NULL,
  `staff_mobile` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_staff
-- ----------------------------

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
  `has_supervisor` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_task
-- ----------------------------

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
