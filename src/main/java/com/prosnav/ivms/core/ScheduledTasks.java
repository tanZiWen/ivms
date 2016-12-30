/**
 * 
 */
package com.prosnav.ivms.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.prosnav.ivms.repository.ivm.TestRepository;

/**
 * @author wangnan
 *
 */
@Component
public class ScheduledTasks {
	
	//private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TestRepository testRepository;
	
	@Scheduled(initialDelay=30000, fixedRate=30000)
	public void mongoAutoRetry() {
		//logger.debug("[mongoAutoRetry] >>>>>>>>>>>>>>>>>>>>>>");
		try {
			testRepository.findOne(1L);
		} catch (Exception e) {
			//logger.error("connect mongodb error", e);
		}
	}
	
}
