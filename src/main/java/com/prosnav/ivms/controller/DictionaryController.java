package com.prosnav.ivms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prosnav.ivms.service.DictionaryService;

@Controller
public class DictionaryController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(value = "/dictionary", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getDictionaryList(ModelMap model,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		logger.debug("get all dictionary items");

		try {
			result.put("dictionaries", dictionaryService.getAll());
		} catch (Exception e) {
			logger.error("failed to get all dictionary items", e.getCause());
			result.put("code", 505);
		}

		return result;
	}

}
