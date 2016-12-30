/**
 * 
 */
package com.prosnav.ivms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prosnav.ivms.service.AdviserService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.AdviserView;

/**
 * @author wangnan
 *
 */
@RestController
public class AdviserController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdviserService adviserService;
	
	@RequestMapping(value="/adviser/{id}", method=RequestMethod.GET)
	public Map<String, Object> get(@PathVariable Long id) {
		if(logger.isDebugEnabled()) {
			logger.debug("get adviser : id = " + id);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		AdviserView adviserView = adviserService.get(id);
		result.put("viewObj", adviserView);
		return result;
	}
	
	@RequestMapping(value="/advisers", method=RequestMethod.GET)
	public Map<String, Object> search(HttpServletRequest request, @RequestParam(required=false) String name) {
		if(logger.isDebugEnabled()) {
			logger.debug("get adviser list : name = " + name);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		PageView page = new PageView(request);
		return adviserService.list(params, page);
	}
	
	@RequestMapping(value="/adviser", method=RequestMethod.POST)
	public Map<String, Object> save(@RequestBody AdviserView adviserView) {
		if(logger.isDebugEnabled()) {
			logger.debug("save adviser : id = " + adviserView.getId());
		}
		Map<String, Object> result = new HashMap<String, Object>();
		adviserService.save(adviserView);
		result.put("message", "Successed save adviser");
		result.put("view", adviserView);
		return result;
	}
	
	@RequestMapping(value="/adviser/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> remove(@PathVariable Long id) {
		if(logger.isDebugEnabled()) {
			logger.debug("remove adviser : id = " + id);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		adviserService.remove(id);
		result.put("message", "Successed remove adviser");
		return result;
	}
}
