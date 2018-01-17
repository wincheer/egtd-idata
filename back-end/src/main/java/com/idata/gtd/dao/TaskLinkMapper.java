package com.idata.gtd.dao;

import com.idata.gtd.entity.TaskLink;

public interface TaskLinkMapper {

	int deleteTaskLinkByPK(Integer id);

	int insertTaskLink(TaskLink record);

	TaskLink selectTaskLinkByPK(Integer id);

	int updateTaskLink(TaskLink record);
}