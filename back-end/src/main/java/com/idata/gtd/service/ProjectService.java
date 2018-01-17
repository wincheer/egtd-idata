package com.idata.gtd.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.idata.gtd.dao.ProjectContractMapper;
import com.idata.gtd.dao.ProjectMapper;
import com.idata.gtd.entity.Document;
import com.idata.gtd.entity.Project;
import com.idata.gtd.entity.ProjectContract;

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
	@Autowired
	private ProjectContractMapper projectContractDao;
	@Autowired
	private DocumentService documentService;
	
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

	public int insertProjectContract(MultipartFile file, ProjectContract data) throws Exception {
		
		Document doc = new Document();
		doc.setDocumentName(file.getName());
		doc.setFileName(String.valueOf(System.currentTimeMillis()));
		doc.setBelongTo("contract");
		doc.setSourceId(data.getId());
		
		documentService.upload(file,doc);
		projectContractDao.insertProjectContract(data);
		
		return 0;
	}

	public int updateProjectContract(MultipartFile file, ProjectContract data) {
		
		projectContractDao.updateProjectContract(data);
		//删除当前记录对应的合同，然后新增一条合同
		return 0;
	}

	public int deleteProjectContract(Integer id) {
		//TODO 同时删除合同文档
		return projectContractDao.deleteProjectContractByPK(id);
	}
	
}
