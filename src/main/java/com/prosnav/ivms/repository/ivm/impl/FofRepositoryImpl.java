/**
 * 
 */
package com.prosnav.ivms.repository.ivm.impl;

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

import com.prosnav.ivms.model.Fof;
import com.prosnav.ivms.repository.ivm.FofRepositoryCustom;

/**
 * @author wangnan
 *
 */
public class FofRepositoryImpl implements FofRepositoryCustom {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MongoTemplate template;

	@Override
	public Page<Fof> searchPage(String keyword, PageRequest pageRequest) {

		Query query = new Query();
		Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
		query.addCriteria(
				new Criteria().orOperator(
						Criteria.where("name").regex(pattern),
						Criteria.where("alias").regex(pattern)))
						.addCriteria(Criteria.where("deled").is(false))
				.skip(pageRequest.getOffset()).limit(pageRequest.getPageSize());
		logger.debug("query\t" + query.toString());
		
		List<Fof> fofs = template.find(query, Fof.class);
		long count = template.count(query, Fof.class);

		Page<Fof> fofPage = new PageImpl<Fof>(fofs, pageRequest,
				count);

		return fofPage;
	}

	@Override
	public void logicDelete(long id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("deled", true);
		
		template.updateFirst(query, update, Fof.class);
	}
	
	@Override
	public List<Fof> getFofByFirmId(Long id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("fofFirmId").is(id));
		List<Fof> fofs = template.find(query, Fof.class);
		return fofs;
	}
}
