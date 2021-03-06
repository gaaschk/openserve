package org.gsoft.openserv.service.payment;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.openserv.buslogic.payment.PaymentLogic;
import org.gsoft.openserv.rulesengine.annotation.RunRulesEngine;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class PaymentService {
	@Resource
	private PaymentLogic paymentLogic;
	
	@PreAuthorize("hasRole('PERM_AddPayment')")
	@Transactional
	@RunRulesEngine
	public void applyPayment(long borrowerPersonID, int amount, Date effectiveDate){
		paymentLogic.applyPayment(borrowerPersonID, amount, effectiveDate);
	}
}
