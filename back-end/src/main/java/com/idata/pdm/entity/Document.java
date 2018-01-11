package com.idata.pdm.entity;

public class Document {

	private Integer documentId;

    private Integer folderId;

    private String documentName;

    private Integer projectId;

    public Integer getDocumentId() {
        return documentId;
    }
    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public Integer getFolderId() {
        return folderId;
    }
    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getDocumentName() {
        return documentName;
    }
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Integer getProjectId() {
        return projectId;
    }
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}