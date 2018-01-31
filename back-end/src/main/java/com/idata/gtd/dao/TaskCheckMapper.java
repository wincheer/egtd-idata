package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.TaskCheck;

public interface TaskCheckMapper {

	int deleteTaskCheckByPK(Integer id);

    int insertTaskCheck(TaskCheck record);

    TaskCheck selectTaskCheckByPK(Integer id);

    int updateTaskCheck(TaskCheck record);
    
    List<TaskCheck> selectTaskCheckList(TaskCheck tc);

}