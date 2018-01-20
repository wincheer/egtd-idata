package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.Project;

public interface ProjectMapper {

	int deleteProjectByPK(Integer id);

	int insertProject(Project record);

	Project selectProjectByPK(Integer id);

	int updateProject(Project record);

	List<Project> selectProjectList();

	List<Project> selectMyProjectList(String staffCode); 
	
}