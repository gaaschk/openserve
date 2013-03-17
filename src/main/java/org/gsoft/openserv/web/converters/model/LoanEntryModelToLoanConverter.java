package org.gsoft.openserv.web.converters.model;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.domain.loan.Disbursement;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.repositories.lender.LenderRepository;
import org.gsoft.openserv.repositories.loan.LoanProgramRepository;
import org.gsoft.openserv.web.models.DisbursementModel;
import org.gsoft.openserv.web.models.LoanEntryModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanEntryModelToLoanConverter implements Converter<LoanEntryModel, Loan>{
	@Resource
	private ConversionService conversionService;
	@Resource
	private LoanProgramRepository loanProgramRepository;
	@Resource
	private LenderRepository lenderRepository;
	
	
	public Loan convert(LoanEntryModel loanModel){
		Loan newLoan = new Loan();
		newLoan.setServicingStartDate(loanModel.getEffectiveDate());
		LoanProgram loanProgram = loanProgramRepository.findOne(Long.valueOf(loanModel.getSelectedLoanProgramID()));
		newLoan.setLoanProgram(loanProgram);
		newLoan.setLenderID(Long.valueOf(loanModel.getSelectedLenderID()));
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
