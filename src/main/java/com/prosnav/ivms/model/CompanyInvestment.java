/**
 * 
 */
package com.prosnav.ivms.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.prosnav.ivms.model.GpFundInvestment.CapitalCall;

/**
 * @author wangnan
 *
 */
@Document
public class CompanyInvestment extends BaseBean implements IModel {
	
	private Long id;
	private Long companyId;
	private Long fofId;
	private Long gpFundId;
	private String companyName;
	private String financingRoundCode;
	private String financingRoundName;
	private Integer currencyCode;
	private Double investmentAmount;
	private Date investmentDate;
	private String ownership;
	private String investmentType;
	private String type;	
	private Float boardSeat;
	private Double shares;
	private String notes;
	
	List<CapitalCall> capitalCalls;
	public static class CapitalCall extends BaseBean {
		private String code;
		private Date dueDate;
		private Date callDate;
		private Integer currencyCode;
		private Double amount;
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
		public Date getCallDate() {
			return callDate;
		}
		public void setCallDate(Date callDate) {
			this.callDate = callDate;
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
	
	List<Distribution> distributions;
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
	
	List<Valuation> valuations;
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
		private Double unrealizedShares;
		private Double realizedShares;
		private String ownership;
		
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
		public Double getUnrealizedShares() {
			return unrealizedShares;
		}
		public void setUnrealizedShares(Double unrealizedShares) {
			this.unrealizedShares = unrealizedShares;
		}
		public Double getRealizedShares() {
			return realizedShares;
		}
		public void setRealizedShares(Double realizedShares) {
			this.realizedShares = realizedShares;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getGpFundId() {
		return gpFundId;
	}

	public void setGpFundId(Long gpFundId) {
		this.gpFundId = gpFundId;
	}

	public List<Valuation> getValuations() {
		return valuations;
	}

	public void setValuations(List<Valuation> valuations) {
		this.valuations = valuations;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFinancingRoundCode() {
		return financingRoundCode;
	}

	public void setFinancingRoundCode(String financingRoundCode) {
		this.financingRoundCode = financingRoundCode;
	}

	public Double getInvestmentAmount() {
		return investmentAmount;
	}

	public void setInvestmentAmount(Double investmentAmount) {
		this.investmentAmount = investmentAmount;
	}

	public Date getInvestmentDate() {
		return investmentDate;
	}

	public void setInvestmentDate(Date investmentDate) {
		this.investmentDate = investmentDate;
	}

	public String getOwnership() {
		return ownership;
	}

	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}

	public Float getBoardSeat() {
		return boardSeat;
	}

	public void setBoardSeat(Float boardSeat) {
		this.boardSeat = boardSeat;
	}

	public Integer getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Double getShares() {
		return shares;
	}

	public void setShares(Double shares) {
		this.shares = shares;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFinancingRoundName() {
		return financingRoundName;
	}
	

	public String getInvestmentType() {
		return investmentType;
	}

	public void setInvestmentType(String investmentType) {
		this.investmentType = investmentType;
	}

	public void setFinancingRoundName(String financingRoundName) {
		this.financingRoundName = financingRoundName;
	}

	public Long getFofId() {
		return fofId;
	}

	public void setFofId(Long fofId) {
		this.fofId = fofId;
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
	
	@Override
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.WriteDateUseDateFormat);
	}
}
