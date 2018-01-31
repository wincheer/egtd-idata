package com.idata.gtd.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.gtd.dao.MessageMapper;
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
	private ProjectService projectService;
	@Autowired
	private TaskService taskService;

	public List<Message> selectMessageList(Message msg) {

		logger.info("查询消息");
		return msgDao.selectMessageList(msg);
	}

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
				project.setAuditState(msg.getIsExec() == 2 ? "refuse" : "ready"); // 2拒绝，3同意
				projectService.updateProject(project);
			}
			if (msg.getType().equals("confirm")) {
				ProjectTask task = new ProjectTask();
				task.setId(msg.getRelationId());
				task.setState(msg.getIsExec());
				
				TaskCheck tc = new TaskCheck();
				tc.setTaskId(msg.getRelationId());
				tc.setChecker(msg.getTo()); 
				tc.setResult(msg.getTitle()); //临时借用title
				msg.setTitle(null); //防止误更新
				tc.setCheckDate(new Date());
				
				if (msg.getIsExec() == 2) { // 转发继续确认
					//给下一个检查人发消息
				}
				if (msg.getIsExec() == 3) { // 同意
					task.setReal_end_date(new Date());
					//给任务接收人发消息
				}
				if (msg.getIsExec() == 4) { // 拒绝了
					//给任务接收人发消息
				}
				taskService.updateProjectTask(task);
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

}
