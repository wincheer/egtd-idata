package com.idata.gtd.entity;

public class StageCheck {

	private Integer id;
	private Integer stageId;
	private Integer checkStaffId;
	private Integer order;

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

	public Integer getCheckStaffId() {
		return checkStaffId;
	}

	public void setCheckStaffId(Integer checkStaffId) {
		this.checkStaffId = checkStaffId;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}