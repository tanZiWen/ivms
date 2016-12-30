package com.prosnav.ivms.core;


import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.auth0.jwt.internal.com.fasterxml.jackson.databind.DeserializationFeature;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class RedisStoreUser {
	private final String key = "ps:ivms:";
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public void StoreUser(String username, String user, Long expire) {
		 String redisKey = key + username;
		 BoundValueOperations<String, String> bvo = stringRedisTemplate.boundValueOps(redisKey);
		 bvo.set(user);
		 bvo.expire(expire, TimeUnit.SECONDS);
	}
	
	public User GetUser(String username) {
		 String redisKey = key + username;
		 BoundValueOperations<String, String> bvo = stringRedisTemplate.boundValueOps(redisKey);
		 String userStr = bvo.get();
		 ObjectMapper objectMapper = new ObjectMapper();
		 objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
         try {
			return objectMapper.readValue(userStr, User.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}
}
