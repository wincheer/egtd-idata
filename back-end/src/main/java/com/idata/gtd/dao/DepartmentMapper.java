package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.Department;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 业主部门表DAO接口<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 上午11:42:28 | 创建
 */
public interface DepartmentMapper {

	int deleteDepartmentByPK(Integer id);

	int insertDepartment(Department record);

	Department selectDepartmentByPK(Integer id);

	int updateDepartment(Department record);

	List<Department> selectDepartmentList();
}