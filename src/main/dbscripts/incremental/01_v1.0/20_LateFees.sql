create table LateFee(
	LateFeeID BIGINT PRIMARY KEY AUTO_INCREMENT,
	BillingStatementID BIGINT,
	LoanEventID BIGINT,
	CancelledLoanEventID BIGINT,
	FeeAmount INT
);

ALTER TABLE LateFee
ADD FOREIGN KEY LateFee_BillingStatement_FK (BillingStatementID) REFERENCES BillingStatement(BillingStatementID);

ALTER TABLE LateFee
ADD FOREIGN KEY LateFee_LoanEvent_FK (LoanEventID) REFERENCES LoanEvent(LoanEventID);

alter table LoanTypeProfile add column DaysLateForFee INT;

update LoanTypeProfile set DaysLateForFee = 10;

alter table LoanTypeProfile add column LateFeeAmount INT;

update LoanTypeProfile set LateFeeAmount = 2500;

insert into LoanEventType values (50, 'LATE_FEE_ASSESSED', 'Assessed a late fee on a loan.');

insert into LoanEventType values (60, 'LATE_FEE_CANCELLED', 'Cancelled a late fee on a loan.');
