package com.idata.gtd.common;

/**
 * <b>版权信息 :</b> 2017，广州智数 iData<br/>
 * <b>功能描述 :</b> 用来枚举文档类别名称<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2017年10月25日 下午3:56:44 | 创建
 */
public enum DocCat {

	DOCUMENT("1", "普通文档"), STANDARD("2", "任务要求"),RESULT("3","任务结果");

	private final String value;
	private final String msg;

	private DocCat(String value, String msg) {
		this.value = value;
		this.msg = msg;
	}

	public String getValue() {
		return value;
	}

	public String getMsg() {
		return msg;
	}
	
}
