package com.idata.gtd.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.gtd.common.Utils;
import com.idata.gtd.dao.MessageMapper;
import com.idata.gtd.dao.ProjectTaskMapper;
import com.idata.gtd.dao.TaskCheckMapper;
import com.idata.gtd.entity.Message;
import com.idata.gtd.entity.Project;
import com.idata.gtd.entity.ProjectTask;
import com.idata.gtd.entity.TaskCheck;

@Service
@Transactional
public class MessageService {

	private final static Logger logger = LoggerFactory.getLogger(MessageService.class);
	@Autowired
	private MessageMapper msgDao;
	@Autowired
	private TaskCheckMapper tcDao;
	@Autowired
	private ProjectTaskMapper taskDao;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private TaskService taskService;

//	public List<Message> selectMessageList(Message msg) {
//
//		logger.info("查询消息");
//		return msgDao.selectMessageList(msg);
//	}

	public int insertMessage(Message msg) {

		msg.setTime(new Date());
		if (msg.getType().equals("audit")) { // 提醒审核的消息
			msg.setTo(projectService.selectProjectLeader(msg.getRelationId()).getId());
			// 同时更新项目的审批状态状态为 "wait"
			Project project = new Project();
			project.setId(msg.getRelationId());
			project.setAuditState("wait");
			projectService.updateProject(project);
		}

		if (msg.getType().equals("confirm")) { // 提醒任务确认的消息
			// 同时更新task状态为完成待检查
			ProjectTask task = new ProjectTask();
			task.setId(msg.getRelationId());
			task.setState(2); // 未开始0，进行中1，已完成待检查2，确认完成3
			taskService.updateProjectTask(task);
		}

		msgDao.insertMessage(msg);

		return msg.getId();
	}

	public int updateMessage(Message msg) {

		if (msg.getIsExec() != null && msg.getIsExec() > 1) {
			if (msg.getType().equals("audit")) {
				Project project = new Project();
				project.setId(msg.getRelationId());
				project.setAuditState(msg.getIsExec() == 2 ? "init" : "ready"); // 2拒绝，3同意
				projectService.updateProject(project);
				if(msg.getIsExec() == 2){
					//发送一条消息，通知项目审核未通过
					Message _msg = new Message();
					_msg.setFrom(msg.getTo());
					_msg.setTo(msg.getFrom());
					_msg.setTitle("项目计划审核被拒绝");
					String projectName = msg.getBody().substring(msg.getBody().indexOf("【")+1,msg.getBody().indexOf("】"));
					_msg.setBody("您提交的项目"+projectName+"的计划审核申请被拒绝，理由是【"+msg.getNote()+"】");
					_msg.setToScope("actor"); 
					_msg.setRelationId(msg.getRelationId());
					_msg.setType("normal");
					_msg.setTime(new Date());
					msgDao.insertMessage(_msg);
				}
			}
			if (msg.getType().equals("confirm")) {
				ProjectTask task = new ProjectTask();
				task.setId(msg.getRelationId());
				
				
				TaskCheck tc = new TaskCheck();
				tc.setTaskId(msg.getRelationId());
				tc.setChecker(msg.getTo()); 
				tc.setCheckDate(new Date());
				
				Message _msg = new Message();
				_msg.setFrom(msg.getTo());
				_msg.setToScope("actor");
				_msg.setTime(new Date());
				_msg.setRelationId(msg.getRelationId());
				
				ProjectTask originalTask = taskDao.selectProjectTaskByPK(msg.getRelationId());
				if (msg.getIsExec() == 2) { // 转发继续确认
					//给下一个检查人发消息
					_msg.setTo(Integer.parseInt(msg.getNote()));
					_msg.setTitle(msg.getTitle());
					String taskName = msg.getBody().substring(msg.getBody().indexOf("【")+1,msg.getBody().indexOf("】"));
					_msg.setBody("任务【"+taskName+"】已完成，需要您再次确认。");
					_msg.setType("confirm");
					
					task.setState(2);
				}
				if (msg.getIsExec() == 3) { // 同意
					task.setReal_end_date(new Date());
					//给任务接收人发消息
					_msg.setTo(originalTask.getActorStaffId()); 
					_msg.setTitle("任务完成");
					String taskName = msg.getBody().substring(msg.getBody().indexOf("【")+1,msg.getBody().indexOf("】"));
					_msg.setBody("您承接的任务【"+taskName+"】确认完成");
					_msg.setType("normal");
					
					task.setState(3);
				}
				if (msg.getIsExec() == 4) { // 拒绝了
					//给任务接收人发消息
					_msg.setTo(originalTask.getActorStaffId());
					_msg.setTitle("任务检查未通过");
					_msg.setBody("您标记完成的任务检查没有通过，原因是【"+msg.getNote()+"】");
					_msg.setType("normal");
					
					task.setState(1);
					task.setProgress(0.8F); 
					
					tc.setResult(msg.getNote()); 
				}
				taskService.updateProjectTask(task);
				tcDao.insertTaskCheck(tc);
				msgDao.insertMessage(_msg);
			}
			
			msg.setIsExec(1);
		}
		if (msg.getType().equals("normal")) {
			ProjectTask task = new ProjectTask();
			task.setId(msg.getRelationId());
			task.setState(1); // 开始
			task.setReal_start_date(new Date());
			taskService.updateProjectTask(task);
			msg.setIsExec(1);
		}

		return msgDao.updateMessage(msg);
	}

	public int deleteMessage(Integer msgId) {

		return msgDao.deleteMessageByPK(msgId);
	}

	public int totalMessages(Map<String, Object> queryParam) {
		
		@SuppressWarnings("unchecked")
		Map<String, Object> params = (Map<String, Object>) queryParam.get("filter");
		return msgDao.totalMessages(params);
	}

	public List<Message> selectMessagePageList(Map<String, Object> queryParam) {
		
		int pageNo = (Integer) queryParam.get("pageNo");
		int pageSize = (Integer) queryParam.get("pageSize");
		int start = (pageNo - 1) * pageSize;

		@SuppressWarnings("unchecked")
		Map<String, Object> params = (Map<String, Object>) queryParam.get("filter");
		logger.info(params.toString());
		params.put("start", start);
		params.put("pageSize", pageSize);

		return msgDao.selectMessagePageList(Utils.cleanMap(params));
		
		//return null;
	}

}
