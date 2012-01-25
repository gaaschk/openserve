create table LoanEventType(
	LoanEventTypeID BIGINT PRIMARY KEY,
	LoanEventName VARCHAR(25),
	LoanEventDesc VARCHAR(75)
);

create table LoanEvent(
	LoanEventID BIGINT PRIMARY KEY AUTO_INCREMENT,
	LoanEventTypeID BIGINT,
	LoanTransactionID BIGINT,
	LoanID BIGINT,
	EffectiveDate DATE,
	PostDate DATETIME
);

create table LoanTransaction(
	LoanTransactionID BIGINT PRIMARY KEY AUTO_INCREMENT,
	InterestAccrued DECIMAL(20,6),
	InterestPaid DECIMAL(20,6),
	PrincipalChange INTEGER,
	InterestChange DECIMAL(20,6),
	FeesChange INTEGER,
	EndingPrincipal INTEGER,
	EndingInterest DECIMAL(20,6),
	EndingFees INTEGER
);

ALTER TABLE LoanEvent
ADD FOREIGN KEY LoanEvent_LoanEventType_FK (LoanEventTypeID) REFERENCES LoanEventType(LoanEventTypeID);

ALTER TABLE LoanEvent
ADD FOREIGN KEY LoanEvent_Loan_FK (LoanID) REFERENCES Loan(LoanID);

ALTER TABLE LoanEvent
ADD FOREIGN KEY LoanEvent_LoanTransaction_FK (LoanTransactionID) REFERENCES LoanTransaction(LoanTransactionID);

INSERT INTO LoanEventType values (10, 'LOAN_ADDED', 'Loan added');
INSERT INTO LoanEventType values (20, 'PAYMENT_APPLIED', 'Payment Applied to Loan');