package com.prosnav.ivms.core;

public class Role {
	private String code;
	private String name;
	private String appCode;
	private String[] fnCodes;

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String[] getFnCodes() {
		return fnCodes;
	}
	public void setFnCodes(String[] fnCodes) {
		this.fnCodes = fnCodes;
	}
}
