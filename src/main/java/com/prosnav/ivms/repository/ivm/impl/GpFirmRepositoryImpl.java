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

import com.prosnav.ivms.model.GpFirm;
import com.prosnav.ivms.repository.ivm.GpFirmRepositoryCustom;

/**
 * @author wangnan
 *
 */
public class GpFirmRepositoryImpl implements GpFirmRepositoryCustom {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MongoTemplate template;

	@Override
	public Page<GpFirm> searchPage(String keyword, PageRequest pageRequest) {

		Query query = new Query();
		Criteria condition = Criteria.where("deled").is(false);
		Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
		if(!StringUtils.isEmpty(keyword)) {
			condition.orOperator(
					Criteria.where("name").regex(pattern),
					Criteria.where("alias").regex(pattern));
		}
		query.addCriteria(condition).skip(pageRequest.getOffset()).limit(pageRequest.getPageSize());
		logger.debug("query\t" + query.toString());

		List<GpFirm> gpFirms = template.find(query, GpFirm.class);
		long count = template.count(query, GpFirm.class);

		Page<GpFirm> gpFirmPage = new PageImpl<GpFirm>(gpFirms, pageRequest,
				count);

		return gpFirmPage;
	}

	@Override
	public void logicDelete(long id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("deled", true);
		
		template.updateFirst(query, update, GpFirm.class);
	}
}
