package com.idata.gtd.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata.gtd.entity.ProjectStaff;
import com.idata.gtd.service.LoginService;

@RestController
@CrossOrigin
public class LoginAction {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ProjectStaff login(@RequestBody ProjectStaff staff) {
		ProjectStaff loginUser = loginService.login(staff);
		return loginUser;
	}

}
