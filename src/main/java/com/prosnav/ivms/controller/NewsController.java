package com.prosnav.ivms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prosnav.ivms.controller.helper.Reference;
import com.prosnav.ivms.controller.helper.ReferenceFinder;
import com.prosnav.ivms.model.News;
import com.prosnav.ivms.model.News.Topic;
import com.prosnav.ivms.model.Relation;
import com.prosnav.ivms.service.NewsService;
import com.prosnav.ivms.service.PersonService;
import com.prosnav.ivms.util.PageView;

@Controller
public class NewsController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NewsService newsService;

	@Autowired
	private PersonService personService;

	@Autowired
	private ReferenceFinder referenceFinder;

	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getNewsList(ModelMap model,
			HttpServletRequest request) {
		logger.debug("get person list");
		PageView page = new PageView(request);
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = newsService.listNews(page);

			return result;
		} catch (Exception e) {
			logger.error("failed to get news list", e.getCause());
			result.put("code", 505);
			return result;
		}
	}

	@RequestMapping(value = "/news/search", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> searchNews(
			@RequestParam String keyword, @RequestParam int type, @RequestParam String name, HttpServletRequest request) {

		logger.debug("search news by keyword\t" + keyword);
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			result = newsService.searchNews(keyword, type, name,
					new PageView(request));
			result.put("code", 200);
			return result;
		} catch (Exception e) {
			logger.error("failed to seach news" + e.getCause());
			result = new HashMap<String, Object>();
			result.put("code", 505);
			return result;
		}
	}

	@RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getNewsList(
			@PathVariable int id, ModelMap model) {
		logger.debug("get news ", id);
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			News news = newsService.getNews(id);
			if (news == null) {
				return result;
			}

			result.put("news", news);

			Reference reference = new Reference();

			for (Topic topic : news.getTopics()) {
				for (Relation relation : topic.getRelations()) {
					if (relation.getType() == null || relation.getRid() == null) {
						continue;
					}

					reference.with(relation.getType(), relation.getRid());
				}
			}

			Map<String, Object> refData = referenceFinder.find(reference);
			result.put("reference", refData);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("failed to get news", e.getCause(), e.getMessage());
			result.put("code", 505);
			return result;
		}
	}

	@RequestMapping(value = "/news", method = RequestMethod.POST)
	public @ResponseBody Object createNews(@RequestBody News news,
			ModelMap model, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (newsService.addNews(news)) {
				result.put("news", news);
			} else {
				result.put("code", 500);
				result.put("message", "failed to save news.");
			}
		} catch (Exception e) {
			logger.error("Failed to save news", e.getCause());
			result.put("code", 500);
			result.put("message", e.getCause());
		}

		return result;
	}

	@RequestMapping(value = "/news", method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> updateNews(
			@RequestBody News news, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>();

		boolean ok = newsService.updateNews(news);

		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}

	@RequestMapping(value = "/news/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> deleteNews(
			@PathVariable long id, ModelMap model) {
		Map<String, Object> result = new HashMap<String, Object>();

		boolean ok = newsService.deleteNews(id);

		if (!ok) {
			result.put("code", 500);
		}

		return result;
	}
}
