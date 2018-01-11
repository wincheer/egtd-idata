package com.idata.pdm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.pdm.dao.TemplateFolderMapper;
import com.idata.pdm.dao.TemplateMapper;
import com.idata.pdm.entity.Template;
import com.idata.pdm.entity.TemplateFolder;

@Service
@Transactional
public class TemplateService {
	// private final static Logger logger =
	// LoggerFactory.getLogger(TemplateService.class);

	@Autowired
	private TemplateMapper dao;
	@Autowired
	private TemplateFolderMapper tfDao;

	public List<Template> selectTemplateList() {
		return dao.selectTemplateList();
	}

	public int insertTemplate(Template tpl) {

		dao.insertTemplate(tpl);
		// 新增项目时候，模板目录中同时要插入一条根目录的记录
		TemplateFolder tplFolder = new TemplateFolder();
		tplFolder.setTemplateId(tpl.getTemplateId());
		tplFolder.setParentTemplateFolderId(0);
		tplFolder.setTemplateFolderName("根目录");
		tplFolder.setTemplateFolderDesc("新建模板时自动创建的目录");
		tfDao.insertTemplateFolder(tplFolder);
		
		return tpl.getTemplateId();
	}

}
