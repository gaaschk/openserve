package org.gsoft.openserv.web.loanprogram.converter;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.repositories.loan.LoanProgramRepository;
import org.gsoft.openserv.web.loanprogram.model.LoanProgramModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanProgramModelToLoanProgramConverter implements Converter<LoanProgramModel, LoanProgram>{
	@Resource
	private LoanProgramRepository loanProgramRepository;
	
	public LoanProgram convert(LoanProgramModel lpm){
		Long lpmID = lpm.getLoanProgramID();
		LoanProgram lp = null;
		if(lpmID == null || lpmID < 0){
			lp = new LoanProgram();
			loanProgramRepository.save(lp);
		}
		else{
			lp = loanProgramRepository.findOne(lpmID);
		}
		lp.setName(lpm.getName());
		lp.setDescription(lpm.getDescription());
		return lp;
	}
}
