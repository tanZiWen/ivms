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

import com.prosnav.ivms.model.FofFirm;
import com.prosnav.ivms.repository.ivm.FofFirmRepositoryCustom;

/**
 * @author wangnan
 *
 */
public class FofFirmRepositoryImpl implements FofFirmRepositoryCustom {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MongoTemplate template;

	@Override
	public Page<FofFirm> searchPage(String keyword, PageRequest pageRequest) {
		if (StringUtils.isEmpty(keyword)) {
			return null;
		}

		Query query = new Query();
		Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
		query.addCriteria(
				new Criteria().orOperator(
						Criteria.where("name").regex(pattern),
						Criteria.where("alias").regex(pattern)))
				.skip(pageRequest.getOffset()).limit(pageRequest.getPageSize());
		logger.debug("query\t" + query.toString());

		List<FofFirm> fofFirms = template.find(query, FofFirm.class);
		long count = template.count(query, FofFirm.class);

		Page<FofFirm> fofFirmPage = new PageImpl<FofFirm>(fofFirms, pageRequest,
				count);

		return fofFirmPage;
	}

	@Override
	public void logicDelete(long id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("deled", true);
		
		template.updateFirst(query, update, FofFirm.class);
	}
}
