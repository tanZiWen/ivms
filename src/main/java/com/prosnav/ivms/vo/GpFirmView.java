/**
 * 
 */
package com.prosnav.ivms.vo;

import com.prosnav.ivms.model.GpFirm;

/**
 * @author wangnan
 *
 */
public class GpFirmView extends GpFirm {
	
	public GpFirm toModel() {
		GpFirm gpFirm = new GpFirm();
		this.copyTo(gpFirm);
		return gpFirm;
	}
	
	public void fromModel(GpFirm gpFirm) {
		this.copyFrom(gpFirm);
	}
}
