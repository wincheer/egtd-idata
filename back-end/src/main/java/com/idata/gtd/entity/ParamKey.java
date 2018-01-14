package com.idata.gtd.entity;

public class ParamKey {

	private Integer id;
    private String paramKey;
    private String paramKeyName;
    private String dataType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamKeyName() {
        return paramKeyName;
    }

    public void setParamKeyName(String paramKeyName) {
        this.paramKeyName = paramKeyName;
    }

    public String getDataType() {
        return dataType;
    }

   public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}