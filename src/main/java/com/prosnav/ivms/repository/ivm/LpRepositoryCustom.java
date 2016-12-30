/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.prosnav.ivms.model.Lp;

/**
 * @author wuzixiu
 *
 */
public interface LpRepositoryCustom {
	public Page<Lp> searchPage(String keyword, PageRequest pageRequest);
	
	public void logicDelete(long id);
}
