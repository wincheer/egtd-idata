package com.idata.gtd.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.idata.gtd.entity.ParamKey;
import com.idata.gtd.entity.ParamValue;
import com.idata.gtd.service.ParamService;

@RestController
@CrossOrigin
public class ParamAction {

	@Autowired
	private ParamService paramService;

	@RequestMapping(value = "/selectParamKeyList", method = RequestMethod.POST)
	public List<ParamKey> selectParamKeyList() {

		return paramService.selectParamKeyList();
	};

	@RequestMapping(value = "/updateParamKey", method = RequestMethod.POST)
	public int updateParamKey(@RequestBody ParamKey paramKey) {

		if (paramKey.getId() == null)
			return paramService.insertParamKey(paramKey);
		else
			return paramService.updateParamKey(paramKey);
	};

	@RequestMapping(value = "/delParamKey", method = RequestMethod.POST)
	public int delParamKey(@RequestBody ParamKey paramKey) {

		return paramService.delParamKey(paramKey.getId());
	};
	
	@RequestMapping(value = "/selectParamValueList", method = RequestMethod.POST)
	public List<ParamValue> selectParamValueList(@RequestBody ParamKey paramKey) {

		return paramService.selectParamValueList(paramKey);
	};
	
	@RequestMapping(value = "/updateParamValue", method = RequestMethod.POST)
	public int updateParamValue(@RequestBody ParamValue paramValue) {

		if (paramValue.getId() == null)
			return paramService.insertParamValue(paramValue);
		else
			return paramService.updateParamValue(paramValue);
	};

	@RequestMapping(value = "/delParamValue", method = RequestMethod.POST)
	public int delParamValue(@RequestBody ParamValue paramKey) {

		return paramService.delParamValue(paramKey.getId());
	};

}
