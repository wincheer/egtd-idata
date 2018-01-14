package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.TplStage;

public interface TplStageMapper {

	int deleteTplStageByPK(Integer id);

	int insertTplStage(TplStage record);

	TplStage selectTplStageByPK(Integer id);
	
	int updateTplStage(TplStage record);

	List<TplStage> selectTplStageList(Integer tplId);

	void deleteTplStages(Integer tplId);
}