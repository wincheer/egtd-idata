package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.TplProject;

public interface TplProjectMapper {

	int deleteTplProjectByPK(Integer id);

	int insertTplProject(TplProject record);

	TplProject selectTplProjectByPK(Integer id);

	int updateTplProject(TplProject record);

	List<TplProject> selectTplProjectList();
}