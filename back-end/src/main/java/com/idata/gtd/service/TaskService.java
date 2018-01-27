package com.idata.gtd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.idata.gtd.common.DocCat;
import com.idata.gtd.dao.ProjectTaskMapper;
import com.idata.gtd.entity.Document;
import com.idata.gtd.entity.ProjectTask;

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
	private DocumentService documentService;

	public List<ProjectTask> selectProjectTaskList(Integer projectId) {

		// 查询项目阶段，转换为task
		List<ProjectTask> taskList = taskDao.selectProjectTaskList(projectId);
		return taskList;
	}

	public int insertProjectTask(ProjectTask task) {
		
		taskDao.insertProjectTask(task);
		return task.getId();
	}

	public int updateProjectTask(ProjectTask task) {
		
		taskDao.updateProjectTask(task);
		return task.getId();
	}

	public int insertProjectTask(MultipartFile file, ProjectTask model) throws Exception {

		taskDao.insertProjectTask(model);

		Document doc = new Document();
		doc.setName(file.getOriginalFilename());
		doc.setFileName(String.valueOf(System.currentTimeMillis()));
		doc.setBelongTo("task");
		doc.setSourceId(model.getId());
		doc.setCategory(DocCat.STANDARD.getValue()); //?

		documentService.upload(file, doc);
		
		return 0;
	}

	public int updateProjectTask(MultipartFile file, ProjectTask model) throws Exception {
		
		taskDao.updateProjectTask(model);

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

	

}
