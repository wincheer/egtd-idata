package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.ProjectTask;

public interface ProjectTaskMapper {

	int deleteProjectTaskByPK(Integer id);

	int insertProjectTask(ProjectTask record);
    
	ProjectTask selectProjectTaskByPK(Integer id);

	int updateProjectTask(ProjectTask record);

	List<ProjectTask> selectProjectTaskList(Integer projectId);
}