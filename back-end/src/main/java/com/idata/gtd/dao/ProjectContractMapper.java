package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.ProjectContract;

public interface ProjectContractMapper {

	int deleteProjectContractByPK(Integer id);

	int insertProjectContract(ProjectContract record);

	ProjectContract selectProjectContractByPK(Integer id);

	int updateProjectContract(ProjectContract record);

	List<ProjectContract> selectProjectContractList(Integer projectId); 

}