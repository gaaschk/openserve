package org.gsoft.phoenix.web.controller.accountsummary.model;

import org.gsoft.phoenix.domain.loan.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanSummaryModelConverter {
	public LoanSummaryModel convertToModel(Loan loan){
		LoanSummaryModel model = new LoanSummaryModel();
		model.setLoanID(loan.getLoanID());
		model.setLoanType(loan.getLoanType());
		model.setCurrentPrincipal(loan.getCurrentPrincipal());
		model.setCurrentInterest(loan.getCurrentInterest());
		model.setCurrentFees(loan.getCurrentFees());
		return model;
	}
}
