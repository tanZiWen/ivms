package com.prosnav.ivms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.prosnav.ivms.service.GpFundInvestmentService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.GpFundInvestmentView;


@RestController
public class GpFundInvestmentController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GpFundInvestmentService service;
	
	@RequestMapping(value="/gpFundInvestment", method=RequestMethod.GET)
	public Map<String, Object> searchGPFundInvestment(HttpServletRequest request, @ModelAttribute GpFundInvestmentView view) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("search gpFundInvestments");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("view", view);
		PageView page = new PageView(request);
		
		Map<String, Object> result = service.listGpFundInvestment(params, page);
		
		System.out.println(result);
		
		return result;
	}
	
	@RequestMapping(value="/gpFundInvestment", method=RequestMethod.POST)
	public  Map<String, Object> saveGPFundInvestment(@RequestBody GpFundInvestmentView view) {
		 Map<String, Object> result = new HashMap<String, Object>();
		service.addGpFundInvestment(view);
		result.put("view", view);
		return result;
	}
	
	@RequestMapping(value="/gpFundInvestment/{id}", method=RequestMethod.GET)
	public Map<String, Object> getGPFundInvestment(@PathVariable Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("views", service.getGpFundInvestment(id));
		return resultMap;
	}
	
	@RequestMapping(value="/gpFundInvestment/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> removeGPFundInvestment(@PathVariable Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(service.removeGpFundInvestment(id)) {
			resultMap.put("message", "Remove file successed");
		} else {
			resultMap.put("message", "Remove file failed");
		}
		return resultMap;
	}
}
