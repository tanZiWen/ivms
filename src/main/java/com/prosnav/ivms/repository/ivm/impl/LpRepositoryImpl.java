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
import org.springframework.util.StringUtils;

import com.prosnav.ivms.model.Lp;
import com.prosnav.ivms.repository.ivm.LpRepositoryCustom;

/**
 * @author wangnan
 *
 */
public class LpRepositoryImpl implements LpRepositoryCustom {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MongoTemplate template;

	@Override
	public Page<Lp> searchPage(String keyword, PageRequest pageRequest) {
//		if (StringUtils.isEmpty(keyword)) {
//			return null;
//		}

		Query query = new Query();
		Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
		query.addCriteria(
				new Criteria().orOperator(
						Criteria.where("name").regex(pattern),
						Criteria.where("alias").regex(pattern)))
						.addCriteria(Criteria.where("deled").is(false))
				.skip(pageRequest.getOffset()).limit(pageRequest.getPageSize());
		logger.debug("query\t" + query.toString());

		List<Lp> lps = template.find(query, Lp.class);
		long count = template.count(query, Lp.class);

		Page<Lp> lpPage = new PageImpl<Lp>(lps, pageRequest,
				count);

		return lpPage;
	}

	@Override
	public void logicDelete(long id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("deled", true);
		
		template.updateFirst(query, update, Lp.class);
	}
}
