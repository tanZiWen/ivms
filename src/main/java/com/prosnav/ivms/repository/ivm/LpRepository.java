/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import com.prosnav.ivms.core.BaseMongoRepository;
import com.prosnav.ivms.model.Lp;

/**
 * @author wangnan
 *
 */
public interface LpRepository extends BaseMongoRepository<Lp, Long>, LpRepositoryCustom {
	
}
