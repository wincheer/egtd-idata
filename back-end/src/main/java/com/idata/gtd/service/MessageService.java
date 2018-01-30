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
		if (msg.getType().equals("audit")) {
			msg.setTo(projectService.selectProjectLeader(msg.getRelationId()).getId());
		} // 提醒审核的消息
		msgDao.insertMessage(msg);
		// 同时更新项目的审批状态状态为 "wait"
		Project project = new Project();
		project.setId(msg.getRelationId());
		project.setAuditState("wait");
		projectService.updateProject(project);

		return msg.getId();
	}

	public int updateMessage(Message msg) {

		if (msg.getIsExec()!=null && msg.getIsExec() > 1) {
			if (msg.getType().equals("audit")) {
				Project project = new Project();
				project.setId(msg.getRelationId());
				project.setAuditState(msg.getIsExec() == 2 ? "refuse" : "ready"); // 2拒绝，3同意
				projectService.updateProject(project);
			}
			if (msg.getType().equals("confirm")) {
				ProjectTask task = new ProjectTask();
				task.setId(msg.getRelationId());
				task.setState(msg.getIsExec() == 4 ? 4 : 3); // 4直接确认，3转发其他人继续确认
				taskService.updateProjectTask(task);
			}
			msg.setIsExec(1);
		}

		return msgDao.updateMessage(msg);
	}

	public int deleteMessage(Integer msgId) {

		return msgDao.deleteMessageByPK(msgId);
	}

}
