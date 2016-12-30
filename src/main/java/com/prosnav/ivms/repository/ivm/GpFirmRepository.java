/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import com.prosnav.ivms.core.BaseMongoRepository;
import com.prosnav.ivms.model.GpFirm;

/**
 * @author wangnan
 *
 */
public interface GpFirmRepository extends BaseMongoRepository<GpFirm, Long>, GpFirmRepositoryCustom {
	
}
