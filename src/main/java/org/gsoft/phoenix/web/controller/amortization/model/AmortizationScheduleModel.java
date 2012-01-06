package org.gsoft.phoenix.web.controller.amortization.model;

import java.io.Serializable;
import java.util.List;

import org.gsoft.phoenix.web.controller.accountsummary.model.LoanFinancialDataModel;

public class AmortizationScheduleModel implements Serializable{
	private static final long serialVersionUID = -851469198628631973L;

	private List<LoanFinancialDataModel> loans;

	public List<LoanFinancialDataModel> getLoans() {
		return loans;
	}

	public void setLoans(List<LoanFinancialDataModel> loans) {
		this.loans = loans;
	}
}
