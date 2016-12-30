/**
 * 
 */
package com.prosnav.ivms.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author wangnan
 *
 */
@Component()
public class RedisIdGenerator implements IDGenerator<IRedisIDGeneratorSupporter> {

	private final String key = "ivm:id:map";
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public Long next(IRedisIDGeneratorSupporter type) {
		BoundHashOperations<String, String, String> op = redisTemplate.boundHashOps(key);
		String id = op.entries().get(type.getKey());
		Long ids = 1l;
		if(id == null || Long.parseLong(id) < type.getInitId()) {
			ids = type.getInitId() + 1L;
		} else {
			ids = Long.parseLong(id) + 1L;
		}
		op.put(type.getKey(), String.valueOf(ids));
		return ids;
	}
	
}
