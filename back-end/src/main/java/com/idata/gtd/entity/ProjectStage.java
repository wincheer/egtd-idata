package com.idata.gtd.entity;

import java.util.Date;

public class ProjectStage {

	private Integer id;
	private Integer projectId;
	private String stageName;
	private Date startDate;
	private Date endDate;
	private Integer hasSupervisor;
	private Integer isApproval;
	private Integer progress;
	private Integer actorStaffId;
	private Date modifyDate;
	private String note;
	private Integer parent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getHasSupervisor() {
		return hasSupervisor;
	}

	public void setHasSupervisor(Integer hasSupervisor) {
		this.hasSupervisor = hasSupervisor;
	}

	public Integer getIsApproval() {
		return isApproval;
	}

	public void setIsApproval(Integer isApproval) {
		this.isApproval = isApproval;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public Integer getActorStaffId() {
		return actorStaffId;
	}

	public void setActorStaffId(Integer actorStaffId) {
		this.actorStaffId = actorStaffId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}
}