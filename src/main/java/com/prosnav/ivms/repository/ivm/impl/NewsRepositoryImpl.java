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

import com.prosnav.ivms.model.News;
import com.prosnav.ivms.repository.ivm.NewsRepositoryCustom;

/**
 * @author wangnan
 *
 */
public class NewsRepositoryImpl implements NewsRepositoryCustom {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MongoTemplate template;

	@Override
	public Page<News> searchPage(String keyword, int type, String name, PageRequest pageRequest) {
		Query query = new Query();
		
		if (!StringUtils.isEmpty(keyword)) {
			Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
			query.addCriteria(Criteria.where("title").regex(pattern));
		}
		
		if(type != 0 && !StringUtils.isEmpty(name)) {
			query.addCriteria(Criteria.where("topics.relations").elemMatch(Criteria.where("type").is(type).and("name").is(name)));
		}else if(type != 0 && StringUtils.isEmpty(name)) {
			query.addCriteria(Criteria.where("topics.relations").elemMatch(Criteria.where("type").is(type)));
		}else if(type == 0 && !StringUtils.isEmpty(name)) {
			query.addCriteria(Criteria.where("topics.relations").elemMatch(Criteria.where("name").is(name)));
		}
		
		query.addCriteria(Criteria.where("deled").is(false));
		
		query.skip(pageRequest.getOffset()).limit(pageRequest.getPageSize());
		
		logger.debug("query\t" + query.toString());

		List<News> newss = template.find(query, News.class);
		long count = template.count(query, News.class);

		Page<News> newsPage = new PageImpl<News>(newss,
				pageRequest, count);

		return newsPage;
	}

	@Override
	public void logicDelete(long id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("deled", true);
		
		template.updateFirst(query, update, News.class);
	}
}
