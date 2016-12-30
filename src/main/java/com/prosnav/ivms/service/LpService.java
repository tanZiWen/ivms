package com.prosnav.ivms.service;

import java.util.Map;

import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.model.Lp;
import com.prosnav.ivms.util.PageView;

public interface LpService {
	boolean addLp(Lp lp);

	Map<String, Object> listLp(PageView page);
	
	Map<String, Object> searchLp(String keyword, PageView pv);

	boolean updateLp(Lp lp);

	boolean deleteLp(long id);
	
	Map<Long, Lp> getAndMap(Condiction condiction);
}
