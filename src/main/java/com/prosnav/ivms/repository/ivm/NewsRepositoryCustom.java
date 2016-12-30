/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.prosnav.ivms.model.News;

/**
 * @author wangnan
 *
 */
public interface NewsRepositoryCustom {
	public Page<News> searchPage(String keyword,  int type, String name, PageRequest pageRequest);
	
	public void logicDelete(long id);
}
