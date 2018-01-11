package com.idata.pdm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.pdm.dao.FileMapper;
import com.idata.pdm.entity.File;

@Service
@Transactional
public class FileService
{
	//private final static Logger logger = LoggerFactory.getLogger(FileService.class);

	@Autowired
	private FileMapper fileDao;

	public File selectFile(int fileId)
	{
		return fileDao.selectFile(fileId);
	}

}
