package com.idata.pdm.entity;

public class EmployeeProject {

	private Integer employeeProjectId;

	private Integer employeeId;

	private Integer projectId;
	
	private Integer roleId;

	public Integer getEmployeeProjectId() {
		return employeeProjectId;
	}

	public void setEmployeeProjectId(Integer employeeProjectId) {
		this.employeeProjectId = employeeProjectId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}