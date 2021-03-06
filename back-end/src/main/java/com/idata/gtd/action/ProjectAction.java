package com.idata.gtd.action;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idata.gtd.common.TreeNode;
import com.idata.gtd.common.Utils;
import com.idata.gtd.entity.Employee;
import com.idata.gtd.entity.Project;
import com.idata.gtd.entity.ProjectContract;
import com.idata.gtd.entity.ProjectGroup;
import com.idata.gtd.entity.ProjectStaff;
import com.idata.gtd.entity.ProjectTask;
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

	private final static Logger logger = LoggerFactory.getLogger(ProjectAction.class);
	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/selectProject", method = RequestMethod.POST)
	public Project selectProject(@RequestBody Project project) throws Exception {

		return projectService.selectProject(project.getId());
	}

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

	////////
	// 根据模板创建项目
	@RequestMapping(value = "/createProjectFromTpl", method = RequestMethod.POST)
	public int createProjectFromTpl(@RequestBody Map<String, Object> data) {

		Integer tplId = (Integer) data.get("tplId");
		Integer empId = (Integer) data.get("empId");
		String ProjectName = data.get("projectName") == null ? "未命名项目" : data.get("projectName").toString();
		return projectService.createProjectFromTpl(tplId, ProjectName, empId);
	};

	////////

	@RequestMapping(value = "/selectProjectContractList", method = RequestMethod.POST)
	public List<ProjectContract> selectProjectContractList(@RequestBody ProjectContract data) throws Exception {

		List<ProjectContract> projectContractList = projectService.selectProjectContractList(data.getProjectId());
		return projectContractList;
	}

	@RequestMapping(value = "/updateProjectContract", method = RequestMethod.POST)
	public int updateProjectContract(@RequestParam Map<String, Object> data, @RequestParam MultipartFile file)
			throws Exception {

		logger.info("合同上传的附件 = " + file.getOriginalFilename());
		Gson gson = new GsonBuilder().setDateFormat(DateFormat.FULL).create();
		ProjectContract model = gson.fromJson(data.toString(), ProjectContract.class);

		if (model.getId() == 0) {
			model.setId(null);
			return projectService.insertProjectContract(file, model);
		} else
			return projectService.updateProjectContract(file, model);

	};

	@RequestMapping(value = "/updateProjectContractNoFile", method = RequestMethod.POST)
	public int updateProjectContractNoFile(@RequestBody ProjectContract data) throws Exception {

		if (data.getId() == 0) {
			data.setId(null);
			return projectService.insertProjectContract(data);
		} else
			return projectService.updateProjectContract(data);
	};

	@RequestMapping(value = "/deleteProjectContract", method = RequestMethod.POST)
	public int deleteProjectContract(@RequestBody ProjectContract data) {

		return projectService.deleteProjectContract(data.getId());
	};

	/////////

	@RequestMapping(value = "/selectProjectGroupTreeList", method = RequestMethod.POST)
	public List<TreeNode> selectProjectGroupTreeList(@RequestBody ProjectGroup data) throws Exception {

		List<ProjectGroup> groupList = projectService.selectProjectGroupList(data.getProjectId());

		List<TreeNode> groupTreeNodeList = new ArrayList<TreeNode>();
		for (ProjectGroup group : groupList) {
			TreeNode treeNode = new TreeNode(group.getId(), group.getGroupName(), group.getParentId(), group);
			groupTreeNodeList.add(treeNode);
		}
		List<TreeNode> groupTreeList = Utils.builderTree(groupTreeNodeList);

		return groupTreeList;
	}

	@RequestMapping(value = "/updateProjectGroup", method = RequestMethod.POST)
	public int updateProjectGroup(@RequestBody ProjectGroup data) throws Exception {

		if (data.getId() == null)
			return projectService.insertProjectGroup(data);
		else
			return projectService.updateProjectGroup(data);
	};

	@RequestMapping(value = "/deleteProjectGroup", method = RequestMethod.POST)
	public int deleteProjectGroup(@RequestBody ProjectGroup data) {

		return projectService.deleteProjectGroup(data.getId());
	};

	///////
	@RequestMapping(value = "/selectProjectStageList", method = RequestMethod.POST)
	public List<ProjectTask> selectProjectStageList(@RequestBody ProjectTask data) throws Exception {

		List<ProjectTask> projectStageList = projectService.selectProjectStageList(data.getProjectId());
		return projectStageList;
	}

	@RequestMapping(value = "/updateProjectStage", method = RequestMethod.POST)
	public int updateProjectStage(@RequestBody ProjectTask data) throws Exception {

		if (data.getId() == null)
			return projectService.insertProjectStage(data);
		else
			return projectService.updateProjectStage(data);
	};

	@RequestMapping(value = "/deleteProjectStage", method = RequestMethod.POST)
	public int deleteProjectStage(@RequestBody ProjectTask data) {

		return projectService.deleteProjectStage(data.getId());
	};

	@RequestMapping(value = "/selectGroupEmpList", method = RequestMethod.POST)
	public List<Employee> selectGroupEmpList(@RequestBody ProjectStaff data) throws Exception {

		List<Employee> projectStaffList = projectService.selectGroupEmpList(data.getGroupId());
		return projectStaffList;
	}

	// 项目中所有可用的人员（不一定全部用上） - available
	@RequestMapping(value = "/selectAvailableProjectEmployeeList", method = RequestMethod.POST)
	public List<Employee> selectAvailableProjectEmployeeList(@RequestBody Project data) throws Exception {

		List<Employee> employeeList = projectService.selectAvailableProjectEmployeeList(data.getId());
		return employeeList;
	}

	// 项目中所有可用的人员（按公司分组） - available
	@RequestMapping(value = "/selectAvailableGroupProjectEmployeeList", method = RequestMethod.POST)
	public List<Map<String, Object>> selectAvailableGroupProjectEmployeeList(@RequestBody Project data)
			throws Exception {

		List<Map<String, Object>> employeeList = projectService.selectAvailableGroupProjectEmployeeList(data.getId());
		return employeeList;
	}

	// 项目中已经分配的人员
	@RequestMapping(value = "/selectProjectEmployeeList", method = RequestMethod.POST)
	public List<Employee> selectProjectEmployeeList(@RequestBody Project data) throws Exception {

		List<Employee> staffList = projectService.selectProjectEmployeeList(data.getId());
		return staffList;
	}

	// 项目中已经分配的人员
	@RequestMapping(value = "/selectEmployeeList", method = RequestMethod.POST)
	public List<Employee> selectEmployeeList() throws Exception {

		List<Employee> staffList = projectService.selectEmployeeList();
		return staffList;
	}

	// 甲方项目中已经分配的人员
	@RequestMapping(value = "/selectOwnerProjectEmpList", method = RequestMethod.POST)
	public List<Employee> selectOwnerProjectEmpList(@RequestBody Project data) throws Exception {

		List<Employee> staffList = projectService.selectOwnerProjectEmpList(data.getId());
		return staffList;
	}

	@RequestMapping(value = "/updateProjectStaffs", method = RequestMethod.POST)
	public int updateProjectStaffs(@RequestBody Map<String, Object> staffListMap) throws Exception {

		return projectService.updateProjectStaffs(staffListMap);
	}

	@RequestMapping(value = "/selectMyProjectList", method = RequestMethod.POST)
	public List<Project> selectMyProjectList(@RequestBody Employee data) throws Exception {

		List<Project> myProjectList = projectService.selectMyProjectList(data.getCode());
		return myProjectList;
	}

	// 项目中已经分配的人员
	@RequestMapping(value = "/selectProjectLeaderList", method = RequestMethod.POST)
	public List<Employee> selectProjectLeaderList(@RequestBody Project data) throws Exception {

		List<Employee> staffList = projectService.selectProjectLeaderList(data.getId());
		return staffList;
	}

}
