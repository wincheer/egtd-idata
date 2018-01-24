package com.idata.gtd.entity;

public class TplGroup {

	private Integer id;
	private Integer tplId;
	private String groupName;
	private String groupRole;
	private Integer parentId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTplId() {
		return tplId;
	}

	public void setTplId(Integer tplId) {
		this.tplId = tplId;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getGroupRole() {
		return groupRole;
	}

	public void setGroupRole(String groupRole) {
		this.groupRole = groupRole;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}