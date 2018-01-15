package com.idata.gtd.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata.gtd.entity.Project;
import com.idata.gtd.service.ProjectService;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 向UI提供模板表的restful访问接口<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月15日 上午18:43:27 | 创建
 */
@RestController
@CrossOrigin
public class ProjectAction {

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/selectProjectList", method = RequestMethod.POST)
	public List<Project> selectProjectList() throws Exception {

		List<Project> projectList = projectService.selectProjectList();
		return projectList;
	}

	@RequestMapping(value = "/updateProject", method = RequestMethod.POST)
	public int updateProject(@RequestBody Project project) {

		if (project.getId() == null)
			return projectService.insertProject(project);
		else
			return projectService.updateProject(project);
	};

	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
	public int deleteProject(@RequestBody Project project) {

		return projectService.deleteProject(project.getId());
	};

	

}
