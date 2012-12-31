package org.gsoft.openserv.domain.loan;

import java.math.BigDecimal;
import java.util.Date;

import org.gsoft.openserv.util.Constants;
import org.joda.time.DateTime;
import org.joda.time.Days;

public class LoanState {
	private Integer principal;
	private BigDecimal interest;
	private Integer fees;
	private BigDecimal interestRate;
	private Date stateDate;

	public LoanState(Integer principal, BigDecimal interest, Integer fees,
			BigDecimal interestRate, Date stateDate) {
		super();
		this.principal = (principal==null)?0:principal;
		this.interest = (interest==null)?BigDecimal.ZERO:interest;
		this.fees = (fees==null)?0:fees;
		this.interestRate = (interestRate==null)?BigDecimal.ZERO:interestRate;
		this.stateDate = stateDate;
	}

	public Integer getPrincipal() {
		return principal;
	}
	public BigDecimal getInterest() {
		return interest;
	}
	public Integer getFees() {
		return fees;
	}
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public Date getStateDate() {
		return stateDate;
	}
	
	public void adjustPrincipal(Integer amount, Date effectiveDate){
		this.accrueInterestThru(effectiveDate);
		principal += amount;
	}
	
	public void adjustInterest(BigDecimal amount, Date effectiveDate){
		this.accrueInterestThru(effectiveDate);
		interest = interest.add(amount);
	}

	public void adjustFees(Integer amount, Date effectiveDate){
		this.accrueInterestThru(effectiveDate);
		fees += amount;
	}

	public void setInterestRate(BigDecimal newRate, Date effectiveDate){
		this.accrueInterestThru(effectiveDate);
		this.interestRate = newRate;
	}
	
	public Integer applyPayment(Integer amount, Date effectiveDate){
		this.accrueInterestThru(effectiveDate);
		if(amount >= fees){
			amount -= fees; 
			fees = 0;
		}
		else{
			fees -= amount;
			amount = 0;
		}
		Integer intInterest = interest.intValue();
		if(amount >= intInterest){
			amount -= intInterest;
			interest.subtract(BigDecimal.valueOf(intInterest));
		}
		else{
			interest.subtract(BigDecimal.valueOf(amount));
			amount = 0;
		}
		if(amount >= principal){
			amount -= principal;
			principal = 0;
		}
		else{
			principal -= amount;
			amount = 0;
		}
		return amount;
	}
	
	public void accrueInterestThru(Date asOfDate){
		BigDecimal dailyIntRate = interestRate.divide(BigDecimal.valueOf(Constants.DAYS_IN_YEAR),Constants.INTEREST_ROUNDING_SCALE_35, Constants.INTEREST_ROUNDING_MODE);
		int daysDiff = Days.daysBetween(new DateTime(stateDate), new DateTime(asOfDate)).getDays();
		interest = interest.add(dailyIntRate.multiply(BigDecimal.valueOf(principal*daysDiff)));
		stateDate = asOfDate;
	}
}
