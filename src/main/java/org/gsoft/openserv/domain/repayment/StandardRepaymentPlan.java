package org.gsoft.openserv.domain.repayment;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="RepaymentPlanID")
public class StandardRepaymentPlan extends RepaymentPlan{
	private static final long serialVersionUID = 3758652556712328331L;
	private Integer maxLoanTerm = null;
	private Integer minPaymentAmount = null;
	
	public Integer getMaxLoanTerm() {
		return maxLoanTerm;
	}
	public void setMaxLoanTerm(Integer maxLoanTerm) {
		this.maxLoanTerm = maxLoanTerm;
	}
	public Integer getMinPaymentAmount() {
		return minPaymentAmount;
	}
	public void setMinPaymentAmount(Integer minPaymentAmount) {
		this.minPaymentAmount = minPaymentAmount;
	}
	
}
