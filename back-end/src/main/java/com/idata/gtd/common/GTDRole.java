package com.idata.gtd.common;

public class GTDRole {

	// 项目角色
	public final static String LEADER = "R01"; // 分管领导
	public final static String PM = "R02"; // 项目经理
	public final static String ASSISTAND = "R03"; // 项目助理
	public final static String SUPERVISOR = "R04"; // 监理
	public final static String STAFF = "R10"; // 常规项目用户

	// 系统角色
	public final static String ADMIN = "R00"; // 超级用户
	public final static String LEFT = "R20"; // 甲方普通用户
	public final static String RIGHT = "R30"; // 供应商普通用户
	public final static String MIDDLE = "R40"; // 监理方普通用户

	// 多余的一个角色
	public final static String IDLER = "R99"; // 非项目用户

}
