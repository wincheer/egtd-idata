package com.idata.pdm.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.idata.pdm.dao.DocumentMapper;
import com.idata.pdm.dao.DocumentVersionMapper;
import com.idata.pdm.dao.FileMapper;
import com.idata.pdm.entity.Document;
import com.idata.pdm.entity.DocumentVersion;
import com.idata.pdm.entity.File;

@Service
@Transactional
public class DocumentService {
	private final static Logger logger = LoggerFactory.getLogger(DocumentService.class);

	@Value("${upload_path}")
	private String UPLOAD_PATH;
	@Autowired
	private DocumentMapper docDao;
	@Autowired
	private FileMapper fileDao;
	@Autowired
	private DocumentVersionMapper verDao;

	/**
	 * 用户上传文档： 
	 * 1、查找md5对应的file,若没找到新建并返回fileId同时上传文件；若找到返回fileId并且不上传文件
	 * 2、查找当前项目下（要不要检查当前目录下？）同名文档，若有新建版本号，若无新建document，然后新建版本号: 
	 * 3、新建doc_version
	 * 
	 * @param data，包含docName、docMd5、projectId、folderId、employeeId
	 * @throws  
	 * @throws Exception 
	 */
	public void upload(MultipartFile uploadFile,Map<String, Object> data) throws Exception {

		logger.info("上传参数: " + data.toString());
		String docName = data.get("docName").toString();
		String docMd5 = data.get("docMd5").toString();
		Integer projectId = Integer.parseInt(data.get("projectId").toString());
		Integer folderId = Integer.parseInt(data.get("folderId").toString());
		Integer employeeId = Integer.parseInt(data.get("employeeId").toString());

		long timeMillis = System.currentTimeMillis();
		
		File file = fileDao.selectFileByMd5(docMd5);
		if (file == null) {
			//上传文件
			java.io.File outputFile = new java.io.File(UPLOAD_PATH, String.valueOf(timeMillis));
			uploadFile.transferTo(outputFile);
			//更新记录
			file = new File();
			file.setFileName(String.valueOf(timeMillis));
			file.setFileMd5(data.get("docMd5").toString());
			fileDao.insertFile(file);
		}

		Map<String, Object> queryParam = new LinkedHashMap<String, Object>();
		queryParam.put("projectId", projectId);
		queryParam.put("folderId", folderId);
		queryParam.put("documentName", docName);
		queryParam.put("start", 0);
		queryParam.put("pageSize", 1);

		List<Document> docList = docDao.selectDocumentListStrict(queryParam);
		Document doc;
		if (docList.size() != 0) {
			doc = docList.get(0);
		} else {
			doc = new Document();
			doc.setProjectId(projectId);
			doc.setFolderId(folderId);
			doc.setDocumentName(docName);
			docDao.insertDocument(doc);
		}

		DocumentVersion ver = new DocumentVersion();
		ver.setDocumentId(doc.getDocumentId());
		ver.setFileId(file.getFileId());
		ver.setUploadDate(new Date());
		ver.setUploadEmployee(employeeId);
		ver.setVersion(new Timestamp(timeMillis).toString());
		verDao.insertDocumentVersion(ver);

	}

	public List<Document> selectFolderDocumentList(int folderId) {
		
		Map<String, Object> queryParam = new LinkedHashMap<String, Object>();
		queryParam.put("folderId", folderId);
		queryParam.put("start", 0);
		queryParam.put("pageSize", Integer.MAX_VALUE);

		List<Document> docList = docDao.selectDocumentList(queryParam);
		return docList;
	}

	public List<Document> selectDocumentList(Map<String, Object> queryParam)
	{
		//TODO 处理一下当前用户
		queryParam.put("start", 0);
		queryParam.put("pageSize", Integer.MAX_VALUE);
		List<Document> docList = docDao.selectDocumentList(queryParam);
		return docList;
	}
	
	public List<Map<String, Object>> selectDocumentMapList(Map<String, Object> queryParam)
	{
		//TODO 处理一下当前用户
		queryParam.put("start", 0);
		queryParam.put("pageSize", Integer.MAX_VALUE);
		List<Map<String, Object>> docList = docDao.selectDocumentMapList(queryParam);
		return docList;
	}

	public Document selectDocument(int docId)
	{
		return docDao.selectDocument(docId);
	}

}
