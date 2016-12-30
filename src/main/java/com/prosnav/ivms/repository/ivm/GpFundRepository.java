/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import com.prosnav.ivms.core.BaseMongoRepository;
import com.prosnav.ivms.model.GpFund;

/**
 * @author wangnan
 *
 */
public interface GpFundRepository extends BaseMongoRepository<GpFund, Long>, GpFundRepositoryCustom {
	
}
