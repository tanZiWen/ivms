package com.prosnav.ivms.service.impl;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.prosnav.ivms.model.Meeting;
import com.prosnav.ivms.repository.ivm.MeetingRepository;
import com.prosnav.ivms.service.MeetingService;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.MeetingView;

@Service
public class MeetingServiceImpl extends AbstractService implements
		MeetingService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MeetingRepository meetingRepository;

	@Override
	public Map<String, Object> listMeeting(PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		Criteria condition = Criteria.where("deled").is(false);
		Page<Meeting> page = meetingRepository.findPage(condition, pr);
		pv.setCount(page.getTotalElements());
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("meetings", page.getContent());
		return result;
	}

	@Override
	public Map<String, Object> searchMeeting(String keyword, int type, String name, PageView pv) {
		PageRequest pr = new PageRequest(pv.getPage() - 1, pv.getSize());
		
		Page<Meeting> page = meetingRepository.searchPage(keyword, type, name, pr);
		pv.setCount(page.getTotalElements());
		
		Map<String, Object> result = new java.util.HashMap<String, Object>();
		result.put("pager", pv);
		result.put("meetings", page.getContent());
		
		return result;
	}

	@Override
	public boolean addMeeting(Meeting meeting) {
		try {
			Date now = new Date();
			meeting.setCrt(now);
			meeting.setLut(now);
			meeting.setId(meetingRepository.nextId(idg));
			meeting = meetingRepository.save(meeting);
			return true;
		} catch (Exception e) {
			logger.error("Failed to add person", e);
			return false;
		}
	}

	@Override
	public boolean updateMeeting(Meeting meeting) {
		try {
			meeting.setLut(new Date());
			meeting = meetingRepository.save(meeting);
			return true;
		} catch (Exception e) {
			logger.error("Failed to update person", e);
			return false;
		}
	}

	@Override
	public boolean deleteMeeting(long id) {
		try {
			meetingRepository.logicDelete(id);
			return true;
		} catch (Exception e) {
			logger.error("Failed to delete meeting", e);
			return false;
		}
	}

	@Override
	public MeetingView getMeeting(long id) {
		Meeting meeting = meetingRepository.findOne(id);

		if (meeting == null) {
			return null;
		}
		
		return MeetingView.fromModel(meeting);
	}

}
