package com.idata.gtd.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.idata.gtd.dao.EmployeeMapper;
import com.idata.gtd.dao.ProjectContractMapper;
import com.idata.gtd.dao.ProjectGroupMapper;
import com.idata.gtd.dao.ProjectMapper;
import com.idata.gtd.dao.ProjectStaffMapper;
import com.idata.gtd.dao.ProjectTaskMapper;
import com.idata.gtd.dao.VendorMapper;
import com.idata.gtd.entity.Document;
import com.idata.gtd.entity.Employee;
import com.idata.gtd.entity.Project;
import com.idata.gtd.entity.ProjectContract;
import com.idata.gtd.entity.ProjectGroup;
import com.idata.gtd.entity.ProjectStaff;
import com.idata.gtd.entity.ProjectTask;
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
	private ProjectGroupMapper projectGroupDao;
	@Autowired
	private ProjectTaskMapper projectStageDao;
	@Autowired
	private ProjectStaffMapper projectStaffDao;
	@Autowired
	private DocumentService documentService;
	///
	@Autowired
	private EmployeeMapper empDao;
	@Autowired
	private VendorMapper vendorDao;

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

		return projectGroupDao.selectProjectGroupList(projectId);
	}

	public int insertProjectGroup(ProjectGroup data) {

		return projectGroupDao.insertProjectGroup(data);
	}

	public int updateProjectGroup(ProjectGroup data) {

		return projectGroupDao.updateProjectGroup(data);
	}

	public int deleteProjectGroup(Integer groupId) {

		// 删除当前组
		projectGroupDao.deleteProjectGroupByPK(groupId);
		// 删除当前组下面的项目组成员
		projectStaffDao.deleteStaffsByGroupId(groupId);

		return 1;
	}

	public List<ProjectTask> selectProjectStageList(Integer projectId) {

		return projectStageDao.selectProjectTaskList(projectId);
	}

	public int insertProjectStage(ProjectTask data) {

		projectStageDao.insertProjectTask(data);
		return data.getId();
	}

	public int updateProjectStage(ProjectTask data) {

		projectStageDao.updateProjectTask(data);
		return data.getId();
	}

	public int deleteProjectStage(Integer id) {

		return projectStageDao.deleteProjectTaskByPK(id);
	}

	public List<ProjectStaff> selectGroupStaffList(Integer groupId) {

		return projectStaffDao.selectGroupStaffList(groupId);
	}

	public List<Map<String, Object>> selectAvailableProjectStaffList(Integer projectId) {

		List<Map<String, Object>> staffList = new ArrayList<Map<String, Object>>();
		// 查询业主的员工并转换格式 => projectStaff
		List<Employee> depEmpList = empDao.selectAllDepEmpList();
		List<ProjectStaff> staffList_dep = new ArrayList<ProjectStaff>();
		for (Employee emp : depEmpList) {
			ProjectStaff staff = new ProjectStaff();

			staff.setEmpId(emp.getId());
			staff.setIsVendor(0);
			staff.setPassword("12345678");
			staff.setStaffEmail(emp.getEmpEmail());
			staff.setStaffMobile(emp.getEmpMobile());
			staff.setStaffName(emp.getEmpName());
			staff.setId(emp.getId()); // -- 唯一标识，业主的用正数，供应商负数
			staff.setCode("o" + emp.getId()); // ownder o+empId

			staffList_dep.add(staff);
		}
		// 整理成前端所需要的格式
		Map<String, Object> m0 = new LinkedHashMap<String, Object>();
		m0.put("branch", "本机构员工");
		m0.put("staffList", staffList_dep);
		staffList.add(m0);

		// 根据合同查询对应的供应商，根据供应商分别查询员工并整理格式 => projectStaff
		List<Employee> venderEmpList = empDao.selectVendorEmployeeListByProject(projectId);
		List<Vendor> projectVendorList = vendorDao.selectVendorListByProject(projectId);
		for (Vendor vendor : projectVendorList) {
			List<ProjectStaff> vendorStaffList = new ArrayList<ProjectStaff>();
			// 填充并转换 => projectStaff
			for (Employee emp : venderEmpList) {
				if (emp.getOrgId().equals(vendor.getId())) {
					ProjectStaff staff = new ProjectStaff();
					staff.setEmpId(emp.getId());
					staff.setIsVendor(1);
					staff.setPassword("12345678");
					staff.setStaffEmail(emp.getEmpEmail());
					staff.setStaffMobile(emp.getEmpMobile());
					staff.setStaffName(emp.getEmpName());
					staff.setId(-1 * emp.getId()); // -- 唯一标识，业主的用正数，供应商负数
					staff.setCode("s" + emp.getId()); // supply s+empId

					vendorStaffList.add(staff);
				}
			}
			Map<String, Object> m1 = new LinkedHashMap<String, Object>();
			m1.put("branch", vendor.getVendorName());
			m1.put("staffList", vendorStaffList);
			staffList.add(m1);
		}

		return staffList;
	}

	public int updateProjectStaffs(Map<String, Object> map) {

		int groupId = (Integer) map.get("groupId");
		// 删除组内所有成员
		projectStaffDao.deleteStaffsByGroupId(groupId);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> _staffList = (List<Map<String, Object>>) map.get("staffList");
		@SuppressWarnings("unused")
		List<ProjectStaff> staffList = new ArrayList<ProjectStaff>();
		// 添加所有成员
		for (Map<String, Object> _map : _staffList) {
			ProjectStaff staff = new ProjectStaff();
			staff.setGroupId(groupId);
			staff.setEmpId((Integer) _map.get("empId"));
			staff.setId(null);
			staff.setIsVendor((Integer) _map.get("isVendor"));
			staff.setPassword((String) _map.get("password"));
			staff.setStaffEmail((String) _map.get("staffEmail"));
			staff.setStaffMobile((String) _map.get("staffMobile"));
			staff.setStaffName((String) _map.get("staffName"));
			if (staff.getIsVendor() == 1)
				staff.setCode("s" + staff.getEmpId());
			else
				staff.setCode("o" + staff.getEmpId());

			projectStaffDao.insertStaff(staff);

			Employee employee = new Employee();
			employee.setId(staff.getEmpId());
			employee.setCode(staff.getCode());
			empDao.updateEmployee(employee);
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
			return projectDao.selectMyProjectList(empCode);
	}

}
