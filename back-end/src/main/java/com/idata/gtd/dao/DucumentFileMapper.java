package com.idata.gtd.dao;

import com.idata.gtd.entity.DucumentFile;

public interface DucumentFileMapper {

	int deleteDucumentFileByPK(Integer id);

	int insertDucumentFile(DucumentFile record);

	DucumentFile selectDucumentFileByPK(Integer id);

	int updateDucumentFile(DucumentFile record);
	
}