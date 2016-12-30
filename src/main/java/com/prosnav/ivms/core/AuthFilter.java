package com.prosnav.ivms.core;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.prosnav.ivms.model.UserOperateLog;
import com.prosnav.ivms.service.UserOperateLogService;
import com.prosnav.ivms.service.impl.UserOperateLogServiceImpl;
import com.prosnav.ivms.util.CommonUtil;


public class AuthFilter implements Filter {	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private InitParam initParam;
	
	@Autowired
	private UserOperateLogService userOperateLogService;
	
	ArrayList<String> excludeList;
	
	@SuppressWarnings("null")
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.excludeList = new ArrayList<String>();
		String exclude = filterConfig.getInitParameter("exclude");
		if(exclude != null || exclude.trim() != "") {
			String[] excludeArry = exclude.split(",");
			for(String s : excludeArry) {
				excludeList.add(s);
			}
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()); 
		String url = req.getRequestURI();
		String agent = "";
		if(req.getHeaders("user-agent") != null) {
			agent = req.getHeaders("user-agent").nextElement();
		}
		String ip = CommonUtil.getIpAddr(req);
		
		if(userOperateLogService == null) {                                      
			userOperateLogService = (UserOperateLogServiceImpl) factory.getBean("userOperateLogServiceImpl");
		}
		
		UserOperateLog userOperateLog = new UserOperateLog();
		userOperateLog.setAgent(agent);
		userOperateLog.setIp(ip);
		userOperateLog.setOriginalUrl(url);
		
		boolean isCheck = true;
		for(String s : excludeList) {
			if(url.indexOf(s) != -1) {
				isCheck = false;
				break;
			}
		}
		
		request.setAttribute("isCheck", isCheck);
		
		String username = "";
		if(isCheck) {
			if(jwtService == null) {
				jwtService = (JWTService) factory.getBean("JWTService");
			}
			
			if(initParam == null) {
				initParam = (InitParam) factory.getBean("initParam");
			}
			
			//判断来至客户端的请求是否包含JWT
			String jwt = jwtService.getJWTFromReq(req);
			
			if(jwt == null) {
				userOperateLogService.save(userOperateLog);
				CommonUtil.respErrorMsg(res, "{error : {code : 'unauthorization_token_null', msg : '没有访问权限_token为空!'}}");
				return;
			}
			
			Boolean invalidJWT = true;
			//根据JWT判断是否来自正确的客户端或超时

			invalidJWT = jwtService.invalidJWT(jwt, initParam.getCid());

			if(invalidJWT) {
				userOperateLogService.save(userOperateLog);
				CommonUtil.respErrorMsg(res, "{error : {code : 'unauthorization_token_timeout', msg : '没有访问权限_token超时!'}}");
				return;
			}
			
			username = jwtService.getUsernameByJWT(jwt);
		}
		
		userOperateLog.setUsername(username);
		userOperateLogService.save(userOperateLog);
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}