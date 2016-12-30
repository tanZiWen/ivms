package com.prosnav.ivms.core;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.prosnav.ivms.model.IModel;

public interface BaseMongoRepository<T extends IModel, ID extends Serializable> extends MongoRepository<T, ID> {
	public Long nextId(IDGenerator<IRedisIDGeneratorSupporter> idg);
	public Page<T> findPage(String rawCondition, Pageable pageable);
	public Page<T> findPage(Criteria criteria, Pageable pageable);
	public List<T> find(String rawCondition, Sort sort);
	public List<T> find(Criteria criteria, Sort sort);
}
