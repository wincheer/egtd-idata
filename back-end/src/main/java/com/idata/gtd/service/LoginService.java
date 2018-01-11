package com.idata.gtd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.gtd.dao.ProjectStaffMapper;
import com.idata.gtd.entity.ProjectStaff;

@Service
@Transactional
public class LoginService {

	private final static Logger logger = LoggerFactory.getLogger(LoginService.class);
	@Autowired
	private ProjectStaffMapper staffDao;

	public ProjectStaff login(ProjectStaff staff) {
		ProjectStaff _staff = staffDao.login(staff);
		if (_staff != null)
			logger.info(_staff.getStaffName() + " is login ...... ");

		return _staff;
	}

}
