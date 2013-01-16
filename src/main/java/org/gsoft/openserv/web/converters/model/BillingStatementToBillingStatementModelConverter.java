package org.gsoft.openserv.web.converters.model;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.payment.BillingStatement;
import org.gsoft.openserv.domain.payment.LateFee;
import org.gsoft.openserv.repositories.payment.LateFeeRepository;
import org.gsoft.openserv.web.models.BillingStatementModel;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BillingStatementToBillingStatementModelConverter implements Converter<BillingStatement, BillingStatementModel>{
	@Resource
	private LateFeeRepository lateFeeRepo;
	
	@Override
	public BillingStatementModel convert(BillingStatement source) {
		BillingStatementModel model = new BillingStatementModel();
		model.setCreatedDate(source.getCreatedDate());
		model.setDueDate(source.getDueDate());
		model.setPaidAmount(source.getPaidAmount());
		model.setMinimumRequiredPayment(source.getMinimumRequiredPayment());
		model.setSatisfiedDate(source.getSatisfiedDate());
		LateFee lateFee = lateFeeRepo.findByBillingStatementID(source.getBillingStatementID());
		model.setLateFeeAmount((lateFee == null || lateFee.isCancelled())?0:lateFee.getFeeAmount());
		return model;
	}

}
