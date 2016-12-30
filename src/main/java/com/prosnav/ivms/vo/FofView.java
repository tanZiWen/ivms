package com.prosnav.ivms.vo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.util.StringUtils;

import com.prosnav.ivms.model.Fof;


public class FofView extends Fof{
	private String firstClosingDateStr;
	private String finalClosingDateStr;
	
	public Fof toModel() {
		Fof entity = new Fof();
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
	
	public void fromModel(Fof fof) {
		this.copyFrom(fof);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(fof.getFirstClosingDate() != null) {
			this.setFirstClosingDateStr(sdf.format(fof.getFirstClosingDate()));
		}
		if(fof.getFinalClosingDate() != null) {
			this.setFinalClosingDateStr(sdf.format(fof.getFinalClosingDate()));
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
