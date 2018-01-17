package com.idata.gtd.dao;

import com.idata.gtd.entity.Document;

public interface DocumentMapper {

	int deleteDocumentByPK(Integer id);

	int insertDocument(Document record);

	Document selectDocumentByPK(Integer id);

	int updateDocument(Document record);
}