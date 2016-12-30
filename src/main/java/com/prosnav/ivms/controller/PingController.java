/**
 * 
 */
package com.prosnav.ivms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prosnav.ivms.service.PingService;

/**
 * @author wangnan
 *
 */
@RestController()
@RequestMapping("/ping")
public class PingController {
	
	@Autowired
	private PingService pingService;
	
	@RequestMapping("/hello")
	public Map<String, Object> hello(@RequestParam(required=false, defaultValue="World") String name) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("message", pingService.hello(name));
		return result;
	}
}
