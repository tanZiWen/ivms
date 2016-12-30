package com.prosnav.ivms.vo;

import com.prosnav.ivms.model.Lp;

public class LpView extends Lp {
	
	public Lp toModel() {
		Lp lp = new Lp();
		this.copyTo(lp);
		return lp;
	}
	
	public void fromModel(Lp lp) {
		this.copyFrom(lp);
	}
}
