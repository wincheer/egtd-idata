package com.idata.gtd.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata.gtd.entity.Employee;
import com.idata.gtd.entity.Vendor;
import com.idata.gtd.service.VendorService;

@RestController
@CrossOrigin
public class VendorAction {

	@Autowired
	private VendorService vendorService;

	@RequestMapping(value = "/selectVendorList", method = RequestMethod.POST)
	public List<Vendor> selectVendorList() {

		return vendorService.selectVendorList();
	};

	@RequestMapping(value = "/updateVendor", method = RequestMethod.POST)
	public int updateVendor(@RequestBody Vendor vendor) {

		if (vendor.getId() == null)
			return vendorService.insertVendor(vendor);
		else
			return vendorService.updateVendor(vendor);
	};

	@RequestMapping(value = "/deleteVendor", method = RequestMethod.POST)
	public int deleteVendor(@RequestBody Vendor vendor) {

		return vendorService.deleteVendor(vendor.getId()); 
	};
	
	@RequestMapping(value = "/selectVendorEmpList", method = RequestMethod.POST)
	public List<Employee> selectVendorEmpList(@RequestBody Vendor vendor) {

		return vendorService.selectVendorEmpList(vendor.getId());
	};
	
	@RequestMapping(value = "/updateVendorEmp", method = RequestMethod.POST)
	public int updateVendorEmp(@RequestBody Employee vendorEmp) {

		if (vendorEmp.getId() == null)
			return vendorService.insertVendorEmp(vendorEmp);
		else
			return vendorService.updateVendorEmp(vendorEmp);
	};

	@RequestMapping(value = "/deleteVendorEmp", method = RequestMethod.POST)
	public int delVendorEmp(@RequestBody Employee vendorEmp) {

		return vendorService.deleteVendorEmp(vendorEmp.getId());
	};

}
