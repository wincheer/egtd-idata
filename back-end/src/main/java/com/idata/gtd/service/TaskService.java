package com.idata.gtd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.gtd.dao.ProjectTaskMapper;
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
	
	public List<ProjectTask> selectProjectTaskList(Integer projectId) {
		
		//查询项目阶段，转换为task
		//查询常规task
		return null;
	}

}