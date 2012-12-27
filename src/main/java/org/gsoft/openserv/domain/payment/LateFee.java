package org.gsoft.openserv.domain.payment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.gsoft.openserv.domain.OpenServDomainObject;
import org.gsoft.openserv.rulesengine.annotation.RulesEngineEntity;

@Entity
@RulesEngineEntity
public class LateFee extends OpenServDomainObject{
	private static final long serialVersionUID = 8734376381093496118L;
	private Long lateFeeID;
	private Integer feeAmount;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getLateFeeID() {
		return lateFeeID;
	}
	public void setLateFeeID(Long lateFeeID) {
		this.lateFeeID = lateFeeID;
	}
	public Integer getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(Integer feeAmount) {
		this.feeAmount = feeAmount;
	}
	@Override
	@Transient
	public Long getID() {
		return this.getLateFeeID();
	}
}
