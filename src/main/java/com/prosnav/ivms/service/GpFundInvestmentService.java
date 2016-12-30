package com.prosnav.ivms.service;

import java.util.List;
import java.util.Map;

import com.prosnav.ivms.model.GpFundInvestment;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.GpFundInvestmentView;

public interface GpFundInvestmentService {
	Map<String, Object> listGpFundInvestment(Map<String, Object> params, PageView pv);
	
	boolean addGpFundInvestment(GpFundInvestmentView view);
	
	GpFundInvestmentView getGpFundInvestment(Long id);
	
	boolean removeGpFundInvestment(Long id);

	List<GpFundInvestment> getGpFundInvestmentByFofId(Long id);
}
