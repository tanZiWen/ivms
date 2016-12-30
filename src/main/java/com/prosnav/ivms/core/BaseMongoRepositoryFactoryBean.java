/**
 * 
 */
package com.prosnav.ivms.core;

import java.io.Serializable;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 * @author wangnan
 *
 */
public class BaseMongoRepositoryFactoryBean<R extends MongoRepository<T, I>, T, I extends Serializable> extends
		MongoRepositoryFactoryBean<R, T, I> {

	@Override
	protected RepositoryFactorySupport getFactoryInstance(
			MongoOperations operations) {
		return new BaseMongoRepositoryFactory<T, Serializable>(operations);
	}
}
