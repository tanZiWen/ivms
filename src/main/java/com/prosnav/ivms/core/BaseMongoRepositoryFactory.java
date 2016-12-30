package com.prosnav.ivms.core;

import java.io.Serializable;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.data.repository.core.RepositoryMetadata;

public class BaseMongoRepositoryFactory<T, I extends Serializable> extends MongoRepositoryFactory {

	private final MongoOperations mongoOperations;
	
	public BaseMongoRepositoryFactory(MongoOperations mongoOperations) {
		super(mongoOperations);
		this.mongoOperations = mongoOperations;
	}

	@Override
	protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
		return BaseMongoRepositoryImpl.class;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object getTargetRepository(RepositoryMetadata metadata) {
		MongoEntityInformation<?, Serializable> entityInformation = getEntityInformation(metadata.getDomainType());
		return new BaseMongoRepositoryImpl(entityInformation, this.mongoOperations);
	}

}
