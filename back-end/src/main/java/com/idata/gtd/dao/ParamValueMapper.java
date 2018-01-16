package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.ParamKey;
import com.idata.gtd.entity.ParamValue;

public interface ParamValueMapper {

	int deleteParamValueByPK(Integer id);

	int insertParamValue(ParamValue record);

	ParamValue selectParamValueByPK(Integer id);

	int updateParamValue(ParamValue record);

	List<ParamValue> selectParamValueList(ParamKey paramKey);

	void deleteParamValues(Integer paramKeyId); 
}