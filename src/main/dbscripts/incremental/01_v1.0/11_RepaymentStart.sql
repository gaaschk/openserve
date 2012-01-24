create table RepaymentStartType(
	RepaymentStartTypeID BIGINT PRIMARY KEY,
	Name VARCHAR(25),
	Description VARCHAR(50)
);

alter table LoanTypeProfile add column RepaymentStartTypeID BIGINT after EndDate;

insert into RepaymentStartType values (10, 'FIRST_DISBURSEMENT', 'Repayment begins after first disbursement.');
insert into RepaymentStartType values (20, 'LAST_DISBURSEMENT', 'Repayment begins after last disbursement.');

insert into LoanEventType values (30, 'DISBURSEMENT_ADDED', 'Disbursement Added to Loan.');
insert into LoanEventType values (40, 'LOAN_ADD_ADJUSTMENT', 'Initial adjustment made to balances at loan add.');
