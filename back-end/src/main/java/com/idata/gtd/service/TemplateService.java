package com.idata.gtd.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.gtd.dao.TplGroupMapper;
import com.idata.gtd.dao.TplProjectMapper;
import com.idata.gtd.dao.TplStageMapper;
import com.idata.gtd.entity.TplGroup;
import com.idata.gtd.entity.TplProject;
import com.idata.gtd.entity.TplStage;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 业主部门的服务层<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 上午11:43:02 | 创建
 */
@Service
@Transactional
public class TemplateService {

	private final static Logger logger = LoggerFactory.getLogger(TemplateService.class);

	@Autowired
	private TplProjectMapper tplProjectDao;
	@Autowired
	private TplStageMapper tplStageDao;
	@Autowired
	private TplGroupMapper tplGroupDao;
	
	public List<TplProject> selectTplProjectList() {
		
		logger.info("查询业主的组织结构");
		return tplProjectDao.selectTplProjectList();
	}

	public int insertTplProject(TplProject row) {

		return tplProjectDao.insertTplProject(row);
	}

	public int updateTplProject(TplProject row) {

		return tplProjectDao.updateTplProject(row);
	}

	public int deleteTplProject(Integer tplId) {

		tplProjectDao.deleteTplProjectByPK(tplId);
		//同时删除当前模板下的阶段/任务
		tplStageDao.deleteTplStages(tplId);
		//同时删除当前模板下的项目组
		tplGroupDao.deleteTplGroups(tplId);
		
		return 0;
	}
	
	public List<TplStage> selectTplStageList(Integer tplId){
		
		return tplStageDao.selectTplStageList(tplId);
	}

	public int insertTplStage(TplStage row) {

		return tplStageDao.insertTplStage(row);
	}

	public int updateTplStage(TplStage row) {

		return tplStageDao.updateTplStage(row);
	}

	public int deleteTplStage(Integer id) {

		return tplStageDao.deleteTplStageByPK(id);
	}
	
	public List<TplGroup> selectTplGroupList(Integer tplId){
		
		return tplGroupDao.selectTplGroupList(tplId);
	}

	public int insertTplGroup(TplGroup row) {

		return tplGroupDao.insertTplGroup(row);
	}

	public int updateTplGroup(TplGroup row) {

		return tplGroupDao.updateTplGroup(row);
	}

	public int deleteTplGroup(Integer id) {

		return tplGroupDao.deleteTplGroupByPK(id);
	}

}
