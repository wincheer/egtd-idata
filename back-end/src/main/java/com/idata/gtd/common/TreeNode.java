package com.idata.pdm.common;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {

	private Integer id;
	private Integer ownerId; //比如模板ID或者项目ID
	private String label;
	private Integer parentId;
	private List<TreeNode> children = new ArrayList<TreeNode>();

	public TreeNode() {
	}

	public TreeNode(Integer id, String label, Integer parentId,Integer ownerId) {
		this.id = id;
		this.label = label;
		this.parentId = parentId;
		this.ownerId = ownerId;
	}

	public Integer getId() {
		return id;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

}
