package org.gsoft.phoenix.service.payment;

import java.util.Date;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.payment.PaymentLogic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class PaymentService {
	@Resource
	private PaymentLogic paymentLogic;
	
	@Transactional
	public void applyPayment(long borrowerPersonID, int amount, Date effectiveDate){
		paymentLogic.applyPayment(borrowerPersonID, amount, effectiveDate);
	}
}
