package com.idata.gtd.entity;

public class StageCheck {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column stage_check.id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column stage_check.stage_id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	private Integer stageId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column stage_check.check_staff_id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	private Integer checkStaffId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column stage_check.order
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	private Integer order;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column stage_check.id
	 * @return  the value of stage_check.id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column stage_check.id
	 * @param id  the value for stage_check.id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column stage_check.stage_id
	 * @return  the value of stage_check.stage_id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public Integer getStageId() {
		return stageId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column stage_check.stage_id
	 * @param stageId  the value for stage_check.stage_id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public void setStageId(Integer stageId) {
		this.stageId = stageId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column stage_check.check_staff_id
	 * @return  the value of stage_check.check_staff_id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public Integer getCheckStaffId() {
		return checkStaffId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column stage_check.check_staff_id
	 * @param checkStaffId  the value for stage_check.check_staff_id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public void setCheckStaffId(Integer checkStaffId) {
		this.checkStaffId = checkStaffId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column stage_check.order
	 * @return  the value of stage_check.order
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public Integer getOrder() {
		return order;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column stage_check.order
	 * @param order  the value for stage_check.order
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
}