package com.prosnav.ivms.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document
public class GpFundInvestment extends BaseBean implements IModel{
	private Long id;
	private Long fofId;
	private Long gpFundId;
	private String gpFundName;
	private Double commitmentAmount;
	private Date commitmentDate;

	private Integer currencyCode;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFofId() {
		return fofId;
	}

	public void setFofId(Long fofId) {
		this.fofId = fofId;
	}
	
	public String getGpFundName() {
		return gpFundName;
	}

	public void setGpFundName(String gpFundName) {
		this.gpFundName = gpFundName;
	}

	public Long getGpFundId() {
		return gpFundId;
	}

	public void setGpFundId(Long gpFundId) {
		this.gpFundId = gpFundId;
	}
	
	public Double getCommitmentAmount() {
		return commitmentAmount;
	}

	public void setCommitmentAmount(Double commitmentAmount) {
		this.commitmentAmount = commitmentAmount;
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
