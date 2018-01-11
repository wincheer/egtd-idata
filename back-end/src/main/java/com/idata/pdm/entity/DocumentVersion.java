package com.idata.pdm.entity;

import java.util.Date;

public class DocumentVersion {

	private Integer documentVersionId;

    private Integer documentId;
    
    private Integer fileId;

    private Date uploadDate;

    private Integer uploadEmployee;

    private String version;

    public Integer getDocumentVersionId() {
        return documentVersionId;
    }

    public void setDocumentVersionId(Integer documentVersionId) {
        this.documentVersionId = documentVersionId;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Integer getUploadEmployee() {
        return uploadEmployee;
    }

    public void setUploadEmployee(Integer uploadEmployee) {
        this.uploadEmployee = uploadEmployee;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}