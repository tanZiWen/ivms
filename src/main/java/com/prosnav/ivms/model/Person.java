package com.prosnav.ivms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@Document
public class Person extends BaseBean implements IModel {
	
	public static final String[] FIELDS_MINIMAL = new String[] {"id", "name"};
	
	@Id
	private Long id;
	private String name;
	private String enname;
	private String tel;
	private String email;
	private String corpName;
	private String corpType;
	private String title;
	private String edu;
	private String notes;
	/**
	 * last update user id
	 */
	private String luuid;
	/**
	 * create time
	 */
	private Date crt;
	/**
	 * last update time
	 */
	private Date lut;

	private int status;

	private boolean deled;

	private int v;

	private List<WorkExperience> workExperiences = new ArrayList<WorkExperience>();

	public static class WorkExperience extends BaseBean implements Comparable<WorkExperience> {
		private Integer corpType;
		private Long corpId;
		private String corpName;
		private Integer titleCode;
		private String title;
		private Date joinDate;
		private Date leaveDate;
		private String notes;
		private Boolean primary;

		public Boolean getPrimary() {
			return primary;
		}


		public void setPrimary(Boolean primary) {
			this.primary = primary;
		}


		public Integer getCorpType() {
			return corpType;
		}


		public void setCorpType(Integer corpType) {
			this.corpType = corpType;
		}


		public Long getCorpId() {
			return corpId;
		}


		public void setCorpId(Long corpId) {
			this.corpId = corpId;
		}


		public String getCorpName() {
			return corpName;
		}


		public void setCorpName(String corpName) {
			this.corpName = corpName;
		}


		public Integer getTitleCode() {
			return titleCode;
		}


		public void setTitleCode(Integer titleCode) {
			this.titleCode = titleCode;
		}


		public String getTitle() {
			return title;
		}


		public void setTitle(String title) {
			this.title = title;
		}


		public Date getJoinDate() {
			return joinDate;
		}


		public void setJoinDate(Date joinDate) {
			this.joinDate = joinDate;
		}


		public Date getLeaveDate() {
			return leaveDate;
		}


		public void setLeaveDate(Date leaveDate) {
			this.leaveDate = leaveDate;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}
		
		
		@Override
		public int compareTo(WorkExperience o) {
			if(o != null) {
				if(o.primary) {
					return 1;
				}
			}
			return leaveDate == null ? 0 : leaveDate.compareTo(o == null ? null
					: o.getLeaveDate());
		}


		@Override
		public String toString() {
			return "WorkExperience [corpType=" + corpType + ", corpId="
					+ corpId + ", corpName=" + corpName + ", titleCode="
					+ titleCode + ", title=" + title + ", joinDate=" + joinDate
					+ ", leaveDate=" + leaveDate + ", notes=" + notes
					+ ", primary=" + primary + "]";
		}
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCorpType() {
		return corpType;
	}

	public void setCorpType(String corpType) {
		this.corpType = corpType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getLuuid() {
		return luuid;
	}

	public void setLuuid(String luuid) {
		this.luuid = luuid;
	}

	public Date getCrt() {
		return crt;
	}

	public void setCrt(Date crt) {
		this.crt = crt;
	}

	public Date getLut() {
		return lut;
	}

	public void setLut(Date lut) {
		this.lut = lut;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isDeled() {
		return deled;
	}

	public void setDeled(boolean deled) {
		this.deled = deled;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}

	public List<WorkExperience> getWorkExperiences() {
		return workExperiences;
	}

	public void setWorkExperiences(List<WorkExperience> workExperiences) {
		this.workExperiences = workExperiences;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.WriteDateUseDateFormat);
	}
}
