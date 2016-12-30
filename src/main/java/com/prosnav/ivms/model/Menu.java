package com.prosnav.ivms.model;

public class Menu {
	private Integer id;
	private String name;
	private String funcAction;
	private boolean divider;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFuncAction() {
		return funcAction;
	}
	public void setFuncAction(String funcAction) {
		this.funcAction = funcAction;
	}
	public boolean isDivider() {
		return divider;
	}
	public void setDivider(boolean divider) {
		this.divider = divider;
	}
}
