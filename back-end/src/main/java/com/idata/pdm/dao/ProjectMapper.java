package com.idata.pdm.dao;

import java.util.List;
import java.util.Map;

import com.idata.pdm.entity.Project;

public interface ProjectMapper {

	int deleteProject(Integer projectId);

    int insertProject(Project record);

    Project selectProject(Integer projectId);

    int updateProject(Project record);

	List<Project> selectProjectPageList(Map<String,Object> project);

	List<Project> selectProjectListByEmployeeId(int employeeId);
}