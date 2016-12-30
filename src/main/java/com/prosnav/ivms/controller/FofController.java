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

import com.prosnav.ivms.model.Fof;
import com.prosnav.ivms.service.CompanyInvestmentService;
import com.prosnav.ivms.service.FofFirmService;
import com.prosnav.ivms.service.FofService;
import com.prosnav.ivms.service.GpFundInvestmentService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.FofView;

@Controller
public class FofController {
	@Autowired
	private FofService fofService;
	
	@Autowired
	private FofFirmService fofFirmService;
	
	@Autowired
	private GpFundInvestmentService gpFundInvestmentService;
	
	@Autowired
	private CompanyInvestmentService companyInvestmentService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/fof", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getFofList(@ModelAttribute FofView view, HttpServletRequest request) {
		logger.debug("get fof list");
		PageView page = new PageView(request);
		Map<String, Object> result = null;
		try {
			result = fofService.searchFof(view.getName(), page);
			logger.debug("Got fof list", result);
			return result;
		} catch (Exception e) {
			logger.error("failed to get fof list", e.getCause());
			result = new HashMap<String, Object>();
			result.put("code", 505);
			return result;
		}
	}
	
	@RequestMapping(value="/fof/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getFof(@PathVariable Long id) {
		logger.debug("get fof by id");
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			Fof fof = fofService.getFof(id);
			
			if (fof != null) {			
				
				Long fofFirmId = fof.getFofFirmId();
				if(fofFirmId != null) {					
					result.put("fofFirm", fofFirmService.getFofFirm(fofFirmId));
				}
				result.put("gpFundInvestments", gpFundInvestmentService.getGpFundInvestmentByFofId(fof.getId()));
				result.put("companyInvestments", companyInvestmentService.getCompanyInvestmentByFofId(fof.getId()));
			}
			result.put("fof", fof);
		}catch (Exception e) {
			logger.debug("fialed to get fof by id", e.getCause());
			result.put("code", 500);
		}
		return result;
	}

	@RequestMapping(value = "/fof", method = RequestMethod.POST)
	public @ResponseBody Object createFof(@RequestBody Fof fof,
			ModelMap model, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		logger.debug("create fof", fof);
		fof.setDeled(false);
		Date time = new Date();
		fof.setCrt(time);
		fof.setLut(time);
		//TODO: set current user id as luuid here
		
		try {
			if (fofService.addFof(fof)) {
				result.put("fof", fof);
			} else {
				result.put("code", 500);
				result.put("message", "failed to save fof.");
			}
		} catch (Exception e) {
			logger.error("Failed to create fof", e.getCause());
			result.put("code", 500);
			result.put("message", e.getCause());
		}

		return result;
	}

	@RequestMapping(value = "/fof", method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> updateFof(
			@RequestBody Fof fof, ModelMap model) {
		logger.debug("update fof", fof);
		System.out.println("fof"+ fof);
		Map<String, Object> result = new HashMap<String, Object>();
		boolean ok = fofService.updateFof(fof);
		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}

	@RequestMapping(value = "/fof/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteFof(@PathVariable int id,
			ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>();

		boolean ok = fofService.deleteFof(id);

		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}

}
