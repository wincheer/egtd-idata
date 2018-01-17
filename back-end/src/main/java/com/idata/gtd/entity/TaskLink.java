package com.idata.gtd.entity;

public class TaskLink {

	private Integer id;
	private String fromTask;
	private String toTask;
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFromTask() {
		return fromTask;
	}

	public void setFromTask(String fromTask) {
		this.fromTask = fromTask;
	}

	public String getToTask() {
		return toTask;
	}

	public void setToTask(String toTask) {
		this.toTask = toTask;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}