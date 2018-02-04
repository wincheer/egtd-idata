package com.idata.gtd.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idata.gtd.common.GTDRole;
import com.idata.gtd.common.LogEvent;
import com.idata.gtd.common.LogTarget;
import com.idata.gtd.entity.Employee;
import com.idata.gtd.service.LogService;
import com.idata.gtd.service.LoginService;

@RestController
@CrossOrigin
public class LoginAction {

	@Autowired
	private LoginService loginService;
	@Autowired
	private LogService logService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Map<String,Object> login(@RequestBody Map<String, Object> param) {

		Gson gson = new GsonBuilder().create();
		Employee model = gson.fromJson(param.get("model").toString(), Employee.class);

		Employee loginUser = loginService.login(model);
		
		//查询我的角色
		Map<String,Object> map = new HashMap<String ,Object>();
		if(loginUser!=null) {
			List<Map<String,Object>> myRoles = loginService.selectMyRoles(loginUser.getId());
			map.put("loginUser", loginUser);
			if(myRoles.size()== 0) {
				Map<String,Object> _speciaRole = new HashMap<String,Object>();
				_speciaRole.put("project_id", 0);
				if(loginUser.getCode().toLowerCase().equals("o0")) 
					_speciaRole.put("group_role", GTDRole.ADMIN); //myRoles.add(GTDRole.ADMIN); //超级用户 
				else
					_speciaRole.put("group_role", GTDRole.IDLER); //myRoles.add(GTDRole.IDLER); //非项目用户
				myRoles.add(_speciaRole);
			}
			map.put("myRoles", myRoles);
		}
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

		return map;
	}

}
