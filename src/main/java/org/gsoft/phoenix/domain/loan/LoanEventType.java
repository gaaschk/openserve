package org.gsoft.phoenix.domain.loan;

import org.gsoft.phoenix.util.jpa.PhoenixEnum;

public enum LoanEventType implements PhoenixEnum<LoanEventType> {
	LOAN_ADDED(10L, "LOAN_ADDED", "Loan Added", true),
	PAYMENT_APPLIED(20L, "PAYMENT_APPLIED", "Payment Applied to Loan", false),
	DISBURSEMENT_ADDED(30L, "DISBURSMENT_ADDED", "Disbursement Added to Loan", true),
	//this is used if the starting balances don't equal the sum of the disbursement amounts
	LOAN_ADD_ADJUSTMENT(40L, "LOAN_ADD_ADJUSTMENT", "Initial adjustment made to balances at loan add.", true),
	LATE_FEE_ASSESSED(50L, "LATE_FEE_ASSESSED", "Assessed a late fee on a loan.", true),
	LATE_FEE_CANCELLED(60L, "LATE_FEE_CANCELLED", "Cancelled a late fee on a loan.", true);
	

	private Long loanEventTypeID;
	private String loanEventName;
	private String loanEventDesc;
	private boolean isFixedAllocation = false;

	LoanEventType(Long loanEventTypeID, String loanEventName,
			String loanEventDesc, boolean isFixedAllocation) {
		this.setLoanEventTypeID(loanEventTypeID);
		this.setLoanEventName(loanEventName);
		this.setLoanEventDesc(loanEventDesc);
		this.setFixedAllocation(isFixedAllocation);
	}

	public Long getLoanEventTypeID() {
		return loanEventTypeID;
	}

	public void setLoanEventTypeID(Long loanEventTypeID) {
		this.loanEventTypeID = loanEventTypeID;
	}

	public String getLoanEventName() {
		return loanEventName;
	}

	public void setLoanEventName(String loanEventName) {
		this.loanEventName = loanEventName;
	}

	public String getLoanEventDesc() {
		return loanEventDesc;
	}

	public void setLoanEventDesc(String loanEventDesc) {
		this.loanEventDesc = loanEventDesc;
	}

	public boolean isFixedAllocation() {
		return isFixedAllocation;
	}

	public void setFixedAllocation(boolean isFixedAllocation) {
		this.isFixedAllocation = isFixedAllocation;
	}

	public Long getID() {
		return this.getLoanEventTypeID();
	}

	public static LoanEventType forID(Long id) {
		for (final LoanEventType loanEventType : values()) {
			if (loanEventType.getID().equals(id)) {
				return loanEventType;
			}
		}
		return null;
	}
	
	public boolean allocateToPrincipal(){
		return this == LoanEventType.PAYMENT_APPLIED;
	}
	public boolean allocateToInterest(){
		return this == LoanEventType.PAYMENT_APPLIED;
	}
	public boolean allocateToFees(){
		return this == LoanEventType.PAYMENT_APPLIED;
	}
}
