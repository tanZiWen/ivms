/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.prosnav.ivms.model.Fof;

/**
 * @author wuzixiu
 *
 */
public interface FofRepositoryCustom {
	public Page<Fof> searchPage(String keyword, PageRequest pageRequest);
	
	public void logicDelete(long id);

	List<Fof> getFofByFirmId(Long id);
}
