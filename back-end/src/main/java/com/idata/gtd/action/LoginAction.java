package com.idata.gtd.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idata.gtd.common.LogEvent;
import com.idata.gtd.common.LogTarget;
import com.idata.gtd.entity.ProjectStaff;
import com.idata.gtd.service.LogService;
import com.idata.gtd.service.LoginService;

@RestController
@CrossOrigin
public class LoginAction {

	@Autowired
	private LoginService loginService;
	@Autowired
	private LogService logService;

	// @RequestMapping(value = "/login", method = RequestMethod.POST)
	// public ProjectStaff login(@RequestBody ProjectStaff staff) {
	//
	// ProjectStaff loginUser = loginService.login(staff);
	// return loginUser;
	// }

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ProjectStaff login(@RequestBody Map<String, Object> param) {

		Gson gson = new GsonBuilder().create();
		ProjectStaff model = gson.fromJson(param.get("model").toString(), ProjectStaff.class);

		ProjectStaff loginUser = loginService.login(model);

		// 记录日志
		Integer actor;
		String detail;
		if (loginUser == null) {
			actor = 0;
			detail = "失败";
		} else {
			actor = loginUser.getId();
			detail = "成功";
		}
		logService.insertLog(actor, LogEvent.LOGIN, LogTarget.APP, detail);

		return loginUser;
	}

}
