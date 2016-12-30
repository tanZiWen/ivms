package com.prosnav.ivms.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document
public class Lp extends BaseBean implements IModel {
	@Id
	private Long id;
	private String name;
	private String investmentName;
	private Date commitmentDate;
	private Integer currencyCode;
	private Double commitmentAmount;
	private String Ownership;
	private String notes;
	/**
	 * last update user id
	 */
	private String luuid;
	/**
	 * create time
	 */
	private Date crt;
	/**
	 * last update time
	 */
	private Date lut;

	private int status;

	private boolean deled;

	private int v;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInvestmentName() {
		return investmentName;
	}

	public void setInvestmentName(String investmentName) {
		this.investmentName = investmentName;
	}

	public Date getCommitmentDate() {
		return commitmentDate;
	}

	public void setCommitmentDate(Date commitmentDate) {
		this.commitmentDate = commitmentDate;
	}

	public Integer getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Double getCommitmentAmount() {
		return commitmentAmount;
	}

	public void setCommitmentAmount(Double commitmentAmount) {
		this.commitmentAmount = commitmentAmount;
	}

	public String getOwnership() {
		return Ownership;
	}

	public void setOwnership(String ownership) {
		Ownership = ownership;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getLuuid() {
		return luuid;
	}

	public void setLuuid(String luuid) {
		this.luuid = luuid;
	}

	public Date getCrt() {
		return crt;
	}

	public void setCrt(Date crt) {
		this.crt = crt;
	}

	public Date getLut() {
		return lut;
	}

	public void setLut(Date lut) {
		this.lut = lut;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isDeled() {
		return deled;
	}

	public void setDeled(boolean deled) {
		this.deled = deled;
	}

	public int getV() {
		return v;
	}

	public void setV(int v) {
		this.v = v;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.WriteDateUseDateFormat);
	}
}
