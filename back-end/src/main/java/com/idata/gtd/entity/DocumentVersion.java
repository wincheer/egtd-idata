package com.idata.gtd.entity;

import java.util.Date;

public class DocumentVersion {

	private Integer id;
	private Integer docId;
	private Integer ownerStaffId;
	private Date uploadDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDocId() {
		return docId;
	}

	public void setDocId(Integer docId) {
		this.docId = docId;
	}

	public Integer getOwnerStaffId() {
		return ownerStaffId;
	}

	public void setOwnerStaffId(Integer ownerStaffId) {
		this.ownerStaffId = ownerStaffId;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
}