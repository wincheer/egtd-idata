package com.idata.gtd.entity;

import java.util.Date;

public class Message {

	private Integer id;
    private String from;
    private String to;
    private String toScop; //actor个人、group项目组
    private String title;
    private String body;
    private String type; //normal 普通消息(任务发布、任务延期（系统发送）)，audit 针对项目审批、confirm针对任务确认
    private Integer relationId;
    private Date time;
    private Integer isRead;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getToScop() {
        return toScop;
    }

    public void setToScop(String toScop) {
        this.toScop = toScop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}
}