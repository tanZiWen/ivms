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

import com.prosnav.ivms.model.Lp;
import com.prosnav.ivms.service.LpService;
import com.prosnav.ivms.util.PageView;

@Controller
public class LpController {
	@Autowired
	private LpService lpService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/lp", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getLpList(@ModelAttribute Lp view, HttpServletRequest request) {
		logger.debug("get lp list");
		PageView page = new PageView(request);
		Map<String, Object> result = null;
		try {
			result = lpService.searchLp(view.getName(), page);
			logger.debug("Got lp list", result);
			return result;
		} catch (Exception e) {
			logger.error("failed to get lp list", e.getCause());
			result = new HashMap<String, Object>();
			result.put("code", 505);
			return result;
		}
	}

	@RequestMapping(value = "/lp", method = RequestMethod.POST)
	public @ResponseBody Object createLp(@RequestBody Lp lp,
			ModelMap model, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		logger.debug("create lp", lp);
		lp.setDeled(false);
		Date time = new Date();
		lp.setCrt(time);
		lp.setLut(time);
		//TODO: set current user id as luuid here
		try {
			if (lpService.addLp(lp)) {
				result.put("lp", lp);
			} else {
				result.put("code", 500);
				result.put("message", "failed to save lp.");
			}
		} catch (Exception e) {
			logger.error("Failed to create lp", e.getCause());
			result.put("code", 500);
			result.put("message", e.getCause());
		}

		return result;
	}

	@RequestMapping(value = "/lp", method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> updateLp(
			@RequestBody Lp lp, ModelMap model) {
		logger.debug("update lp", lp);
		Map<String, Object> result = new HashMap<String, Object>();
		boolean ok = lpService.updateLp(lp);
		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}

	@RequestMapping(value = "/lp/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteLp(@PathVariable int id,
			ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>();

		boolean ok = lpService.deleteLp(id);

		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}

}
