package com.prosnav.ivms.service;

import java.util.List;
import java.util.Map;

import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.model.GpFund;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.GpFundView;

public interface GpFundService {
	GpFundView get(Long id);
	
	boolean addGp(GpFundView view);

	Map<String, Object> listGp(PageView page);
	
	Map<String, Object> searchGp(String keyword, PageView pv);
	
	Map<String, Object> searchGp(GpFundView view, PageView pv);

	boolean updateGp(GpFundView view);

	boolean deleteGp(long id);
	
	Map<Long, GpFundView> getAndMap(Condiction condiction);

	List<GpFund> getGPFundByFirmId(Long gpFirmId);
	
}
