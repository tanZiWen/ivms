package com.prosnav.ivms.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document
public class GpFund extends BaseBean implements IModel {
	@Id
	private Long id;
	private String name;
	private String alias;
	private Integer geographyCode;
	private List<Integer> industryGroupCodes;
	//del by Alex.wang
//	private Integer industryCode;
	private Integer assetClassCode;
	private Long gpFirmId;
	private String terms;
	private String gpCommitment;
	private String managementFee;
	private String carry;
	private String hurdleRate;
	private Date firstClosingDate;
	private Date finalClosingDate;
	private Double fundSize;
	private String investmentPeriod;
	private String vintageYear;
	private String catchUp;
	
	private String registeredAddress;
	private String registrationAmount;
	private int currencyCode;
	private Date registeredDate;
	private Long legalRepresentative;
	
	private String amacRecordNo;
	private Date recordDate;
	
	private Boolean dealByDealCarry;

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

	public int getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(int currencyCode) {
		this.currencyCode = currencyCode;
	}

	private int status;

	private boolean deled;

	private int v;
	
	private List<OwnershipStructure> ownershipStructures;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
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
		public Double getInvestmentAmount() {
			return investmentAmount;
		}
		public void setInvestmentAmount(Double investmentAmount) {
			this.investmentAmount = investmentAmount;
		}
		public String getOwnership() {
			return ownership;
		}
		public void setOwnership(String ownership) {
			this.ownership = ownership;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
	}
	
	private List<Valuation> valuations;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Valuation extends BaseBean {
		private String code;
		private Date valuationDate;
		private Integer currencyCode;
		private Double unrealizedValuation;
		private Double realizedValuation;
		private Double totalValuation;
		private String multiple;
		private String grossIRR;
		private String netIRR;
		private String notes;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public Date getValuationDate() {
			return valuationDate;
		}
		public void setValuationDate(Date valuationDate) {
			this.valuationDate = valuationDate;
		}
		public Integer getCurrencyCode() {
			return currencyCode;
		}
		public void setCurrencyCode(Integer currencyCode) {
			this.currencyCode = currencyCode;
		}
		public Double getUnrealizedValuation() {
			return unrealizedValuation;
		}
		public void setUnrealizedValuation(Double unrealizedValuation) {
			this.unrealizedValuation = unrealizedValuation;
		}
		public Double getRealizedValuation() {
			return realizedValuation;
		}
		public void setRealizedValuation(Double realizedValuation) {
			this.realizedValuation = realizedValuation;
		}
		public Double getTotalValuation() {
			return totalValuation;
		}
		public void setTotalValuation(Double totalValuation) {
			this.totalValuation = totalValuation;
		}
		public String getMultiple() {
			return multiple;
		}
		public void setMultiple(String multiple) {
			this.multiple = multiple;
		}
		public String getGrossIRR() {
			return grossIRR;
		}
		public void setGrossIRR(String grossIRR) {
			this.grossIRR = grossIRR;
		}
		public String getNetIRR() {
			return netIRR;
		}
		public void setNetIRR(String netIRR) {
			this.netIRR = netIRR;
		}
		public String getNotes() {
			return notes;
		}
		public void setNotes(String notes) {
			this.notes = notes;
		}
		
	}
	
	public List<Valuation> getValuations() {
		return valuations;
	}

	public void setValuations(List<Valuation> valuations) {
		this.valuations = valuations;
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

	public Integer getGeographyCode() {
		return geographyCode;
	}

	public void setGeographyCode(Integer geographyCode) {
		this.geographyCode = geographyCode;
	}
	
	public List<Integer> getIndustryGroupCodes() {
		return industryGroupCodes;
	}

	public void setIndustryGroupCodes(List<Integer> industryGroupCodes) {
		this.industryGroupCodes = industryGroupCodes;
	}
	
//	public Integer getIndustryCode() {
//		return industryCode;
//	}
//
//	public void setIndustryCode(Integer industryCode) {
//		this.industryCode = industryCode;
//	}

	public String getCatchUp() {
		return catchUp;
	}

	public void setCatchUp(String catchUp) {
		this.catchUp = catchUp;
	}

	public Boolean getDealByDealCarry() {
		return dealByDealCarry;
	}

	public void setDealByDealCarry(Boolean dealByDealCarry) {
		this.dealByDealCarry = dealByDealCarry;
	}

	public Integer getAssetClassCode() {
		return assetClassCode;
	}

	public void setAssetClassCode(Integer assetClassCode) {
		this.assetClassCode = assetClassCode;
	}

	public Long getGpFirmId() {
		return gpFirmId;
	}

	public void setGpFirmId(Long gpFirmId) {
		this.gpFirmId = gpFirmId;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}
	
	
	public String getGpCommitment() {
		return gpCommitment;
	}

	public void setGpCommitment(String gpCommitment) {
		this.gpCommitment = gpCommitment;
	}

	public String getManagementFee() {
		return managementFee;
	}

	public void setManagementFee(String managementFee) {
		this.managementFee = managementFee;
	}

	public String getCarry() {
		return carry;
	}

	public void setCarry(String carry) {
		this.carry = carry;
	}

	public String getHurdleRate() {
		return hurdleRate;
	}

	public void setHurdleRate(String hurdleRate) {
		this.hurdleRate = hurdleRate;
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

	public Double getFundSize() {
		return fundSize;
	}

	public void setFundSize(Double fundSize) {
		this.fundSize = fundSize;
	}

	public String getInvestmentPeriod() {
		return investmentPeriod;
	}

	public void setInvestmentPeriod(String investmentPeriod) {
		this.investmentPeriod = investmentPeriod;
	}

	public String getVintageYear() {
		return vintageYear;
	}

	public void setVintageYear(String vintageYear) {
		this.vintageYear = vintageYear;
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
