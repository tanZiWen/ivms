package com.prosnav.ivms.service.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.prosnav.ivms.model.News;
import com.prosnav.ivms.repository.ivm.NewsRepository;
import com.prosnav.ivms.service.NewsService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.NewsView;

@Service
public class NewsServiceImpl extends AbstractService implements
		NewsService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NewsRepository newsRepository;

	@Override
	public Map<String, Object> listNews(PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		Criteria condition = Criteria.where("deled").is(false);
		Page<News> page = newsRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("newss", page.getContent());
		return result;
	}

	@Override
	public Map<String, Object> searchNews(String keyword, int type, String name, PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Page<News> page = newsRepository.searchPage(keyword, type, name, pr);
		pv.setCount(page.getTotalElements());
		
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("newss", page.getContent());
		
		return result;
	}

	@Override
	public boolean addNews(News news) {
		try {
			Date now = new Date();
			news.setCrt(now);
			news.setLut(now);
			news.setId(newsRepository.nextId(idg));
			news = newsRepository.save(news);
			return true;
		} catch (Exception e) {
			logger.error("Failed to add person", e);
			return false;
		}
	}

	@Override
	public boolean updateNews(News news) {
		try {
			news.setLut(new Date());
			news = newsRepository.save(news);
			return true;
		} catch (Exception e) {
			logger.error("Failed to update person", e);
			return false;
		}
	}

	@Override
	public boolean deleteNews(long id) {
		try {
			newsRepository.logicDelete(id);
			return true;
		} catch (Exception e) {
			logger.error("Failed to delete news", e);
			return false;
		}
	}

	@Override
	public NewsView getNews(long id) {
		News news = newsRepository.findOne(id);

		if (news == null) {
			return null;
		}
		
		return NewsView.fromModel(news);
	}

}
