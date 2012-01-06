package org.gsoft.phoenix.web.controller.amortization;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.domain.Person;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.service.AccountSummaryService;
import org.gsoft.phoenix.service.PersonService;
import org.gsoft.phoenix.service.amortization.AmortizationService;
import org.gsoft.phoenix.web.controller.accountsummary.model.LoanFinancialDataModel;
import org.gsoft.phoenix.web.controller.amortization.model.AmortizationScheduleModel;
import org.gsoft.phoenix.web.controller.amortization.model.AmortizationScheduleModelConverter;
import org.gsoft.phoenix.web.person.PersonSearchCriteria;
import org.springframework.stereotype.Service;

@Service
public class AmortizationFlowController {
	@Resource
	private PersonService personService;
	@Resource
	private AmortizationScheduleModelConverter amortizationScheduleModelConverter;
	@Resource
	private AccountSummaryService accountSummaryService;
	@Resource
	private AmortizationService amortizationService;

	public AmortizationScheduleModel findBorrowerAndCreateAmortizationScheduleModel(PersonSearchCriteria searchCriteria){
		Person person = personService.findPersonBySSN(searchCriteria.getSsn());
		List<Loan> loans = accountSummaryService.getAllLoansForBorrower(person.getPersonID());
		AmortizationScheduleModel amortizationScheduleModel = amortizationScheduleModelConverter.convertToModel(loans);
		return amortizationScheduleModel;
	}
	
	public void amortizeLoans(AmortizationScheduleModel amortizationScheduleModel){
		ArrayList<Long> loanIDs = new ArrayList<Long>();
		for(LoanFinancialDataModel loanFinModel:amortizationScheduleModel.getLoans()){
			if(loanFinModel.isSelected())
				loanIDs.add(loanFinModel.getLoanID());
		}
		amortizationService.calculateAmortizationSchedule(loanIDs);
	}
}
