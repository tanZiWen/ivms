/**
 * 
 */
package com.prosnav.ivms.core;

/**
 * @author wangnan
 *
 */
public interface IDGenerator<T> {
	public Long next(T type);
}
