package com.idata.pdm.dao;

import java.util.List;
import java.util.Map;

import com.idata.pdm.entity.DocumentVersion;

public interface DocumentVersionMapper {

	int deleteDocumentVersion(Integer documentVersionId);

    int insertDocumentVersion(DocumentVersion record);

    DocumentVersion selectDocumentVersion(Integer documentVersionId);

    int updateDocumentVersion(DocumentVersion record);

	List<Map<String, Object>> selectDocumentVersionList(Map<String, Object> queryParam);
}