package com.prosnav.ivms.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.model.Person;
import com.prosnav.ivms.model.Person.WorkExperience;
import com.prosnav.ivms.repository.ivm.PersonRepository;
import com.prosnav.ivms.service.PersonService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.PersonView;

@Service
public class PersonServiceImpl extends AbstractService implements PersonService {
	@Autowired
	private PersonRepository personRepository;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Boolean addPerson(PersonView personView) {
		try {
			Person person = personView.toModel();
			person.setId(personRepository.nextId(idg));
			Collections.sort(person.getWorkExperiences(), new Comparator<WorkExperience>() {

				@Override
				public int compare(WorkExperience o1, WorkExperience o2) {
					return o2.compareTo(o1);
				}
				
			});
			person = personRepository.save(person);
			personView.fromModel(person);
			return true;
		} catch (Exception e) {
			logger.error("Fail add person", e);
		}
		return false;
	}

	@Override
	public Map<String, Object> listPerson(PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		Criteria condition = Criteria.where("deled").is(false);
		Page<Person> page = personRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("persons", page.getContent());
		return result;
	}

	@Override
	public boolean updatePerson(PersonView personView) {
		try {
			Person person = personView.toModel();
			Collections.sort(person.getWorkExperiences(), new Comparator<WorkExperience>() {

				@Override
				public int compare(WorkExperience o1, WorkExperience o2) {
					return o2.compareTo(o1);
				}
				
			});
			person = personRepository.save(person);
			System.out.println(person);
			personView.fromModel(person);
			return true;
		} catch (Exception e) {
			logger.error("Fail add person", e);
		}
		return false;
	}

	@Override
	public boolean deletePerson(long id) {
		try {
			Person person = personRepository.findOne(id);
			if (person != null) {
				person.setDeled(true);
				person.setLut(new Date());
				personRepository.save(person);
			}
			return true;
		} catch (Exception e) {
			logger.error("Failed to delete person", e);
			return false;
		}
	}

	@Override
	public PersonView getPerson(Long id) {
		PersonView pv = new PersonView();
		pv.fromModel(personRepository.findOne(id));
		return pv;
	}

	@Override
	public Map<String, Object> listPerson(Map<String, Object> params,
			PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		Criteria condition = Criteria.where("deled").is(false);
		String name = (String) params.get("name");
		if (!StringUtils.isEmpty(name)) {
			condition.and("name").regex(name);
		}
		Page<Person> page = personRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		
		List<PersonView> persons = new ArrayList<PersonView>();
		for(Person p : page.getContent()) {
			PersonView personView = new PersonView();
			personView.fromModel(p);
			persons.add(personView);
		}
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("persons", persons);
		return result;
	}

	@Override
	public Map<String, Object> searchPerson(String keyword, PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Page<Person> page = personRepository.searchPage(keyword, pr);
		pv.setCount(page.getTotalElements());
		
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("persons", page.getContent());
		
		return result;
	}

	@Override
	public Map<Long, Person> getAndMap(Condiction condiction) {
		return personRepository.getAndMap(condiction);
	}

	@Override
	public Map<String, Object> searchPersonWithCompany(
			Map<String, Object> params, PageView pv) {
		
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		Criteria condition = Criteria.where("deled").is(false);
		
		Integer corpType = (Integer) params.get("corpType");
		Long corpId = (Long) params.get("corpId");
		if(corpType == null || corpId == null) {
			pv.setCount(0);
			result.put("pager", pv);
			result.put("persons", new ArrayList<PersonView>());
			return result;
		}
		condition.and("workExperiences").elemMatch(
				Criteria.where("primary").is(true)
					.and("corpType").is(corpType)
					.and("corpId").is(corpId));
		String name = (String) params.get("name");
		if (!StringUtils.isEmpty(name)) {
			condition.and("name").regex(name);
		}
		Page<Person> page = personRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		
		List<PersonView> persons = new ArrayList<PersonView>();
		for(Person p : page.getContent()) {
			PersonView personView = new PersonView();
			personView.fromModel(p);
			persons.add(personView);
		}
		
		result.put("pager", pv);
		result.put("persons", persons);
		return result;
	}

	@Override
	public List<PersonView> searchPersonByIds(List<Long> ids) {
		
		if(ids == null || ids.size() == 0) {
			return new ArrayList<PersonView>();
		}
		
		List<Person> persons = personRepository.find(Criteria.where("id").in(ids), null);
		List<PersonView> personViews = new ArrayList<PersonView>();
		for(Person p : persons) {
			PersonView pv = new PersonView();
			pv.fromModel(p);
			personViews.add(pv);
		}
		
		return personViews;
	}

	@Override
	public List<Person> getPersonByName(String name) {
		return personRepository.find(Criteria.where("name").is(name), null);
	}
	
	@Override
	public List<PersonView> getPersonByCorpId(Long corpId) {
		List<Person> persons = personRepository.find(Criteria.where("workExperiences").elemMatch(Criteria.where("corpId").is(corpId).and("primary").is(true)), null);
		List<PersonView> personViews = new ArrayList<PersonView>();
		for(Person p : persons) {
			PersonView pv = new PersonView();
			pv.fromModel(p);
			personViews.add(pv);
		}
		return personViews;
	}
}
