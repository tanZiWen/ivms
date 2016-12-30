/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import com.prosnav.ivms.core.BaseMongoRepository;
import com.prosnav.ivms.model.Company;

/**
 * @author wangnan
 *
 */
public interface CompanyRepository extends BaseMongoRepository<Company, Long>, CompanyRepositoryCustom {
	
}
