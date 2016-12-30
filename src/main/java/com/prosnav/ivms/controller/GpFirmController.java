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

import com.prosnav.ivms.controller.helper.ReferenceFinder;
import com.prosnav.ivms.model.GpFirm;
import com.prosnav.ivms.service.GpFirmService;
import com.prosnav.ivms.service.GpFundService;
import com.prosnav.ivms.service.PersonService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.GpFirmView;

@Controller
public class GpFirmController {
	@Autowired
	private GpFirmService gpFirmService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private GpFundService gpFundService;
	
	@Autowired
	private ReferenceFinder referenceFinder;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/gpFirm", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getGpFirmList(@ModelAttribute GpFirm view, HttpServletRequest request) {
		logger.debug("get gpFirm list");
		PageView page = new PageView(request);
		Map<String, Object> result = null;
		try {
			result = gpFirmService.searchGpFirm(view.getName(), page);
			logger.debug("Got gpFirm list", result);
			return result;
		} catch (Exception e) {
			logger.error("failed to get gpFirm list", e);
			result = new HashMap<String, Object>();
			result.put("code", 505);
			return result;
		}
	}
	
	@RequestMapping(value = "/gpFirm/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getGpFirm(@PathVariable Long id) {
		logger.debug("get gpFirm : " + id);
		Map<String, Object> result = new HashMap<String, Object>();
		GpFirmView gpFirmView = gpFirmService.get(id);
		if (gpFirmView != null) {
//			Long personId = gpFirmView.getLegalRepresentative();
//			
//			if(personId != null) {
//				Reference reference = new Reference();
//				
//				reference.with(ModelType.PERSON, gpFirmView.getLegalRepresentative());
//				
//				Map<String, Object> personList = referenceFinder.find(reference);
//				
//				result.put("reference", personList);
				result.put("personList", personService.getPersonByCorpId(gpFirmView.getId()));
				result.put("gpFundList", gpFundService.getGPFundByFirmId(gpFirmView.getId()));
		}
		result.put("view", gpFirmView);
		return result;
	}

	@RequestMapping(value = "/gpFirm", method = RequestMethod.POST)
	public @ResponseBody Object createGpFirm(@RequestBody GpFirm gpFirm,
			ModelMap model, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		logger.debug("create gpFirm", gpFirm);
		gpFirm.setDeled(false);
		Date time = new Date();
		gpFirm.setCrt(time);
		gpFirm.setLut(time);
		
		try {
			if (gpFirmService.addGpFirm(gpFirm)) {
				result.put("gpFirm", gpFirm);
			} else {
				result.put("code", 500);
				result.put("message", "failed to save gpFirm.");
			}
		} catch (Exception e) {
			logger.error("Failed to create gpFirm", e.getCause());
			result.put("code", 500);
			result.put("message", e.getCause());
		}

		return result;
	}

	@RequestMapping(value = "/gpFirm", method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> updateGpFirm(
			@RequestBody GpFirm gpFirm, ModelMap model) {
		logger.debug("update gpFirm", gpFirm);
		Map<String, Object> result = new HashMap<String, Object>();
		boolean ok = gpFirmService.updateGpFirm(gpFirm);
		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}

	@RequestMapping(value = "/gpFirm/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteGpFirm(@PathVariable int id,
			ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>();

		boolean ok = gpFirmService.deleteGpFirm(id);

		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}

}
