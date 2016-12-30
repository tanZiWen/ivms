/**
 * 
 */
package com.prosnav.ivms.controller;

import java.util.HashMap;
import java.util.List;
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

import com.prosnav.ivms.service.CompanyInvestmentService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.CompanyInvestmentView;

/**
 * @author wangnan
 *
 */
@RestController
public class CompanyInvestmentController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CompanyInvestmentService service;
	
	@RequestMapping(value="/companyInvestment/{id}", method=RequestMethod.GET)
	public Map<String, Object> getCompanyInvestment(@PathVariable Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("view", service.getCompanyInvestment(id));
		return resultMap;
	}
	
	@RequestMapping(value="/companyInvestments", method=RequestMethod.GET)
	public Map<String, Object> searchCompanyInvestments(HttpServletRequest request, @ModelAttribute CompanyInvestmentView view) {
		
		if(logger.isDebugEnabled()) {
			logger.debug("search companyInvestments");
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("view", view);
		PageView page = new PageView(request);
		
		Map<String, Object> result = service.listCompanyInvestment(params, page);
		
		System.out.println(result);
		if(view.isWithCompany()) {
			service.mergeCompany((List<CompanyInvestmentView>) result.get("views")); 
		}
		return result;
	}
	
	@RequestMapping(value="/companyInvestment", method=RequestMethod.POST)
	public  Map<String, Object> saveCompanyInvestment(@RequestBody CompanyInvestmentView view) {
		 Map<String, Object> result = new HashMap<String, Object>();
		service.addCompanyInvestment(view);
		result.put("view", view);
		return result;
	}
	
	@RequestMapping(value="/companyInvestment/{id}", method=RequestMethod.DELETE)
	public Map<String, Object> removeCompanyInvestment(@PathVariable Long id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(service.removeCompanyInvestment(id)) {
			resultMap.put("message", "Remove file successed");
		} else {
			resultMap.put("message", "Remove file failed");
		}
		return resultMap;
	}
	
}
