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

import com.idata.gtd.dao.DepartmentEmployeeMapper;
import com.idata.gtd.dao.ProjectContractMapper;
import com.idata.gtd.dao.ProjectGroupMapper;
import com.idata.gtd.dao.ProjectMapper;
import com.idata.gtd.dao.ProjectStaffMapper;
import com.idata.gtd.dao.ProjectStageMapper;
import com.idata.gtd.dao.VendorEmployeeMapper;
import com.idata.gtd.dao.VendorMapper;
import com.idata.gtd.entity.DepartmentEmployee;
import com.idata.gtd.entity.Document;
import com.idata.gtd.entity.Project;
import com.idata.gtd.entity.ProjectContract;
import com.idata.gtd.entity.ProjectGroup;
import com.idata.gtd.entity.ProjectStaff;
import com.idata.gtd.entity.ProjectStage;
import com.idata.gtd.entity.Vendor;
import com.idata.gtd.entity.VendorEmployee;

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
	private ProjectStageMapper projectStageDao;
	@Autowired
	private ProjectStaffMapper projectStaffDao;
	@Autowired
	private DocumentService documentService;
	///
	@Autowired 
	private DepartmentEmployeeMapper depEmpDao;
	@Autowired
	private VendorMapper vendorDao;
	@Autowired
	private VendorEmployeeMapper venderEmpDao;

	public List<Project> selectProjectList() {

		logger.info("查询项目");
		return projectDao.selectProjectList();
	}

	public int insertProject(Project row) {

		return projectDao.insertProject(row);
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
		documentService.deleteDocuments(doc); //TODO 没有删除文件，只是删除了记录
		
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

	public int deleteProjectGroup(Integer id) {

		return projectGroupDao.deleteProjectGroupByPK(id);
	}

	public List<ProjectStage> selectProjectStageList(Integer projectId) {

		return projectStageDao.selectProjectStageList(projectId);
	}

	public int insertProjectStage(ProjectStage data) {

		return projectStageDao.insertProjectStage(data);
	}

	public int updateProjectStage(ProjectStage data) {

		return projectStageDao.updateProjectStage(data);
	}

	public int deleteProjectStage(Integer id) {

		return projectStageDao.deleteProjectStageByPK(id);
	}

	public List<ProjectStaff> selectProjectStaffList(Integer groupId) {
		
		return projectStaffDao.selectProjectStaffList(groupId);
	}

	public List<Map<String,Object>> selectEmployeeList(Integer projectId) { 
		
		List<Map<String,Object>> staffList = new ArrayList<Map<String,Object>>();
		//查询业主的员工并转换格式 => projectStaff
		List<DepartmentEmployee> depEmpList = depEmpDao.selectAllDepEmpList();
		List<ProjectStaff> staffList_dep = new ArrayList<ProjectStaff>();
		for(DepartmentEmployee emp:depEmpList){
			ProjectStaff staff  = new ProjectStaff();
			
			staff.setEmpId(emp.getId());
			staff.setIsVendor(0);
			staff.setPassword("12345678");
			staff.setStaffEmail(emp.getEmpName());
			staff.setStaffMobile(emp.getEmpMobile());
			staff.setStaffName(emp.getEmpName());
			
			staffList_dep.add(staff);
		}
		//整理成前端所需要的格式
		Map<String,Object> m0 = new LinkedHashMap<String,Object>();
		m0.put("branch", "本机构员工");
		m0.put("staffList", staffList_dep);
		staffList.add(m0);
		
		// 根据合同查询对应的供应商，根据供应商分别查询员工并整理格式 => projectStaff
		List<VendorEmployee> venderEmpList = venderEmpDao.selectVendorEmployeeListByProject(projectId);
		List<Vendor> projectVendorList = vendorDao.selectVendorListByProject(projectId);
		for(Vendor vendor:projectVendorList){
			List<ProjectStaff> vendorStaffList = new ArrayList<ProjectStaff>();
			//填充并转换 => projectStaff
			for(VendorEmployee emp:venderEmpList){
				if(emp.getVendorId().equals(vendor.getId())){
					ProjectStaff staff  = new ProjectStaff();
					staff.setEmpId(emp.getId());
					staff.setIsVendor(1);
					staff.setPassword("12345678");
					staff.setStaffEmail(emp.getEmpName());
					staff.setStaffMobile(emp.getEmpMobile());
					staff.setStaffName(emp.getEmpName());	
					vendorStaffList.add(staff);
				}
			}
			Map<String,Object> m1 = new LinkedHashMap<String,Object>();
			m1.put("branch", vendor.getVendorName());
			m1.put("staffList", vendorStaffList);
			staffList.add(m1);
		}
		
		return staffList;
	}

}
