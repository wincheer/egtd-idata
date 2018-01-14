package com.idata.gtd.entity;

public class ParamValue {

	private Integer id;
	private Integer paramId;
	private String paramValue;
	private String paramDesc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParamId() {
		return paramId;
	}

	public void setParamId(Integer paramId) {
		this.paramId = paramId;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	public String getParamDesc()
	{
		return paramDesc;
	}

	public void setParamDesc(String paramDesc)
	{
		this.paramDesc = paramDesc;
	}
}