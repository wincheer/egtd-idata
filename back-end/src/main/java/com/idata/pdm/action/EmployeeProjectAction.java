package com.idata.pdm.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata.pdm.entity.EmployeeProject;
import com.idata.pdm.service.EmployeeProjectService;

@RestController
@CrossOrigin
public class EmployeeProjectAction
{
	@Autowired
	private EmployeeProjectService employeeProjectService;

	@RequestMapping(value = "/projectEmployeeList", method = RequestMethod.GET)
	public List<EmployeeProject> selectProjectEmployeeList(@RequestParam int projectId) throws Exception {

		List<EmployeeProject> projectEmployeeList = employeeProjectService.selectProjectEmployeeList(projectId);
		
		return projectEmployeeList;
	}
	
	@RequestMapping(value = "/employeeProjectList", method = RequestMethod.GET)
	public List<EmployeeProject> selectEmployeeProjectList(@RequestParam int employeeId) throws Exception {

		List<EmployeeProject> employeeProjectList = employeeProjectService.selectEmployeeProjectList(employeeId);
		
		return employeeProjectList;
	}
	
	@RequestMapping(value = "/editEmployeeProject", method = RequestMethod.POST)
	public int editEmployee(@RequestBody EmployeeProject projectEmployee) {

		int employeeProjectId = 0;
		if (projectEmployee.getEmployeeProjectId() != null && projectEmployee.getEmployeeProjectId() > 0) {
			//update
			employeeProjectId = employeeProjectService.updateEmployeeProject(projectEmployee);
		} else {
			//insert
			employeeProjectId = employeeProjectService.insertEmployeeProject(projectEmployee);
		}

		return employeeProjectId;
	}
	
	@RequestMapping(value = "/removeProjectEmployee", method = RequestMethod.GET)
	public int deleteEmployeeProject(@RequestParam int employeeProjectId) {
		return employeeProjectService.deleteEmployeeProject(employeeProjectId);
	}
}