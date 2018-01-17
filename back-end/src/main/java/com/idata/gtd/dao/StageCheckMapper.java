package com.idata.gtd.dao;

import com.idata.gtd.entity.StageCheck;

public interface StageCheckMapper {

	int deleteStageCheckByPK(Integer id);

	int insertStageCheck(StageCheck record);

	StageCheck selectStageCheckByPK(Integer id);

	int updateStageCheck(StageCheck record);
}