package com.idata.gtd.entity;

import java.util.Date;

public class ProjectStage {

	private Integer id;
	private Integer projectId;
	private String text;
	private Date start_date;
	private Date end_date;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
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