package com.idata.gtd.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.gtd.dao.EmployeeMapper;
import com.idata.gtd.dao.VendorMapper;
import com.idata.gtd.entity.Employee;
import com.idata.gtd.entity.Vendor;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 系统参数配置UI访问restful接口<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 上午11:46:54 | 创建
 */
@Service
@Transactional
public class VendorService {

	private final static Logger logger = LoggerFactory.getLogger(VendorService.class);

	@Autowired
	private VendorMapper vendorDao;
	@Autowired
	private EmployeeMapper empDao;

	public List<Vendor> selectVendorList(Vendor vendor) {

		List<Vendor> vendorList = vendorDao.selectVendorList(vendor);
		logger.info("查询供应商列表...");

		return vendorList;
	}

	public int deleteVendor(Integer vendorId) {

		vendorDao.deleteVendorByPK(vendorId);
		empDao.deleteVendorEmps(vendorId);
		
		return 0;
	}

	public int insertVendor(Vendor vendor) {

		return vendorDao.insertVendor(vendor);
	}

	public int updateVendor(Vendor vendor) {

		return vendorDao.updateVendor(vendor);
	}

	public List<Employee> selectVendorEmpList(Integer vendorId) {

		List<Employee> vendorEmployeeList = empDao.selectVendorEmployeeList(vendorId);
		return vendorEmployeeList;
	}

	public int insertVendorEmp(Employee vendorEmp) {
		
		vendorEmp.setPassword("123456");
		return empDao.insertEmployee(vendorEmp);
	}

	public int updateVendorEmp(Employee vendorEmp) {
		
		return empDao.updateEmployee(vendorEmp);
	}

	public int deleteVendorEmp(Integer empId) {
		
		return empDao.deleteEmployeeByPK(empId);
	}

}
