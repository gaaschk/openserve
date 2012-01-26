package org.gsoft.phoenix.web.converters;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.domain.loan.Disbursement;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.web.models.DisbursementModel;
import org.gsoft.phoenix.web.models.LoanEntryModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanEntryModelToLoanConverter implements Converter<LoanEntryModel, Loan>{
	@Resource
	private ConversionService conversionService;
	
	public Loan convert(LoanEntryModel loanModel){
		Loan newLoan = new Loan();
		newLoan.setLoanType(loanModel.getLoanType());
		Person person = conversionService.convert(loanModel.getPerson(), Person.class);
		newLoan.setBorrower(person);
		newLoan.setDisbursements(new ArrayList<Disbursement>());
		for(DisbursementModel disbModel: loanModel.getAddedDisbursements()){
			newLoan.getDisbursements().add(this.convertFromModel(newLoan,disbModel));
		}
		newLoan.setStartingPrincipal(loanModel.getStartingPrincipal());
		newLoan.setStartingInterest(loanModel.getStartingInterest());
		newLoan.setStartingFees(loanModel.getStartingFees());
		return newLoan;
	}
	
	private Disbursement convertFromModel(Loan newLoan, DisbursementModel disbModel){
		Disbursement disb = new Disbursement();
		disb.setLoan(newLoan);
		disb.setDisbursementEffectiveDate(disbModel.getDisbursementDate());
		disb.setDisbursementAmount(disbModel.getDisbursementAmount());
		return disb;
	}
}
