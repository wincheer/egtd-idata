package com.idata.gtd.dao;

import java.util.List;
import java.util.Map;

import com.idata.gtd.entity.Message;

public interface MessageMapper {
    
	int deleteMessageByPK(Integer id);

    int insertMessage(Message record);

    Message selectMessageByPK(Integer id);

    int updateMessage(Message record);

//	List<Message> selectMessageList(Message msg);

	int totalMessages(Map<String, Object> params);

	List<Message> selectMessagePageList(Map<String, Object> queryParam); 

}