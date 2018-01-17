package com.idata.gtd.dao;

import com.idata.gtd.entity.ProjectGroup;

public interface ProjectGroupMapper {

	int deleteProjectGroupByPK(Integer id);

	int insertProjectGroup(ProjectGroup record);

	ProjectGroup selectProjectGroupByPK(Integer id);

	int updateProjectGroup(ProjectGroup record);
}