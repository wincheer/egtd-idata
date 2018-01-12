package com.idata.gtd.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.gtd.dao.ParamKeyMapper;
import com.idata.gtd.dao.ParamValueMapper;
import com.idata.gtd.entity.ParamKey;
import com.idata.gtd.entity.ParamValue;

@Service
@Transactional
public class ParamService {

	private final static Logger logger = LoggerFactory.getLogger(ParamService.class);

	@Autowired
	private ParamKeyMapper paramKeyDao;
	private ParamValueMapper paramValueDao;

	public List<ParamKey> selectParamKeyList() {

		List<ParamKey> paramKeyList = paramKeyDao.selectParamKeyList();
		logger.info("查询参数列表...");

		return paramKeyList;
	}

	public int delParamKey(Integer id) {

		return paramKeyDao.deleteParamKeyByPK(id);
	}

	public int insertParamKey(ParamKey paramKey) {

		logger.info("新增参数，" + paramKey.getParamKeyName());
		return paramKeyDao.insertParamKey(paramKey);
	}

	public int updateParamKey(ParamKey paramKey) {

		logger.info("删除参数，" + paramKey.getParamKeyName());
		return paramKeyDao.updateParamKey(paramKey);
	}

	public List<ParamValue> selectParamValueList(Integer paramKeyId) {

		return paramValueDao.selectParamValueList(paramKeyId);
	}

}
