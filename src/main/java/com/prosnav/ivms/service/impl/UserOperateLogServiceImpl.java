package com.prosnav.ivms.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prosnav.ivms.model.UserOperateLog;
import com.prosnav.ivms.repository.ivm.UserOperateLogRepository;
import com.prosnav.ivms.service.UserOperateLogService;

@Service
public class UserOperateLogServiceImpl extends AbstractService implements UserOperateLogService {
	@Autowired
	private UserOperateLogRepository userOperateLogRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean save(UserOperateLog userOperateLog) {
		try{
			userOperateLog.setCrt(new Date());
			userOperateLog.setId(userOperateLogRepository.nextId(idg));
			userOperateLogRepository.save(userOperateLog);
			return true;
		}catch(Exception e) {
			logger.error("save user_operate_log fail:"+e.getMessage());
			return false;
		}
	}

}
