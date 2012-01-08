create table LoanTypeProfile(
	LoanTypeProfileID BIGINT PRIMARY KEY AUTO_INCREMENT,
	LoanTypeID BIGINT,
	EffectiveDate DATETIME,
	EndDate DATETIME,
	MaximumLoanTerm INTEGER
);

ALTER TABLE LoanTypeProfile
ADD FOREIGN KEY LoanTypeProfile_LoanType_FK(LoanTypeID) REFERENCES LoanType(LoanTypeID);

alter table loan add column EffectiveLoanTypeProfileID BIGINT after borrowerPersonID;

alter table loan 
add foreign key Loan_LoanTypeProfile(effectiveLoanTypeProfileID) references LoanTypeProfile(LoanTypeProfileID);

insert into LoanTypeProfile (LoanTypeID, EffectiveDate, EndDate, MaximumLoanTerm) values (10, "1900-01-01", null, 180);
insert into LoanTypeProfile (LoanTypeID, EffectiveDate, EndDate, MaximumLoanTerm) values (20, "1900-01-01", null, 360);
