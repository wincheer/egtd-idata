package com.idata.pdm.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata.pdm.entity.ActionLog;
import com.idata.pdm.service.LogService;

@RestController
@CrossOrigin
public class LogAction
{
	@Autowired
	private LogService logService;

	@RequestMapping(value = "/logPageList", method = RequestMethod.POST)
	public Map<String, Object> selectLogPageList(@RequestBody Map<String, Object> queryParam)
	{
		int total = logService.totalLogs(queryParam);
		List<ActionLog> logList = logService.selectLogPageList(queryParam);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", logList);

		
		return map;
	}

}