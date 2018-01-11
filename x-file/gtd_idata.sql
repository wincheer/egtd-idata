/*
Navicat MySQL Data Transfer

Source Server         : local_mysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : gtd_idata

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-11 17:59:06
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
-- Table structure for department_employe
-- ----------------------------
DROP TABLE IF EXISTS `department_employe`;
CREATE TABLE `department_employe` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dep_id` int(11) DEFAULT NULL,
  `emp_name` varchar(255) DEFAULT NULL,
  `emp_gender` varchar(255) DEFAULT NULL,
  `emp_mobile` varchar(255) DEFAULT NULL,
  `emp_email` varchar(255) DEFAULT NULL,
  `emp_title` varchar(255) DEFAULT NULL,
  `emp_grade` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department_employe
-- ----------------------------

-- ----------------------------
-- Table structure for document
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `document_name` varchar(255) DEFAULT NULL,
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
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actor` varchar(255) DEFAULT NULL,
  `op_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `action` varchar(255) DEFAULT NULL,
  `target` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------

-- ----------------------------
-- Table structure for param_key
-- ----------------------------
DROP TABLE IF EXISTS `param_key`;
CREATE TABLE `param_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of param_key
-- ----------------------------

-- ----------------------------
-- Table structure for param_value
-- ----------------------------
DROP TABLE IF EXISTS `param_value`;
CREATE TABLE `param_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `param_id` int(11) DEFAULT NULL,
  `param_value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of param_value
-- ----------------------------

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
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `start_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `dep_id` int(11) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `amount` int(255) DEFAULT NULL,
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
  `staff_name` varchar(255) DEFAULT NULL,
  `staff_email` varchar(255) DEFAULT NULL,
  `staff_mobile` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of project_staff
-- ----------------------------
INSERT INTO `project_staff` VALUES ('1', '0', '0', '0', '超级用户', 'root@system', '000', '000');

-- ----------------------------
-- Table structure for project_stage
-- ----------------------------
DROP TABLE IF EXISTS `project_stage`;
CREATE TABLE `project_stage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stage_name` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `has_supervisor` int(11) DEFAULT NULL,
  `is_approval` int(11) DEFAULT NULL,
  `progress` int(11) DEFAULT NULL,
  `actor_staff_id` int(11) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `note` varchar(255) DEFAULT NULL,
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
  `stage_id` int(11) DEFAULT NULL,
  `task_name` varchar(255) DEFAULT NULL,
  `task_desc` varchar(255) DEFAULT NULL,
  `from_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `end_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `priority` int(11) DEFAULT NULL,
  `progress` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL COMMENT '未开始0，进行中1，已完成待检查2，完成3',
  `assign_staff_id` int(11) DEFAULT NULL,
  `actor_staff_id` int(255) DEFAULT NULL,
  `plan_workload` int(11) DEFAULT NULL,
  `real_workload` int(11) DEFAULT NULL,
  `is_delay` int(11) DEFAULT NULL,
  `delay_reason` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
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

-- ----------------------------
-- Table structure for vendor_employee
-- ----------------------------
DROP TABLE IF EXISTS `vendor_employee`;
CREATE TABLE `vendor_employee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vendor_id` int(11) DEFAULT NULL,
  `emp_name` varchar(255) DEFAULT NULL,
  `emp_gender` varchar(255) DEFAULT NULL,
  `emp_mobile` varchar(255) DEFAULT NULL,
  `emp_email` varchar(255) DEFAULT NULL,
  `emp_title` varchar(255) DEFAULT NULL,
  `emp_grade` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of vendor_employee
-- ----------------------------
