package com.idata.pdm.entity;

public class Employee {
    private Integer employeeId;
    private String displayName;
    private String loginName;
    private String loginPassword;
    private Integer role;
    private Integer isDelete;

    public Integer getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
    
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	
	public Integer getIsDelete()
	{
		return isDelete;
	}
	public void setIsDelete(Integer isDelete)
	{
		this.isDelete = isDelete;
	}
}