package com.prosnav.ivms.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document
public class FofFirm implements IModel {
	@Id
	private Long id;
	private String name;
	private String otherName;
	private int geographyCode;
	private String notes;
	
	private String registeredAddress;
	private String registrationAmount;
	private int currencyCode;
	private Date registeredDate;
	
	private String amacRecordNo;
	private Date recordDate;

	private List<OwnershipStructure> ownershipStructures;
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
	
	public static class OwnershipStructure extends BaseBean {
		private String code;
		private Relation relation;
		private int currencyCode;
		private Double investmentAmount;
		private String ownership;
		private String notes;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public Relation getRelation() {
			return relation;
		}
		public void setRelation(Relation relation) {
			this.relation = relation;
		}
		public int getCurrencyCode() {
			return currencyCode;
		}
		public void setCurrencyCode(int currencyCode) {
			this.currencyCode = currencyCode;
		}
		public String getOwnership() {
			return ownership;
		}
		public void setOwnership(String ownership) {
			this.ownership = ownership;
		}
		public Double getInvestmentAmount() {
			return investmentAmount;
		}
		public void setInvestmentAmount(Double investmentAmount) {
			this.investmentAmount = investmentAmount;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
	}
	
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

	public int getGeographyCode() {
		return geographyCode;
	}

	public void setGeographyCode(int geographyCode) {
		this.geographyCode = geographyCode;
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
	
	public String getOtherName() {
		return otherName;
	}

	public void setOtherName(String otherName) {
		this.otherName = otherName;
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
	
	public List<OwnershipStructure> getOwnershipStructures() {
		return ownershipStructures;
	}

	public void setOwnershipStructures(List<OwnershipStructure> ownershipStructures) {
		this.ownershipStructures = ownershipStructures;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getRegistrationAmount() {
		return registrationAmount;
	}

	public void setRegistrationAmount(String registrationAmount) {
		this.registrationAmount = registrationAmount;
	}

	public int getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(int currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	

	public String getAmacRecordNo() {
		return amacRecordNo;
	}

	public void setAmacRecordNo(String amacRecordNo) {
		this.amacRecordNo = amacRecordNo;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.WriteDateUseDateFormat);
	}
}
