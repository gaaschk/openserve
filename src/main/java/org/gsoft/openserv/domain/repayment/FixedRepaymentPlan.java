package org.gsoft.openserv.domain.repayment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

import org.gsoft.openserv.util.time.FrequencyType;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

@Entity
@PrimaryKeyJoinColumn(name="RepaymentPlanID")
public class FixedRepaymentPlan extends RepaymentPlan{
	private static final long serialVersionUID = 2015497316387413255L;

	private Integer paymentAmount;
	private FrequencyType capFrequency;
	private Boolean capAtEnd;
	
	public Integer getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	@Type( type = "org.gsoft.openserv.util.jpa.GenericEnumUserType", parameters={
			@Parameter(name = "enumClass", value = "org.gsoft.openserv.util.time.FrequencyType")
	})
    @Column( name = "CapFrequencyID" )
	public FrequencyType getCapFrequency() {
		return capFrequency;
	}
	public void setCapFrequency(FrequencyType capFrequency) {
		this.capFrequency = capFrequency;
	}
	public Boolean getCapAtEnd() {
		return capAtEnd;
	}
	public void setCapAtEnd(Boolean capAtEnd) {
		this.capAtEnd = capAtEnd;
	}
}
