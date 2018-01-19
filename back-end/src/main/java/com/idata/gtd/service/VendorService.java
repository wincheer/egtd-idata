package com.idata.gtd.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.gtd.dao.VendorEmployeeMapper;
import com.idata.gtd.dao.VendorMapper;
import com.idata.gtd.entity.Vendor;
import com.idata.gtd.entity.VendorEmployee;

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
	private VendorEmployeeMapper vendorEmpDao;

	public List<Vendor> selectVendorList() {

		List<Vendor> vendorList = vendorDao.selectVendorList();
		logger.info("查询供应商列表...");

		return vendorList;
	}

	public int deleteVendor(Integer vendorId) {

		vendorDao.deleteVendorByPK(vendorId);
		vendorEmpDao.deleteVendorEmps(vendorId);
		
		return 0;
	}

	public int insertVendor(Vendor vendor) {

		return vendorDao.insertVendor(vendor);
	}

	public int updateVendor(Vendor vendor) {

		return vendorDao.updateVendor(vendor);
	}

	public List<VendorEmployee> selectVendorEmpList(Integer vendorId) {

		List<VendorEmployee> vendorEmployeeList = vendorEmpDao.selectVendorEmployeeList(vendorId);
		return vendorEmployeeList;
	}

	public int insertVendorEmp(VendorEmployee vendorEmp) {
		
		return vendorEmpDao.insertVendorEmployee(vendorEmp);
	}

	public int updateVendorEmp(VendorEmployee vendorEmp) {
		
		return vendorEmpDao.updateVendorEmployee(vendorEmp);
	}

	public int deleteVendorEmp(Integer empId) {
		
		return vendorEmpDao.deleteVendorEmployeeByPK(empId);
	}

}