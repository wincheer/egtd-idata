package com.idata.pdm.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.pdm.common.Utils;
import com.idata.pdm.dao.EmployeeMapper;
import com.idata.pdm.entity.Employee;

@Service
@Transactional
public class EmployeeService {
	private final static Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeMapper dao;

	public Employee login(Employee employee) {
		Employee emp = dao.login(employee);
		if(emp!=null)
			logger.info(emp.getDisplayName());

		return emp;
	}

	public int totalEmployees(Map<String, Object> queryParam) {
		int total = 0;

		@SuppressWarnings("unchecked")
		Map<String, Object> params = (Map<String, Object>) queryParam.get("filter");
		total = dao.totalEmployees(Utils.cleanMap(params));

		return total;
	}

	public List<Employee> selectEmployeePageList(Map<String, Object> queryParam) {
		int pageNo = (Integer) queryParam.get("pageNo");
		int pageSize = (Integer) queryParam.get("pageSize");
		int start = (pageNo - 1) * pageSize;

		@SuppressWarnings("unchecked")
		Map<String, Object> params = (Map<String, Object>) queryParam.get("filter");
		logger.info(params.toString());
		params.put("start", start);
		params.put("pageSize", pageSize);

		return dao.selectEmployeePageList(Utils.cleanMap(params));
	}

	public int deleteEmployee(int employeeId) {
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setIsDelete(1);
		//没有真正删除，而是做了删除标记
		return dao.updateEmployee(employee);
	}

	public int updateEmployee(Employee employee) {
		return dao.updateEmployee(employee);
	}

	public int insertEmployee(Employee employee) {
		return dao.insertEmployee(employee);
	}

	public List<Employee> selectEmployeeList() {
		return dao.selectEmployeeList();
	}

}
