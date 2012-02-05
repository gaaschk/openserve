package org.gsoft.openserv.web.converters.model;

import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.web.models.LoanSummaryModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanToLoanSummaryModelConverter implements Converter<Loan, LoanSummaryModel>{
	public LoanSummaryModel convert(Loan loan){
		LoanSummaryModel model = new LoanSummaryModel();
		model.setLoanID(loan.getLoanID());
		model.setLoanType(loan.getLoanType());
		model.setCurrentPrincipal(loan.getCurrentPrincipal());
		model.setCurrentInterest(loan.getCurrentInterest());
		model.setCurrentFees(loan.getCurrentFees());
		return model;
	}
}
