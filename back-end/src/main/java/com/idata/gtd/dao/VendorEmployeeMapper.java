package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.VendorEmployee;

public interface VendorEmployeeMapper {

	int deleteVendorEmployeeByPK(Integer id);

	int insertVendorEmployee(VendorEmployee record);

	VendorEmployee selectVendorEmployeeByPK(Integer id);

	int updateVendorEmployee(VendorEmployee record);

	int deleteVendorEmps(Integer vendorId);

	List<VendorEmployee> selectVendorEmployeeList(Integer vendorId);

	List<VendorEmployee> selectVendorEmployeeListByProject(Integer projectId); 
	
}