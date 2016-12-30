package com.prosnav.ivms.core;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWTVerifier;

@Component
public class JWTService {
	@Autowired
	private InitParam initParam;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String getJWTFromReq(HttpServletRequest req) {
		String token = (String) req.getParameter("token");
		if (!StringUtils.isEmpty(token)) {
			return token;
		}
		Cookie[] cookies = req.getCookies();
		if (cookies != null) { 
			for (Cookie cookie : cookies) {
				if (initParam.getAuthorizeTokenName().equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	public Boolean invalidJWT(String jwt, String client_id) {
		String secret = initParam.getPrivateKey();
		JWTVerifier jwtVerifier = new JWTVerifier(secret);
		try {
			Map<String, Object> claims = jwtVerifier.verify(jwt);
			Object cid = claims.get("cid");
			
			if(cid == null || !(cid instanceof String)) {
				logger.error("jwt get cid error!");
				return true;
			}
			
			String clientId = (String) cid;
			if(!clientId.equals(client_id)) {
				logger.debug("invalid client!");
				return true;
			}
			
			Object exp = claims.get("exp");
			if(exp == null || !(exp instanceof Integer)) {
				logger.error("jwt get exp error!");
				return true;
			}
			
			long expireAt = (Integer)exp;
			long now = System.currentTimeMillis() / 1000;
			if (now > expireAt) {
				logger.debug("jwt timeout!");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getUsernameByJWT(String jwt) {
		String secret = initParam.getPrivateKey();
		JWTVerifier jwtVerifier = new JWTVerifier(secret);
		try {
			Map<String, Object> claims = jwtVerifier.verify(jwt);
			Object username = claims.get("aud");
			if(username == null || !(username instanceof String)) {
				logger.error("jwt get username error!");
				return "";
			}
			return (String) username;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
