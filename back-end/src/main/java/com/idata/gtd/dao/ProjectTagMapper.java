package com.idata.gtd.dao;

import com.idata.gtd.entity.ProjectTag;

public interface ProjectTagMapper {

	int deleteProjectTagByPK(Integer id);

	int insertProjectTag(ProjectTag record);

	ProjectTag selectProjectTagByPK(Integer id);

	int updateProjectTag(ProjectTag record);
	
}