/**
 * 
 */
package com.prosnav.ivms.repository.ivm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.prosnav.ivms.model.Meeting;

/**
 * @author wangnan
 *
 */
public interface MeetingRepositoryCustom {
	public Page<Meeting> searchPage(String keyword, int type, String name, PageRequest pageRequest);
	
	public void logicDelete(long id);
}
