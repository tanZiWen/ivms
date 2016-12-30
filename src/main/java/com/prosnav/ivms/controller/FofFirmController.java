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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prosnav.ivms.controller.helper.ReferenceFinder;
import com.prosnav.ivms.model.FofFirm;
import com.prosnav.ivms.service.FofFirmService;
import com.prosnav.ivms.service.FofService;
import com.prosnav.ivms.service.PersonService;
import com.prosnav.ivms.util.PageView;

@Controller
public class FofFirmController {
	@Autowired
	private FofFirmService fofFirmService;
	
	@Autowired
	private FofService fofService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private ReferenceFinder referenceFinder;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/fofFirm", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getFofFirmList(ModelMap model, HttpServletRequest request) {
		logger.debug("get fofFirm list");
		String name = request.getParameter("name");
		Map<String, Object> params = new HashMap<String, Object>();
		PageView page = new PageView(request);
		Map<String, Object> result = null;
		try {
			params.put("name", name);
			result = fofFirmService.listFofFirm(params, page);
			return result;
		} catch (Exception e) {
			logger.error("failed to get fofFirm list", e.getCause());
			result = new HashMap<String, Object>();
			result.put("code", 505);
			return result;
		}
	}
	
	@RequestMapping(value = "/fofFirm/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getFofFirm(@PathVariable Long id) {
		logger.debug("get fofFirm by id");
		Map<String, Object> result = new HashMap<String, Object>();;
		try {
			FofFirm fofFirm = fofFirmService.getFofFirm(id);
			if(fofFirm != null) {
				result.put("personList", personService.getPersonByCorpId(fofFirm.getId()));
				result.put("fofList",fofService.getFofByFirmId(fofFirm.getId()));
			}
			result.put("view", fofFirm);
			return result;
		} catch (Exception e) {
			logger.error("failed to get fofFirm by id", e.getCause());
			result.put("code", 505);
			return result;
		}
	}


	@RequestMapping(value = "/fofFirm", method = RequestMethod.POST)
	public @ResponseBody Object createFofFirm(@RequestBody FofFirm fofFirm,
			ModelMap model, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		fofFirm.setDeled(false);
		Date time = new Date();
		fofFirm.setCrt(time);
		fofFirm.setLut(time);
		//TODO: set current user id as luuid here
		
		try {
			if (fofFirmService.addFofFirm(fofFirm)) {
				result.put("fofFirm", fofFirm);
			} else {
				result.put("code", 500);
				result.put("message", "failed to save fofFirm.");
			}
		} catch (Exception e) {
			logger.error("Failed to create fofFirm", e.getCause());
			result.put("code", 500);
			result.put("message", e.getCause());
		}

		return result;
	}

	@RequestMapping(value = "/fofFirm", method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> updateFofFirm(
			@RequestBody FofFirm fofFirm, ModelMap model) {
		logger.debug("update fofFirm", fofFirm);
		Map<String, Object> result = new HashMap<String, Object>();
		boolean ok = fofFirmService.updateFofFirm(fofFirm);
		if (!ok) {
			result.put("code", 500);
		}
		return result;
	}

	@RequestMapping(value = "/fofFirm/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteFofFirm(@PathVariable int id,
			ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>();

		boolean ok = fofFirmService.deleteFofFirm(id);

		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}

}
