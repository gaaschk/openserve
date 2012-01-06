package org.gsoft.phoenix.web.controller.amortization.model;

import java.util.ArrayList;
import java.util.List;

import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.web.controller.accountsummary.model.LoanFinancialDataModel;
import org.springframework.stereotype.Component;

@Component
public class AmortizationScheduleModelConverter{

	public AmortizationScheduleModel convertToModel(List<Loan> loans) {
		AmortizationScheduleModel model = new AmortizationScheduleModel();
		ArrayList<LoanFinancialDataModel> loanModels = new ArrayList<LoanFinancialDataModel>();
		for(Loan loan:loans){
			loanModels.add(this.convertToModel(loan));
		}
		model.setLoans(loanModels);
		return model;
	}

	public LoanFinancialDataModel convertToModel(Loan loan){
		LoanFinancialDataModel model = new LoanFinancialDataModel();
		model.setLoanID(loan.getLoanID());
		model.setLoanType(loan.getLoanType());
		model.setCurrentPrincipal(loan.getCurrentPrincipal());
		model.setCurrentInterest(loan.getCurrentInterest());
		model.setCurrentFees(loan.getCurrentFees());
		return model;
	}
}
