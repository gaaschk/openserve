package org.gsoft.openserv.domain.rates;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.OpenServDomainObject;

@Entity
public class Stock extends OpenServDomainObject {
	private static final long serialVersionUID = -7125016712126176480L;
	private Long stockID;
	private String symbol;
	private String name;
	private Boolean autoUpdate;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getStockID() {
		return stockID;
	}

	public void setStockID(Long stockID) {
		this.stockID = stockID;
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

	public Boolean getAutoUpdate() {
		return autoUpdate;
	}

	public void setAutoUpdate(Boolean autoUpdate) {
		this.autoUpdate = autoUpdate;
	}

	@Override
	@Transient
	public Long getID() {
		// TODO Auto-generated method stub
		return null;
	}
}
