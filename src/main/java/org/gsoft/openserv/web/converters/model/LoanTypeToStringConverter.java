package org.gsoft.openserv.web.converters.model;

import org.gsoft.openserv.domain.loan.LoanType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LoanTypeToStringConverter implements Converter<LoanType, String>{
	public String convert(LoanType loanType){
		return loanType.getName();
	}
}
