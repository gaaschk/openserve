package org.gsoft.openserv.web.loanprogram.converter;

import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.web.loanprogram.model.LoanProgramModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanProgramToLoanProgramModelConverter implements Converter<LoanProgram, LoanProgramModel>{

	@Override
	public LoanProgramModel convert(LoanProgram source) {
		LoanProgramModel model = new LoanProgramModel();
		model.setLoanProgramID(source.getLoanProgramID());
		model.setName(source.getName());
		model.setDescription(source.getDescription());
		return model;
	}

}
