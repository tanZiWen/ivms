package com.prosnav.ivms.vo;

import com.prosnav.ivms.model.GpFundInvestment;

public class GpFundInvestmentView extends GpFundInvestment{
	private Long relationRid;
	private int relationType;
	
	public GpFundInvestment toModel() {
		GpFundInvestment gpFundInvestment = new GpFundInvestment();
		this.copyTo(gpFundInvestment);
		return gpFundInvestment;
	}
	
	public void fromModel(GpFundInvestment gpFundInvestment) {
		this.copyFrom(gpFundInvestment);
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
}
