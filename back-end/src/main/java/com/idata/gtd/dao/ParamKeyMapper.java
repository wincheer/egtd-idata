package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.ParamKey;

public interface ParamKeyMapper {

	int deleteParamKeyByPK(Integer id);

	int insertParamKey(ParamKey record);

	ParamKey selectParamKeyByPK(Integer id);

	int updateParamKey(ParamKey record);

	List<ParamKey> selectParamKeyList();
}