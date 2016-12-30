/**
 * 
 */
package com.prosnav.ivms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author wangnan
 *
 */
@Document
public class Test implements IModel {
	
	@Id
	private Long id;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
