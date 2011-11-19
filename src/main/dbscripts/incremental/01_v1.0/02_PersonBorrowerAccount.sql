create table Person(
	PersonID BIGINT PRIMARY KEY AUTO_INCREMENT,
	SSN Char(9) NOT NULL,
	FirstName VarChar(15) NOT NULL,
	LastName VarChar(35) NOT NULL
	);

alter table Loan add column BorrowerPersonID BIGINT after LoanID;

ALTER TABLE Loan
ADD FOREIGN KEY Loan_Borrower_FK (BorrowerPersonID) REFERENCES Person(PersonID);
