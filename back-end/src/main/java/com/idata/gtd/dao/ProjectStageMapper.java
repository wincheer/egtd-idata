package com.idata.gtd.dao;

import com.idata.gtd.entity.ProjectStage;

public interface ProjectStageMapper {

	int deleteProjectStageByPK(Integer id);

	int insertProjectStage(ProjectStage record);

	ProjectStage selectProjectStageByPK(Integer id);

	int updateProjectStage(ProjectStage record);
}