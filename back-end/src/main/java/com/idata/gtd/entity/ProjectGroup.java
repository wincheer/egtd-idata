package com.idata.gtd.entity;

public class ProjectGroup {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column project_group.id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	private Integer id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column project_group.project_id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	private Integer projectId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column project_group.group_name
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	private String groupName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column project_group.group_desc
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	private String groupDesc;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column project_group.parent_id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	private Integer parentId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column project_group.id
	 * @return  the value of project_group.id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column project_group.id
	 * @param id  the value for project_group.id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column project_group.project_id
	 * @return  the value of project_group.project_id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public Integer getProjectId() {
		return projectId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column project_group.project_id
	 * @param projectId  the value for project_group.project_id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column project_group.group_name
	 * @return  the value of project_group.group_name
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column project_group.group_name
	 * @param groupName  the value for project_group.group_name
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column project_group.group_desc
	 * @return  the value of project_group.group_desc
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public String getGroupDesc() {
		return groupDesc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column project_group.group_desc
	 * @param groupDesc  the value for project_group.group_desc
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column project_group.parent_id
	 * @return  the value of project_group.parent_id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column project_group.parent_id
	 * @param parentId  the value for project_group.parent_id
	 * @mbg.generated  Thu Jan 11 18:00:33 CST 2018
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}