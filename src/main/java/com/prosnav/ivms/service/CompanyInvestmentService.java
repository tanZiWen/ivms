/**
 * 
 */
package com.prosnav.ivms.service;

import java.util.List;
import java.util.Map;

import com.prosnav.ivms.model.CompanyInvestment;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.CompanyInvestmentView;

/**
 * @author wangnan
 *
 */
public interface CompanyInvestmentService {
	
	Map<String, Object> listCompanyInvestment(Map<String, Object> params, PageView pv);
	boolean addCompanyInvestment(CompanyInvestmentView view);
	CompanyInvestmentView getCompanyInvestment(Long id);
	boolean removeCompanyInvestment(Long id);
	void mergeCompany(List<CompanyInvestmentView> views);
	List<CompanyInvestment> getCompanyInvestmentByFofId(Long id);
	List<CompanyInvestment> getCompanyInvestmentByGPFundId(Long id);
}
