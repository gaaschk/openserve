package org.gsoft.openserv.buslogic.interest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.openserv.domain.interest.LoanRateValue;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanBalanceAdjustment;
import org.gsoft.openserv.domain.loan.LoanState;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.repositories.LoanRateValueRepository;
import org.gsoft.openserv.repositories.loan.LoanBalanceAdjustmentRepository;
import org.gsoft.openserv.repositories.payment.LoanPaymentRepository;
import org.springframework.stereotype.Component;

@Component
public class LoanBalanceCalculator {
	@Resource
	private LoanBalanceAdjustmentRepository loanBalanceAdjustmentRepo;
	@Resource
	private LoanPaymentRepository loanPaymentRepo;
	@Resource
	private LoanRateValueRepository loanRateValueRepo;

	
	public LoanState calculateLoanBalanceAsOf(Loan loan, Date asOfDate){
		ArrayList<LoanBalanceEvent> events = new ArrayList<LoanBalanceEvent>();
		List<LoanBalanceAdjustment> loanBalanceAdjustments = loanBalanceAdjustmentRepo.findAllForLoanThruDate(loan.getLoanID(), asOfDate);
		for(LoanBalanceAdjustment adj:loanBalanceAdjustments){
			events.add(LoanBalanceEvent.valueOf(adj));
		}
		List<LoanPayment> loanPayments = loanPaymentRepo.findAllLoanPaymentsEffectiveOnOrBefore(loan.getLoanID(), asOfDate);
		for(LoanPayment payment:loanPayments){
			events.add(LoanBalanceEvent.valueOf(payment));
		}
		List<LoanRateValue> loanRates = loanRateValueRepo.findAllLoanRateValuesThruDate(loan.getLoanID(), asOfDate);
		for(LoanRateValue rateValue:loanRates){
			events.add(LoanBalanceEvent.valueOf(rateValue));
		}
		Collections.sort(events, new Comparator<LoanBalanceEvent>(){
			@Override
			public int compare(LoanBalanceEvent arg0, LoanBalanceEvent arg1) {
				int dateCompare = arg0.getEventDate().compareTo(arg1.getEventDate());
				if(dateCompare == 0){
					if(arg0.isPayment() && arg1.isPayment()){return 0;}
					if(arg0.isPayment()){return 1;}
					if(arg1.isPayment()){return -1;}
					return 0;
				}
				return dateCompare;
			}
		});
		LoanRateValue currentRate = loanRates.get(0);
		LoanState loanState = new LoanState(loan.getStartingPrincipal(), loan.getStartingInterest(), loan.getStartingFees(), currentRate.getRateValue().getRateValue(), loan.getServicingStartDate());
		for(LoanBalanceEvent event:events){
			if(event.getFeeAmount() != null){
				loanState.adjustFees(event.getFeeAmount(), event.getEventDate());
			}
			if(event.getInterestAmount() != null){
				loanState.adjustInterest(event.getInterestAmount(), event.getEventDate());
			}
			if(event.getPrincipalAmount() != null){
				loanState.adjustPrincipal(event.getPrincipalAmount(), event.getEventDate());
			}
			if(event.getRateValue() != null){
				loanState.setInterestRate(event.getRateValue(), event.getEventDate());
			}
		}
		loanState.accrueInterestThru(asOfDate);
		return loanState;
	}
}

class LoanBalanceEvent{
	private Date eventDate;
	private Integer principalAmount;
	private BigDecimal interestAmount;
	private Integer feeAmount;
	private Integer paymentAmount;
	private BigDecimal rateValue;
	private boolean isPayment = false;
	
	private LoanBalanceEvent(){}
	
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public Integer getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(Integer principalAmount) {
		this.principalAmount = principalAmount;
	}

	public BigDecimal getInterestAmount() {
		return interestAmount;
	}

	public void setInterestAmount(BigDecimal interestAmount) {
		this.interestAmount = interestAmount;
	}

	public Integer getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Integer feeAmount) {
		this.feeAmount = feeAmount;
	}

	public Integer getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public BigDecimal getRateValue() {
		return rateValue;
	}

	public void setRateValue(BigDecimal rateValue) {
		this.rateValue = rateValue;
	}
	
	public boolean isPayment() {
		return isPayment;
	}

	public void setPayment(boolean isPayment) {
		this.isPayment = isPayment;
	}

	public static LoanBalanceEvent valueOf(LoanBalanceAdjustment adj){
		LoanBalanceEvent event = new LoanBalanceEvent();
		event.setEventDate(adj.getEffectiveDate());
		event.setFeeAmount(adj.getFeesChange());
		if(adj.getInterestChange()!=null){
			event.setInterestAmount(BigDecimal.valueOf(adj.getInterestChange()));
		}
		event.setPrincipalAmount(adj.getPrincipalChange());
		return event;
	}
	
	public static LoanBalanceEvent valueOf(LoanPayment payment){
		LoanBalanceEvent event = new LoanBalanceEvent();
		event.setPaymentAmount(payment.getAppliedAmount());
		event.setEventDate(payment.getPayment().getEffectiveDate());
		event.setPayment(true);
		return event;
	}
	
	public static LoanBalanceEvent valueOf(LoanRateValue rate){
		LoanBalanceEvent event = new LoanBalanceEvent();
		event.setEventDate(rate.getLockedDate());
		event.setRateValue(rate.getRateValue().getRateValue());
		return event;
	}
}
