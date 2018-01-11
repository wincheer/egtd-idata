package com.idata.pdm.common;


/**
 * <b>版权信息 :</b> 2017，广州智数 iData<br/>
 * <b>功能描述 :</b> 用来枚举日志的事件名称<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2017年10月25日 下午3:56:44 | 创建
 */
public enum LogEvent
{
	LOGIN("签入"), LOGOUT("签出"), QUERY("查询"), CREATE("增加"), UPDATE("修改"), DELETE("删除");

	private final String eventName;

	private LogEvent(String eventName)
	{
		this.eventName = eventName;
	}

	public String getEventName()
	{
		return eventName;
	}

	//重写 toString() 方法
	@Override
	public String toString()
	{
		return eventName;
	}
}
