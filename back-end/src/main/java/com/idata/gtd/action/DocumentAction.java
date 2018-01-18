package com.idata.gtd.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata.gtd.entity.Document;
import com.idata.gtd.service.DocumentService;

@RestController
@CrossOrigin
public class DocumentAction {

	private final static Logger logger = LoggerFactory.getLogger(DocumentAction.class);
	
	@Autowired
	private DocumentService docService;
	
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

}
