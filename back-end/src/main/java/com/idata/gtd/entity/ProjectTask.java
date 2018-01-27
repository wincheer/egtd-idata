package com.idata.gtd.entity;

import java.util.Date;

public class ProjectTask {

	private Integer id;
    private String text;
    private Date start_date;
    private Date end_date;
    private Integer parent;
    private String taskDesc;
    private Integer priority;
    private Float progress;
    private Integer state;
    private Integer assignStaffId;
    private Integer actorStaffId;
    private Integer planWorkload;
    private Integer realWorkload;
    private Integer isDelay;
    private String delayReason;
    private Integer readonly;
    private Integer editable;
    private String type;
    private Integer projectId;
    private Date real_start_date;
    private Date real_end_date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

	public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
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

    public Integer getReadonly() {
        return readonly;
    }

    public void setReadonly(Integer readonly) {
        this.readonly = readonly;
    }

    public Integer getEditable() {
        return editable;
    }

    public void setEditable(Integer editable) {
        this.editable = editable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Date getReal_start_date() {
		return real_start_date;
	}

	public void setReal_start_date(Date real_start_date) {
		this.real_start_date = real_start_date;
	}

	public Date getReal_end_date() {
		return real_end_date;
	}

	public void setReal_end_date(Date real_end_date) {
		this.real_end_date = real_end_date;
	}
}