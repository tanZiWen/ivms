/**
 * 
 */
package com.prosnav.ivms.vo;

import com.prosnav.ivms.model.Company.FinancingRound;
import com.prosnav.ivms.model.CompanyInvestment;
import com.prosnav.ivms.model.Relation;

/**
 * @author wangnan
 *
 */
public class CompanyInvestmentView extends CompanyInvestment {
	
	private CompanyView companyView;
	private FinancingRound financingRound;
	private String financingRoundName = "";
	private Long relationRid;
	private int relationType;
	private boolean withCompany = false;

	public CompanyInvestment toModel() {
		CompanyInvestment gpFundInvestment = new CompanyInvestment();
		this.copyTo(gpFundInvestment);
		return gpFundInvestment;
	}
	
	public void fromModel(CompanyInvestment gpFundInvestment) {
		this.copyFrom(gpFundInvestment);
	}

	public CompanyView getCompanyView() {
		return companyView;
	}

	public void setCompanyView(CompanyView companyView) {
		this.companyView = companyView;
	}

	public String getFinancingRoundName() {
		return financingRoundName;
	}

	public void setFinancingRoundName(String financingRoundName) {
		this.financingRoundName = financingRoundName;
	}

	public FinancingRound getFinancingRound() {
		return financingRound;
	}

	public void setFinancingRound(FinancingRound financingRound) {
		this.financingRound = financingRound;
	}

	public boolean isWithCompany() {
		return withCompany;
	}

	public void setWithCompany(boolean withCompany) {
		this.withCompany = withCompany;
	}
	
	public Long getRelationRid() {
		return relationRid;
	}

	public void setRelationRid(Long relationRid) {
		this.relationRid = relationRid;
	}

	public int getRelationType() {
		return relationType;
	}

	public void setRelationType(int relationType) {
		this.relationType = relationType;
	}

	@Override
	public String toString() {
		return "CompanyInvestmentView [companyView=" + companyView
				+ ", financingRound=" + financingRound
				+ ", financingRoundName=" + financingRoundName
				+ ", relationRid=" + relationRid + ", relationType="
				+ relationType + ", withCompany=" + withCompany + "]";
	}
	
	
}
