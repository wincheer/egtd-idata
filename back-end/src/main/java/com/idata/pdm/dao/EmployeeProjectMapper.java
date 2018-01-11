package com.idata.pdm.dao;

import java.util.List;

import com.idata.pdm.entity.EmployeeProject;

public interface EmployeeProjectMapper {
    
	int deleteEmployeeProject(Integer employeeProjectId);

    int insertEmployeeProject(EmployeeProject record);

    EmployeeProject selectEmployeeProject(Integer employeeProjectId);

    int updateEmployeeProject(EmployeeProject record);

	List<EmployeeProject> selectProjectEmployeeList(int projectId);
	List<EmployeeProject> selectEmployeeProjectList(int employeeId);

}