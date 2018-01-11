package com.idata.pdm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.pdm.dao.TemplateFolderMapper;
import com.idata.pdm.entity.TemplateFolder;

@Service
@Transactional
public class TemplateFolderService
{
	//private final static Logger logger = LoggerFactory.getLogger(TemplateService.class);
	
	@Autowired
	private TemplateFolderMapper dao;

	public List<TemplateFolder> selectTemplateFolderList(int templateId) {
		return dao.selectTemplateFolderList(templateId);
	}

	public int insertTemplateFolder(TemplateFolder folder) {
		return dao.insertTemplateFolder(folder);
	}

	public int deleteTemplateFolder(int templateFolderId) {
		return dao.deleteTemplateFolder(templateFolderId);
	}

	
}
