package com.idata.gtd.dao;

import com.idata.gtd.entity.Employee;

public interface EmployeeMapper {

    int deleteEmployeeByPK(Integer id);

    int insertEmployee(Employee record);

    Employee selectEmployeeByPK(Integer id);

    int updateEmployee(Employee record);
}