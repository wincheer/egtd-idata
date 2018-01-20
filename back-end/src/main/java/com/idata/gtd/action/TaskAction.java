package com.idata.gtd.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata.gtd.entity.Project;
import com.idata.gtd.entity.ProjectTask;
import com.idata.gtd.service.TaskService;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 任务的控制器，向前端提供restful接口 <br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月20日 下午8:12:54 | 创建
 */
@RestController
@CrossOrigin
public class TaskAction {
	
	private final static Logger logger = LoggerFactory.getLogger(TaskAction.class);
	@Autowired
	private TaskService taskService;
	
	@RequestMapping(value = "/selectProjectTaskList", method = RequestMethod.POST)
	public List<ProjectTask> selectProjectTaskList(@RequestBody Project data) {

		logger.info("查询项目的任务列表，项目：" + data.getId()); 
		
		List<ProjectTask> taskList = taskService.selectProjectTaskList(data.getId());
		return taskList;
	}

}
