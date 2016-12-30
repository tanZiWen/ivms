/**
 * 
 */
package com.prosnav.ivms.model;

/**
 * @author wuzixiu
 *
 */
public class Relation {
	private Integer type;
	private String name;
	private Long rid;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}