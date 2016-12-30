/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.model.Person;

/**
 * @author wangnan
 *
 */
public interface PersonRepositoryCustom {
	public Page<Person> searchPage(String keyword, PageRequest pageRequest);
	
	public Map<Long, Person> getAndMap(Condiction condiction);
	
	public void logicDelete(long id);
}
