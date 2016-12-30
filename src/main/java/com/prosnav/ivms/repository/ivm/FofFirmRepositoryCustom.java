/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.prosnav.ivms.model.FofFirm;

/**
 * @author wuzixiu
 *
 */
public interface FofFirmRepositoryCustom {
	public Page<FofFirm> searchPage(String keyword, PageRequest pageRequest);
	
	public void logicDelete(long id);
}
