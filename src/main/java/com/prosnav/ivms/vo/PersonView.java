/**
 * 
 */
package com.prosnav.ivms.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.prosnav.ivms.model.Person;

/**
 * @author wangnan
 *
 */
public class PersonView extends Person {
	
	private List<WorkExperienceView> workExperienceViews = new ArrayList<WorkExperienceView>();

	public List<WorkExperienceView> getWorkExperienceViews() {
		return workExperienceViews;
	}

	public void setWorkExperienceViews(List<WorkExperienceView> workExperienceViews) {
		this.workExperienceViews = workExperienceViews;
	}

	public Person toModel() throws ParseException {
		Person p = new Person();
		copyTo(p);
		List<Person.WorkExperience> weList = new ArrayList<Person.WorkExperience>();
		p.setWorkExperiences(weList);
		if(this.getWorkExperienceViews() != null) {
			for(WorkExperienceView wev : this.getWorkExperienceViews()) {
				weList.add(wev.toModel());
			}
		}
		return p;
	}
	
	public void fromModel(Person p) {
		copyFrom(p);
		List<PersonView.WorkExperienceView> wevList = new ArrayList<PersonView.WorkExperienceView>();
		this.setWorkExperienceViews(wevList);
		if(p.getWorkExperiences() != null) {
			for(WorkExperience we : p.getWorkExperiences()) {
				WorkExperienceView wev = new WorkExperienceView();
				wev.fromModel(we);
				wevList.add(wev);
			}
		}
	}
	
	public static class WorkExperienceView extends WorkExperience {
		
		private String joinDateStr;
		private String leaveDateStr;
		
		public String getJoinDateStr() {
			return joinDateStr;
		}

		public void setJoinDateStr(String joinDateStr) {
			this.joinDateStr = joinDateStr;
		}

		public String getLeaveDateStr() {
			return leaveDateStr;
		}

		public void setLeaveDateStr(String leaveDateStr) {
			this.leaveDateStr = leaveDateStr;
		}

		public WorkExperience toModel() throws ParseException {
			WorkExperience we = new WorkExperience();
			copyTo(we);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(!StringUtils.isEmpty(this.getJoinDateStr()))
				we.setJoinDate(sdf.parse(this.getJoinDateStr()));
			if(!StringUtils.isEmpty(this.getLeaveDateStr()))
				we.setLeaveDate(sdf.parse(this.getLeaveDateStr()));
			return we;
		}
		
		public void fromModel(WorkExperience we) {
			copyFrom(we);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(we.getJoinDate() == null) {
				this.setJoinDateStr("");
			} else {
				this.setJoinDateStr(sdf.format(we.getJoinDate()));
			}
			if(we.getLeaveDate() == null) {
				this.setLeaveDateStr("");
			} else {
				this.setLeaveDateStr(sdf.format(we.getLeaveDate()));
			}
		}
	}
}