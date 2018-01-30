package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.Employee;

public interface EmployeeMapper {

    int deleteEmployeeByPK(Integer id);

    int insertEmployee(Employee record);

    Employee selectEmployeeByPK(Integer id);

    int updateEmployee(Employee record);
    
    //////////

    Employee login(Employee record);
    
    void deleteDepartmentEmployees(Integer depId);

	List<Employee> selectDepartmentEmployeeList(Integer depId);

	List<Employee> selectAllDepEmpList();

	void deleteVendorEmps(Integer vendorId);

	List<Employee> selectVendorEmployeeList(Integer vendorId);

	int insertVendorEmployee(Employee vendorEmp);

	List<Employee> selectVendorEmployeeListByProject(Integer projectId); 
	
	List<Employee> selectProjectEmployeeList(Integer projectId);

	List<Employee> selectEmployeeList();  
	
	List<Employee> selectGroupEmpList(Integer groupId);

	List<Employee> selectAvailableProjectEmployeeList(Integer projectId);

	List<Employee> selectOwnerProjectEmpList(Integer projectId);

	List<String> selectMyRoles(Integer empId);

	Employee selectProjectLeader(Integer projectId);    
}