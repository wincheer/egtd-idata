package com.idata.gtd.entity;

import java.util.Date;

public class ProjectStage {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_stage.id
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_stage.stage_name
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    private String stageName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_stage.start_date
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    private Date startDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_stage.end_date
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    private Date endDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_stage.has_supervisor
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    private String hasSupervisor;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_stage.is_approval
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    private String isApproval;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_stage.progress
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    private Integer progress;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_stage.actor_staff_id
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    private Integer actorStaffId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_stage.modify_date
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    private Date modifyDate;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column project_stage.note
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    private String note;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_stage.id
     *
     * @return the value of project_stage.id
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_stage.id
     *
     * @param id the value for project_stage.id
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_stage.stage_name
     *
     * @return the value of project_stage.stage_name
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public String getStageName() {
        return stageName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_stage.stage_name
     *
     * @param stageName the value for project_stage.stage_name
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_stage.start_date
     *
     * @return the value of project_stage.start_date
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_stage.start_date
     *
     * @param startDate the value for project_stage.start_date
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_stage.end_date
     *
     * @return the value of project_stage.end_date
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_stage.end_date
     *
     * @param endDate the value for project_stage.end_date
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_stage.has_supervisor
     *
     * @return the value of project_stage.has_supervisor
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public String getHasSupervisor() {
        return hasSupervisor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_stage.has_supervisor
     *
     * @param hasSupervisor the value for project_stage.has_supervisor
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public void setHasSupervisor(String hasSupervisor) {
        this.hasSupervisor = hasSupervisor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_stage.is_approval
     *
     * @return the value of project_stage.is_approval
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public String getIsApproval() {
        return isApproval;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_stage.is_approval
     *
     * @param isApproval the value for project_stage.is_approval
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public void setIsApproval(String isApproval) {
        this.isApproval = isApproval;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_stage.progress
     *
     * @return the value of project_stage.progress
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public Integer getProgress() {
        return progress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_stage.progress
     *
     * @param progress the value for project_stage.progress
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_stage.actor_staff_id
     *
     * @return the value of project_stage.actor_staff_id
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public Integer getActorStaffId() {
        return actorStaffId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_stage.actor_staff_id
     *
     * @param actorStaffId the value for project_stage.actor_staff_id
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public void setActorStaffId(Integer actorStaffId) {
        this.actorStaffId = actorStaffId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_stage.modify_date
     *
     * @return the value of project_stage.modify_date
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_stage.modify_date
     *
     * @param modifyDate the value for project_stage.modify_date
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column project_stage.note
     *
     * @return the value of project_stage.note
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public String getNote() {
        return note;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column project_stage.note
     *
     * @param note the value for project_stage.note
     *
     * @mbg.generated Thu Jan 11 17:28:31 CST 2018
     */
    public void setNote(String note) {
        this.note = note;
    }
}