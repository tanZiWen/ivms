package com.prosnav.ivms.service;

import java.util.List;
import java.util.Map;

import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.model.Fof;
import com.prosnav.ivms.util.PageView;

public interface FofService {
	boolean addFof(Fof fof);
	
	Fof getFof(Long id);
	
	Map<String, Object> listFof(PageView page);
	
	Map<String, Object> searchFof(String keyword, PageView pv);

	boolean updateFof(Fof fof);

	boolean deleteFof(long id);
	
	Map<Long, Fof> getAndMap(Condiction condiction);

	List<Fof> getFofByFirmId(Long id);
}
