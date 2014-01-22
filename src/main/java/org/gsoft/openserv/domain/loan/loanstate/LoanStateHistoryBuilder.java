package org.gsoft.openserv.domain.loan.loanstate;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.gsoft.openserv.domain.interest.LoanRateValue;
import org.gsoft.openserv.domain.loan.Disbursement;
import org.gsoft.openserv.domain.loan.Loan;
import org.gsoft.openserv.domain.loan.LoanBalanceAdjustment;
import org.gsoft.openserv.domain.payment.LateFee;
import org.gsoft.openserv.domain.payment.LoanPayment;
import org.gsoft.openserv.repositories.loan.LoanBalanceAdjustmentRepository;
import org.gsoft.openserv.repositories.payment.LateFeeRepository;
import org.gsoft.openserv.repositories.payment.LoanPaymentRepository;
import org.gsoft.openserv.repositories.rates.LoanRateValueRepository;
import org.springframework.context.ApplicationContext;

public class LoanStateHistoryBuilder {
	private LoanStateHistory loanStateHistory = null;
	private ApplicationContext applicationContext;

	/**
	 * 
	 * @param applicationContext used to lookup repositories
	 */
	LoanStateHistoryBuilder(ApplicationContext applicationContext){
		this.applicationContext = applicationContext;
		this.loanStateHistory = new LoanStateHistory();
	}
	
	public LoanStateHistoryBuilder(LoanStateHistory beginingState){
		this.loanStateHistory = beginingState;
	}

	void setLoanStateHistory(LoanStateHistory loanStateHistory){
		this.loanStateHistory = loanStateHistory;
	}
	
	public void buildLoanStateHistoryForLoan(Loan loan){
		assert(applicationContext != null);
		this.addDisbursements(loan.getDisbursements());
		LoanRateValueRepository loanRateValueRepo = applicationContext.getBean(LoanRateValueRepository.class);
		this.addAllRateChanges(loanRateValueRepo.findAllLoanRateValues(loan.getLoanID()));
		LoanBalanceAdjustmentRepository loanBalanceAdjustmentRepo = applicationContext.getBean(LoanBalanceAdjustmentRepository.class);
		this.addAllAdjustments(loanBalanceAdjustmentRepo.findAllByLoanID(loan.getLoanID()));
		LoanPaymentRepository paymentRepo = applicationContext.getBean(LoanPaymentRepository.class);
		this.addAllPayments(paymentRepo.findAllLoanPayments(loan.getLoanID()));
		LateFeeRepository lateFeeRepository = applicationContext.getBean(LateFeeRepository.class);
		this.addAllLateFees(lateFeeRepository.findAllByLoanID(loan.getLoanID()));
	}
	
	public void buildLoanStateHistoryForLoanAsOf(Loan loan, Date asOfDate){
		assert(applicationContext != null);
		for(Disbursement disb:loan.getDisbursements()){
			if(!disb.getDisbursementEffectiveDate().after(asOfDate)){
				this.addDisbursement(disb);
			}
		}
		LoanRateValueRepository loanRateValueRepo = applicationContext.getBean(LoanRateValueRepository.class);
		this.addAllRateChanges(loanRateValueRepo.findAllLoanRateValuesThruDate(loan.getLoanID(), asOfDate));
		LoanBalanceAdjustmentRepository loanBalanceAdjustmentRepo = applicationContext.getBean(LoanBalanceAdjustmentRepository.class);
		this.addAllAdjustments(loanBalanceAdjustmentRepo.findAllForLoanThruDate(loan.getLoanID(), asOfDate));
		LoanPaymentRepository paymentRepo = applicationContext.getBean(LoanPaymentRepository.class);
		this.addAllPayments(paymentRepo.findAllLoanPaymentsForLoanEffectiveOnOrBefore(loan.getLoanID(), asOfDate));
		LateFeeRepository lateFeeRepository = applicationContext.getBean(LateFeeRepository.class);
		this.addAllLateFees(lateFeeRepository.findAllByLoanIDAssessedOnOrBefore(loan.getLoanID(), asOfDate));
	}
	
	public LoanStateHistory getLoanStateHistory(){
		return loanStateHistory;
	}
	
	public void addDisbursement(Disbursement disbursement){
		LoanState newState = new DisbursementLoanState(disbursement);
		this.addState(newState);
	}
	
	public void addDisbursements(Collection<Disbursement> disbursements){
		for(Disbursement disb:disbursements){
			this.addDisbursement(disb);
		}
	}
	
	public void addPayment(LoanPayment payment){
		LoanState newState = new PaymentLoanState(payment);
		this.addState(newState);
	}
	
	public void addLateFee(LateFee lateFee){
		LoanState newState = new LateFeeState(lateFee);
		this.addState(newState);
	}
	
	public void addAllLateFees(Collection<LateFee> lateFees){
		for(LateFee lateFee:lateFees){
			this.addLateFee(lateFee);
		}
	}
	
	public void addAllPayments(Collection<LoanPayment> payments){
		for(LoanPayment payment:payments){
			this.addPayment(payment);
		}
	}
	
	public void addAdjustment(LoanBalanceAdjustment adj){
		LoanState newState = new BalanceAdjustmentLoanState(adj);
		this.addState(newState);
	}
	
	public void addAllAdjustments(Collection<LoanBalanceAdjustment> adjs){
		for(LoanBalanceAdjustment adj:adjs){
			this.addAdjustment(adj);
		}
	}
	
	public void addRateChange(LoanRateValue loanRate){
		LoanState newState = new RateValueLoanState(loanRate);
		this.addState(newState);
	}
	
	public void addAllRateChanges(Collection<LoanRateValue> loanRates){
		for(LoanRateValue rate:loanRates){
			this.addRateChange(rate);
		}
	}

	private void addState(LoanState newState){
		loanStateHistory.getLoanStates().add(newState);
		Collections.sort(loanStateHistory.getLoanStates(), new Comparator<LoanState>(){
				@Override
				public int compare(LoanState arg0, LoanState arg1) {
					if(arg0 == arg1){return 0;}
					int compareValue = arg0.getStateEffectiveDate().compareTo(arg1.getStateEffectiveDate());
					if(compareValue == 0){
						if(arg0 instanceof PaymentLoanState && arg1 instanceof PaymentLoanState){
							compareValue = arg0.getStatePostDate().compareTo(arg0.getStatePostDate());
						}
						else if(arg0 instanceof PaymentLoanState){compareValue = 1;}
						else if(arg1 instanceof PaymentLoanState){compareValue = -1;}
					}
					return compareValue;
				}
			});
		int stateIndex = loanStateHistory.getLoanStates().indexOf(newState);
		if((stateIndex+1) < loanStateHistory.getLoanStates().size()){
			LoanState nextState = loanStateHistory.getLoanStates().get(stateIndex+1);
			nextState.setPreviousLoanState(newState);
		}
		if(stateIndex > 0){
			LoanState previousState = loanStateHistory.getLoanStates().get(stateIndex-1);
			newState.setPreviousLoanState(previousState);
		}
	}
}