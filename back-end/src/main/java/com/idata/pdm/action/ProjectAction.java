package com.idata.pdm.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata.pdm.entity.Project;
import com.idata.pdm.service.ProjectService;

@RestController
@CrossOrigin
public class ProjectAction
{
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "/insertProject", method = RequestMethod.POST)
	public int insertProject(@RequestBody Project project) {

		int projectId = projectService.insertProject(project);
		return projectId;
	}
	
	@RequestMapping(value = "/projectPageList", method = RequestMethod.POST)
	public List<Project> selectProjectPageList(@RequestBody Map<String,Object> project){
		
		List<Project> projectrList = projectService.selectProjectPageList(project);
		return projectrList;
	}
	
	@RequestMapping(value = "/myProjectList", method = RequestMethod.GET)
	public List<Project> selectProjectListByEmployeeId(@RequestParam int employeeId) {

		List<Project> myProjectList = projectService.selectProjectListByEmployeeId(employeeId);
		
		return myProjectList;
	}
}