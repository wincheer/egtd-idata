package com.idata.gtd.common;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 将行记录转换为通用的树节点结构<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 上午11:40:59 | 创建
 */
public class TreeNode {

	private Integer id;
	private String label;
	private String desc;
	private Integer parentId;
	private List<TreeNode> children = new ArrayList<TreeNode>();

	public TreeNode() {
	}

	public TreeNode(Integer id, String label, Integer parentId) {
		this.id = id;
		this.label = label;
		this.parentId = parentId;
	}
	
	public TreeNode(Integer id, String label, String desc, Integer parentId) {
		this.id = id;
		this.label = label;
		this.desc = desc;
		this.parentId = parentId;
	}

	public Integer getId() {
		return id;
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
