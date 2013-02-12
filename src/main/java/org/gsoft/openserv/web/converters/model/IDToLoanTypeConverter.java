package org.gsoft.openserv.web.converters.model;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.loan.LoanType;
import org.gsoft.openserv.repositories.loan.LoanTypeRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IDToLoanTypeConverter  implements Converter<String, LoanType>{
	@Resource
	private LoanTypeRepository loanTypeRepository;
	
	public LoanType convert(String id){
		return loanTypeRepository.findOne(Long.valueOf(id));
	}
}
