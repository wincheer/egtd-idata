package com.idata.gtd.entity;

import java.util.Date;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 项目表<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月15日 下午4:10:29 | 创建
 */
public class Project {

	private Integer id;
	private String projectName;
	private String projectDesc;
	private String category;
	private Integer isKey;
	private Integer isForzen;
	private Date createDate;
	private Date startDate;
	private Date endDate;
	private Integer depId;
	private String icon;
	private Integer amount;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getIsKey() {
		return isKey;
	}

	public void setIsKey(Integer isKey) {
		this.isKey = isKey;
	}

	public Integer getIsForzen() {
		return isForzen;
	}

	public void setIsForzen(Integer isForzen) {
		this.isForzen = isForzen;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getDepId() {
		return depId;
	}

	public void setDepId(Integer depId) {
		this.depId = depId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}