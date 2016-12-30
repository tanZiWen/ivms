/**
 * 
 */
package com.prosnav.ivms.model;

import org.springframework.data.annotation.Id;

/**
 * @author wangnan
 *
 */
public class Dictionary implements IModel {
	
	@Id
	private Long id;
	private String type;
	private int value;
	private String label;
	private String labeCn;
	private int order;
	private boolean valid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabeCn() {
		return labeCn;
	}

	public void setLabeCn(String labeCn) {
		this.labeCn = labeCn;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}
