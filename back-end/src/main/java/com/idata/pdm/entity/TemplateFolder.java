package com.idata.pdm.entity;

public class TemplateFolder {

	private Integer templateFolderId;

	private Integer templateId;

	private String templateFolderName;

	private String templateFolderDesc;

	private Integer parentTemplateFolderId;

	public Integer getTemplateFolderId() {
		return templateFolderId;
	}

	public void setTemplateFolderId(Integer templateFolderId) {
		this.templateFolderId = templateFolderId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getTemplateFolderName() {
		return templateFolderName;
	}

	public void setTemplateFolderName(String templateFolderName) {
		this.templateFolderName = templateFolderName;
	}

	public String getTemplateFolderDesc() {
		return templateFolderDesc;
	}

	public void setTemplateFolderDesc(String templateFolderDesc) {
		this.templateFolderDesc = templateFolderDesc;
	}

	public Integer getParentTemplateFolderId() {
		return parentTemplateFolderId;
	}

	public void setParentTemplateFolderId(Integer parentTemplateFolderId) {
		this.parentTemplateFolderId = parentTemplateFolderId;
	}


}