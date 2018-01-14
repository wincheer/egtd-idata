package com.idata.gtd.entity;

import java.util.Date;

public class Log {

	private Integer id;
	private Integer actor;
	private Date opTime;
	private String action;
	private String target;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActor() {
		return actor;
	}

	public void setActor(Integer actor) {
		this.actor = actor;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}