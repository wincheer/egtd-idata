package com.idata.gtd.dao;

import com.idata.gtd.entity.Log;

public interface LogMapper {

	int deleteLogByPK(Integer id);

	int insertLog(Log record);

	Log selectLogByPK(Integer id);

	int updateLog(Log record);

}