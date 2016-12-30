package com.prosnav.ivms.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.prosnav.ivms.util.CommonUtil;

public class AuthInterceptor implements HandlerInterceptor  {
	
	@Autowired
	private RedisStoreUser redisStoreUser;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private InitParam initParam;
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object arg2) throws Exception {
		// TODO Auto-generated method stub	
		
		if(!(boolean)req.getAttribute("isCheck")) {
			return true;
		}
		
		BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext()); 
		if(jwtService == null) {
			jwtService = (JWTService) factory.getBean("JWTService");
		}
		
		if(initParam == null) {
			initParam = (InitParam) factory.getBean("initParam");
		}
		
		if(redisStoreUser == null) {
			redisStoreUser = (RedisStoreUser) factory.getBean("redisStoreUser");
		}
		
	    String jwt = jwtService.getJWTFromReq(req);
	    
	    String username = jwtService.getUsernameByJWT(jwt);
	    
	    if(username == "") {
	    	CommonUtil.respErrorMsg(res, "{error : {code : 'unauthorization_username_null', msg : '没有访问权限用户名不存在!'}}");
	    	return false;
	    }
	    
	    User user = redisStoreUser.GetUser(username);
	    
	    if(user == null) {
	    	CommonUtil.respErrorMsg(res, "{error : {code : 'unauthorization_user_null', msg : '没有访问权限用户不存在!'}}");
	    	return false;
	    }
	    
	    req.setAttribute("user", user);
	        
	    if(user.getFunctionMap() == null || user.getFunctionMap().size() == 0) {
	    	CommonUtil.respErrorMsg(res, "{error : {code : 'unauthorization_function_list_null', msg : '没有访问权限!'}}");
	    	return false;
	    }
	    
	    HandlerMethod handlerMethod = (HandlerMethod) arg2;  
	    String methodName = handlerMethod.getMethod().getName(); 
	    String className = handlerMethod.getBean().getClass().getName();
	    
	    String actionName = className + "." + methodName;
	    
	    String actionKey = actionName.replace(initParam.getPackageName(), "");
	    
	    System.out.println("actionKey"+actionKey);
	    if(!user.getFunctionMap().containsKey(actionKey)) {
	    	CommonUtil.respErrorMsg(res, "{error : {code : 'unauthorization_action_error', msg : '没有访问权限没有匹配的action!'}}");
	    	return false;
	    }
		return true;
	}

}
