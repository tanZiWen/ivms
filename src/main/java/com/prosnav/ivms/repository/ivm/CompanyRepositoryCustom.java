/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.prosnav.ivms.model.Company;

/**
 * @author wuzixiu
 *
 */
public interface CompanyRepositoryCustom {
	public Page<Company> searchPage(String keyword, PageRequest pageRequest);
	
	public void logicDelete(long id);
}
