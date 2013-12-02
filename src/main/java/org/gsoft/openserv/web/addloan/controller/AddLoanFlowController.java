package org.gsoft.openserv.web.addloan.controller;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.Person;
import org.gsoft.openserv.domain.lender.Lender;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.repositories.lender.LenderRepository;
import org.gsoft.openserv.repositories.loan.LoanProgramRepository;
import org.gsoft.openserv.service.PersonService;
import org.gsoft.openserv.service.loanentry.LoanEntryService;
import org.gsoft.openserv.util.ListUtility;
import org.gsoft.openserv.web.addloan.model.DisbursementModel;
import org.gsoft.openserv.web.addloan.model.LoanEntryModel;
import org.gsoft.openserv.web.person.PersonSearchCriteria;
import org.gsoft.openserv.web.person.model.PersonModel;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AddLoanFlowController {
	@Resource
	private PersonService personService;
	@Resource
	private ConversionService conversionService;
	@Resource
	private LoanEntryService loanEntryService;
	@Resource
	private LoanProgramRepository loanProgramRepository;
	@Resource
	private LenderRepository lenderRepository;
	
	@RequestMapping(value="addloan/personsearch", method=RequestMethod.GET)
	public String loadPersonSearch(Model model){
		model.addAttribute("personSearchCriteria", new PersonSearchCriteria());
		return "addloan/personsearch";
	}

	@RequestMapping(value="addloan/personsearch", method=RequestMethod.POST)
	public String findPerson(@ModelAttribute String ssn, Model model){
		ssn = ssn.replace("-", "");
		Person person = personService.findPersonBySSN(ssn);
		PersonModel newPersonModel = conversionService.convert(person, PersonModel.class);
		if(newPersonModel == null)newPersonModel = new PersonModel();
		newPersonModel.setSsn(ssn);
		LoanEntryModel loanModel = new LoanEntryModel();
		loanModel.setNewDisbursement(new DisbursementModel());
		Iterable<LoanProgram> loanPrograms = loanProgramRepository.findAll();
		Iterable<Lender> lenders = lenderRepository.findAll();
		loanModel.setLenderList(ListUtility.addAll(new ArrayList<Lender>(), lenders));
		loanModel.setLoanProgramList(ListUtility.addAll(new ArrayList<LoanProgram>(), loanPrograms));
		loanModel.setPerson(newPersonModel);
		model.addAttribute("loanEntryModel", loanModel);
		return "addloan/disbursemententry";
	}

	public void addDisbursement(LoanEntryModel loanModel){
		if(loanModel.getAddedDisbursements() == null)
			loanModel.setAddedDisbursements(new ArrayList<DisbursementModel>());
		loanModel.getAddedDisbursements().add(loanModel.getNewDisbursement());
		loanModel.setNewDisbursement(new DisbursementModel());
	}
	
	public Long saveLoan(LoanEntryModel loanModel){
		Loan newLoanDoc = conversionService.convert(loanModel, Loan.class);
		Loan loan = loanEntryService.addNewLoan(newLoanDoc);
		return loan.getLoanID();
	}
}
