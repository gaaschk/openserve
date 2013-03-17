package org.gsoft.openserv.web.converters.model;

import org.gsoft.openserv.domain.loan.LoanProgram;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanProgramToStringConverter implements Converter<LoanProgram, String>{
	public String convert(LoanProgram loanProgram){
		return loanProgram.getName();
	}
}
