/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import com.prosnav.ivms.core.BaseMongoRepository;
import com.prosnav.ivms.model.Fof;

/**
 * @author wangnan
 *
 */
public interface FofRepository extends BaseMongoRepository<Fof, Long>, FofRepositoryCustom {
	
}
