/**
 * 
 */
package com.prosnav.ivms.vo;

import org.springframework.beans.BeanUtils;

import com.prosnav.ivms.model.News;

/**
 * @author wangnan
 *
 */
public class NewsView extends News {
	
	public News toModel() {
		News news = new News();
		copyTo(news);
		//TODO something special

		return news;
	}
	
	public static NewsView fromModel(News p) {
		NewsView mv = new NewsView();
		BeanUtils.copyProperties(p, mv);
		
		return mv;
	}
}