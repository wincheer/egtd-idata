/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50626
Source Host           : localhost:3306
Source Database       : gtd_idata

Target Server Type    : MYSQL
Target Server Version : 50626
File Encoding         : 65001

Date: 2018-01-24 14:38:24
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('1', '0', 'o0', '超级用户', '0', '000', 'root@system', 'root', 'roor', '000');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('1', '0', '2018-01-24 14:25:49', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('2', '0', '2018-01-24 14:26:59', '登录', '系统', '失败');
INSERT INTO `log` VALUES ('3', '1', '2018-01-24 14:28:21', '登录', '系统', '成功');

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of param_key
-- ----------------------------
INSERT INTO `param_key` VALUES ('1', 'category', '项目类型', 'String');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of param_value
-- ----------------------------
INSERT INTO `param_value` VALUES ('1', '1', '1', '软件实施项目');
INSERT INTO `param_value` VALUES ('2', '1', '2', '土木工程建筑');

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_contract
-- ----------------------------

-- ----------------------------
-- Table structure for project_group
-- ----------------------------
DROP TABLE IF EXISTS `project_group`;
CREATE TABLE `project_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `group_desc` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_group
-- ----------------------------

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
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tpl_group
-- ----------------------------

-- ----------------------------
-- Table structure for tpl_project
-- ----------------------------
DROP TABLE IF EXISTS `tpl_project`;
CREATE TABLE `tpl_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tpl_name` varchar(255) DEFAULT NULL,
  `tpl_category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tpl_project
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tpl_stage
-- ----------------------------

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vendor
-- ----------------------------
