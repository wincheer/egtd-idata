package com.idata.pdm.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.pdm.common.Utils;
import com.idata.pdm.dao.ActionLogMapper;
import com.idata.pdm.entity.ActionLog;

@Service
@Transactional
public class LogService
{
	private final static Logger logger = LoggerFactory.getLogger(LogService.class);
	
	@Autowired
	private ActionLogMapper dao;


	public List<ActionLog> selectLogPageList(Map<String, Object> queryParam)
	{
		int pageNo = (Integer) queryParam.get("pageNo");
		int pageSize = (Integer) queryParam.get("pageSize");
		@SuppressWarnings("unchecked")
		Map<String,Object> params =  (Map<String, Object>) queryParam.get("filter");
		logger.info(params.toString());
		
		int start = (pageNo - 1) * pageSize;
		params.put("start", start);
		params.put("pageSize", pageSize);
				
		return dao.selectLogPageList(Utils.cleanMap(params));
	}


	public int totalLogs(Map<String, Object> queryParam)
	{
		int total = 0;
		
		@SuppressWarnings("unchecked")
		Map<String,Object> params =  (Map<String, Object>) queryParam.get("filter");
		total = dao.totalLogs(Utils.cleanMap(params));
		
		return total;
	}
	
}
