package org.gsoft.phoenix.service.disclosure;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.disclosure.DisclosureLogic;
import org.gsoft.phoenix.domain.disclosure.LoanDisclosure;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.springframework.stereotype.Service;

@Service
public class DisclosureService {
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private DisclosureLogic disclosureLogic;
	
	public LoanDisclosure calculateDisclosure(Long loanID){
		return disclosureLogic.createDisclosure(loanRepository.findOne(loanID));
	}
}
