package com.prosnav.ivms.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class News extends BaseBean implements IModel {
	@Id
	private Long id;
	private String title;
	private String notes;
	private String url;
	private Long advType;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	private String date;
	
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
	
	private List<Topic> topics;

	public static class Topic {
		private String notes;
		private List<Relation> relations;
		
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
		public List<Relation> getRelations() {
			return relations;
		}
		public void setRelations(List<Relation> relations) {
			this.relations = relations;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getAdvType() {
		return advType;
	}

	public void setAdvType(Long advType) {
		this.advType = advType;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", notes=" + notes
				+ ", url=" + url + ", advType=" + advType + ", date=" + date
				+ ", luuid=" + luuid + ", crt=" + crt + ", lut=" + lut
				+ ", status=" + status + ", deled=" + deled + ", v=" + v
				+ ", topics=" + topics + "]";
	}
}
