package com.prosnav.ivms.core;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.prosnav.ivms.model.DBOperateLog;
import com.prosnav.ivms.service.DBOperateLogService;
import com.prosnav.ivms.util.CommonUtil;

@Aspect
@Component
public class DBOpearteLog {
	@Autowired
	private DBOperateLogService dbOperateLogService;
	
	@Pointcut("execution(* com.prosnav.ivms.service..*.*(..))")
	public void logDBOperate() {
	}
	
	@Before("logDBOperate() && args(obj)")
	public void logDBOperate(JoinPoint joinPoint, Object obj) {
		String methodName = joinPoint.getSignature().getName();
		String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
		if(methodName != null && (methodName.indexOf("add") != -1 || methodName.indexOf("update") != -1 )) {
			DBOperateLog dbOperateLog = new DBOperateLog();
			String method = declaringTypeName + "." + methodName;
			dbOperateLog.setMethod(method);
			dbOperateLog.setHistoryRecord(joinPoint.getArgs()[0].toString());
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
			User user = (User) request.getAttribute("user");
			dbOperateLog.setUserId(user.get_id());
			dbOperateLog.setUsername(user.getUsername());
			dbOperateLog.setRealName(user.getRealName());
			dbOperateLogService.save(dbOperateLog);
		}
	}
}
