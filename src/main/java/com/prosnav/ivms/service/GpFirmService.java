package com.prosnav.ivms.service;

import java.util.Map;

import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.model.GpFirm;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.GpFirmView;

public interface GpFirmService {
	boolean addGpFirm(GpFirm gpFirm);

	Map<String, Object> listGpFirm(PageView page);
	
	Map<String, Object> searchGpFirm(String keyword, PageView pv);

	boolean updateGpFirm(GpFirm gpFirm);

	boolean deleteGpFirm(long id);
	
	Map<Long, GpFirm> getAndMap(Condiction condiction);
	
	GpFirmView get(Long id);
}
