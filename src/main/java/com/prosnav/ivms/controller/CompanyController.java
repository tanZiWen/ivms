package com.prosnav.ivms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prosnav.ivms.controller.helper.Reference;
import com.prosnav.ivms.controller.helper.ReferenceFinder;
import com.prosnav.ivms.model.Company;
import com.prosnav.ivms.model.Meeting;
import com.prosnav.ivms.model.ModelType;
import com.prosnav.ivms.service.CompanyService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.CompanyView;

@Controller
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private ReferenceFinder referenceFinder;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getCompany(@PathVariable Long id) {
		logger.debug("get company : " + id);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			CompanyView company = companyService.getCompany(id);
			if(company != null && company.getCompIds() != null && company.getCompIds().size() > 0) {
				result.put("comps", companyService.searchByIds(company.getCompIds()));
			}
			result.put("company", company);
			return result;
		} catch (Exception e) {
			logger.error("failed to get company : " + id, e.getCause());
			result.put("code", 505);
			return result;
		}
	}
	
	@RequestMapping(value = "/company/search", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> searchCompanys(@RequestParam String keyword,
			HttpServletRequest request) {
		logger.debug("search company by keyword\t" + keyword);
		PageView page = new PageView(request);
		Map<String, Object> result = null;
		try {
			result = companyService.searchCompany(keyword, page);
			return result;
		} catch (Exception e) {
			logger.error("failed to get company list", e.getCause());
			result = new HashMap<String, Object>();
			result.put("code", 505);
			return result;
		}
	}

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getCompanyList(@ModelAttribute CompanyView view, HttpServletRequest request) {
		logger.debug("get company list");
		PageView page = new PageView(request);
		Map<String, Object> result = null;
		try {
			result = companyService.list(view, page);
			logger.debug("Got company list", result);
			return result;
		} catch (Exception e) {
			logger.error("failed to get company list", e.getCause());
			result = new HashMap<String, Object>();
			result.put("code", 505);
			return result;
		}
	}

	@RequestMapping(value = "/company", method = RequestMethod.POST)
	public @ResponseBody Object createCompany(@RequestBody Company company,
			ModelMap model, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		logger.debug("create company", company);
		company.setDeled(false);
		Date time = new Date();
		company.setCrt(time);
		company.setLut(time);
		//TODO: set current user id as luuid here
		try {
			if (companyService.addCompany(company)) {
				result.put("company", company);
			} else {
				result.put("code", 500);
				result.put("message", "failed to save company.");
			}
		} catch (Exception e) {
			logger.error("Failed to create company", e.getCause());
			result.put("code", 500);
			result.put("message", e.getCause());
		}

		return result;
	}

	@RequestMapping(value = "/company", method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> updateCompany(
			@RequestBody Company company, ModelMap model) {
		logger.debug("update company", company);
		Map<String, Object> result = new HashMap<String, Object>();
		boolean ok = companyService.updateCompany(company);
		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}

	@RequestMapping(value = "/company/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteCompany(@PathVariable int id,
			ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>();

		boolean ok = companyService.deleteCompany(id);

		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}

}
