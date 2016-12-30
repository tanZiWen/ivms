package com.prosnav.ivms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prosnav.ivms.controller.helper.Reference;
import com.prosnav.ivms.controller.helper.ReferenceFinder;
import com.prosnav.ivms.model.ModelType;
import com.prosnav.ivms.model.Person;
import com.prosnav.ivms.service.CompanyService;
import com.prosnav.ivms.service.FofFirmService;
import com.prosnav.ivms.service.FofService;
import com.prosnav.ivms.service.GpFirmService;
import com.prosnav.ivms.service.GpFundService;
import com.prosnav.ivms.service.LpService;
import com.prosnav.ivms.service.PersonService;
import com.prosnav.ivms.util.PageView;

@Controller
public class SearchController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PersonService personService;
	
	@Autowired
	private FofService fofService;
	
	@Autowired
	private FofFirmService fofFirmService;
	
	@Autowired
	private GpFundService gpService;
	
	@Autowired
	private GpFirmService gpFirmService;
	
	@Autowired
	private LpService lpService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ReferenceFinder referenceFinder;

	// TODO: implement this
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> searchRelation(
			@RequestParam int type, @RequestParam String keyword,
			HttpServletRequest request) {

		logger.debug("search model by keyword\t" + type + "\t" + keyword);
		System.out.println("search model by keyword\t" + type + "\t" + keyword);
		PageView page = new PageView(request);
		page.setSize(100);
		ModelType modelType = ModelType.ofCode(type);
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			switch (modelType) {
			case PERSON:
				result = personService.searchPerson(keyword, page);
				List<Person> persons = (List<Person>) result.get("persons");
				result.put(Reference.KEY,
						referenceFinder.findPersonLatestWorkExperience(persons));
				break;
			case COMPANY:
				result = companyService.searchCompany(keyword, page);
				break;
			case FOF:
				result = fofService.searchFof(keyword, page);
				break;
			case FOF_FIRM:
				result = fofFirmService.searchFofFirm(keyword, page);
				break;
			case GP:
				result = gpService.searchGp(keyword, page);
				break;
			case GP_FIRM:
				result = gpFirmService.searchGpFirm(keyword, page);
				break;
			case LP:
				result = lpService.searchLp(keyword, page);
				break;
			default:
				break;
			}

			result.put("type", type);

			return result;
		} catch (Exception e) {
			logger.error("failed to get relation list", e.getCause());
			result = new HashMap<String, Object>();
			result.put("type", type);
			result.put("code", 505);
			return result;
		}
	}

}
