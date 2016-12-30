package com.prosnav.ivms.service;

import java.util.Map;

import com.prosnav.ivms.model.News;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.NewsView;

public interface NewsService {
	Map<String, Object> listNews(PageView page);
	
	Map<String, Object> searchNews(String keyword, int type, String name, PageView pv);

	boolean addNews(News news);

	boolean updateNews(News news);
	
	boolean deleteNews(long id);
	
	NewsView getNews(long id);
}
