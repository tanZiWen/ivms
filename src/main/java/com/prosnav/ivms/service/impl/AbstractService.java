/**
 * 
 */
package com.prosnav.ivms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.prosnav.ivms.core.IDGenerator;
import com.prosnav.ivms.core.IRedisIDGeneratorSupporter;

/**
 * @author wangnan
 *
 */
public abstract class AbstractService {
	@Autowired()
	@Qualifier("redisIdGenerator")
	protected IDGenerator<IRedisIDGeneratorSupporter> idg;
}
