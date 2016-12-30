/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.prosnav.ivms.model.GpFund;

/**
 * @author wuzixiu
 *
 */
public interface GpFundRepositoryCustom {
	public Page<GpFund> searchPage(String keyword, PageRequest pageRequest);
	
	public void logicDelete(long id);

	List<GpFund> getGPFundByFirmId(Long id);
}
