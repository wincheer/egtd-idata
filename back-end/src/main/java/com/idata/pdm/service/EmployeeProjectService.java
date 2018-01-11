package com.idata.pdm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.pdm.dao.EmployeeProjectMapper;
import com.idata.pdm.entity.EmployeeProject;

@Service
@Transactional
public class EmployeeProjectService
{
	//private final static Logger logger = LoggerFactory.getLogger(EmployeeProjectService.class);
	
	@Autowired
	private EmployeeProjectMapper dao;

	public List<EmployeeProject> selectProjectEmployeeList(int projectId) {
		return dao.selectProjectEmployeeList(projectId);
	}
	
	public List<EmployeeProject> selectEmployeeProjectList(int employeeId) {
		return dao.selectEmployeeProjectList(employeeId);
	}

	public int updateEmployeeProject(EmployeeProject projectEmployee) {
		return dao.updateEmployeeProject(projectEmployee);
	}

	public int insertEmployeeProject(EmployeeProject projectEmployee) {
		return dao.insertEmployeeProject(projectEmployee);
	}

	public int deleteEmployeeProject(int employeeProjectId) {
		return dao.deleteEmployeeProject(employeeProjectId);
	}
	
}
