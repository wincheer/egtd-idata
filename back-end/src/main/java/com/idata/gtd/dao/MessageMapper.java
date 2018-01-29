package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.Message;

public interface MessageMapper {
    
	int deleteMessageByPK(Integer id);

    int insertMessage(Message record);

    Message selectMessageByPK(Integer id);

    int updateMessage(Message record);

	List<Message> selectMessageList(Message msg);

}