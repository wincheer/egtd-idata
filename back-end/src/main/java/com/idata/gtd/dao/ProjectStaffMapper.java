package com.idata.gtd.dao;

import com.idata.gtd.entity.ProjectStaff;

public interface ProjectStaffMapper {

	int deleteStaffByPK(Integer id);

	int insertStaff(ProjectStaff record);

	ProjectStaff selectStaffByPK(Integer id);

	int updateStaff(ProjectStaff record);

	ProjectStaff login(ProjectStaff record);
}