package com.idata.gtd.dao;

import com.idata.gtd.entity.ProjectTask;

public interface ProjectTaskMapper {

	int deleteProjectTaskByPK(Integer id);

	int insertProjectTask(ProjectTask record);
    
	ProjectTask selectProjectTaskByPK(Integer id);

	int updateProjectTask(ProjectTask record);
}