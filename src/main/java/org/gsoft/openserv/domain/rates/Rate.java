package org.gsoft.openserv.domain.rates;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@RulesEngineEntity
public class Rate extends PersistentDomainObject{
	
	private static final long serialVersionUID = 9018072728872806482L;

	private Long rateId;
	private String rateName;
	private Boolean shouldAutoUpdate = false;
	private String tickerSymbol; 
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO)
	public Long getRateId() {
		return rateId;
	}

	//Needed for Hibernate
	@SuppressWarnings("unused")
	private void setRateId(Long rateId) {
		this.rateId = rateId;
	}

	@JsonProperty("label")
	public String getRateName() {
		return rateName;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

	@Column(columnDefinition = "SMALLINT")
	public Boolean getShouldAutoUpdate() {
		return shouldAutoUpdate;
	}

	public void setShouldAutoUpdate(Boolean autoUpdate) {
		this.shouldAutoUpdate = autoUpdate;
	}

	public String getTickerSymbol() {
		return tickerSymbol;
	}

	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}

	@Override
	@Transient
	@JsonFormat(shape=JsonFormat.Shape.STRING)
	public Long getID() {
		return this.getRateId();
	}

}
