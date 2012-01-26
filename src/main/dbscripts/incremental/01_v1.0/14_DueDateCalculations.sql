alter table LoanTypeProfile add column GraceMonths INT after MaximumLoanTerm;
alter table LoanTypeProfile add column MinDaysToFirstDue INT after GraceMonths;
alter table LoanTypeProfile add column PrepaymentDays INT after MinDaysToFirstDue;

alter table Loan add column FirstDueDate DATE after RepaymentStartDate;
alter table Loan add column InitialDueDate DATE after FirstDueDate;
alter table Loan add column LastPaidDate Date after FirstDueDate;
alter table Loan add column NextDueDate Date after LastPaidDate;

update LoanTypeProfile set RepaymentStartTypeID = 10, GraceMonths = 3, MinDaysToFirstDue = 45, PrepaymentDays = 10 where LoanTypeID = 10;
update LoanTypeProfile set RepaymentStartTypeID = 20, GraceMonths = 6, MinDaysToFirstDue = 45, PrepaymentDays = 10 where LoanTypeID = 20;
