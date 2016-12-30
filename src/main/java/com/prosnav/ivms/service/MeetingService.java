package com.prosnav.ivms.service;

import java.util.Map;

import com.prosnav.ivms.model.Meeting;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.MeetingView;

public interface MeetingService {
	Map<String, Object> listMeeting(PageView page);
	
	Map<String, Object> searchMeeting(String keyword, int type, String name, PageView pv);

	boolean addMeeting(Meeting meeting);

	boolean updateMeeting(Meeting meeting);
	
	boolean deleteMeeting(long id);
	
	MeetingView getMeeting(long id);
}
