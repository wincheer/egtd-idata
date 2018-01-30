package com.idata.gtd.entity;

import java.util.Date;

public class Message {

	private Integer id;
    private Integer from;
    private Integer to;
    private String toScope; //actor个人、group项目组
    private String title;
    private String body;
    private String type; //normal 普通消息(任务发布、任务延期（系统发送）)，audit 针对项目审批、confirm针对任务确认
    private Integer relationId;
    private Date time;
    private Integer isRead;
    private Integer isExec;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public String getToScope() {
        return toScope;
    }

    public void setToScope(String toScope) {
        this.toScope = toScope;
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

	public Integer getIsExec() {
		return isExec;
	}

	public void setIsExec(Integer isExec) {
		this.isExec = isExec;
	}
}