package org.gsoft.phoenix.buslogic.payment;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gsoft.phoenix.buslogic.system.SystemSettingsLogic;
import org.gsoft.phoenix.domain.loan.Loan;
import org.gsoft.phoenix.domain.loan.LoanTypeProfile;
import org.gsoft.phoenix.domain.payment.BillPayment;
import org.gsoft.phoenix.domain.payment.BillingStatement;
import org.gsoft.phoenix.domain.payment.LoanPayment;
import org.gsoft.phoenix.repositories.loan.LoanRepository;
import org.gsoft.phoenix.repositories.loan.LoanTypeProfileRepository;
import org.gsoft.phoenix.repositories.payment.BillPaymentRepository;
import org.gsoft.phoenix.repositories.payment.BillingStatementRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class BillingStatementLogic {
	@Resource
	private BillingStatementRepository billingStatementRepository;
	@Resource
	private LoanTypeProfileRepository loanTypeProfileRepository;
	@Resource
	private LoanRepository loanRepository;
	@Resource
	private SystemSettingsLogic systemSettings;
	@Resource
	private BillPaymentRepository billPaymentRepository;
	
	public BillingStatement createBillingStatement(Loan loan){
		LoanTypeProfile ltp = loanTypeProfileRepository.findOne(loan.getEffectiveLoanTypeProfileID());
		BillingStatement lastStatement = billingStatementRepository.findMostRecentBillingStatementForLoan(loan.getLoanID());
		DateTime dueDateMinusWindow = new DateTime(loan.getCurrentUnpaidDueDate()).minusDays(ltp.getDaysBeforeDueToBill());
		DateTime sysDate = new DateTime(systemSettings.getCurrentSystemDate());
		if(!dueDateMinusWindow.isAfter(sysDate) && (lastStatement == null || lastStatement.getDueDate().before(loan.getNextDueDate()))){
			Date dueDate = null;
			if(lastStatement == null)
				dueDate = loan.getCurrentUnpaidDueDate();
			else
				dueDate = new DateTime(lastStatement.getDueDate()).plusMonths(1).toDate();
			BillingStatement statement = new BillingStatement();
			statement.setLoanID(loan.getLoanID());
			statement.setCreatedDate(new Date());
			statement.setDueDate(dueDate);
			statement.setMinimumRequiredPayment(loan.getMinimumPaymentAmount());
			statement = billingStatementRepository.save(statement);
			lastStatement = statement;
		}
		return lastStatement;
	}
	
	public void applyPayment(LoanPayment payment){
		Loan loan = loanRepository.findOne(payment.getLoanID());
		LoanTypeProfile ltp = loanTypeProfileRepository.findOne(loan.getEffectiveLoanTypeProfileID());
		DateTime checkDate = new DateTime(payment.getPayment().getEffectiveDate()).plusDays(ltp.getPrepaymentDays());
		List<BillingStatement> unpaidStatements = billingStatementRepository.findAllUnpaidBillsForLoanWithDueDateOnOrBefore(payment.getLoanID(), checkDate.toDate());
		int amountRemainingToApply = payment.getAppliedAmount();
		BillPayment lastUpdatedPayment = null;
		for(BillingStatement statement:unpaidStatements){
			List<BillPayment> billPayments = statement.getBillPayments();
			int unpaidAmount = statement.getMinimumRequiredPayment() - statement.getPaidAmount();
			int amountToApply = 0;
			//this should always be > 0 because it's built into the query
			//but checking here any for posterity
			if(unpaidAmount > 0){
				amountToApply = (unpaidAmount > amountRemainingToApply)?amountRemainingToApply:unpaidAmount;
				BillPayment billPayment = new BillPayment();
				billPayments.add(billPayment);
				billPayment.setBillingStatement(statement);
				billPayment.setLoanPayment(payment);
				billPayment.setBillingStatement(statement);
				billPayment = billPaymentRepository.save(billPayment);
				billPayment.setAmountAppliedToBill(amountToApply);
				lastUpdatedPayment = billPayment;
				statement.setPaidAmount(statement.getPaidAmount()+amountToApply);
				if(statement.getPaidAmount() >= statement.getMinimumRequiredPayment())
					statement.setSatisfiedDate(payment.getPayment().getEffectiveDate());
			}	
			amountRemainingToApply -= amountToApply;
			if(amountRemainingToApply<=0)break;
		}
		if(lastUpdatedPayment != null && amountRemainingToApply > 0){
			lastUpdatedPayment.setAmountAppliedToBill(lastUpdatedPayment.getAmountAppliedToBill()+amountRemainingToApply);
			lastUpdatedPayment.getBillingStatement().setPaidAmount(lastUpdatedPayment.getBillingStatement().getPaidAmount() + amountRemainingToApply);
		}
		else{
			//apply anything remaining to the most recent bill
			//need to recheck the satisfied date to ensure that it
			//is updated to the earliest payment that meets the minimum
			//amount.
			BillingStatement statement = billingStatementRepository.findMostRecentBillForLoanWithDueDateOnOrBefore(payment.getLoanID(), checkDate.toDate());
			if(statement != null && amountRemainingToApply > 0){
				List<BillPayment> billPayments = statement.getBillPayments();
				BillPayment newPayment = new BillPayment();
				billPayments.add(newPayment);
				newPayment.setBillingStatement(statement);
				newPayment.setLoanPayment(payment);
				newPayment = billPaymentRepository.save(newPayment);
				newPayment.setAmountAppliedToBill(amountRemainingToApply);
				statement.setPaidAmount(statement.getPaidAmount() + amountRemainingToApply);
				Collections.sort(billPayments, new BillPaymentComparator());
				int paidAmount = 0;
				for(BillPayment billPayment:billPayments){
					paidAmount += billPayment.getAmountAppliedToBill();
					if(paidAmount >= statement.getMinimumRequiredPayment()){
						statement.setSatisfiedDate(billPayment.getLoanPayment().getPayment().getEffectiveDate());
						break;
					}
				}
			}
		}
	}
	
	private class BillPaymentComparator implements Comparator<BillPayment>{

		@Override
		public int compare(BillPayment arg0, BillPayment arg1) {
			if(arg0 == arg1 || arg0.getLoanPayment() == arg1.getLoanPayment())return 0;
			if(arg0 == null || arg0.getLoanPayment() == null)return -1;
			if(arg1 == null || arg1.getLoanPayment() == null)return 1;
			if(arg0.getLoanPayment().getPayment().getEffectiveDate().equals(arg1.getLoanPayment().getPayment().getEffectiveDate())){
				return arg0.getLoanPayment().getLoanPaymentID().compareTo(arg1.getLoanPayment().getLoanPaymentID());
			}
			return arg0.getLoanPayment().getPayment().getEffectiveDate().compareTo(arg1.getLoanPayment().getPayment().getEffectiveDate());
		}
		
	}
}
