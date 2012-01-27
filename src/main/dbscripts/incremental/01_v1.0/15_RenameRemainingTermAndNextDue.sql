alter table loan change column RemainingLoanTerm StartingLoanTerm INT after Margin;
alter table loan change column NextDueDate CurrentUnpaidDueDate DATE after LastPaidDate;