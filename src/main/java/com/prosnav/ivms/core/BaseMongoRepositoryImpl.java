/**
 * 
 */
package com.prosnav.ivms.core;

import static org.springframework.data.domain.Sort.Direction.DESC;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;

import com.prosnav.ivms.model.IModel;

/**
 * @author wangnan
 *
 */
public class BaseMongoRepositoryImpl<T extends IModel, ID extends Serializable> extends SimpleMongoRepository<T, ID>
		implements BaseMongoRepository<T, ID>, IRedisIDGeneratorSupporter {
	
	private final MongoOperations mongoOperations;
	private final MongoEntityInformation<T, ID> entityInformation;
	private final Long initId;

	public BaseMongoRepositoryImpl(MongoEntityInformation<T, ID> metadata,
			MongoOperations mongoOperations) {
		super(metadata, mongoOperations);
		this.mongoOperations = mongoOperations;
		this.entityInformation = metadata;
		T entity = mongoOperations.findOne(new Query().with(
				new Sort(DESC, "id")), this.entityInformation.getJavaType());
		if(entity == null) {
			this.initId = 1L;
		} else {
			this.initId = entity.getId();
		}
		
	}

	@Override
	public Page<T> findPage(String rawCondition, Pageable pageable) {
		Query query = new BasicQuery(rawCondition).with(pageable);
		List<T> list = mongoOperations.find(
				query, this.entityInformation.getJavaType(), this.entityInformation.getCollectionName());
		return new PageImpl<T>(list, pageable, count(query));
	}

	@Override
	public Page<T> findPage(Criteria criteria, Pageable pageable) {
		Query query = new Query(criteria).with(pageable);
		List<T> list = mongoOperations.find(
				query, this.entityInformation.getJavaType(), this.entityInformation.getCollectionName());
		return new PageImpl<T>(list, pageable, count(query));
	}
	
	@Override
	public List<T> find(String rawCondition, Sort sort) {
		Query query = new BasicQuery(rawCondition);
		if(sort != null) {
			query.with(sort);
		}
		List<T> list = mongoOperations.find(
				query, this.entityInformation.getJavaType(), this.entityInformation.getCollectionName());
		return list;
	}

	@Override
	public List<T> find(Criteria criteria, Sort sort) {
		Query query = new Query(criteria);
		if(sort != null) {
			query.with(sort);
		}
		List<T> list = mongoOperations.find(
				query, this.entityInformation.getJavaType(), this.entityInformation.getCollectionName());
		return list;
	}

	@Override
	public Long getInitId() {
		return initId;
	}

	@Override
	public String getKey() {
		return this.entityInformation.getJavaType().getName();
	}

	@Override
	public Long nextId(IDGenerator<IRedisIDGeneratorSupporter> idg) {
		return idg.next(this);
	}

	private long count(Query query) {
		return mongoOperations.count(query, this.entityInformation.getJavaType());
	}
}
