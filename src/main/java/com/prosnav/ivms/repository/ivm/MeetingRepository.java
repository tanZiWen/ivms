package com.prosnav.ivms.repository.ivm;

import com.prosnav.ivms.core.BaseMongoRepository;
import com.prosnav.ivms.model.Meeting;

public interface MeetingRepository extends BaseMongoRepository<Meeting, Long>,
		MeetingRepositoryCustom {

}
