package org.gsoft.phoenix.domain.loan;

import org.gsoft.phoenix.util.jpa.PhoenixEnum;

public enum LoanEventType implements PhoenixEnum<LoanEventType> {
	LOAN_ADDED(10L, "LOAN_ADDED", "Loan Added"), PAYMENT_APPLIED(20L,
			"PAYMENT_APPLIED", "Payment Applied to Loan");

	private Long loanEventTypeID;
	private String loanEventName;
	private String loanEventDesc;

	LoanEventType(Long loanEventTypeID, String loanEventName,
			String loanEventDesc) {
		this.setLoanEventTypeID(loanEventTypeID);
		this.setLoanEventName(loanEventName);
		this.setLoanEventDesc(loanEventDesc);
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
}
