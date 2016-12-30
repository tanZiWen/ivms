/**
 * 
 */
package com.prosnav.ivms.vo;

import java.util.List;

import com.prosnav.ivms.model.Company;

/**
 * @author wangnan
 *
 */
public class CompanyView extends Company {
	
	private String lutStr;
	private List<CompanyView> companyViews;
	
	public Company toModel() {
		Company company = new Company();
		this.copyTo(company);
		return company;
	}
	
	public void fromModel(Company company) {
		this.copyFrom(company);
	}

	public List<CompanyView> getCompanyViews() {
		return companyViews;
	}

	public void setCompanyViews(List<CompanyView> companyViews) {
		this.companyViews = companyViews;
	}

	public String getLutStr() {
		return lutStr;
	}

	public void setLutStr(String lutStr) {
		this.lutStr = lutStr;
	}
}
