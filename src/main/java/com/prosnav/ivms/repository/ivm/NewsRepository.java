package com.prosnav.ivms.repository.ivm;

import com.prosnav.ivms.core.BaseMongoRepository;
import com.prosnav.ivms.model.News;

public interface NewsRepository extends BaseMongoRepository<News, Long>,
		NewsRepositoryCustom {

}
