/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import com.prosnav.ivms.core.BaseMongoRepository;
import com.prosnav.ivms.model.Person;

/**
 * @author wangnan
 *
 */
public interface PersonRepository extends BaseMongoRepository<Person, Long>, PersonRepositoryCustom {
	
}
