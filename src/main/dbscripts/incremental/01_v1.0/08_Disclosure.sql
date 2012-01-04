create table Disclosure(
	DisclosureID BIGINT PRIMARY KEY AUTO_INCREMENT,
	LoanID BIGINT,
	DisclosureDate DATETIME,
	EffectiveDate DATETIME
);

create table DisclosurePayment(
	DisclosurePaymentID BIGINT PRIMARY KEY AUTO_INCREMENT,
	DisclosureID BIGINT,
	PaymentAmount INTEGER,
	PaymentCount INTEGER
);

create table LoanTypeProfile(
	LoanTypeProfile BIGINT PRIMARY KEY AUTO_INCREMENT,
	LoanTypeID BIGINT,
	EffectiveDate DATETIME,
	EndDate DATETIME,
	MaximumLoanTerm INTEGER
);

ALTER TABLE Disclosure
ADD FOREIGN KEY Disclosure_Loan_FK(LoanID) REFERENCES Loan(LoanID);

ALTER TABLE DisclosurePayment
ADD FOREIGN KEY DisclosurePayment_Disclosure_FK(DisclosureID) REFERENCES Disclosure(DisclosureID);

alter table Loan add column RemainingLoanTerm INTEGER after Margin;

ALTER TABLE LoanTypeProfile
ADD FOREIGN KEY LoanTypeProfile_LoanType_FK(LoanTypeID) REFERENCES LoanType(LoanTypeID);
