package com.idata.gtd.dao;

import com.idata.gtd.entity.ProjectContract;

public interface ProjectContractMapper {

	int deleteProjectContractByPK(Integer id);

	int insertProjectContract(ProjectContract record);

	ProjectContract selectProjectContractByPK(Integer id);

	int updateProjectContract(ProjectContract record);

}