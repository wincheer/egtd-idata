package com.idata.pdm.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.pdm.dao.DocumentVersionMapper;

@Service
@Transactional
public class DocumentVersionService
{
	private final static Logger logger = LoggerFactory.getLogger(DocumentVersionService.class);

	@Autowired
	private DocumentVersionMapper verDao;

	public List<Map<String, Object>> selectDocumentVersionList(int documentId)
	{

		Map<String, Object> queryParam = new LinkedHashMap<String, Object>();
		queryParam.put("documentId", documentId);
		queryParam.put("start", 0);
		queryParam.put("pageSize", Integer.MAX_VALUE);

		List<Map<String, Object>> docVerList = verDao.selectDocumentVersionList(queryParam);
		logger.info("版本数量：" + docVerList);
		
		return docVerList;
	}

}
