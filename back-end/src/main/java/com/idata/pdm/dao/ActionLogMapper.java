package com.idata.pdm.dao;

import java.util.List;
import java.util.Map;

import com.idata.pdm.entity.ActionLog;

public interface ActionLogMapper
{
	int deleteByPrimaryKey(Integer logId);

	int insert(ActionLog record);

	int insertSelective(ActionLog record);

	ActionLog selectByPrimaryKey(Integer logId);

	int updateByPrimaryKeySelective(ActionLog record);

	int updateByPrimaryKey(ActionLog record);

	List<ActionLog> selectLogPageList(Map<String, Object> params);

	int totalLogs(Map<String, Object> params);
}