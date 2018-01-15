package com.idata.gtd.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.gtd.dao.ProjectMapper;
import com.idata.gtd.entity.Project;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 业主部门的服务层<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 上午11:43:02 | 创建
 */
@Service
@Transactional
public class ProjectService {

	private final static Logger logger = LoggerFactory.getLogger(ProjectService.class);

	@Autowired
	private ProjectMapper projectDao;
	
	public List<Project> selectProjectList() {
		
		logger.info("查询项目");
		return projectDao.selectProjectList();
	}

	public int insertProject(Project row) {

		return projectDao.insertProject(row);
	}

	public int updateProject(Project row) {

		return projectDao.updateProject(row);
	}

	public int deleteProject(Integer projectId) {

		projectDao.deleteProjectByPK(projectId);
		// TODO 同时删除当前项目下的相关内容
		
		return 0;
	}
	
}
