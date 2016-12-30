/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.prosnav.ivms.model.GpFirm;

/**
 * @author wuzixiu
 *
 */
public interface GpFirmRepositoryCustom {
	public Page<GpFirm> searchPage(String keyword, PageRequest pageRequest);
	
	public void logicDelete(long id);
}
