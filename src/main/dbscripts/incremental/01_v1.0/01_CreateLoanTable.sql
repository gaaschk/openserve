#just a comment to force a database rebuild.
#Touch this comment to force a rebuild. touch--
create table Loan(
	LoanID  BIGINT PRIMARY KEY AUTO_INCREMENT,
	StartingPrincipal INTEGER NOT NULL,
	StartingInterest DECIMAL(20,6) NOT NULL,
	StartingFees INTEGER NOT NULL,
	Margin DECIMAL(20,6) NOT NULL
);