package com.idata.gtd.action;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
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
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
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

	@RequestMapping(value = "/selectTaskPageList", method = RequestMethod.POST)
	public Map<String, Object> selectTaskPageList(@RequestBody Map<String, Object> queryParam) {

		int total = taskService.totalTasks(queryParam);
		List<ProjectTask> rows = taskService.selectTaskPageList(queryParam);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", total);
		result.put("rows", rows);

		return result;
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

		logger.info("任务上传的附件 = " + file.getOriginalFilename());
		
		GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();
		
		ProjectTask model = gson.fromJson(data.toString(), ProjectTask.class);

		if (model.getId() == null) {
			model.setId(null);
			return taskService.insertProjectTask(file, model);
		} else
			return taskService.updateProjectTask(file, model);

	};

	// 上传的是任务执行结果的附件，比如照片啥的
	@RequestMapping(value = "/updateProjectTaskWithFileResult", method = RequestMethod.POST)
	public int updateProjectTaskWithFileResult(@RequestParam Map<String, Object> data, @RequestParam MultipartFile file)
			throws Exception {

		logger.info("任务结果上传的附件 = " + file.getOriginalFilename());
		
		GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();
		
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
