package com.prosnav.ivms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prosnav.ivms.controller.helper.Reference;
import com.prosnav.ivms.controller.helper.ReferenceFinder;
import com.prosnav.ivms.model.ModelType;
import com.prosnav.ivms.model.Person;
import com.prosnav.ivms.model.Person.WorkExperience;
import com.prosnav.ivms.service.PersonService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.PersonView;

@Controller
public class PersonController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PersonService personService;

	@Autowired
	private ReferenceFinder referenceFinder;

	@RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getPerson(@PathVariable Long id) {
		logger.debug("get person : " + id);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			PersonView person = personService.getPerson(id);
			result.put("person", person);

			if (person != null) {
				List<WorkExperience> wes = person.getWorkExperiences();

				if (wes != null && wes.size() > 0) {
					Reference reference = new Reference();

					for (WorkExperience we : person.getWorkExperiences()) {
						if (we.getCorpType() != null && we.getCorpType() != ModelType.OTHER.getCode()) {
							reference.with(we.getCorpType(), we.getCorpId());
						}
					}

					result.put(Reference.KEY, referenceFinder.find(reference));
				}
			}

			return result;
		} catch (Exception e) {
			logger.error("failed to get person : " + id, e.getCause());
			result.put("code", 505);
			return result;
		}
	}

	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getPersonList(ModelMap model,
			HttpServletRequest request) {
		logger.debug("get person list");
		String name = request.getParameter("name");
		Map<String, Object> params = new HashMap<String, Object>();
		PageView page = new PageView(request);
		Map<String, Object> result = null;
		try {
			params.put("name", name);
			result = personService.listPerson(params, page);
			return result;
		} catch (Exception e) {
			logger.error("failed to get person list", e);
			result = new HashMap<String, Object>();
			result.put("code", 505);
			return result;
		}
	}
	
	@RequestMapping(value = "/person/company", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getPersonWithCompany(
			HttpServletRequest request,
			@RequestParam(required=false) Integer corpType,
			@RequestParam(required=false) Long corpId,
			@RequestParam(required=false) String name) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		PageView page = new PageView(request);
		page.setSize(100);
		Map<String, Object> result = null;
		try {
			params.put("name", name);
			params.put("corpType", corpType);
			params.put("corpId", corpId);
			result = personService.searchPersonWithCompany(params, page);
			return result;
		} catch (Exception e) {
			logger.error("failed to get person list", e);
			result = new HashMap<String, Object>();
			result.put("code", 505);
			return result;
		}
	}
	
	@RequestMapping(value = "/person/ids", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getPersonByIds(
			@RequestParam List<Long> ids) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("persons", personService.searchPersonByIds(ids));
			return result;
		} catch (Exception e) {
			logger.error("failed to get person list", e);
			result.put("code", 505);
			result.put("persons", new ArrayList<PersonView>());
			return result;
		}
	}

	@RequestMapping(value = "/person/search", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> searchPerson(@RequestParam String keyword,
			HttpServletRequest request) {
		logger.debug("search person by keyword\t" + keyword);
		PageView page = new PageView(request);
		Map<String, Object> result = null;
		try {
			result = personService.searchPerson(keyword, page);
			List<Person> persons = (List<Person>) result.get("persons");
			
			if(persons == null) {
				return result;
			}
			
			Reference ref = new Reference();
			
			for(Person person : persons) {
				List<WorkExperience> wes = person.getWorkExperiences();

				if (wes != null && wes.size() > 0) {
					WorkExperience we = wes.get(0);
					if (we.getCorpType() != null && we.getCorpType() != ModelType.OTHER.getCode()) {
						ref.with(we.getCorpType(), we.getCorpId());
					}
				}
			}
			
			result.put(Reference.KEY, referenceFinder.find(ref));
			return result;
		} catch (Exception e) {
			logger.error("failed to get person list", e.getCause());
			result = new HashMap<String, Object>();
			result.put("code", 505);
			return result;
		}
	}

	@RequestMapping(value = "/person", method = RequestMethod.POST)
	public @ResponseBody Object createPerson(@RequestBody PersonView personView,
			ModelMap model, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		logger.debug("create person", personView);
		personView.setDeled(false);
		Date time = new Date();
		personView.setCrt(time);
		personView.setLut(time);

		try {
			if (personService.addPerson(personView)) {
				result.put("person", personView);
			} else {
				result.put("code", 500);
				result.put("message", "failed to save person.");
			}
		} catch (Exception e) {
			logger.error("Failed to create person", e.getCause());
			result.put("code", 500);
			result.put("message", e.getCause());
		}

		return result;
	}

	@RequestMapping(value = "/person", method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> updatePerson(
			@RequestBody PersonView personView, ModelMap model) {
		logger.debug("update person", personView);
		personView.setLut(new Date());
		Map<String, Object> result = new HashMap<String, Object>();
		boolean ok = personService.updatePerson(personView);
		if (!ok) {
			result.put("code", 500);
		}
		result.put("code", 200);
		return result;
	}

	@RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deletePerson(@PathVariable int id,
			ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>();

		boolean ok = personService.deletePerson(id);

		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}

}
