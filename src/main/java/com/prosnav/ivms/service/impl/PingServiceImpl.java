/**
 * 
 */
package com.prosnav.ivms.service.impl;

import org.springframework.stereotype.Service;

import com.prosnav.ivms.service.PingService;

/**
 * @author wangnan
 *
 */
@Service
public class PingServiceImpl implements PingService {

	private final static String template = "Hello, %s!";
	
	@Override
	public String hello(String name) {
		return String.format(template, name);
	}

}
