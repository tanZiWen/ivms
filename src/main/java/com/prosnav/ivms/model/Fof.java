package com.prosnav.ivms.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prosnav.ivms.model.GpFundInvestment.CapitalCall;
import com.prosnav.ivms.model.GpFundInvestment.Distribution;

@Document
public class Fof extends BaseBean implements IModel {
	@Id
	private Long id;
	private String name;
	private String alias;
	private int geographyCode;
	private List<Integer> industryGroupCodes;
	private Double fundSize;
	private String terms;
	private Long fofFirmId;
	private String investmentPeriod;
	private Date firstClosingDate;
	private Date finalClosingDate;
	private String managementFree;
	private String hurdlerRate;
	private String carry;
	private Integer assetClassCode;
	private String catchUp;
	private String gpCommitment;
	private Integer vintageYear;
	
	private String registeredAddress;
	private String registrationAmount;
	private int currencyCode;
	private Date registeredDate;
	private Long legalRepresentative;
	
	private String amacRecordNo;
	private Date recordDate;
	
	private Boolean dealByDealCarry;
	
	private List<CapitalCall> capitalCalls;
	
	public static class CapitalCall extends BaseBean {
		private String code;
		private Date dueDate;
		private Integer currencyCode;
		private Double amount;
		private Double callPercent;
		private Integer callForCode;
		private String notes;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public Date getDueDate() {
			return dueDate;
		}
		public void setDueDate(Date dueDate) {
			this.dueDate = dueDate;
		}
		public Integer getCurrencyCode() {
			return currencyCode;
		}
		public void setCurrencyCode(Integer currencyCode) {
			this.currencyCode = currencyCode;
		}
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		public Double getCallPercent() {
			return callPercent;
		}
		public void setCallPercent(Double callPercent) {
			this.callPercent = callPercent;
		}
		public Integer getCallForCode() {
			return callForCode;
		}
		public void setCallForCode(Integer callForCode) {
			this.callForCode = callForCode;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
	}
	
	private List<Distribution> distributions;
	
	public static class Distribution extends BaseBean {
		private String code;
		private Date date;
		private Integer currencyCode;
		private Double amount;
		private String from;
		private String notes;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public Integer getCurrencyCode() {
			return currencyCode;
		}
		public void setCurrencyCode(Integer currencyCode) {
			this.currencyCode = currencyCode;
		}
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
	}
	
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
									 
	private List<OwnershipStructure> ownershipStructures;
	
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
		@Override
		public String toString() {
			return "OwnershipStructure [code=" + code + ", relation=" + relation + ", ownership=" + ownership
					+ ", investmentAmount=" + investmentAmount + ", notes=" + notes + "]";
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getGeographyCode() {
		return geographyCode;
	}

	public void setGeographyCode(int geographyCode) {
		this.geographyCode = geographyCode;
	}
	
	public Long getFofFirmId() {
		return fofFirmId;
	}

	public void setFofFirmId(Long fofFirmId) {
		this.fofFirmId = fofFirmId;
	}

	public List<Integer> getIndustryGroupCodes() {
		return industryGroupCodes;
	}

	public void setIndustryGroupCodes(List<Integer> industryGroupCodes) {
		this.industryGroupCodes = industryGroupCodes;
	}

	public Double getFundSize() {
		return fundSize;
	}

	public void setFundSize(Double fundSize) {
		this.fundSize = fundSize;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getInvestmentPeriod() {
		return investmentPeriod;
	}

	public void setInvestmentPeriod(String investmentPeriod) {
		this.investmentPeriod = investmentPeriod;
	}

	public Date getFirstClosingDate() {
		return firstClosingDate;
	}

	public void setFirstClosingDate(Date firstClosingDate) {
		this.firstClosingDate = firstClosingDate;
	}

	public Date getFinalClosingDate() {
		return finalClosingDate;
	}

	public void setFinalClosingDate(Date finalClosingDate) {
		this.finalClosingDate = finalClosingDate;
	}

	public String getManagementFree() {
		return managementFree;
	}

	public void setManagementFree(String managementFree) {
		this.managementFree = managementFree;
	}

	public String getHurdlerRate() {
		return hurdlerRate;
	}

	public void setHurdlerRate(String hurdlerRate) {
		this.hurdlerRate = hurdlerRate;
	}

	public String getCarry() {
		return carry;
	}

	public void setCarry(String carry) {
		this.carry = carry;
	}

	public int getCurrencyCode() {
		return currencyCode;
	}

	public Boolean getDealByDealCarry() {
		return dealByDealCarry;
	}

	public void setDealByDealCarry(Boolean dealByDealCarry) {
		this.dealByDealCarry = dealByDealCarry;
	}

	public void setCurrencyCode(int currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public Integer getAssetClassCode() {
		return assetClassCode;
	}

	public void setAssetClassCode(Integer assetClassCode) {
		this.assetClassCode = assetClassCode;
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

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public Long getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(Long legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}
	
	public String getCatchUp() {
		return catchUp;
	}

	public void setCatchUp(String catchUp) {
		this.catchUp = catchUp;
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

	public List<CapitalCall> getCapitalCalls() {
		return capitalCalls;
	}

	public void setCapitalCalls(List<CapitalCall> capitalCalls) {
		this.capitalCalls = capitalCalls;
	}

	public List<Distribution> getDistributions() {
		return distributions;
	}

	public void setDistributions(List<Distribution> distributions) {
		this.distributions = distributions;
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

	public String getGpCommitment() {
		return gpCommitment;
	}

	public void setGpCommitment(String gpCommitment) {
		this.gpCommitment = gpCommitment;
	}

	public Integer getVintageYear() {
		return vintageYear;
	}

	public void setVintageYear(Integer vintageYear) {
		this.vintageYear = vintageYear;
	}

	public List<OwnershipStructure> getOwnershipStructures() {
		return ownershipStructures;
	}

	public void setOwnershipStructures(List<OwnershipStructure> ownershipStructures) {
		this.ownershipStructures = ownershipStructures;
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
