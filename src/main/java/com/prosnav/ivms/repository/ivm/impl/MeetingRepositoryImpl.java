/**
 * 
 */
package com.prosnav.ivms.repository.ivm.impl;

import java.util.ArrayList;
import java.util.List;
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

import com.prosnav.ivms.model.Meeting;
import com.prosnav.ivms.model.Person;
import com.prosnav.ivms.repository.ivm.MeetingRepositoryCustom;
import com.prosnav.ivms.service.PersonService;

/**
 * @author wangnan
 *
 */
public class MeetingRepositoryImpl implements MeetingRepositoryCustom {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MongoTemplate template;
	
	@Autowired
	private PersonService personService;
	
	@Override
	public Page<Meeting> searchPage(String keyword, int type, String name, PageRequest pageRequest) {
		Query query = new Query();
		
		if (!StringUtils.isEmpty(keyword)) {
			Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
			query.addCriteria(Criteria.where("title").regex(pattern));
		}
		
		if(type != 0 && !StringUtils.isEmpty(name)) {
			if(type == 1) {
				List<Long> ids = getPersonIdsByName(name);
				query.addCriteria(new Criteria().orOperator(Criteria.where("topics.relations").elemMatch(Criteria.where("type").is(type).and("name").is(name)), (Criteria.where("participants").in(ids))));
			}else {
				query.addCriteria(Criteria.where("topics.relations").elemMatch(Criteria.where("type").is(type).and("name").is(name)));
			}
		}else if(type !=0 && StringUtils.isEmpty(name)) {
			if(type == 1) {
				query.addCriteria(new Criteria().orOperator(Criteria.where("topics.relations").elemMatch(Criteria.where("type").is(type)), (Criteria.where("participants").exists(true).ne(new ArrayList<Long>()))));
			}else {
				query.addCriteria(Criteria.where("topics.relations").elemMatch(Criteria.where("type").is(type)));
			}
			
		}else if(type ==0 && !StringUtils.isEmpty(name)) {
			List<Long> ids = getPersonIdsByName(name);
			query.addCriteria(new Criteria().orOperator(Criteria.where("topics.relations").elemMatch(Criteria.where("name").is(name)), (Criteria.where("participants").in(ids))));
		}
		 
		query.addCriteria(Criteria.where("deled").is(false));
		query.skip(pageRequest.getOffset()).limit(pageRequest.getPageSize());
		logger.debug("query\t" + query.toString());
		
		List<Meeting> meetings = template.find(query, Meeting.class);
		long count = template.count(query, Meeting.class);

		Page<Meeting> meetingPage = new PageImpl<Meeting>(meetings,
				pageRequest, count);

		return meetingPage;
	}

	private List<Long> getPersonIdsByName(String name) {
		List<Person> personList = personService.getPersonByName(name);
		
		List<Long> ids = new ArrayList<Long>();
		for(Person person: personList) {
			ids.add(person.getId());
		}
		return ids;
	}

	@Override
	public void logicDelete(long id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("deled", true);
		
		template.updateFirst(query, update, Meeting.class);
	}
}
