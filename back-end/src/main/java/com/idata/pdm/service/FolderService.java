package com.idata.pdm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.pdm.dao.FolderMapper;
import com.idata.pdm.entity.Folder;

@Service
@Transactional
public class FolderService
{
	//private final static Logger logger = LoggerFactory.getLogger(FolderService.class);
	
	@Autowired
	private FolderMapper dao;

	public List<Folder> selectFolderList(int projectId) {
		return dao.selectFolderList(projectId);
	}

	public int insertFolder(Folder folder) {
		return dao.insertFolder(folder);
	}

	public int deleteFolder(int folderId) {
		return dao.deleteFolder(folderId);
	}

	
}
