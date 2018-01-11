package com.idata.pdm.dao;

import java.util.List;
import java.util.Map;

import com.idata.pdm.entity.Document;

public interface DocumentMapper {

	int deleteDocument(Integer documentId);

	int insertDocument(Document record);

    Document selectDocument(Integer documentId);

    int updateDocument(Document record);

    List<Document> selectDocumentList(Map<String, Object> queryParam);
    List<Document> selectDocumentListStrict(Map<String, Object> queryParam);
    
    List<Map<String, Object>> selectDocumentMapList(Map<String, Object> queryParam);

}