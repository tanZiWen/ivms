package com.prosnav.ivms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prosnav.ivms.controller.helper.Reference;
import com.prosnav.ivms.controller.helper.ReferenceFinder;
import com.prosnav.ivms.model.ModelType;
import com.prosnav.ivms.service.CompanyInvestmentService;
import com.prosnav.ivms.service.GpFirmService;
import com.prosnav.ivms.service.GpFundService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.GpFundView;

@Controller
public class GpFundController {
	@Autowired
	private GpFundService gpService;
	
	@Autowired
	private GpFirmService gpFirmService;
	
	@Autowired
	private CompanyInvestmentService companyInvestmentService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ReferenceFinder referenceFinder;
	
	@RequestMapping(value="/gpFund/{id}", method=RequestMethod.GET)
	public @ResponseBody Map<String, Object> getGPFund(@PathVariable Long id) {
		if(logger.isDebugEnabled()) {
			logger.debug("get gp fund : id = " + id);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		GpFundView view = gpService.get(id);
		
		if (view != null) {
//			if(personId != null) {
//				Reference reference = new Reference();
//				
//				reference.with(ModelType.PERSON, view.getLegalRepresentative());
//				
//				Map<String, Object> personList = referenceFinder.find(reference);
//				
//				result.put("reference", personList);
				
				Long gpFirmId = view.getGpFirmId();
				
				if(gpFirmId != null) {		
					result.put("gpFirm", gpFirmService.get(gpFirmId));
				}
				result.put("companyInvestments", companyInvestmentService.getCompanyInvestmentByGPFundId(view.getId()));
		}
		result.put("view", view);
		return result;
	}

	@RequestMapping(value = "/gpFund", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getGPFundlist(
			@ModelAttribute GpFundView view,
			HttpServletRequest request) {
		logger.debug("get gp list");
		PageView page = new PageView(request);
		Map<String, Object> result = null;
		try {
			result = gpService.searchGp(view, page);
			logger.debug("Got gp list", result);
			return result;
		} catch (Exception e) {
			logger.error("failed to get gp list", e.getCause());
			result = new HashMap<String, Object>();
			result.put("code", 505);
			return result;
		}
	}

	@RequestMapping(value = "/gpFund", method = RequestMethod.POST)
	public @ResponseBody Object createGPFund(@RequestBody GpFundView view,
			ModelMap model, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		logger.debug("create gp fund", view);
		view.setDeled(false);
		Date time = new Date();
		view.setCrt(time);
		view.setLut(time);
		
		try {
			if (gpService.addGp(view)) {
				result.put("view", view);
			} else {
				result.put("code", 500);
				result.put("message", "failed to save gp fund.");
			}
		} catch (Exception e) {
			logger.error("Failed to create gp fund", e.getCause());
			result.put("code", 500);
			result.put("message", e.getCause());
		}

		return result;
	}

	@RequestMapping(value = "/gpFund", method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> updateGPFund(
			@RequestBody GpFundView view, ModelMap model) {
		logger.debug("update gp fund", view);
		Map<String, Object> result = new HashMap<String, Object>();
		boolean ok = gpService.updateGp(view);
		if (!ok) {
			result.put("code", 500);
		} else {
			result.put("view", view);
		}

		return result;
	}

	@RequestMapping(value = "/gpFund/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteGPFund(@PathVariable int id,
			ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>();

		boolean ok = gpService.deleteGp(id);

		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}

}
