/**
 * 
 */
package com.prosnav.ivms.repository.ivm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.StringUtils;

import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.model.Person;
import com.prosnav.ivms.repository.ivm.FieldPicker;
import com.prosnav.ivms.repository.ivm.PersonRepositoryCustom;

/**
 * @author wangnan
 *
 */
public class PersonRepositoryImpl implements PersonRepositoryCustom {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MongoTemplate template;

	@Override
	public Page<Person> searchPage(String keyword, PageRequest pageRequest) {
		if (StringUtils.isEmpty(keyword)) {
			return null;
		}

		Query query = new Query();
		Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
		query.addCriteria(
				new Criteria().orOperator(
						Criteria.where("name").regex(pattern),
						Criteria.where("enname").regex(pattern)))
						.addCriteria(Criteria.where("deled").is(false))
				.skip(pageRequest.getOffset()).limit(pageRequest.getPageSize());
		logger.debug("query\t" + query.toString());

		List<Person> persons = template.find(query, Person.class);
		long count = template.count(query, Person.class);

		Page<Person> personPage = new PageImpl<Person>(persons, pageRequest,
				count);

		return personPage;
	}
	
	@Override
	public Map<Long, Person> getAndMap(Condiction condiction) {
		Map<Long, Person> map = new HashMap<Long, Person>();

		if (condiction.getIds() == null || condiction.getIds().size() == 0) {
			return map;
		}

		Iterable<Person> persons = null;
		Query query = new Query();
		query.addCriteria(Criteria.where("id").in(condiction.getIds()));

		if (condiction.getFieldPicker() != null) {
			if (condiction.getFieldPicker() == FieldPicker.INCLUDE) {
				for (String field : condiction.getFields()) {
					query.fields().include(field);
				}
			} else {
				for (String field : condiction.getFields()) {
					query.fields().exclude(field);
				}
			}
		}
		
		persons = template.find(query, Person.class);

		for (Person person : persons) {
			map.put(person.getId(), person);
		}

		return map;
	}

	@Override
	public void logicDelete(long id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("deled", true);
		
		template.updateFirst(query, update, Person.class);
	}

}
