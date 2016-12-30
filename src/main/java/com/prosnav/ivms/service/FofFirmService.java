package com.prosnav.ivms.service;

import java.util.Map;

import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.model.FofFirm;
import com.prosnav.ivms.util.PageView;

public interface FofFirmService {
	boolean addFofFirm(FofFirm fofFirm);
	
	FofFirm getFofFirm(Long id);
	
	Map<String, Object> listFofFirm(Map<String, Object> params, PageView page);
	
	Map<String, Object> searchFofFirm(String keyword, PageView pv);

	boolean updateFofFirm(FofFirm fofFirm);

	boolean deleteFofFirm(long id);
	
	Map<Long, FofFirm> getAndMap(Condiction condiction);
}
