package com.idata.gtd.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.idata.gtd.common.DocCat;
import com.idata.gtd.common.TreeNode;
import com.idata.gtd.common.Utils;
import com.idata.gtd.dao.EmployeeMapper;
import com.idata.gtd.dao.ProjectContractMapper;
import com.idata.gtd.dao.ProjectGroupMapper;
import com.idata.gtd.dao.ProjectMapper;
import com.idata.gtd.dao.ProjectStaffMapper;
import com.idata.gtd.dao.ProjectTaskMapper;
import com.idata.gtd.dao.TplGroupMapper;
import com.idata.gtd.dao.TplStageMapper;
import com.idata.gtd.dao.VendorMapper;
import com.idata.gtd.entity.Document;
import com.idata.gtd.entity.Employee;
import com.idata.gtd.entity.Project;
import com.idata.gtd.entity.ProjectContract;
import com.idata.gtd.entity.ProjectGroup;
import com.idata.gtd.entity.ProjectStaff;
import com.idata.gtd.entity.ProjectTask;
import com.idata.gtd.entity.TplGroup;
import com.idata.gtd.entity.TplStage;
import com.idata.gtd.entity.Vendor;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 业主部门的服务层<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 上午11:43:02 | 创建
 */
@Service
@Transactional
public class ProjectService {

	private final static Logger logger = LoggerFactory.getLogger(ProjectService.class);

	@Autowired
	private ProjectMapper projectDao;
	@Autowired
	private ProjectContractMapper projectContractDao;
	@Autowired
	private ProjectGroupMapper groupDao;
	@Autowired
	private ProjectTaskMapper taskDao;
	@Autowired
	private ProjectStaffMapper projectStaffDao;
	@Autowired
	private DocumentService documentService;
	@Autowired
	private VendorMapper vendorDao;
	@Autowired
	private EmployeeMapper empDao;
	@Autowired
	private TplGroupMapper tplGroupDao;
	@Autowired
	private TplStageMapper tplStageDao;

	public Project selectProject(Integer projectId) {

		return projectDao.selectProjectByPK(projectId);
	}

	public List<Project> selectProjectList() {

		logger.info("查询项目");
		return projectDao.selectProjectList();
	}

	public int insertProject(Project row) {

		projectDao.insertProject(row);
		return row.getId();
	}

	public int updateProject(Project row) {

		return projectDao.updateProject(row);
	}

	public int deleteProject(Integer projectId) {

		projectDao.deleteProjectByPK(projectId);
		// TODO 同时删除当前项目下的相关内容

		return 0;
	}

	public int insertProjectContract(MultipartFile file, ProjectContract data) throws Exception {

		projectContractDao.insertProjectContract(data);

		Document doc = new Document();
		doc.setName(file.getOriginalFilename());
		doc.setFileName(String.valueOf(System.currentTimeMillis()));
		doc.setBelongTo("contract");
		doc.setSourceId(data.getId());
		doc.setCategory(DocCat.DOCUMENT.getValue());

		documentService.upload(file, doc);

		return 0;
	}

	public int insertProjectContract(ProjectContract data) {

		return projectContractDao.insertProjectContract(data);
	}

	public int updateProjectContract(MultipartFile file, ProjectContract data) throws Exception {

		projectContractDao.updateProjectContract(data);

		Document doc = new Document();
		doc.setName(file.getOriginalFilename());
		doc.setFileName(String.valueOf(System.currentTimeMillis()));
		doc.setBelongTo("contract");
		doc.setSourceId(data.getId());
		doc.setCategory(DocCat.DOCUMENT.getValue());

		documentService.upload(file, doc);
		return 0;
	}

	public int updateProjectContract(ProjectContract data) {

		return projectContractDao.updateProjectContract(data);
	}

	public int deleteProjectContract(Integer id) {

		projectContractDao.deleteProjectContractByPK(id);

		Document doc = new Document();
		doc.setBelongTo("contract");
		doc.setSourceId(id);
		documentService.deleteDocuments(doc); // TODO 没有删除文件，只是删除了记录

		return 0;
	}

	public List<ProjectContract> selectProjectContractList(Integer projectId) {

		return projectContractDao.selectProjectContractList(projectId);
	}

	public List<ProjectGroup> selectProjectGroupList(Integer projectId) {

		return groupDao.selectProjectGroupList(projectId);
	}

	public int insertProjectGroup(ProjectGroup data) {

		return groupDao.insertProjectGroup(data);
	}

	public int updateProjectGroup(ProjectGroup data) {

		return groupDao.updateProjectGroup(data);
	}

	public int deleteProjectGroup(Integer groupId) {

		// 删除当前组
		groupDao.deleteProjectGroupByPK(groupId);
		// 删除当前组下面的项目组成员
		projectStaffDao.deleteStaffsByGroupId(groupId);

		return 1;
	}

	public List<ProjectTask> selectProjectStageList(Integer projectId) {

		return taskDao.selectProjectStageList(projectId);
	}

	public int insertProjectStage(ProjectTask data) {

		taskDao.insertProjectTask(data);
		return data.getId();
	}

	public int updateProjectStage(ProjectTask data) {

		taskDao.updateProjectTask(data);
		return data.getId();
	}

	public int deleteProjectStage(Integer id) {

		return taskDao.deleteProjectTaskByPK(id);
	}

	public List<Employee> selectGroupEmpList(Integer groupId) {

		return empDao.selectGroupEmpList(groupId);
	}

	public List<Employee> selectAvailableProjectEmployeeList(Integer projectId) {

		List<Employee> projectEmpList = empDao.selectAvailableProjectEmployeeList(projectId);
		return projectEmpList;
	}

	public List<Map<String, Object>> selectAvailableGroupProjectEmployeeList(Integer projectId) {

		List<Map<String, Object>> employeeGroupList = new ArrayList<Map<String, Object>>();
		// 查询业主的员工并转换格式 => projectStaff
		List<Employee> depEmpList = empDao.selectAllDepEmpList();
		// 整理成前端所需要的格式
		Map<String, Object> m0 = new LinkedHashMap<String, Object>();
		m0.put("branch", "本机构员工");
		m0.put("employeeList", depEmpList);
		employeeGroupList.add(m0);

		// 根据合同查询对应的供应商，根据供应商分别查询员工并整理格式 => projectStaff
		List<Employee> venderEmpList = empDao.selectVendorEmployeeListByProject(projectId);
		List<Vendor> projectVendorList = vendorDao.selectVendorListByProject(projectId);
		for (Vendor vendor : projectVendorList) {
			List<Employee> vendorEmployeeList = new ArrayList<Employee>();
			// 填充
			for (Employee emp : venderEmpList) {
				if (emp.getOrgId().equals(vendor.getId())) {
					vendorEmployeeList.add(emp);
				}
			}
			Map<String, Object> m1 = new LinkedHashMap<String, Object>();
			m1.put("branch", vendor.getVendorName());
			m1.put("employeeList", vendorEmployeeList);
			employeeGroupList.add(m1);
		}

		return employeeGroupList;
	}

	public int updateProjectStaffs(Map<String, Object> map) {

		int groupId = (Integer) map.get("groupId");
		// 删除组内所有成员
		projectStaffDao.deleteStaffsByGroupId(groupId);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> _staffList = (List<Map<String, Object>>) map.get("staffList");
		@SuppressWarnings("unused")
		List<ProjectStaff> staffList = new ArrayList<ProjectStaff>();
		// 添加所有成员 TODO 改为批量插入
		for (Map<String, Object> _map : _staffList) {
			ProjectStaff staff = new ProjectStaff();
			staff.setGroupId(groupId);
			staff.setEmpId((Integer) _map.get("id"));

			projectStaffDao.insertStaff(staff);
		}

		return 1;
	}

	public List<Employee> selectProjectEmployeeList(Integer projectId) {

		return empDao.selectProjectEmployeeList(projectId);
	}

	public List<Project> selectMyProjectList(String empCode) {

		if (empCode.equals("o0")) // 超级用户的唯一标记是o0 (欧零)
			return projectDao.selectProjectList();
		else
			return projectDao.selectMyProjectList(Integer.parseInt(empCode.substring(1)));
	}

	public List<Employee> selectEmployeeList() {

		return empDao.selectEmployeeList();
	}

	public List<Employee> selectOwnerProjectEmpList(Integer projectId) {

		return empDao.selectOwnerProjectEmpList(projectId);
	}

	public Employee selectProjectLeader(Integer projectId) {

		return empDao.selectProjectLeader(projectId);
	}

	// 从模板创建项目
	public int createProjectFromTpl(Integer tplId, String projectName, Integer empId) {

		// 1 、创建项目
		Project project = new Project();
		project.setProjectName(projectName);
		project.setAuditState("init");
		project.setOwnerId(empId);
		project.setActorStaffId(empId);
		projectDao.insertProject(project);
		// 2、创建项目组：查询模板中的项目组，迭代新建
		List<TplGroup> tplGroupList = tplGroupDao.selectTplGroupList(tplId);
		List<TreeNode> tplGroupTreeNodeList = new ArrayList<TreeNode>();
		for (TplGroup group : tplGroupList) {
			TreeNode treeNode = new TreeNode(group.getId(), group.getGroupName(), group.getParentId(), group);
			tplGroupTreeNodeList.add(treeNode);
		}
		List<TreeNode> tplGroupTreeList = Utils.builderTree(tplGroupTreeNodeList);
		for (TreeNode node : tplGroupTreeList) {
			traverseTree(node, project.getId(), "group", empId);
		}
		// 3、创建项目阶段(有可能包含一级任务)
		List<TplStage> tplStageList = tplStageDao.selectTplStageList(tplId);
		List<TreeNode> tplStageTreeNodeList = new ArrayList<TreeNode>();
		for (TplStage stage : tplStageList) {
			TreeNode treeNode = new TreeNode(stage.getId(), stage.getStageName(), stage.getParentId(), stage);
			tplStageTreeNodeList.add(treeNode);
		}
		List<TreeNode> tplStageTreeList = Utils.builderTree(tplStageTreeNodeList);
		for (TreeNode node : tplStageTreeList) {
			traverseTree(node, project.getId(), "task", empId);
		}

		return project.getId();
	}

	// 通过递归遍历指定节点的子节点
	private void traverseTree(TreeNode node, int projectId, String objName, Integer empId) {

		int parentId = 0;

		if (objName.equals("task")) { // 转化为(任务)阶段节点
			ProjectTask task = new ProjectTask();
			task.setProjectId(projectId);
			task.setText(node.getLabel());
			task.setParent(node.getParentId());
			task.setState(0);
			task.setCreate_date(new Date());
			task.setActorStaffId(empId);

			taskDao.insertProjectTask(task);
			parentId = task.getId();

		} else if (objName.equals("group")) { // 转化为项目组节点
			ProjectGroup group = new ProjectGroup();
			group.setProjectId(projectId);
			group.setParentId(node.getParentId());
			group.setGroupName(node.getLabel());
			group.setGroupRole(((TplGroup) node.getData()).getGroupRole());

			groupDao.insertProjectGroup(group);

			parentId = group.getId();
		}

		// 递归调用
		if (node.getChildren() != null) {
			for (TreeNode _node : node.getChildren()) {
				_node.setParentId(parentId);
				traverseTree(_node, projectId, objName, empId);
			}
		}
	}

}
