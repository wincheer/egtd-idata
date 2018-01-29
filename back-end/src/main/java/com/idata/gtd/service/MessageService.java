package com.idata.gtd.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.gtd.dao.MessageMapper;
import com.idata.gtd.entity.Message;

@Service
@Transactional
public class MessageService {

	private final static Logger logger = LoggerFactory.getLogger(MessageService.class);
	@Autowired
	private MessageMapper msgDao;

	public List<Message> selectMessageList(Message msg) {
		
		logger.info("查询消息");
		return msgDao.selectMessageList(msg);
	}

	public int insertDepartment(Message msg) {

		return msgDao.insertMessage(msg);
	}

	public int updateDepartment(Message msg) {

		return msgDao.updateMessage(msg);
	}

	public int deleteMessage(Integer msgId) {

		return msgDao.deleteMessageByPK(msgId);
	}

}
