package com.idata.gtd.dao;

import com.idata.gtd.entity.DocumentVersion;

public interface DocumentVersionMapper {

	int deleteDocumentVersionByPK(Integer id);

	int insertDocumentVersion(DocumentVersion record);

	DocumentVersion selectDocumentVersionByPK(Integer id);

	int updateDocumentVersion(DocumentVersion record);

}