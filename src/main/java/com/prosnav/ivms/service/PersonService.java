package com.prosnav.ivms.service;

import java.util.List;
import java.util.Map;

import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.model.Person;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.PersonView;

public interface PersonService {
	
	PersonView getPerson(Long id);
	
	List<Person> getPersonByName(String name);
	
	Boolean addPerson(PersonView personView);
	
	Map<String, Object> listPerson(PageView page);

	Map<String, Object> listPerson(Map<String, Object> params, PageView page);
	
	Map<String, Object> searchPerson(String keyword, PageView pv);

	boolean updatePerson(PersonView personView);

	boolean deletePerson(long id);
	
	Map<Long, Person> getAndMap(Condiction condiction);
	
	Map<String, Object> searchPersonWithCompany(Map<String, Object> params, PageView page);
	
	List<PersonView> searchPersonByIds(List<Long> ids);

	List<PersonView> getPersonByCorpId(Long corpId);
}
