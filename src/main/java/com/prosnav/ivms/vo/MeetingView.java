/**
 * 
 */
package com.prosnav.ivms.vo;

import org.springframework.beans.BeanUtils;

import com.prosnav.ivms.model.Meeting;

/**
 * @author wangnan
 *
 */
public class MeetingView extends Meeting {
	
	public Meeting toModel() {
		Meeting meeting = new Meeting();
		copyTo(meeting);
		//TODO something special

		return meeting;
	}
	
	public static MeetingView fromModel(Meeting p) {
		MeetingView mv = new MeetingView();
		BeanUtils.copyProperties(p, mv);
		
		return mv;
	}
}