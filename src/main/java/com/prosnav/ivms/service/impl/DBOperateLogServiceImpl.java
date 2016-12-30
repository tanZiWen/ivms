package com.prosnav.ivms.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosnav.ivms.model.DBOperateLog;
import com.prosnav.ivms.repository.ivm.DBOperateLogRepository;
import com.prosnav.ivms.service.DBOperateLogService;

@Service
public class DBOperateLogServiceImpl extends AbstractService implements DBOperateLogService{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DBOperateLogRepository dbOperateLogRepository;
	
	@Override
	public boolean save(DBOperateLog dbOperateLog) {
		try{
			dbOperateLog.setCrt(new Date());
			dbOperateLog.setId(dbOperateLogRepository.nextId(idg));
			dbOperateLogRepository.save(dbOperateLog);
			return true;
		}catch(Exception e) {
			logger.error("save db_operate_log fail:"+e.getMessage());
			return false;
		}
	}
	
}
