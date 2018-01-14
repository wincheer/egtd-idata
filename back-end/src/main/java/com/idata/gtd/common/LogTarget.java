package com.idata.gtd.common;

/**
 * <b>版权信息 :</b> 2017，广州智数 iData<br/>
 * <b>功能描述 :</b> 用来枚举日志的事件名称<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2017年10月25日 下午3:56:44 | 创建
 */
public enum LogTarget {

	DEPARTMENT("部门"), DEP_EMP("部门人员"), VENDOR("供应商"), VENDOR_EMP("供应商人员"), PROJECT("项目"), PROJECT_GROUP(
			"项目组"), PROJECT_STAFF("项目成员"),PROJECT_STAGE("项目阶段"),PROJECT_TASK("项目任务"),DOCUMENT("文档");

	private final String objectName;

	private LogTarget(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectName() {
		return objectName;
	}

	// 重写 toString() 方法
	@Override
	public String toString() {
		return objectName;
	}
}
