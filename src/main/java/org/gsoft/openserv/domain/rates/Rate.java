package org.gsoft.openserv.domain.rates;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.OpenServDomainObject;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

@Entity
@RulesEngineEntity
public class Rate extends OpenServDomainObject{
	
	private static final long serialVersionUID = 9018072728872806482L;

	private Long rateId;
	private String rateName;
	private Boolean autoUpdate = false;
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

	public String getRateName() {
		return rateName;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

	public Boolean getAutoUpdate() {
		return autoUpdate;
	}

	public void setAutoUpdate(Boolean autoUpdate) {
		this.autoUpdate = autoUpdate;
	}

	public String getTickerSymbol() {
		return tickerSymbol;
	}

	public void setTickerSymbol(String tickerSymbol) {
		this.tickerSymbol = tickerSymbol;
	}

	@Override
	@Transient
	public Long getID() {
		return this.getRateId();
	}

}
