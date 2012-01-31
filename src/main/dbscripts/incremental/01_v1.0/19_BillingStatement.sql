create table BillingStatement(
	BillingStatementID BIGINT primary key auto_increment,
	LoanID BIGINT,
	CreatedDate DATETIME,
	DueDate DATE,
	MinimumRequiredPayment INT,
	PaidAmount INT,
	SatisfiedDate DATE
);

create table BillPayment(
	BillPaymentID BIGINT primary key auto_increment,
	BillingStatementID BIGINT,
	LoanPaymentID BIGINT,
	AmountAppliedToBill INT
);

ALTER TABLE BillingStatement
ADD FOREIGN KEY BillingStatement_Loan_FK (LoanID) REFERENCES Loan(LoanID);

ALTER TABLE BillPayment
ADD FOREIGN KEY BillPayment_BillingStatement_FK (BillingStatementID) REFERENCES BillingStatement(BillingStatementID);

ALTER TABLE BillPayment
ADD FOREIGN KEY BillPayment_LoanPayment_FK (LoanPaymentID) REFERENCES LoanPayment(LoanPaymentID);

alter table LoanTypeProfile add column DaysBeforeDueToBill INT after PrepaymentDays;

alter table SystemSettings add column TriggerBatch SMALLINT;

update LoanTypeProfile set DaysBeforeDueToBill = 20;