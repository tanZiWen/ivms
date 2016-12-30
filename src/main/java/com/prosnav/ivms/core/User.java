package com.prosnav.ivms.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



public class User {
	private Long _id;
	private String username;
	private String realName;
	private String sex;
	private String position;
	private String status;
	private String[] roleCodes;
	private long employeeid;
	private String employeeCode;
	private String workno;
	private String email;
	private String orgCode;
	private long role_id;
	private String area;
	private long workgroupId;
	private String[] workgroupRoleCodes;
	private Organization org;
	private Map<String, Role> roleMap;
	private List<Role> roleList;
	private Map<String, Function> functionMap;
	private List<Function> functionList;

	public Long get_id() {
		return _id;
	}

	public void set_id(Long _id) {
		this._id = _id;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(long employeeid) {
		this.employeeid = employeeid;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public String getWorkno() {
		return workno;
	}

	public void setWorkno(String workno) {
		this.workno = workno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

	public String[] getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(String[] roleCodes) {
		this.roleCodes = roleCodes;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public long getRole_id() {
		return role_id;
	}

	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public long getWorkgroupId() {
		return workgroupId;
	}

	public void setWorkgroupId(long workgroupId) {
		this.workgroupId = workgroupId;
	}

	public String[] getWorkgroupRoleCodes() {
		return workgroupRoleCodes;
	}

	public void setWorkgroupRoleCodes(String[] workgroupRoleCodes) {
		this.workgroupRoleCodes = workgroupRoleCodes;
	}

	public Map<String, Role> getRoleMap() {
		return roleMap;
	}

	public void setRoleMap(Map<String, Role> roleMap) {
		this.roleMap = roleMap;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public Map<String, Function> getFunctionMap() {
		return functionMap;
	}

	public void setFunctionMap(Map<String, Function> functionMap) {
		this.functionMap = functionMap;
	}

	public List<Function> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<Function> functionList) {
		this.functionList = functionList;
	}

	@Override
	public String toString() {
		return "User [realName=" + realName + ", sex=" + sex + ", position=" + position + ", status=" + status
				+ ", employeeid=" + employeeid + ", workno=" + workno + ", email=" + email + ", orgCode=" + orgCode
				+ ", role_id=" + role_id + ", area=" + area + ", workgroupId=" + workgroupId + ", workgroupRoleCodes="
				+ Arrays.toString(workgroupRoleCodes) + ", roleMap=" + roleMap + ", roleList=" + roleList
				+ ", functionMap=" + functionMap + ", functionList=" + functionList + "]";
	}
}
