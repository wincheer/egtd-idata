package com.idata.gtd.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.gtd.dao.DepartmentMapper;
import com.idata.gtd.dao.EmployeeMapper;
import com.idata.gtd.entity.Department;
import com.idata.gtd.entity.Employee;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 业主部门的服务层<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 上午11:43:02 | 创建
 */
@Service
@Transactional
public class DepartmentService {

	private final static Logger logger = LoggerFactory.getLogger(DepartmentService.class);

	@Autowired
	private DepartmentMapper depDao;
	@Autowired
	private EmployeeMapper empDao;
	
	public List<Department> selectDepartmentList() {
		
		logger.info("查询业主的组织结构");
		return depDao.selectDepartmentList();
	}

	public int insertDepartment(Department dep) {

		return depDao.insertDepartment(dep);
	}

	public int updateDepartment(Department dep) {

		return depDao.updateDepartment(dep);
	}

	public int deleteDepartment(Integer depId) {

		depDao.deleteDepartmentByPK(depId);
		//同时删除当前机构下的员工
		empDao.deleteDepartmentEmployees(depId);
		
		return 0;
	}
	
	public List<Employee> selectDepartmentEmployeeList(Integer depId){
		
		return empDao.selectDepartmentEmployeeList(depId);
	}

	public int insertEmployee(Employee depEmp) {

		depEmp.setCode("o");
		depEmp.setPassword("123456");
		empDao.insertEmployee(depEmp);
		return depEmp.getId();
	}

	public int updateEmployee(Employee depEmp) {

		return empDao.updateEmployee(depEmp);
	}

	public int deleteDepartmentEmployee(Integer id) {

		return empDao.deleteEmployeeByPK(id);
	}

	public List<Employee> selectAllDepEmpList() {

		return empDao.selectAllDepEmpList();
	}

}
