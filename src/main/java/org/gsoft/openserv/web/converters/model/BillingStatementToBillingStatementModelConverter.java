package org.gsoft.openserv.web.converters.model;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.payment.LateFee;
import org.gsoft.openserv.domain.payment.billing.StatementPaySummary;
import org.gsoft.openserv.repositories.loan.LoanTypeProfileRepository;
import org.gsoft.openserv.repositories.payment.LateFeeRepository;
import org.gsoft.openserv.web.models.BillingStatementModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BillingStatementToBillingStatementModelConverter implements Converter<StatementPaySummary, BillingStatementModel>{
	@Resource
	private LateFeeRepository lateFeeRepo;
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	
	
	@Override
	public BillingStatementModel convert(StatementPaySummary source) {
		BillingStatementModel model = new BillingStatementModel();
		model.setCreatedDate(source.getStatement().getCreatedDate());
		model.setDueDate(source.getStatement().getDueDate());
		model.setPaidAmount(source.getTotalPaid());
		model.setMinimumRequiredPayment(source.getStatement().getMinimumRequiredPayment());
		model.setSatisfiedDate(source.getSatisfiedDate());
		LateFee lateFee = lateFeeRepo.findByBillingStatementID(source.getStatement().getBillingStatementID());
		if(lateFee != null){
			model.setLateFeeAmount(lateFee.getFeeAmount());
		}
		return model;
	}

}
