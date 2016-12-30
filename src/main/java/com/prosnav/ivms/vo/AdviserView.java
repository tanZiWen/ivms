/**
 * 
 */
package com.prosnav.ivms.vo;

import com.prosnav.ivms.model.Adviser;

/**
 * @author wangnan
 *
 */
public class AdviserView extends Adviser {
	
	private String modifyTimeStr;
	
	public Adviser toModel() {
		Adviser a = new Adviser();
		copyTo(a);
		return a;
	}
	
	public void fromModel(Adviser adviser) {
		copyFrom(adviser);
	}

	public String getModifyTimeStr() {
		return modifyTimeStr;
	}

	public void setModifyTimeStr(String modifyTimeStr) {
		this.modifyTimeStr = modifyTimeStr;
	}
}
