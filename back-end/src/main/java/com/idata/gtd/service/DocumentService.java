package com.idata.gtd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.idata.gtd.dao.DocumentMapper;
import com.idata.gtd.entity.Document;

@Service
@Transactional
public class DocumentService {
	
	//private final static Logger logger = LoggerFactory.getLogger(DocumentService.class);
	
	@Value("${upload_path}")
	private String UPLOAD_PATH;
	@Autowired
	private DocumentMapper documentDao;
	
	public void upload(MultipartFile uploadFile,Document data) throws Exception {

		//上传文件
		java.io.File outputFile = new java.io.File(UPLOAD_PATH, String.valueOf(data.getFileName()));
		uploadFile.transferTo(outputFile);
		//更新记录
		documentDao.insertDocument(data);

	}

	public List<Document> selectDocumentList(Document data) {
		
		return documentDao.selectDocumentList(data);
	}

	public int insertDocument(Document doc) {

		return documentDao.insertDocument(doc);
	}

	public int updateDocument(Document doc) {
		
		return documentDao.updateDocument(doc);
	}

	public int deleteDocument(Integer id) {

		return documentDao.deleteDocumentByPK(id);
	}
	
	public void deleteDocuments(Document doc) {

		documentDao.deleteDocuments(doc);
	}
}
