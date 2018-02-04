package com.idata.gtd.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idata.gtd.entity.Employee;
import com.idata.gtd.entity.Project;
import com.idata.gtd.entity.ProjectTask;
import com.idata.gtd.entity.TaskCheck;
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

	@RequestMapping(value = "/selectTaskList", method = RequestMethod.POST)
	public List<ProjectTask> selectTaskList(@RequestBody ProjectTask data) {

		return taskService.selectTaskList(data);
	}
	
	@RequestMapping(value = "/selectProjectTask", method = RequestMethod.POST)
	public ProjectTask selectProjectTask(@RequestBody ProjectTask data) {

		ProjectTask task = taskService.selectProjectTask(data.getId());
		return task;
	}
	
	@RequestMapping(value = "/selectProjectTaskList", method = RequestMethod.POST)
	public List<ProjectTask> selectProjectTaskList(@RequestBody Project data) {

		logger.info("查询项目的任务列表，项目：" + data.getId());

		List<ProjectTask> taskList = taskService.selectProjectTaskList(data.getId());
		return taskList;
	}

	@RequestMapping(value = "/updateProjectTask", method = RequestMethod.POST)
	public int updateProjectTask(@RequestBody ProjectTask task) {

		if (task.getId() == null)
			return taskService.insertProjectTask(task);
		else
			return taskService.updateProjectTask(task);
	};

	// 上传的是任务要求规范的附件
	@RequestMapping(value = "/updateProjectTaskWithFile", method = RequestMethod.POST)
	public int updateProjectTaskWithFile(@RequestParam Map<String, Object> data, @RequestParam MultipartFile file)
			throws Exception {
	
		ProjectTask task = new ProjectTask();

		task.setId(data.get("id").equals("null")?null:Integer.parseInt(data.get("parent").toString()));
		
		if(data.get("start_date").toString().length()>20)
			task.setStart_date(new Date(data.get("start_date").toString()));
		else
			task.setStart_date(new Date(Long.parseLong(data.get("start_date").toString())));
		
		if(data.get("end_date").toString().length()>20)
			task.setEnd_date(new Date(data.get("end_date").toString()));
		else
			task.setEnd_date(new Date(Long.parseLong(data.get("start_date").toString())));
		
		
		task.setText(data.get("text").equals("null")?null:(String)data.get("text"));
		task.setParent(data.get("parent").equals("null")?null:Integer.parseInt(data.get("parent").toString()));
		task.setTaskDesc(data.get("taskDesc").equals("null")?null:(String)data.get("taskDesc"));
		task.setPriority(data.get("priority").equals("null")?null:Integer.parseInt(data.get("priority").toString()));
		task.setProgress(data.get("progress").equals("null")?null:Float.parseFloat(data.get("progress").toString()));
		task.setState(data.get("state").equals("null")?null:Integer.parseInt(data.get("state").toString()));
		task.setAssignStaffId(data.get("assignStaffId").equals("")?null:Integer.parseInt(data.get("assignStaffId").toString()));
		task.setActorStaffId(data.get("actorStaffId").equals("")?null:Integer.parseInt(data.get("actorStaffId").toString()));
		task.setPlanWorkload(data.get("planWorkload").equals("null")?null:Integer.parseInt(data.get("planWorkload").toString()));
		task.setRealWorkload(data.get("realWorkload").equals("null")?null:Integer.parseInt(data.get("realWorkload").toString()));
		task.setIsDelay(data.get("isDelay").equals("null")?null:Integer.parseInt(data.get("isDelay").toString()));
		task.setDelayReason(data.get("delayReason").equals("null")?null:(String)data.get("delayReason"));
		task.setReadonly(data.get("readonly").equals("null")?null:Integer.parseInt(data.get("readonly").toString()));
		task.setEditable(data.get("editable").equals("null")?null:Integer.parseInt(data.get("editable").toString()));
		task.setType(data.get("type").equals("null")?null:(String)data.get("type"));
		task.setProjectId(data.get("projectId").equals("null")?null:Integer.parseInt(data.get("projectId").toString()));

		if (task.getId() == null) {
			return taskService.insertProjectTask(file, task);
		} else
			return taskService.updateProjectTask(file, task);

	};

	// 上传的是任务执行结果的附件，比如照片啥的
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/updateProjectTaskWithFileResult", method = RequestMethod.POST)
	public int updateProjectTaskWithFileResult(@RequestParam Map<String, Object> data, @RequestParam MultipartFile file)
			throws Exception {

		Gson gson = new GsonBuilder().create();

		data.put("start_date", gson.toJson(new Date(data.get("start_date").toString())));
		data.put("end_date", gson.toJson(new Date(data.get("end_date").toString())));
		data.put("real_start_date", null);
		data.put("real_end_date", null);
		data.put("create_date", null);
		if(data.get("delayReason").toString().trim().equals(""))
			data.put("delayReason", null);

		logger.info("任务结果上传的附件 = " + file.getOriginalFilename());
		ProjectTask model = gson.fromJson(data.toString(), ProjectTask.class);

		return taskService.updateProjectTaskWithResult(file, model);

	};

	@RequestMapping(value = "/selectMyTaskList", method = RequestMethod.POST)
	public List<ProjectTask> selectProjectTaskList(@RequestBody Employee data) {

		logger.info("查询用户的任务列表，用户：" + data.getId());

		List<ProjectTask> taskList = taskService.selectMyTaskList(data.getId());
		return taskList;
	}

	@RequestMapping(value = "/selectMyTaskListIn", method = RequestMethod.POST)
	public List<ProjectTask> selectMyTaskListIn(@RequestBody Employee data) {

		logger.info("查询用户的任务列表，用户：" + data.getId());

		List<ProjectTask> taskList = taskService.selectMyTaskListIn(data.getId());
		return taskList;
	}

	@RequestMapping(value = "/selectMyTaskListOut", method = RequestMethod.POST)
	public List<ProjectTask> selectMyTaskListOut(@RequestBody Employee data) {

		logger.info("查询用户的任务列表，用户：" + data.getId());

		List<ProjectTask> taskList = taskService.selectMyTaskListOut(data.getId());
		return taskList;
	}

	//////
	@RequestMapping(value = "/selectTaskCheckList", method = RequestMethod.POST)
	public List<TaskCheck> selectTaskCheckList(@RequestBody TaskCheck data) {

		List<TaskCheck> taskCheckList = taskService.selectTaskCheckList(data);
		return taskCheckList;
	}
	
	@RequestMapping(value = "/updateTaskCheck", method = RequestMethod.POST)
	public int updateTaskCheck(@RequestBody TaskCheck tc) {

		if (tc.getId() == null)
			return taskService.insertTaskCheck(tc);
		else
			return taskService.updateTaskCheck(tc);
	};
	
	//////用在统计图上
	@RequestMapping(value = "/selectTaskStateCount4Chart", method = RequestMethod.POST)
	public List<Map<String,Object>> selectTaskStateCount4Chart(@RequestBody ProjectTask task) {

		List<Map<String,Object>> result = taskService.selectTaskStateCount4Chart(task);
		return result;
	}
}
