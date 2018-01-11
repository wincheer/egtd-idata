package com.idata.pdm.common;


/**
 * <b>版权信息 :</b> 2017，广州智数 iData<br/>
 * <b>功能描述 :</b> 用来枚举日志的事件名称<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2017年10月25日 下午3:56:44 | 创建
 */
public enum LogObject
{
	TEMPLATE("模板"), TEMPLATE_FOLDER("模板目录"), FOLDER("目录"), DOCUMENT("文档"), PROJECT("项目"),EMPLOYEE("用户"),PROJECT_EMPLOYEE("项目用户");

	private final String objectName;

	private LogObject(String objectName)
	{
		this.objectName = objectName;
	}

	public String getObjectName()
	{
		return objectName;
	}

	//重写 toString() 方法
	@Override
	public String toString()
	{
		return objectName;
	}
}
