package com.prosnav.ivms.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.auth0.jwt.internal.org.apache.commons.lang3.builder.ToStringBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document
public class Company extends BaseBean implements IModel {
	@Id
	private Long id;
	private String name;
	private String alias;
	private String listedCode;
	private int geographyCode;
	private int industryCode;
	private String businessBrief;
	private String registeredAddress;
	private String registrationAmount;
	private int currencyCode;
	@DateTimeFormat(pattern="yyyy-MM-dd")  
	private Date registeredDate;
	private Long legalRepresentative;
	private int fiscalYearCode;
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
	
	private List<Long> compIds;
	private List<Ipo> ipos;
	private List<Financial> financials;
	private List<FinancingRound> financingRounds;
	private List<OwnershipStructure> ownershipStructures;
	
	public static class Ipo extends BaseBean {
		
		private String ipoCode;
		private Integer exchangeCode;
		private Date ipoDate;
		private Double ipoPrice;
		private Double ipoShares;
		private Integer currencyCode;
		private Double listedValuation;
		private Integer ipoStatusCode;
		private String notes;

		public String getIpoCode() {
			return ipoCode;
		}

		public void setIpoCode(String ipoCode) {
			this.ipoCode = ipoCode;
		}

		public Integer getExchangeCode() {
			return exchangeCode;
		}

		public void setExchangeCode(Integer exchangeCode) {
			this.exchangeCode = exchangeCode;
		}

		public Date getIpoDate() {
			return ipoDate;
		}

		public void setIpoDate(Date ipoDate) {
			this.ipoDate = ipoDate;
		}

		public Double getIpoShares() {
			return ipoShares;
		}

		public void setIpoShares(Double ipoShares) {
			this.ipoShares = ipoShares;
		}

		public Integer getCurrencyCode() {
			return currencyCode;
		}

		public void setCurrencyCode(Integer currencyCode) {
			this.currencyCode = currencyCode;
		}

		public Integer getIpoStatusCode() {
			return ipoStatusCode;
		}

		public void setIpoStatusCode(Integer ipoStatusCode) {
			this.ipoStatusCode = ipoStatusCode;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		public Double getIpoPrice() {
			return ipoPrice;
		}

		public void setIpoPrice(Double ipoPrice) {
			this.ipoPrice = ipoPrice;
		}

		public Double getListedValuation() {
			return listedValuation;
		}

		public void setListedValuation(Double listedValuation) {
			this.listedValuation = listedValuation;
		}
		
	}
	
	
	public static class Financial extends BaseBean {
		private String code;
		private Double revenue;
		private Double grossProfit;
		private Double cash;
		private Double ebitda;
		private Integer currencyCode;
		private Double totalAsset;
		private Double totalLiability;
		private Double totalEquity;
		private Double operatingIncome;
		private Double operatingCashFlow;
		private Date reportingStartDate;
		private Date reportingEndDate;
		private boolean estimated;
		private Integer estimatedBy;	
		private Double netIncome;
		private String notes;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public Double getRevenue() {
			return revenue;
		}

		public void setRevenue(Double revenue) {
			this.revenue = revenue;
		}

		public Double getGrossProfit() {
			return grossProfit;
		}

		public void setGrossProfit(Double grossProfit) {
			this.grossProfit = grossProfit;
		}

		public Double getEbitda() {
			return ebitda;
		}

		public void setEbitda(Double ebitda) {
			this.ebitda = ebitda;
		}

		public Double getTotalAsset() {
			return totalAsset;
		}

		public void setTotalAsset(Double totalAsset) {
			this.totalAsset = totalAsset;
		}

		public Double getTotalLiability() {
			return totalLiability;
		}

		public void setTotalLiability(Double totalLiability) {
			this.totalLiability = totalLiability;
		}

		public Double getTotalEquity() {
			return totalEquity;
		}

		public void setTotalEquity(Double totalEquity) {
			this.totalEquity = totalEquity;
		}

		public Double getOperatingIncome() {
			return operatingIncome;
		}

		public void setOperatingIncome(Double operatingIncome) {
			this.operatingIncome = operatingIncome;
		}

		public Double getOperatingCashFlow() {
			return operatingCashFlow;
		}

		public void setOperatingCashFlow(Double operatingCashFlow) {
			this.operatingCashFlow = operatingCashFlow;
		}

		public Date getReportingStartDate() {
			return reportingStartDate;
		}

		public void setReportingStartDate(Date reportingStartDate) {
			this.reportingStartDate = reportingStartDate;
		}

		public Date getReportingEndDate() {
			return reportingEndDate;
		}

		public void setReportingEndDate(Date reportingEndDate) {
			this.reportingEndDate = reportingEndDate;
		}

		public boolean isEstimated() {
			return estimated;
		}

		public void setEstimated(boolean estimated) {
			this.estimated = estimated;
		}

		public Integer getEstimatedBy() {
			return estimatedBy;
		}

		public void setEstimatedBy(Integer estimatedBy) {
			this.estimatedBy = estimatedBy;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		public Double getNetIncome() {
			return netIncome;
		}

		public void setNetIncome(Double netIncome) {
			this.netIncome = netIncome;
		}

		public Double getCash() {
			return cash;
		}

		public void setCash(Double cash) {
			this.cash = cash;
		}

		public Integer getCurrencyCode() {
			return currencyCode;
		}

		public void setCurrencyCode(Integer currencyCode) {
			this.currencyCode = currencyCode;
		}
	}
	
	public static class FinancingRound extends BaseBean {
		private String code;
		private String name;
		private Double totalRoundSize;
		private Integer currencyCode;
		private Double preMoneyValuation;
		private Double postMoneyValuation;
		private Integer entryPe;
		private Integer entryPb; 
		private Integer entryPs;
		private String asOfDate;
		private Date investmentDate;
		private Integer entryEvOrEbitda;
		private String notes;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Double getTotalRoundSize() {
			return totalRoundSize;
		}

		public void setTotalRoundSize(Double totalRoundSize) {
			this.totalRoundSize = totalRoundSize;
		}

		public Integer getCurrencyCode() {
			return currencyCode;
		}

		public void setCurrencyCode(Integer currencyCode) {
			this.currencyCode = currencyCode;
		}

		public Double getPreMoneyValuation() {
			return preMoneyValuation;
		}

		public void setPreMoneyValuation(Double preMoneyValuation) {
			this.preMoneyValuation = preMoneyValuation;
		}

		public Double getPostMoneyValuation() {
			return postMoneyValuation;
		}

		public void setPostMoneyValuation(Double postMoneyValuation) {
			this.postMoneyValuation = postMoneyValuation;
		}

		public Integer getEntryPe() {
			return entryPe;
		}

		public void setEntryPe(Integer entryPe) {
			this.entryPe = entryPe;
		}

		public Integer getEntryPb() {
			return entryPb;
		}

		public void setEntryPb(Integer entryPb) {
			this.entryPb = entryPb;
		}

		public Integer getEntryPs() {
			return entryPs;
		}

		public void setEntryPs(Integer entryPs) {
			this.entryPs = entryPs;
		}

		public String getAsOfDate() {
			return asOfDate;
		}

		public void setAsOfDate(String asOfDate) {
			this.asOfDate = asOfDate;
		}

		public Date getInvestmentDate() {
			return investmentDate;
		}

		public void setInvestmentDate(Date investmentDate) {
			this.investmentDate = investmentDate;
		}

		public Integer getEntryEvOrEbitda() {
			return entryEvOrEbitda;
		}

		public void setEntryEvOrEbitda(Integer entryEvOrEbitda) {
			this.entryEvOrEbitda = entryEvOrEbitda;
		}

		public String getNotes() {
			return notes;
		}

		public void setNotes(String notes) {
			this.notes = notes;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
	}
	
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

	public String getListedCode() {
		return listedCode;
	}

	public void setListedCode(String listedCode) {
		this.listedCode = listedCode;
	}

	public int getGeographyCode() {
		return geographyCode;
	}

	public void setGeographyCode(int geographyCode) {
		this.geographyCode = geographyCode;
	}

	public String getBusinessBrief() {
		return businessBrief;
	}

	public void setBusinessBrief(String businessBrief) {
		this.businessBrief = businessBrief;
	}

	public int getFiscalYearCode() {
		return fiscalYearCode;
	}

	public void setFiscalYearCode(int fiscalYearCode) {
		this.fiscalYearCode = fiscalYearCode;
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

	public List<Long> getCompIds() {
		return compIds;
	}

	public void setCompIds(List<Long> compIds) {
		this.compIds = compIds;
	}

	public List<Ipo> getIpos() {
		return ipos;
	}

	public void setIpos(List<Ipo> ipos) {
		this.ipos = ipos;
	}

	public List<Financial> getFinancials() {
		return financials;
	}

	public void setFinancials(List<Financial> financials) {
		this.financials = financials;
	}

	public List<FinancingRound> getFinancingRounds() {
		return financingRounds;
	}

	public void setFinancingRounds(List<FinancingRound> financingRounds) {
		this.financingRounds = financingRounds;
	}

	public int getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(int industryCode) {
		this.industryCode = industryCode;
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

	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.WriteDateUseDateFormat);
	}
	
}
