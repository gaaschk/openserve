create table Loan(
	LoanID BIGINT PRIMARY KEY AUTO_INCREMENT,
	StartingPrincipal INTEGER NOT NULL,
	StartingInterest FLOAT NOT NULL,
	StartingFees INTEGER NOT NULL,
	Margin FLOAT NOT NULL
);