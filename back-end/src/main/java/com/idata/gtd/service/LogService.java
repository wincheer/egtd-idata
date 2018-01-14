package com.idata.gtd.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata.gtd.common.LogEvent;
import com.idata.gtd.common.LogTarget;
import com.idata.gtd.dao.LogMapper;
import com.idata.gtd.entity.Log;

/**
 * <b>版权信息 :</b> 2018，广州智数信息科技有限公司<br/>
 * <b>功能描述 :</b> 登录restful接口<br/>
 * <b>版本历史 :</b> <br/>
 * 杨文清 | 2018年1月13日 上午11:46:27 | 创建
 */
@Service
@Transactional
public class LogService {

	private final static Logger logger = LoggerFactory.getLogger(LogService.class);
	@Autowired
	private LogMapper logDao;

	public int insertLog(Log log) {
		
		logger.info(log.getAction()+" - "+log.getTarget());
		return logDao.insertLog(log);
	}
	
	public int insertLog(Integer actor,LogEvent action,LogTarget target,String detail) {
		
		Log log = new Log();
		log.setActor(actor);
		log.setOpTime(new Date());
		log.setAction(action.toString());
		log.setTarget(target.toString());
		log.setDetail(detail);
		
		return logDao.insertLog(log);
	}

}
