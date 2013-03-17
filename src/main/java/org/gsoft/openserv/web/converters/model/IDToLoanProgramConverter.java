package org.gsoft.openserv.web.converters.model;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanProgram;
import org.gsoft.openserv.repositories.loan.LoanProgramRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IDToLoanProgramConverter  implements Converter<String, LoanProgram>{
	@Resource
	private LoanProgramRepository loanProgramRepository;
	
	public LoanProgram convert(String id){
		return loanProgramRepository.findOne(Long.valueOf(id));
	}
}
