package com.idata.gtd.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.gtd.dao.ProjectStaffMapper;
import com.idata.gtd.entity.ProjectStaff;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 登录restful接口<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 上午11:46:27 | 创建
 */
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