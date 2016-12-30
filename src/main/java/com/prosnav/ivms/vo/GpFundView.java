/**
 * 
 */
package com.prosnav.ivms.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.util.StringUtils;

import com.prosnav.ivms.model.GpFund;

/**
 * @author wangnan
 *
 */
public class GpFundView extends GpFund {
	
	private String firstClosingDateStr;
	private String finalClosingDateStr;
	
	public GpFund toModel() {
		GpFund entity = new GpFund();
		this.copyTo(entity);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(!StringUtils.isEmpty(firstClosingDateStr)) {
			try {
				entity.setFirstClosingDate(sdf.parse(firstClosingDateStr));
			} catch (ParseException e) {
			}
		}
		if(!StringUtils.isEmpty(finalClosingDateStr)) {
			try {
				entity.setFinalClosingDate(sdf.parse(finalClosingDateStr));
			} catch (ParseException e) {
			}
		}
		return entity;
	}
	
	public void fromModel(GpFund gpFund) {
		this.copyFrom(gpFund);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(gpFund.getFirstClosingDate() != null) {
			this.setFirstClosingDateStr(sdf.format(gpFund.getFirstClosingDate()));
		}
		if(gpFund.getFinalClosingDate() != null) {
			this.setFinalClosingDateStr(sdf.format(gpFund.getFinalClosingDate()));
		}
	}
	
	
	public String getFirstClosingDateStr() {
		return firstClosingDateStr;
	}

	public void setFirstClosingDateStr(String firstClosingDateStr) {
		this.firstClosingDateStr = firstClosingDateStr;
	}

	public String getFinalClosingDateStr() {
		return finalClosingDateStr;
	}

	public void setFinalClosingDateStr(String finalClosingDateStr) {
		this.finalClosingDateStr = finalClosingDateStr;
	}
}
