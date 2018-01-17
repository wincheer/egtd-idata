package com.idata.gtd.entity;

import java.util.Date;

public class ProjectTask {

	private Integer id;
	private Integer stageId;
	private String taskName;
	private String taskDesc;
	private Date fromDate;
	private Date endDate;
	private Integer priority;
	private Integer progress;
	private Integer state;
	private Integer assignStaffId;
	private Integer actorStaffId;
	private Integer planWorkload;
	private Integer realWorkload;
	private Integer isDelay;
	private String delayReason;
	private Integer parentId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStageId() {
		return stageId;
	}

	public void setStageId(Integer stageId) {
		this.stageId = stageId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getAssignStaffId() {
		return assignStaffId;
	}

	public void setAssignStaffId(Integer assignStaffId) {
		this.assignStaffId = assignStaffId;
	}

	public Integer getActorStaffId() {
		return actorStaffId;
	}

	public void setActorStaffId(Integer actorStaffId) {
		this.actorStaffId = actorStaffId;
	}

	public Integer getPlanWorkload() {
		return planWorkload;
	}

	public void setPlanWorkload(Integer planWorkload) {
		this.planWorkload = planWorkload;
	}

	public Integer getRealWorkload() {
		return realWorkload;
	}

	public void setRealWorkload(Integer realWorkload) {
		this.realWorkload = realWorkload;
	}

	public Integer getIsDelay() {
		return isDelay;
	}

	public void setIsDelay(Integer isDelay) {
		this.isDelay = isDelay;
	}

	public String getDelayReason() {
		return delayReason;
	}

	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}