package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.TplGroup;

public interface TplGroupMapper {

	int deleteTplGroupByPK(Integer id);

	int insertTplGroup(TplGroup record);

	TplGroup selectTplGroupByPK(Integer id);

	int updateTplGroup(TplGroup record);

	List<TplGroup> selectTplGroupList(Integer tplId);

	void deleteTplGroups(Integer tplId); 
}