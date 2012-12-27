alter table loan change column RemainingLoanTerm StartingLoanTerm INT after StartingFees;
alter table loan change column NextDueDate CurrentUnpaidDueDate DATE after LastPaidDate;