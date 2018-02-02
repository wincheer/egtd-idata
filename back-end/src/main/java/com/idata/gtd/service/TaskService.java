package com.idata.gtd.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.idata.gtd.common.DocCat;
import com.idata.gtd.dao.ProjectTaskMapper;
import com.idata.gtd.dao.TaskCheckMapper;
import com.idata.gtd.entity.Document;
import com.idata.gtd.entity.Message;
import com.idata.gtd.entity.ProjectTask;
import com.idata.gtd.entity.TaskCheck;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 关于任务的服务层<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月20日 下午8:15:05 | 创建
 */
@Service
@Transactional
public class TaskService {

	@Autowired
	private ProjectTaskMapper taskDao;
	@Autowired
	private TaskCheckMapper taskCheckDao;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private MessageService msgService;

	public List<ProjectTask> selectProjectTaskList(Integer projectId) {

		// 查询项目阶段，转换为task
		List<ProjectTask> taskList = taskDao.selectProjectTaskList(projectId);
		return taskList;
	}

	public int insertProjectTask(ProjectTask task) {
		task.setCreate_date(new Date()); 
		// 如果有任务接收人，给任务接收人发送一条任务分配消息
		if (task.getActorStaffId() != null) {
			if (task.getAssignStaffId() == task.getActorStaffId()) { // 给自己分配的任务状态直接设置为1进行中，且不发送消息
				task.setState(1);
				taskDao.insertProjectTask(task);
			} else {
				taskDao.insertProjectTask(task);
				
				Message msg = new Message();
				msg.setFrom(task.getAssignStaffId());
				msg.setTo(task.getActorStaffId());
				msg.setToScope("actor");
				msg.setTime(new Date());
				msg.setTitle("任务分配");
				msg.setBody("您被分配了一条新任务，任务名称为【" + task.getText() + "】");
				msg.setRelationId(task.getId());
				msg.setType("normal");
				msgService.insertMessage(msg);
			}
		}
		else
			taskDao.insertProjectTask(task);
		return task.getId();
	}

	public int updateProjectTask(ProjectTask task) {

		if(task.getAssignStaffId()!=null){ //仅仅在更新状态
			ProjectTask originalTask = taskDao.selectProjectTaskByPK(task.getId());
			if (task.getActorStaffId() != originalTask.getActorStaffId()) { // 意味着换了任务执行人
				// 给新的任务接收人发送任务分配通知
				Message msg = new Message();
				msg.setFrom(task.getAssignStaffId());
				msg.setTo(task.getActorStaffId());
				msg.setToScope("actor");
				msg.setTime(new Date());
				msg.setTitle("任务分配");
				msg.setBody("您被分配了一条新任务，任务名称为【" + task.getText() + "】");
				msg.setRelationId(task.getId());
				msg.setType("normal");
				msgService.insertMessage(msg);
			} else {
				if (task.getProgress() == 1) {
					if (task.getState() == 1) { // 进行中
						if (task.getAssignStaffId() != task.getActorStaffId()) { 
							task.setState(2); // 待确认
							// 给任务创建人发一条请求任务完成的确认消息
							Message msg = new Message();
							msg.setFrom(task.getActorStaffId());
							msg.setTo(task.getAssignStaffId());
							msg.setToScope("actor");
							msg.setTime(new Date());
							msg.setTitle("任务完成确认");
							msg.setBody("您分配的一条任务已完成，需要确认。任务名称为【" + task.getText() + "】");
							msg.setRelationId(task.getId());
							msg.setType("confirm");
							msgService.insertMessage(msg);
						} else { // 给自己分配的任务不提醒
							task.setState(3); // 完成
						}
					}
				}
			}
		}
		taskDao.updateProjectTask(task);
		return task.getId();
	}

	public int insertProjectTask(MultipartFile file, ProjectTask model) throws Exception {

		//model.setCreate_date(new Date());
		insertProjectTask(model);

		Document doc = new Document();
		doc.setName(file.getOriginalFilename());
		doc.setFileName(String.valueOf(System.currentTimeMillis()));
		doc.setBelongTo("task");
		doc.setSourceId(model.getId());
		doc.setCategory(DocCat.STANDARD.getValue()); // ?

		documentService.upload(file, doc);

		return 0;
	}

	public int updateProjectTask(MultipartFile file, ProjectTask model) throws Exception {

		updateProjectTask(model);

		Document doc = new Document();
		doc.setName(file.getOriginalFilename());
		doc.setFileName(String.valueOf(System.currentTimeMillis()));
		doc.setBelongTo("task");
		doc.setSourceId(model.getId());
		doc.setCategory(DocCat.STANDARD.getValue());

		documentService.upload(file, doc);

		return 0;
	}

	public int updateProjectTaskWithResult(MultipartFile file, ProjectTask model) throws Exception {

		taskDao.updateProjectTask(model);

		Document doc = new Document();
		doc.setName(file.getOriginalFilename());
		doc.setFileName(String.valueOf(System.currentTimeMillis()));
		doc.setBelongTo("task");
		doc.setSourceId(model.getId());
		doc.setCategory(DocCat.RESULT.getValue());

		documentService.upload(file, doc);

		return 0;
	}

	public List<ProjectTask> selectMyTaskList(Integer empId) {

		return taskDao.selectMyTaskList(empId);
	}

	public List<ProjectTask> selectMyTaskListIn(Integer empId) {
		return taskDao.selectMyTaskListIn(empId);
	}

	public List<ProjectTask> selectMyTaskListOut(Integer empId) {
		return taskDao.selectMyTaskListOut(empId);
	}

	public ProjectTask selectProjectTask(Integer taskId) {

		return taskDao.selectProjectTaskByPK(taskId);
	}

	public List<TaskCheck> selectTaskCheckList(TaskCheck tc) {

		return taskCheckDao.selectTaskCheckList(tc);
	}

	public int insertTaskCheck(TaskCheck tc) {

		return taskCheckDao.insertTaskCheck(tc);
	}

	public int updateTaskCheck(TaskCheck tc) {

		return taskCheckDao.updateTaskCheck(tc);
	}

	public List<ProjectTask> selectTaskList(ProjectTask data) {
		return taskDao.selectTaskList(data);
	}

}
