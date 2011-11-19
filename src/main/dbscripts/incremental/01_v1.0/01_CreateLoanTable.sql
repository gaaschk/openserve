create table Loan(
	LoanID BIGINT PRIMARY KEY AUTO_INCREMENT,
	StartingPrincipal INTEGER NOT NULL,
	StartingInterest FLOAT NOT NULL,
	StartingFees INTEGER NOT NULL,
	CurrentPrincipal INTEGER,
	CurrentInterest FLOAT,
	CurrentFees INTEGER,
	Margin FLOAT NOT NULL
);