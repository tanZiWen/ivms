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

import com.prosnav.ivms.model.GpFund;
import com.prosnav.ivms.repository.ivm.GpFundRepositoryCustom;

/**
 * @author wangnan
 *
 */
public class GpFundRepositoryImpl implements GpFundRepositoryCustom {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MongoTemplate template;

	@Override
	public Page<GpFund> searchPage(String keyword, PageRequest pageRequest) {
		Query query = new Query();
		Criteria condition = Criteria.where("deled").is(false);
		if(!StringUtils.isEmpty(keyword)) {
			Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
			condition.andOperator(new Criteria().orOperator(
					Criteria.where("name").regex(pattern),
					Criteria.where("alias").regex(pattern)));
			
		}
		query.addCriteria(condition);
		query.skip(pageRequest.getOffset()).limit(pageRequest.getPageSize());
		logger.debug("query\t" + query.toString());

		List<GpFund> gps = template.find(query, GpFund.class);
		long count = template.count(query, GpFund.class);

		Page<GpFund> gpPage = new PageImpl<GpFund>(gps, pageRequest,
				count);

		return gpPage;
	}

	@Override
	public void logicDelete(long id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("deled", true);
		
		template.updateFirst(query, update, GpFund.class);
	}
	
	@Override
	public List<GpFund> getGPFundByFirmId(Long id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("gpFirmId").is(id));
		List<GpFund> gpFundList =  template.find(query, GpFund.class);
		return gpFundList;
	}
}
