package com.idata.gtd.action;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata.gtd.entity.Document;
import com.idata.gtd.service.DocumentService;

@RestController
@CrossOrigin
public class DocumentAction {

	private final static Logger logger = LoggerFactory.getLogger(DocumentAction.class);
	
	@Autowired
	private DocumentService docService;
	@Value("${upload_path}")
	private String UPLOAD_PATH;
	
	@RequestMapping(value = "/selectDocumentList", method = RequestMethod.POST)
	public List<Document> selectDocumentList(@RequestBody Document data) throws Exception {

		logger.info("BelongTo = " + data.getBelongTo() +"SourceId = "+ data.getSourceId());
		
		List<Document> selectDocumentList = docService.selectDocumentList(data);
		return selectDocumentList;
	}
	
	@RequestMapping(value = "/updateDocument", method = RequestMethod.POST)
	public int updateDocument(@RequestBody Document doc) {

		if (doc.getId() == null)
			return docService.insertDocument(doc);
		else
			return docService.updateDocument(doc);
	};

	@RequestMapping(value = "/deleteDocument", method = RequestMethod.POST)
	public int Document(@RequestBody Document doc) {

		return docService.deleteDocument(doc.getId());
	};
	
	@RequestMapping(path = "/download", method = RequestMethod.GET)
	public ResponseEntity<Resource> download(@RequestParam int docId)
			throws IOException
	{
		Document doc = docService.selectDocument(docId);
		String fileName = doc.getFileName();
		String docName = doc.getName();
		
		File outputFile = new File(UPLOAD_PATH, fileName);
		Path path = Paths.get(outputFile.getAbsolutePath());
		Resource resource = new ByteArrayResource(Files.readAllBytes(path));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-disposition", "attachment; filename=" + URLEncoder.encode(docName, "UTF-8"));

		return ResponseEntity.ok()
				.headers(headers)
				.contentLength(outputFile.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}

}
