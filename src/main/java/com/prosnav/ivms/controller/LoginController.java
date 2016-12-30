package com.prosnav.ivms.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.DeserializationFeature;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;
import com.mongodb.util.JSON;
import com.auth0.jwt.JWTSigner.Options;
import com.prosnav.ivms.core.InitParam;
import com.prosnav.ivms.core.MyException;
import com.prosnav.ivms.core.RedisStoreUser;
import com.prosnav.ivms.core.TokenInfo;
import com.prosnav.ivms.core.User;
import com.prosnav.ivms.util.CommonUtil;

@Controller
public class LoginController {
	@Autowired
	private InitParam initParam;
	
	@Autowired
	private RedisStoreUser redisStoreUser;
	
	private String username;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public @ResponseBody Object login(@RequestParam(required=false) String username, String password, HttpServletRequest request, HttpServletResponse response) {
		String loginUrl = initParam.getLoginUrl();
		URL url;
		StringBuffer bs = null;
		InputStream in = null;
		try {
			url = new URL(loginUrl+"?grant_type=password&username="+username+"&password="+password);
			HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
			String clientStr = new String(Base64.encodeBase64((initParam.getClientId()+":"+initParam.getClientSecret()).getBytes()));
			httpConn.setRequestProperty("Authorization", "Basic "+clientStr);
			httpConn.setRequestProperty("Content-Type", "application/json");
			if(httpConn.getResponseCode() >= 300) {
				response.setStatus(httpConn.getResponseCode());
				CommonUtil.respErrorMsg(response, "HTTP Request is not success, Response msg is "+ httpConn.getResponseMessage());
				return JSON.parse("{}");
			}
			in = httpConn.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
			bs = new StringBuffer();
		    String l = null;
            while((l=buffer.readLine())!=null){
               bs.append(l);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	    ObjectMapper objectMapper = new ObjectMapper();
	    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    TokenInfo tokenInfo = null;
        try {
        	tokenInfo = objectMapper.readValue(bs.toString(), TokenInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
        String token = tokenInfo.getAccess_token();
		String userStr = requestUser(token);
		System.out.println(userStr);
		redisStoreUser.StoreUser(username, userStr, new Long(tokenInfo.getExpires_in()).longValue());
		return JSON.parse("{token: '"+token+"'}");
	}
	
	public String requestUser(String jwt){
		InputStream in = null;
		try {
			String load_user_url = initParam.getLoadUserUrl();
			URL url = new URL(load_user_url + "?code=" + jwt);
			URLConnection conn = url.openConnection();
			in = conn.getInputStream();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
            StringBuffer bs = new StringBuffer();
            String l = null;
            while((l=buffer.readLine())!=null){
               bs.append(l);
            }
			return bs.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public String refreshJWT(String jwt) {
		String secret = initParam.getPrivateKey();
		String cid = initParam.getCid();
		String expire = initParam.getExpire();
		JWTVerifier jwtVerifier = new JWTVerifier(secret);
		JWTSigner signer = new JWTSigner(secret);
		try {
			Map<String, Object> claims = jwtVerifier.verify(jwt);
			username = (String) claims.get("aud");
			MyException.validInitParam(username, "username is null");
			Long exp = Long.parseLong(expire) + System.currentTimeMillis() / 1000;
			Options option = new Options();
			option.setAlgorithm(Algorithm.valueOf(initParam.getAlg()));
			claims.put("cid", cid);
			claims.put("exp", exp);
			return signer.sign(claims, option);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
