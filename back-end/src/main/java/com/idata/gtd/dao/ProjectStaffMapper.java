package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.ProjectStaff;

public interface ProjectStaffMapper {

	int deleteStaffByPK(Integer id);
	
	int deleteStaffsByGroupId(Integer groupId);

	int insertStaff(ProjectStaff record);

	ProjectStaff selectStaffByPK(Integer id);

	int updateStaff(ProjectStaff record);

	ProjectStaff login(ProjectStaff record);

	List<ProjectStaff> selectGroupStaffList(Integer groupId);
	
	List<ProjectStaff> selectProjectStaffList(Integer projectId);
}