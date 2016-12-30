package com.prosnav.ivms.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DBOperateLog extends BaseBean implements IModel {
	@Id
	private Long id;
	private String method;
	private String historyRecord;
	private String type;
	private Long userId;
	private String username;
	private String realName;
	private Date crt;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getHistoryRecord() {
		return historyRecord;
	}
	public void setHistoryRecord(String historyRecord) {
		this.historyRecord = historyRecord;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Date getCrt() {
		return crt;
	}
	public void setCrt(Date crt) {
		this.crt = crt;
	}
}
