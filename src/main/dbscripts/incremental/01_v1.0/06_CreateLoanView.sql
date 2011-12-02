create view LoanView as (
select l.*, lt.EndingPrincipal, lt.EndingInterest, lt.EndingFees 
from loan l 
inner join LoanEvent le on l.LoanID = le.LoanID 
inner join LoanTransaction lt on le.LoanTransactionID = lt.LoanTransactionID 
where le.EffectiveDate = (select max(le_sub.effectiveDate) from loanEvent le_sub where le_sub.loanid = le.loanid)
);
