/**
 * 
 */
package com.prosnav.ivms.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.AdviserView;

/**
 * @author wangnan
 *
 */
@Service
public interface AdviserService {
	Map<String, Object> list(Map<String, Object> params, PageView pv);
	boolean save(AdviserView adviserView);
	AdviserView get(Long id);
	boolean remove(Long id);
}
