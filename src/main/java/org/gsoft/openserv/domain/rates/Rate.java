package org.gsoft.openserv.domain.rates;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.OpenServDomainObject;

@Entity
public class Rate extends OpenServDomainObject {
	private static final long serialVersionUID = -7125016712126176480L;
	private Long rateID;
	private String symbol;
	private String name;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getRateID() {
		return rateID;
	}

	public void setRateID(Long rateID) {
		this.rateID = rateID;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	@Transient
	public Long getID() {
		return this.getRateID();
	}
}
