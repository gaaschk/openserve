create table AmortizationSchedule(
	AmortizationScheduleID BIGINT PRIMARY KEY AUTO_INCREMENT,
	CreationDate DATETIME,
	EffectiveDate DATETIME
);

create table LoanAmortizationSchedule(
	LoanAmortizationScheduleID BIGINT PRIMARY KEY AUTO_INCREMENT,
	LoanID BIGINT,
	AmortizationScheduleID BIGINT
);

create table AmortizationLoanPayment(
	AmortizationLoanPaymentID BIGINT PRIMARY KEY AUTO_INCREMENT,
	LoanAmortizationScheduleID BIGINT,
	PaymentAmount INTEGER,
	PaymentCount INTEGER
);

ALTER TABLE LoanAmortizationSchedule
ADD FOREIGN KEY LoanAmortizationSchedule_AmortizationSchedule_FK(AmortizationScheduleID) REFERENCES AmortizationSchedule(AmortizationScheduleID);

ALTER TABLE LoanAmortizationSchedule
ADD FOREIGN KEY LoanAmortizationSchedule_Loan_FK(LoanID) REFERENCES Loan(LoanID);

ALTER TABLE AmortizationLoanPayment
ADD FOREIGN KEY AmortizationLoanPayment_LoanAmortizationSchedule_FK(LoanAmortizationScheduleID) REFERENCES LoanAmortizationSchedule(LoanAmortizationScheduleID);

alter table Loan add column RemainingLoanTerm INTEGER after Margin;
