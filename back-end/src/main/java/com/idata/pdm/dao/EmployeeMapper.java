package com.idata.pdm.dao;

import java.util.List;
import java.util.Map;

import com.idata.pdm.entity.Employee;

public interface EmployeeMapper {

	Employee selectEmployee(Integer employeeId);

	int deleteEmployee(Integer employeeId);

	int insertEmployee(Employee employee);

	int updateEmployee(Employee employee);

	Employee login(Employee employee);

	int totalEmployees(Map<String, Object> params);

	List<Employee> selectEmployeePageList(Map<String, Object> params);

	List<Employee> selectEmployeeList();
}