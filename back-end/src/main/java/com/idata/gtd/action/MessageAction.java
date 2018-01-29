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

import com.idata.gtd.entity.Message;
import com.idata.gtd.service.MessageService;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 消息rest接口<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月29日 下午5:38:40 | 创建
 */

@RestController
@CrossOrigin
public class MessageAction {

	private final static Logger logger = LoggerFactory.getLogger(MessageAction.class);
	@Autowired
	private MessageService msgService;

	@RequestMapping(value = "/selectMessageList", method = RequestMethod.POST)
	public List<Message> selectMessageList(@RequestBody Message msg) {

		logger.info("查询消息");
		List<Message> msgList = msgService.selectMessageList(msg);
		return msgList;
	}

	@RequestMapping(value = "/updateMessage", method = RequestMethod.POST)
	public int updateMessage(@RequestBody Message msg) {

		if (msg.getId() == null)
			return msgService.insertDepartment(msg);
		else
			return msgService.updateDepartment(msg);
	};

	@RequestMapping(value = "/deleteMessage", method = RequestMethod.POST)
	public int deleteMessage(@RequestBody Message msg) {

		return msgService.deleteMessage(msg.getId());
	};
}
