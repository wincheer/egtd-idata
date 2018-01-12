package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.ParamValue;

public interface ParamValueMapper {

	int deleteParamValueByPK(Integer id);

	int insertParamValue(ParamValue record);

	ParamValue selectParamValueByPK(Integer id);

	int updateParamValue(ParamValue record);

	List<ParamValue> selectParamValueList(Integer paramKeyId);

	void deleteParamValues(Integer paramKeyId); 
}