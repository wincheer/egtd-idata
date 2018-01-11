package com.idata.pdm.entity;

import java.util.Date;

public class ActionLog {

    private Integer logId;
    private String actor;
    private Date actionDate = new Date();
    private String ipAddress;
    private String action;
    private String actionObject;

    public Integer getLogId() {
        return logId;
    }
    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getActor() {
        return actor;
    }
    public void setActor(String actor) {
        this.actor = actor;
    }

    public Date getActionDate() {
        return actionDate;
    }
    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public String getActionObject() {
        return actionObject;
    }
    public void setActionObject(String actionObject) {
        this.actionObject = actionObject;
    }
}