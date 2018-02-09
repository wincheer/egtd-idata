package com.idata.gtd.dao;

import java.util.List;

import com.idata.gtd.entity.Vendor;

public interface VendorMapper {

	int deleteVendorByPK(Integer id);
	
	int insertVendor(Vendor record);
	
	Vendor selectVendorByPK(Integer id);

	int updateVendor(Vendor record);

	List<Vendor> selectVendorList(Vendor record);

	List<Vendor> selectVendorListByProject(Integer projectId);
	
}