create table LoanType(
	LoanTypeID BIGINT PRIMARY KEY,
	Name VARCHAR(15),
	Description VARCHAR(45)
);

alter table Loan add column LoanTypeID BIGINT after LoanID;

ALTER TABLE Loan
ADD FOREIGN KEY Loan_LoanType_FK (LoanTypeID) REFERENCES LoanType(LoanTypeID);

INSERT INTO LoanType values (10, 'PRIVATE_STUDENT', 'Private Student Loan'); 
INSERT INTO LoanType values (20, 'MORTGAGE', 'Mortgage Loan');
