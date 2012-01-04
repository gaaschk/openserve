package org.gsoft.phoenix.webflow;

import org.gsoft.phoenix.web.controller.addloan.AddLoanFlowController;
import org.gsoft.phoenix.web.controller.addloan.model.LoanEntryModel;
import org.gsoft.phoenix.web.person.PersonSearchCriteria;

public class StubAddLoanFlowController extends AddLoanFlowController{

	@Override
	public LoanEntryModel findPerson(PersonSearchCriteria personSearchCriteria) {
		return new LoanEntryModel();
	}

}
