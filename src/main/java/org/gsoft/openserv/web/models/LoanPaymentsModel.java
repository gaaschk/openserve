package org.gsoft.openserv.web.models;

import java.util.List;

public class LoanPaymentsModel {
	private List<LoanPaymentModel> loanPayments;

	public List<LoanPaymentModel> getLoanPayments() {
		return loanPayments;
	}

	public void setLoanPayments(List<LoanPaymentModel> loanPayments) {
		this.loanPayments = loanPayments;
	}
}
