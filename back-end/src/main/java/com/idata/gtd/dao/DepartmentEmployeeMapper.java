package com.idata.gtd.dao;

import com.idata.gtd.entity.DepartmentEmployee;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 部门员工DAO接口<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 下午6:07:34 | 创建
 */
public interface DepartmentEmployeeMapper {

    int deleteDepartmentEmployeeByPK(Integer id);

    int insertDepartmentEmployee(DepartmentEmployee record);

    DepartmentEmployee selectDepartmentEmployeeByPK(Integer id);

    int updateDepartmentEmployee(DepartmentEmployee record);

    int deleteDepartmentEmployees(Integer depId);
}