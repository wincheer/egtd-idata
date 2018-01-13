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

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 系统参数配置UI访问restful接口<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 上午11:46:54 | 创建
 */
@Service
@Transactional
public class ParamService {

	private final static Logger logger = LoggerFactory.getLogger(ParamService.class);

	@Autowired
	private ParamKeyMapper paramKeyDao;
	@Autowired
	private ParamValueMapper paramValueDao;

	public List<ParamKey> selectParamKeyList() {

		List<ParamKey> paramKeyList = paramKeyDao.selectParamKeyList();
		logger.info("查询参数列表...");

		return paramKeyList;
	}

	public int delParamKey(Integer paramKeyId) {

		paramKeyDao.deleteParamKeyByPK(paramKeyId);
		paramValueDao.deleteParamValues(paramKeyId);
		
		return 0;
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

		List<ParamValue> paramValueList = paramValueDao.selectParamValueList(paramKeyId);
		return paramValueList;
	}

	public int insertParamValue(ParamValue paramValue) {
		
		return paramValueDao.insertParamValue(paramValue);
	}

	public int updateParamValue(ParamValue paramValue) {
		
		return paramValueDao.updateParamValue(paramValue);
	}

	public int delParamValue(Integer id) {
		
		return paramValueDao.deleteParamValueByPK(id);
	}

}
