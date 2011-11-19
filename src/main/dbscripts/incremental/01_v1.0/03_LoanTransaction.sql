create table LoanEventType(
	LoanEventTypeID BIGINT PRIMARY KEY,
	LoanEventName VARCHAR(15),
	LoanEventDesc VARCHAR(45)
);

create table LoanEvent(
	LoanEventID BIGINT PRIMARY KEY AUTO_INCREMENT,
	LoanEventTypeID BIGINT,
	LoanTransactionID BIGINT,
	LoanID BIGINT,
	EffectiveDate DATETIME,
	PostDate DATETIME
);

create table LoanTransaction(
	LoanTransactionID BIGINT PRIMARY KEY AUTO_INCREMENT,
	InterestAccrued FLOAT,
	InterestPaid FLOAT,
	PrincipalChange INTEGER,
	InterestChange FLOAT,
	FeesChange INTEGER,
	EndingPrincipal INTEGER,
	EndingInterest FLOAT,
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