package org.gsoft.openserv.domain.rates;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.PersistentDomainObject;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

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

	public String getRateName() {
		return rateName;
	}

	public void setRateName(String rateName) {
		this.rateName = rateName;
	}

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
	public Long getID() {
		return this.getRateId();
	}

}
