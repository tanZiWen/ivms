package com.prosnav.ivms.service;

import java.util.Collection;
import java.util.Map;

import com.prosnav.ivms.controller.helper.Reference.Condiction;
import com.prosnav.ivms.model.Company;
import com.prosnav.ivms.util.PageView;
import com.prosnav.ivms.vo.CompanyView;

public interface CompanyService {
	boolean addCompany(Company company);
	
	CompanyView getCompany(Long id);
	
	Map<String, Object> listCompany(PageView page);
	
	Map<String, Object> list(CompanyView cv, PageView pv);
	
	Map<String, Object> searchCompany(String keyword, PageView pv);

	boolean updateCompany(Company company);

	boolean deleteCompany(long id);
	
	Map<Long, Company> getAndMap(Condiction condiction);
	
	Map<Long, CompanyView> searchByIds(Collection<Long> ids);
}
