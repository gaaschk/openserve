create table Payment(
	PaymentID BIGINT PRIMARY KEY AUTO_INCREMENT,
	BorrowerPersonID BIGINT,
	PaymentAmount INTEGER,
	PostDate DATETIME,
	EffectiveDate DATE
);

create table LoanPayment(
	LoanPaymentID BIGINT PRIMARY KEY AUTO_INCREMENT,
	PaymentID BIGINT,
	LoanID BIGINT,
	AppliedAmount INTEGER
);

ALTER TABLE Payment
ADD FOREIGN KEY Payment_BorrowerPerson_FK(BorrowerPersonID) REFERENCES Person(PersonID);

ALTER TABLE LoanPayment
ADD FOREIGN KEY LoanPayment_Payment_FK (PaymentID) REFERENCES Payment(PaymentID);

ALTER TABLE LoanPayment
ADD FOREIGN KEY LoanPayment_Loan_FK (LoanID) REFERENCES Loan(LoanID);
