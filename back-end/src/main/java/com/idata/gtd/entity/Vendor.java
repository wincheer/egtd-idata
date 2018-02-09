package com.idata.gtd.entity;

public class Vendor {

	private Integer id;
	private String vendorName;
	private String vendorFullName;
	private String contactName;
	private String contactMobile;
	private String vendorCategory; //供应商类别：监理supervisor，其它供应商builder

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorFullName() {
		return vendorFullName;
	}

	public void setVendorFullName(String vendorFullName) {
		this.vendorFullName = vendorFullName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getVendorCategory() {
		return vendorCategory;
	}

	public void setVendorCategory(String vendorCategory) {
		this.vendorCategory = vendorCategory;
	}
}