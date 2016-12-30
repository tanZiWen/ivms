/**
 * 
 */
package com.prosnav.ivms.model;

import org.springframework.beans.BeanUtils;

/**
 * @author wangnan
 *
 */
public class BaseBean {
	
	public void copy(Object src, Object target) {
		BeanUtils.copyProperties(src, target);
	}
	
	public void copyTo(Object target) {
		BeanUtils.copyProperties(this, target);
	}
	
	public void copyFrom(Object src) {
		BeanUtils.copyProperties(src, this);
	}
}
